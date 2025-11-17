package org.apache.commons.lang3.event;

import static org.junit.Assert.*;
import org.junit.Test;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

/**
 * Additional boundary value tests for EventUtils.
 * Generated to improve code coverage.
 */
public class EventUtilsAdditionalTest {

    @Test
    public void testBindEventsToMethodWithNoEventTypes() {
        final PropertyChangeSource src = new PropertyChangeSource();
        final TestTarget target = new TestTarget();
        
        // Bind with no specific event types (should support all)
        EventUtils.bindEventsToMethod(target, "handleEvent", src, PropertyChangeListener.class);
        
        assertNotNull("Listener should be bound", target);
        src.setProperty("testValue");
        assertEquals("Event should be handled", 1, target.getCallCount());
    }
    
    @Test
    public void testBindEventsToMethodWithSpecificEventTypes() {
        final PropertyChangeSource src = new PropertyChangeSource();
        final TestTarget target = new TestTarget();
        
        // Bind with specific event type
        EventUtils.bindEventsToMethod(target, "handleEvent", src, PropertyChangeListener.class, "propertyChange");
        
        assertNotNull("Listener should be bound", target);
        src.setProperty("testValue");
        assertEquals("Event should be handled", 1, target.getCallCount());
    }
    
    @Test
    public void testBindEventsToMethodWithMultipleEventTypes() {
        final PropertyChangeSource src = new PropertyChangeSource();
        final TestTarget target = new TestTarget();
        
        // Bind with multiple event types
        EventUtils.bindEventsToMethod(target, "handleEvent", src, PropertyChangeListener.class, 
                                      "propertyChange", "vetoableChange");
        
        assertNotNull("Listener should be bound", target);
        src.setProperty("testValue");
        assertEquals("Event should be handled", 1, target.getCallCount());
    }
    
    @Test
    public void testBindEventsToMethodWithNonMatchingEventType() {
        final PropertyChangeSource src = new PropertyChangeSource();
        final TestTarget target = new TestTarget();
        
        // Bind with event type that doesn't match any in the interface
        EventUtils.bindEventsToMethod(target, "handleEvent", src, PropertyChangeListener.class, "otherEvent");
        
        assertNotNull("Listener should be bound", target);
        src.setProperty("testValue");
        // handleEvent should not be called because "otherEvent" doesn't match "propertyChange"
        assertEquals("Event should not be handled for non-matching event type", 0, target.getCallCount());
    }
    
    @Test
    public void testAddEventListenerDirectly() {
        final PropertyChangeSource src = new PropertyChangeSource();
        final TestTarget target = new TestTarget();
        
        PropertyChangeListener listener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                target.handleEvent();
            }
        };
        
        EventUtils.addEventListener(src, PropertyChangeListener.class, listener);
        
        src.setProperty("testValue");
        assertEquals("Event should be handled", 1, target.getCallCount());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testAddEventListenerWithUnsupportedListenerType() {
        final PropertyChangeSource src = new PropertyChangeSource();
        
        // Try to add a listener type that PropertyChangeSource doesn't support
        EventUtils.addEventListener(src, UnsupportedListener.class, new UnsupportedListener() {
            @Override
            public void unsupportedMethod() {
                // Do nothing
            }
        });
    }
    
    @Test
    public void testBindEventsMultipleTimes() {
        final PropertyChangeSource src = new PropertyChangeSource();
        final TestTarget target1 = new TestTarget();
        final TestTarget target2 = new TestTarget();
        
        // Bind two different targets to same source
        EventUtils.bindEventsToMethod(target1, "handleEvent", src, PropertyChangeListener.class);
        EventUtils.bindEventsToMethod(target2, "handleEvent", src, PropertyChangeListener.class);
        
        src.setProperty("testValue");
        
        // Only the last registered listener should be notified (single listener support)
        assertEquals("First target event count", 0, target1.getCallCount());
        assertEquals("Second target event count", 1, target2.getCallCount());
    }
    
    // Helper classes
    public static class PropertyChangeSource {
        private PropertyChangeListener listener;
        
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            this.listener = listener;
        }
        
        public void setProperty(String value) {
            if (listener != null) {
                listener.propertyChange(new PropertyChangeEvent(this, "property", null, value));
            }
        }
    }
    
    public static class TestTarget {
        private int callCount = 0;
        
        public void handleEvent() {
            callCount++;
        }
        
        public int getCallCount() {
            return callCount;
        }
    }
    
    public interface UnsupportedListener {
        void unsupportedMethod();
    }
}
