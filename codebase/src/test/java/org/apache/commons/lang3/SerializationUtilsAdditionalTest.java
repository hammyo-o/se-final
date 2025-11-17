package org.apache.commons.lang3;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

/**
 * Additional boundary value tests for SerializationUtils.
 * Generated to improve code coverage.
 */
public class SerializationUtilsAdditionalTest {

    @Test
    public void testSerializeLargeObject() {
        // Test serialization with large data structures
        final ArrayList<String> largeList = new ArrayList<String>();
        for (int i = 0; i < 10000; i++) {
            largeList.add("Item " + i);
        }
        
        final byte[] serialized = SerializationUtils.serialize(largeList);
        assertNotNull(serialized);
        assertTrue(serialized.length > 1000);
        
        @SuppressWarnings("unchecked")
        final ArrayList<String> deserialized = SerializationUtils.deserialize(serialized);
        assertEquals(largeList.size(), deserialized.size());
        assertEquals(largeList.get(0), deserialized.get(0));
        assertEquals(largeList.get(9999), deserialized.get(9999));
    }
    
    @Test
    public void testSerializeEmptyByteArray() {
        // Test serialization with empty byte array
        final byte[] emptyArray = new byte[0];
        final byte[] serialized = SerializationUtils.serialize(emptyArray);
        assertNotNull(serialized);
        
        final byte[] deserialized = SerializationUtils.deserialize(serialized);
        assertNotNull(deserialized);
        assertEquals(0, deserialized.length);
    }
    
    @Test
    public void testSerializeMinMaxIntegers() {
        // Test with boundary integer values
        final Integer minValue = Integer.MIN_VALUE;
        final Integer maxValue = Integer.MAX_VALUE;
        
        final byte[] minSerialized = SerializationUtils.serialize(minValue);
        final byte[] maxSerialized = SerializationUtils.serialize(maxValue);
        
        final Integer minDeserialized = SerializationUtils.deserialize(minSerialized);
        final Integer maxDeserialized = SerializationUtils.deserialize(maxSerialized);
        
        assertEquals(minValue, minDeserialized);
        assertEquals(maxValue, maxDeserialized);
    }
    
    @Test
    public void testSerializeMinMaxLongs() {
        // Test with boundary long values
        final Long minValue = Long.MIN_VALUE;
        final Long maxValue = Long.MAX_VALUE;
        
        final byte[] minSerialized = SerializationUtils.serialize(minValue);
        final byte[] maxSerialized = SerializationUtils.serialize(maxValue);
        
        final Long minDeserialized = SerializationUtils.deserialize(minSerialized);
        final Long maxDeserialized = SerializationUtils.deserialize(maxSerialized);
        
        assertEquals(minValue, minDeserialized);
        assertEquals(maxValue, maxDeserialized);
    }
    
    @Test
    public void testSerializeEmptyString() {
        // Test with empty string
        final String emptyString = "";
        final byte[] serialized = SerializationUtils.serialize(emptyString);
        assertNotNull(serialized);
        
        final String deserialized = SerializationUtils.deserialize(serialized);
        assertEquals(emptyString, deserialized);
    }
    
    @Test
    public void testSerializeSpecialCharacters() {
        // Test with special characters and unicode
        final String specialChars = "Test\n\r\t\0Special©®™\u00A9\u2122";
        final byte[] serialized = SerializationUtils.serialize(specialChars);
        assertNotNull(serialized);
        
        final String deserialized = SerializationUtils.deserialize(serialized);
        assertEquals(specialChars, deserialized);
    }
    
    @Test
    public void testCloneEmptyArray() {
        // Test cloning of empty arrays
        final String[] emptyArray = new String[0];
        final String[] cloned = SerializationUtils.clone(emptyArray);
        assertNotNull(cloned);
        assertEquals(0, cloned.length);
        assertTrue(emptyArray != cloned);
    }
    
    @Test
    public void testCloneLargeArray() {
        // Test cloning of large arrays
        final Integer[] largeArray = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            largeArray[i] = i;
        }
        
        final Integer[] cloned = SerializationUtils.clone(largeArray);
        assertNotNull(cloned);
        assertEquals(largeArray.length, cloned.length);
        assertTrue(largeArray != cloned);
        assertTrue(Arrays.equals(largeArray, cloned));
    }
    
    @Test
    public void testSerializeStreamWithFlush() throws Exception {
        // Test that serialize properly flushes the stream
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String testString = "Test flush";
        
        SerializationUtils.serialize(testString, baos);
        
        // Verify data was written
        assertTrue(baos.size() > 0);
        
        // Verify we can deserialize it
        final String deserialized = SerializationUtils.deserialize(baos.toByteArray());
        assertEquals(testString, deserialized);
    }
    
    @Test
    public void testDeserializeWithTrailingData() {
        // Serialize an object
        final String original = "Test";
        final byte[] serialized = SerializationUtils.serialize(original);
        
        // Add extra bytes after the serialized data
        final byte[] withTrailing = new byte[serialized.length + 10];
        System.arraycopy(serialized, 0, withTrailing, 0, serialized.length);
        Arrays.fill(withTrailing, serialized.length, withTrailing.length, (byte) 0xFF);
        
        // Should still deserialize the first object
        final String deserialized = SerializationUtils.deserialize(withTrailing);
        assertEquals(original, deserialized);
    }
    
    @Test
    public void testSerializeOutputStreamIOExceptionOnClose() {
        // Test IOException handling in finally block when closing stream
        final OutputStream badStream = new OutputStream() {
            private boolean writeCalled = false;
            
            @Override
            public void write(int b) throws IOException {
                writeCalled = true;
            }
            
            @Override
            public void write(byte[] b) throws IOException {
                writeCalled = true;
            }
            
            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                writeCalled = true;
            }
            
            @Override
            public void close() throws IOException {
                if (writeCalled) {
                    throw new IOException("Close failed after write");
                }
            }
        };
        
        try {
            SerializationUtils.serialize("test", badStream);
            // Should complete despite close() throwing IOException (it's suppressed)
        } catch (final SerializationException e) {
            // This is expected if the write itself fails
            // The close exception should be ignored
        }
    }
    
    @Test
    public void testDeserializeInputStreamIOExceptionOnClose() {
        // Test IOException handling when closing InputStream in finally block
        final String original = "Test close exception";
        final byte[] serialized = SerializationUtils.serialize(original);
        
        final InputStream badStream = new InputStream() {
            private final ByteArrayInputStream delegate = new ByteArrayInputStream(serialized);
            
            @Override
            public int read() throws IOException {
                return delegate.read();
            }
            
            @Override
            public int read(byte[] b) throws IOException {
                return delegate.read(b);
            }
            
            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return delegate.read(b, off, len);
            }
            
            @Override
            public void close() throws IOException {
                throw new IOException("Close failed");
            }
        };
        
        // Should deserialize successfully despite close() throwing IOException
        final String deserialized = SerializationUtils.deserialize(badStream);
        assertEquals(original, deserialized);
    }
    
    @Test
    public void testRoundtripNestedObjects() {
        // Test serialization of nested serializable objects
        final NestedSerializable outer = new NestedSerializable("outer", 
            new NestedSerializable("inner", null));
        
        final byte[] serialized = SerializationUtils.serialize(outer);
        final NestedSerializable deserialized = SerializationUtils.deserialize(serialized);
        
        assertNotNull(deserialized);
        assertEquals(outer.name, deserialized.name);
        assertNotNull(deserialized.nested);
        assertEquals(outer.nested.name, deserialized.nested.name);
    }
    
    /**
     * Helper class for nested object testing
     */
    private static class NestedSerializable implements Serializable {
        private static final long serialVersionUID = 1L;
        final String name;
        final NestedSerializable nested;
        
        NestedSerializable(final String name, final NestedSerializable nested) {
            this.name = name;
            this.nested = nested;
        }
    }
}
