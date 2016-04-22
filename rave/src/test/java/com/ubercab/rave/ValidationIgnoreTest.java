package com.ubercab.rave;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test Validation Ignore
 */
public class ValidationIgnoreTest {

    @Test
    public void shouldIgnore_whenRaveErrorMatches_shouldReturnTrue() {
        String methodName = "method";
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        assertFalse(ignore.isIgnoreClassAll());
        ignore.setAsIgnoreClassAll();
        assertTrue(ignore.isIgnoreClassAll());
        RaveError error = new RaveError(String.class, methodName, RaveErrorStrings.NON_NULL_ERROR);
        assertTrue(ignore.shouldIgnore(error));
        ignore = new ValidationIgnore(String.class);
        ignore.addMethod(methodName);
        assertTrue(ignore.shouldIgnore(error));
        error = new RaveError(String.class, methodName + "()", RaveErrorStrings.NON_NULL_ERROR);
        ignore.shouldIgnoreMethod(methodName);
        assertTrue(ignore.shouldIgnore(error));
    }

    @Test
    public void getClazz_whenClassIsSet_shouldReturnStringClass_whenStringIsSet() throws Exception {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        assertEquals(String.class, ignore.getClazz());
    }

    @Test
    public void setAsIgnoreClassAll() throws Exception {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        ignore.setAsIgnoreClassAll();
        assertTrue(ignore.isIgnoreClassAll());
    }

    @Test
    public void hasIgnoreMethods_shouldReturnAppropriateValue_whenMethodisSet() throws Exception {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        assertFalse(ignore.hasIgnoreMethods());
        ignore.addMethod("method");
        assertTrue(ignore.hasIgnoreMethods());
    }

    @Test
    public void shouldIgnore_whenIgnoreAllSet_shouldReturnTrue() {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        ignore.setAsIgnoreClassAll();
        RaveError error = new RaveError(String.class, "", "");
        assertTrue(ignore.shouldIgnore(error));
    }

    @Test
    public void shouldIgnore_whenIgnoreAllNotSet_shouldReturnFalse() {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        RaveError error = new RaveError(String.class, "", "");
        assertFalse(ignore.shouldIgnore(error));
    }

    @Test
    public void shouldIgnore_whenClassDifferent_shouldReturnFalse() {
        ValidationIgnore ignore = new ValidationIgnore(Object.class);
        RaveError error = new RaveError(String.class, "", "");
        assertFalse(ignore.shouldIgnore(error));
    }

    @Test
    public void shouldIgnore_whenMethodNameSet_shouldReturnTrue() {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        ignore.addMethod("method");
        RaveError error = new RaveError(String.class, "method()", "");
        assertTrue(ignore.shouldIgnore(error));
    }

    @Test
    public void shouldIgnore_whenMethodNameSetNoparen_shouldReturnTrue() {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        ignore.addMethod("method");
        RaveError error = new RaveError(String.class, "method", "");
        assertTrue(ignore.shouldIgnore(error));
    }
}
