package com.github.jwenjian.jreturn;

import java.util.*;

public class JReturn {

    public static class R {

        private boolean single;

        private Object value;

        private Set<Object> values;

        private Map<Object, Object> keyValues;

        public <V> V unwrap(final Class<V> vClass) {
            Objects.requireNonNull(vClass, "Target class cannot be null");

            if (single) {
                return vClass.cast(value);
            }
            if (values != null) {
                for (final Object o : values) {
                    if (vClass.isAssignableFrom(o.getClass())) {
                        return vClass.cast(o);
                    }
                }
                throw new RuntimeException(String.format("Returned value with target class = %s not found", vClass.getName()));
            }

            // IMPOSSIBLE
            throw new RuntimeException("IMPOSSIBLE STATE");
        }

        public <V> V unwrap(final Object key, final Class<V> vClass) {
            Objects.requireNonNull(vClass, "Target class cannot be null");
            if (single) {
                return vClass.cast(value);
            }

            Objects.requireNonNull(key, "key cannot be null");

            if (keyValues != null) {
                if (keyValues.containsKey(key)) {
                    return vClass.cast(keyValues.get(key));
                }
                throw new RuntimeException(String.format("Returned value with key = %s not found", vClass.getName()));
            }

            // IMPOSSIBLE
            throw new RuntimeException("IMPOSSIBLE STATE");
        }

        private R() {}

        private static R newInstanceWithSingleValue(final Object value) {
            final R r = new R();
            r.single = true;
            r.value = value;
            return r;
        }

        private static R newInstanceWithMultiValues(final Object... inputs) {
            final R r = new R();
            r.single = false;
            r.values = new HashSet<>();
            if (inputs != null && inputs.length > 0) {
                r.values.addAll(Arrays.asList(inputs));
            }
            return r;
        }

        private static R newInstanceWithMultiKeyValuePair(final Map<Object, Object> keyPair) {
            final R r = new R();
            r.single = false;
            r.keyValues = new HashMap<>();
            r.keyValues.putAll(keyPair);
            return r;
        }
    }

    public static class InnerMap {

        private Map<Object, Object> keyPair;

        public InnerMap put(Object key, Object value) {
            if (keyPair == null) {
                keyPair = new HashMap<>();
            }
            keyPair.put(key, value);
            return this;
        }

        public InnerMap putAll(final Map<Object, Object> values) {
            if (keyPair == null) {
                keyPair = new HashMap<>();
            }
            keyPair.putAll(values);
            return this;
        }

        public R toR() {
            return R.newInstanceWithMultiKeyValuePair(this.keyPair);
        }

        private InnerMap() {}
    }

    public static R single(final Object value) {
        return R.newInstanceWithSingleValue(value);
    }

    public static R multi(final Object... values) {
        return R.newInstanceWithMultiValues(values);
    }

    public static InnerMap withKey(final Object firstKey, final Object firstValue) {
        final InnerMap innerMap = new InnerMap();
        innerMap.put(firstKey, firstValue);
        return innerMap;
    }

    private JReturn() {}
}
