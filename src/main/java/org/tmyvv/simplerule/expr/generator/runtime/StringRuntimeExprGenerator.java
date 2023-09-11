package org.tmyvv.simplerule.expr.generator.runtime;

import lombok.NonNull;

import java.util.Map;
import java.util.function.Supplier;

public class StringRuntimeExprGenerator extends RuntimeExprGenerator {

    @Override
    public Supplier<Boolean> empty(@NonNull String left, Object value, Map<String, Object> env) {
        return () -> env.get(left) == null || ((String) env.get(left)).isEmpty();
    }

    @Override
    public Supplier<Boolean> notEmpty(@NonNull String left, Object value, Map<String, Object> env) {
        return () -> env.get(left) != null && !((String) env.get(left)).isEmpty();
    }

    @Override
    public Supplier<Boolean> contains(@NonNull String left, @NonNull Object value, Map<String, Object> env) {
        return () -> {
            if (env.get(left) == null) {
                return false;
            }
            return ((String) env.get(left)).contains((String) value);
        };
    }
}
