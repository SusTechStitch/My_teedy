package com.sismics.util;

import org.junit.Assert;
import org.junit.Test;

import jakarta.json.JsonValue;

/**
 * Test of {@link JsonUtil}
 *
 * Verifies that nullable conversions return JSON NULL for null inputs
 * and the appropriate JSON types for non-null values.
 */
public class TestJsonUtil {

    @Test
    public void testNullableString() {
        JsonValue v = JsonUtil.nullable("abc");
        Assert.assertEquals(JsonValue.ValueType.STRING, v.getValueType());
    }

    @Test
    public void testNullableStringNull() {
        JsonValue v = JsonUtil.nullable((String) null);
        Assert.assertEquals(JsonValue.ValueType.NULL, v.getValueType());
    }

    @Test
    public void testNullableInteger() {
        JsonValue v = JsonUtil.nullable(Integer.valueOf(123));
        Assert.assertEquals(JsonValue.ValueType.NUMBER, v.getValueType());
    }

    @Test
    public void testNullableIntegerNull() {
        JsonValue v = JsonUtil.nullable((Integer) null);
        Assert.assertEquals(JsonValue.ValueType.NULL, v.getValueType());
    }

    @Test
    public void testNullableLong() {
        JsonValue v = JsonUtil.nullable(Long.valueOf(456L));
        Assert.assertEquals(JsonValue.ValueType.NUMBER, v.getValueType());
    }

    @Test
    public void testNullableLongNull() {
        JsonValue v = JsonUtil.nullable((Long) null);
        Assert.assertEquals(JsonValue.ValueType.NULL, v.getValueType());
    }
}
