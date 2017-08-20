package com.uber.rave;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Test Validation Ignore
 */
public class ValidationIgnoreTest {

    @Test
    public void shouldIgnore_whenRaveErrorMatches_shouldReturnTrue() {
        String methodName = "method";
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        assertThat(ignore.isIgnoreClassAll()).isFalse();
        ignore.setAsIgnoreClassAll();
        assertThat(ignore.isIgnoreClassAll()).isTrue();
        RaveError error = new RaveError(String.class, methodName, RaveErrorStrings.NON_NULL_ERROR);
        assertThat(ignore.shouldIgnore(error)).isTrue();
        ignore = new ValidationIgnore(String.class);
        ignore.addMethod(methodName);
        assertThat(ignore.shouldIgnore(error)).isTrue();
        error = new RaveError(String.class, methodName + "()", RaveErrorStrings.NON_NULL_ERROR);
        ignore.shouldIgnoreMethod(methodName);
        assertThat(ignore.shouldIgnore(error)).isTrue();
    }

    @Test
    public void getClazz_whenClassIsSet_shouldReturnStringClass_whenStringIsSet() throws Exception {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        assertThat(String.class).isEqualTo(ignore.getClazz());
    }

    @Test
    public void setAsIgnoreClassAll() throws Exception {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        ignore.setAsIgnoreClassAll();
        assertThat(ignore.isIgnoreClassAll()).isTrue();
    }

    @Test
    public void hasIgnoreMethods_shouldReturnAppropriateValue_whenMethodisSet() throws Exception {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        assertThat(ignore.hasIgnoreMethods()).isFalse();
        ignore.addMethod("method");
        assertThat(ignore.hasIgnoreMethods()).isTrue();
    }

    @Test
    public void shouldIgnore_whenIgnoreAllSet_shouldReturnTrue() {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        ignore.setAsIgnoreClassAll();
        RaveError error = new RaveError(String.class, "", "");
        assertThat(ignore.shouldIgnore(error)).isTrue();
    }

    @Test
    public void shouldIgnore_whenIgnoreAllNotSet_shouldReturnFalse() {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        RaveError error = new RaveError(String.class, "", "");
        assertThat(ignore.shouldIgnore(error)).isFalse();
    }

    @Test
    public void shouldIgnore_whenClassDifferent_shouldReturnFalse() {
        ValidationIgnore ignore = new ValidationIgnore(Object.class);
        RaveError error = new RaveError(String.class, "", "");
        assertThat(ignore.shouldIgnore(error)).isFalse();
    }

    @Test
    public void shouldIgnore_whenMethodNameSet_shouldReturnTrue() {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        ignore.addMethod("method");
        RaveError error = new RaveError(String.class, "method()", "");
        assertThat(ignore.shouldIgnore(error)).isTrue();
    }

    @Test
    public void shouldIgnore_whenMethodNameSetNoparen_shouldReturnTrue() {
        ValidationIgnore ignore = new ValidationIgnore(String.class);
        ignore.addMethod("method");
        RaveError error = new RaveError(String.class, "method", "");
        assertThat(ignore.shouldIgnore(error)).isTrue();
    }
}
