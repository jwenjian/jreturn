package com.github.jwenjian.jreturn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JReturn {

    public static class ValueWrapper {

        private boolean singleMode;

        private Object singleValue;

        private List<Object> values;

        private Map<Object, Object> keyValues;

        private ValueWrapper(final boolean singleMode) {
            this.singleMode = singleMode;
        }

        public ValueWrapper wrap(Object value) {
            if (singleMode) {
                singleValue = value;
                return this;
            }
            if (values == null) {
                values = new ArrayList<Object>();
            }
            values.add(value);
            return this;
        }

        public ValueWrapper wrapWithKey(Object key, Object value) {
            if (singleMode) {
                singleValue = value;
                return this;
            }
            if (keyValues == null) {
                keyValues = new HashMap<Object, Object>();
            }
            keyValues.put(key, value);
            return this;
        }

        public Object getSingleValue() {
            return singleValue;
        }

        public <V> V getSingleValue(Class<V> vClass) {
            if (vClass == null) {
                throw new IllegalArgumentException("class of single value cannot be null!");
            }
            if (singleValue == null) {
                return null;
            }
            if (!vClass.isAssignableFrom(singleValue.getClass())) {
                throw new ClassCastException(String.format("%s cannot be cast to %s!", singleValue.getClass().getName(), vClass.getName()));
            }
            return vClass.cast(singleValue);
        }

    }

    public static ValueWrapper withSingleValue() {
        final ValueWrapper singleValueWrapper = new ValueWrapper(Boolean.TRUE);
        return singleValueWrapper;
    }

    public static ValueWrapper withMultiValue() {
        final ValueWrapper multiValueWrapper = new ValueWrapper(Boolean.FALSE);
        return multiValueWrapper;
    }

    private JReturn() {}
}
