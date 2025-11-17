package org.apache.commons.lang3.time;

import static org.junit.Assert.*;
import org.junit.Test;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Additional tests for FastDateFormat targeting uncovered code paths.
 * Focuses on format/parse edge cases, equals/hashCode, toString, and boundary conditions.
 */
public class FastDateFormatAdditionalTest {

    @Test
    public void testFormatWithStringBufferAndMilliseconds() {
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        final long millis = System.currentTimeMillis();
        final StringBuffer buf = new StringBuffer();
        final StringBuffer result = format.format(millis, buf);
        assertNotNull(result);
        assertTrue(result.length() > 0);
        assertSame(buf, result);
    }

    @Test
    public void testFormatWithStringBufferAndDate() {
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");
        final Date date = new Date();
        final StringBuffer buf = new StringBuffer();
        final StringBuffer result = format.format(date, buf);
        assertNotNull(result);
        assertTrue(result.length() > 0);
        assertSame(buf, result);
    }

    @Test
    public void testFormatWithStringBufferAndCalendar() {
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        final Calendar calendar = new GregorianCalendar();
        final StringBuffer buf = new StringBuffer();
        final StringBuffer result = format.format(calendar, buf);
        assertNotNull(result);
        assertTrue(result.length() > 0);
        assertSame(buf, result);
    }

    @Test
    public void testParseWithString() throws ParseException {
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");
        final Date parsed = format.parse("2020-01-15");
        assertNotNull(parsed);
        final Calendar cal = Calendar.getInstance();
        cal.setTime(parsed);
        assertEquals(2020, cal.get(Calendar.YEAR));
        assertEquals(Calendar.JANUARY, cal.get(Calendar.MONTH));
        assertEquals(15, cal.get(Calendar.DAY_OF_MONTH));
    }

    @Test
    public void testParseWithStringAndParsePosition() {
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");
        final ParsePosition pos = new ParsePosition(0);
        final Date parsed = format.parse("2020-05-20", pos);
        assertNotNull(parsed);
        assertTrue(pos.getIndex() > 0);
    }

    @Test
    public void testParseObjectWithStringAndParsePosition() {
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        final ParsePosition pos = new ParsePosition(0);
        final Object parsed = format.parseObject("2021-03-10 14:30:45", pos);
        assertNotNull(parsed);
        assertTrue(parsed instanceof Date);
        assertTrue(pos.getIndex() > 0);
    }

    @Test
    public void testEqualsWithSameInstance() {
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");
        assertTrue(format.equals(format));
    }

    @Test
    public void testEqualsWithDifferentPattern() {
        final FastDateFormat format1 = FastDateFormat.getInstance("yyyy-MM-dd");
        final FastDateFormat format2 = FastDateFormat.getInstance("dd-MM-yyyy");
        assertFalse(format1.equals(format2));
    }

    @Test
    public void testEqualsWithDifferentTimeZone() {
        final FastDateFormat format1 = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("UTC"));
        final FastDateFormat format2 = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("GMT"));
        assertFalse(format1.equals(format2));
    }

    @Test
    public void testEqualsWithDifferentLocale() {
        final FastDateFormat format1 = FastDateFormat.getInstance("yyyy-MM-dd", Locale.US);
        final FastDateFormat format2 = FastDateFormat.getInstance("yyyy-MM-dd", Locale.FRANCE);
        assertFalse(format1.equals(format2));
    }

    @Test
    public void testEqualsWithNonFastDateFormatObject() {
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd");
        assertFalse(format.equals("Not a FastDateFormat"));
        assertFalse(format.equals(null));
    }

    @Test
    public void testHashCodeConsistency() {
        final FastDateFormat format1 = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("UTC"), Locale.US);
        final FastDateFormat format2 = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("UTC"), Locale.US);
        assertEquals(format1.hashCode(), format2.hashCode());
    }

    @Test
    public void testToStringFormat() {
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("UTC"), Locale.US);
        final String str = format.toString();
        assertNotNull(str);
        assertTrue(str.startsWith("FastDateFormat["));
        assertTrue(str.contains("yyyy-MM-dd"));
        assertTrue(str.contains("UTC"));
    }

    @Test
    public void testGetMaxLengthEstimate() {
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
        final int estimate = format.getMaxLengthEstimate();
        assertTrue(estimate > 0);
        assertTrue(estimate >= 19); // "yyyy-MM-dd HH:mm:ss" is 19 chars
    }

    @Test
    public void testGetPatternReturnsCorrectValue() {
        final String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
        final FastDateFormat format = FastDateFormat.getInstance(pattern);
        assertEquals(pattern, format.getPattern());
    }

    @Test
    public void testGetTimeZoneReturnsCorrectValue() {
        final TimeZone tz = TimeZone.getTimeZone("America/New_York");
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd", tz);
        assertEquals(tz, format.getTimeZone());
    }

    @Test
    public void testGetLocaleReturnsCorrectValue() {
        final Locale locale = Locale.JAPAN;
        final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd", locale);
        assertEquals(locale, format.getLocale());
    }

    @Test
    public void testCachingBehaviorSameInstanceReturned() {
        final FastDateFormat format1 = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("UTC"), Locale.US);
        final FastDateFormat format2 = FastDateFormat.getInstance("yyyy-MM-dd", TimeZone.getTimeZone("UTC"), Locale.US);
        assertSame(format1, format2); // Should return same cached instance
    }
}
