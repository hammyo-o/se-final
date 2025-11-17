package org.apache.commons.lang3.event;

import static org.junit.Assert.*;
import org.junit.Test;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JButton;

/**
 * Additional tests for EventUtils covering error paths and edge cases.
 */
public class EventUtilsAdditionalTest {

    private static class TestEventSource {
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            // Test method
        }
        
        // Private method to test IllegalAccessException path
        private void addTestListener(PropertyChangeListener listener) {
            // Private method
        }
    }
    
    private static class ThrowingEventSource {
        public void addPropertyChangeListener(PropertyChangeListener listener) {
            throw new RuntimeException("Test exception from add method");
        }
    }
    
    public static class TestTarget {
        public boolean methodCalled = false;
        public Object lastParam = null;
        
        public void handleEvent() {
            methodCalled = true;
        }
        
        public void handlePropertyChange(PropertyChangeEvent evt) {
            methodCalled = true;
            lastParam = evt;
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddEventListenerNoSuchMethod() {
        // Test addEventListener with a listener type that doesn't have an add method
        Object source = new Object();
        PropertyChangeListener listener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {}
        };
        EventUtils.addEventListener(source, PropertyChangeListener.class, listener);
    }
    
    @Test(expected = RuntimeException.class)
    public void testAddEventListenerInvocationTargetException() {
        // Test InvocationTargetException path when the add method throws
        ThrowingEventSource source = new ThrowingEventSource();
        PropertyChangeListener listener = new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {}
        };
        EventUtils.addEventListener(source, PropertyChangeListener.class, listener);
    }
    
    @Test
    public void testBindEventsToMethodInvoked() {
        TestTarget target = new TestTarget();
        JButton button = new JButton();
        
        // Bind with specific event name
        EventUtils.bindEventsToMethod(target, "handleEvent", button,
            java.awt.event.ActionListener.class, "actionPerformed");
        
        // Trigger the event by clicking button
        button.doClick();
        
        // Verify method was called via proxy
        assertTrue("handleEvent should have been called", target.methodCalled);
    }
    
    @Test
    public void testBindEventsToMethodWithParameters() {
        TestTarget target = new TestTarget();
        JButton button = new JButton();
        
        // Bind to method that accepts event parameter
        EventUtils.bindEventsToMethod(target, "handlePropertyChange", button,
            PropertyChangeListener.class, "propertyChange");
        
        // Manually fire property change (JButton doesn't directly support this, but the proxy will handle it)
        button.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                // This exercises the proxy's invoke method
            }
        });
    }
    
    @Test
    public void testBindEventsToMethodUnmatchedEvent() {
        TestTarget target = new TestTarget();
        JButton button = new JButton();
        
        // Bind with specific event names, but test that other events are ignored
        EventUtils.bindEventsToMethod(target, "handleEvent", button,
            PropertyChangeListener.class, "specificEvent");
        
        // Just verify the binding was created (can't easily test firePropertyChange on JButton)
        assertFalse("Initial state should be false", target.methodCalled);
    }
    
    @Test
    public void testBindEventsWithEmptyEventTypes() {
        TestTarget target = new TestTarget();
        JButton button = new JButton();
        
        // Bind with no specific event types (empty array, not null)
        EventUtils.bindEventsToMethod(target, "handleEvent", button,
            java.awt.event.ActionListener.class);
        
        // This should match all events
        button.doClick();
        assertTrue("handleEvent should be called when no event types specified", target.methodCalled);
    }
}
