package org.apache.commons.lang3.time;

import static org.junit.Assert.*;
import org.junit.Test;
import java.text.DateFormat;
import java.text.Format;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Additional tests for FormatCache targeting uncovered branches.
 * Tests the abstract FormatCache through FastDateFormat concrete implementation.
 * Focuses on concurrent access, null handling, timezone/locale variations, and caching behavior.
 */
public class FormatCacheAdditionalTest {

    @Test
    public void testGetInstanceWithDefaultParameters() {
        // Test getInstance() with no parameters - uses defaults
        final FastDateFormat format = FastDateFormat.getInstance();
        assertNotNull(format);
        // Should be cached - calling again should return same instance
        final FastDateFormat format2 = FastDateFormat.getInstance();
        assertSame(format, format2);
    }

    @Test(expected = NullPointerException.class)
    public void testGetInstanceWithNullPattern() {
        // Null pattern should throw NullPointerException
        FastDateFormat.getInstance(null, TimeZone.getDefault(), Locale.getDefault());
    }

    @Test
    public void testGetInstanceWithNullTimeZone() {
        // Null timezone should use default
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd", null, Locale.getDefault());
        assertNotNull(format);
        assertEquals(TimeZone.getDefault(), format.getTimeZone());
    }

    @Test
    public void testGetInstanceWithNullLocale() {
        // Null locale should use default
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getDefault(), null);
        assertNotNull(format);
        assertEquals(Locale.getDefault(), format.getLocale());
    }

    @Test
    public void testGetInstanceCaching() {
        // Same pattern, timezone, locale should return cached instance
        final String pattern = "yyyy-MM-dd HH:mm:ss";
        final TimeZone tz = TimeZone.getTimeZone("UTC");
        final Locale locale = Locale.US;
        
        final FastDateFormat format1 = FastDateFormat.getInstance(pattern, tz, locale);
        final FastDateFormat format2 = FastDateFormat.getInstance(pattern, tz, locale);
        
        assertSame("Should return cached instance", format1, format2);
    }

    @Test
    public void testGetInstanceDifferentPatterns() {
        // Different patterns should create different instances
        final FastDateFormat format1 = FastDateFormat.getInstance("yyyy-MM-dd");
        final FastDateFormat format2 = FastDateFormat.getInstance("yyyy/MM/dd");
        
        assertNotSame("Different patterns should create different instances", format1, format2);
    }

    @Test
    public void testGetInstanceDifferentTimeZones() {
        // Different timezones should create different instances
        final String pattern = "yyyy-MM-dd HH:mm:ss";
        final FastDateFormat format1 = FastDateFormat.getInstance(pattern, TimeZone.getTimeZone("UTC"), Locale.US);
        final FastDateFormat format2 = FastDateFormat.getInstance(pattern, TimeZone.getTimeZone("EST"), Locale.US);
        
        assertNotSame("Different timezones should create different instances", format1, format2);
    }

    @Test
    public void testGetInstanceDifferentLocales() {
        // Different locales should create different instances
        final String pattern = "yyyy-MM-dd";
        final FastDateFormat format1 = FastDateFormat.getInstance(pattern, TimeZone.getDefault(), Locale.US);
        final FastDateFormat format2 = FastDateFormat.getInstance(pattern, TimeZone.getDefault(), Locale.UK);
        
        assertNotSame("Different locales should create different instances", format1, format2);
    }

    @Test
    public void testGetDateInstanceWithStyles() {
        // Test getDateInstance with various date styles
        final FastDateFormat shortFormat = FastDateFormat.getDateInstance(DateFormat.SHORT, TimeZone.getDefault(), Locale.US);
        final FastDateFormat mediumFormat = FastDateFormat.getDateInstance(DateFormat.MEDIUM, TimeZone.getDefault(), Locale.US);
        final FastDateFormat longFormat = FastDateFormat.getDateInstance(DateFormat.LONG, TimeZone.getDefault(), Locale.US);
        final FastDateFormat fullFormat = FastDateFormat.getDateInstance(DateFormat.FULL, TimeZone.getDefault(), Locale.US);
        
        assertNotNull(shortFormat);
        assertNotNull(mediumFormat);
        assertNotNull(longFormat);
        assertNotNull(fullFormat);
        
        // Different styles should create different instances
        assertNotSame(shortFormat, mediumFormat);
        assertNotSame(mediumFormat, longFormat);
        assertNotSame(longFormat, fullFormat);
    }

    @Test
    public void testGetTimeInstanceWithStyles() {
        // Test getTimeInstance with various time styles
        final FastDateFormat shortFormat = FastDateFormat.getTimeInstance(DateFormat.SHORT, TimeZone.getDefault(), Locale.US);
        final FastDateFormat mediumFormat = FastDateFormat.getTimeInstance(DateFormat.MEDIUM, TimeZone.getDefault(), Locale.US);
        final FastDateFormat longFormat = FastDateFormat.getTimeInstance(DateFormat.LONG, TimeZone.getDefault(), Locale.US);
        final FastDateFormat fullFormat = FastDateFormat.getTimeInstance(DateFormat.FULL, TimeZone.getDefault(), Locale.US);
        
        assertNotNull(shortFormat);
        assertNotNull(mediumFormat);
        assertNotNull(longFormat);
        assertNotNull(fullFormat);
        
        // Note: Some styles may resolve to the same pattern and thus same cached instance
        // At minimum, SHORT and FULL should differ
        assertNotSame(shortFormat, fullFormat);
    }

    @Test
    public void testGetDateTimeInstanceWithAllStyles() {
        // Test getDateTimeInstance with various combinations
        final FastDateFormat format = FastDateFormat.getDateTimeInstance(
            DateFormat.SHORT, DateFormat.SHORT, TimeZone.getDefault(), Locale.US);
        assertNotNull(format);
        
        final FastDateFormat format2 = FastDateFormat.getDateTimeInstance(
            DateFormat.LONG, DateFormat.MEDIUM, TimeZone.getDefault(), Locale.US);
        assertNotNull(format2);
        assertNotSame(format, format2);
    }

    @Test
    public void testConcurrentAccess() throws InterruptedException {
        // Test that concurrent access to getInstance properly handles cache
        final String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        final TimeZone tz = TimeZone.getTimeZone("GMT");
        final Locale locale = Locale.FRANCE;
        
        final int threadCount = 10;
        final CountDownLatch startLatch = new CountDownLatch(1);
        final CountDownLatch endLatch = new CountDownLatch(threadCount);
        final FastDateFormat[] results = new FastDateFormat[threadCount];
        
        final ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        
        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        startLatch.await(); // Wait for all threads to be ready
                        results[index] = FastDateFormat.getInstance(pattern, tz, locale);
                    } catch (final InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        endLatch.countDown();
                    }
                }
            });
        }
        
        startLatch.countDown(); // Start all threads simultaneously
        endLatch.await(5, TimeUnit.SECONDS);
        executor.shutdown();
        
        // All threads should get the same cached instance
        final FastDateFormat first = results[0];
        assertNotNull(first);
        for (int i = 1; i < threadCount; i++) {
            assertSame("Concurrent access should return same cached instance", first, results[i]);
        }
    }

    @Test
    public void testGetPatternForStyleWithNullDateStyle() {
        // Test pattern retrieval with null date style (time only)
        final String pattern = FormatCache.getPatternForStyle(null, DateFormat.SHORT, Locale.US);
        assertNotNull(pattern);
        // Pattern should not contain date elements
        assertFalse(pattern.contains("y") || pattern.contains("M") || pattern.contains("d"));
    }

    @Test
    public void testGetPatternForStyleWithNullTimeStyle() {
        // Test pattern retrieval with null time style (date only)
        final String pattern = FormatCache.getPatternForStyle(DateFormat.SHORT, null, Locale.US);
        assertNotNull(pattern);
        // Pattern should not contain time elements
        assertFalse(pattern.contains("H") || pattern.contains("m") || pattern.contains("s"));
    }

    @Test
    public void testGetPatternForStyleCaching() {
        // Same style parameters should return cached pattern
        final String pattern1 = FormatCache.getPatternForStyle(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
        final String pattern2 = FormatCache.getPatternForStyle(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
        assertSame("Should return cached pattern string", pattern1, pattern2);
    }

    @Test
    public void testGetPatternForStyleDifferentLocales() {
        // Different locales may have different patterns
        final String usPattern = FormatCache.getPatternForStyle(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
        final String ukPattern = FormatCache.getPatternForStyle(DateFormat.SHORT, DateFormat.SHORT, Locale.UK);
        final String frPattern = FormatCache.getPatternForStyle(DateFormat.SHORT, DateFormat.SHORT, Locale.FRANCE);
        
        assertNotNull(usPattern);
        assertNotNull(ukPattern);
        assertNotNull(frPattern);
        // Patterns may differ by locale (e.g., US uses MM/dd/yyyy, UK uses dd/MM/yyyy)
    }
}
