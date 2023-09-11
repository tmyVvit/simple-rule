package org.tmyvv.simplerule.expr.generator.runtime;

import lombok.NonNull;
import org.tmyvv.simplerule.expr.generator.ExprGenerator;

import java.util.*;
import java.util.function.Supplier;

public class RuntimeExprGenerator implements ExprGenerator<Supplier<Boolean>> {

    @Override
    public Supplier<Boolean> isNull(@NonNull String left, Object value, Map<String, Object> env) {
        return () -> env.get(left) == null;
    }

    @Override
    public Supplier<Boolean> notNull(@NonNull String left, Object value, Map<String, Object> env) {
        return () -> env.get(left) != null;
    }

    @Override
    public Supplier<Boolean> empty(@NonNull String left, Object value, Map<String, Object> env) {
        return () -> toSet(env.get(left)).isEmpty();
    }

    @Override
    public Supplier<Boolean> notEmpty(@NonNull String left, Object value, Map<String, Object> env) {
        return () -> !toSet(env.get(left)).isEmpty();
    }

    @Override
    public Supplier<Boolean> eq(@NonNull String left, Object value, Map<String, Object> env) {
        if (value == null) {
            return isNull(left, value, env);
        }
        return () -> value.equals(env.get(left));
    }

    @Override
    public Supplier<Boolean> neq(@NonNull String left, Object value, Map<String, Object> env) {
        if (value == null) {
            return notNull(left, value, env);
        }
        return () -> !value.equals(env.get(left));
    }

    @Override
    public Supplier<Boolean> gt(@NonNull String left, @NonNull Object value, Map<String, Object> env) {
        comparableCheck(left, value, env.get(left));
        return () -> compare(value, env.get(left)) > 0;
    }

    @Override
    public Supplier<Boolean> gte(@NonNull String left, @NonNull Object value, Map<String, Object> env) {
        comparableCheck(left, value, env.get(left));
        return () -> compare(value, env.get(left)) >= 0;
    }

    @Override
    public Supplier<Boolean> lt(@NonNull String left, @NonNull Object value, Map<String, Object> env) {
        comparableCheck(left, value, env.get(left));
        return () -> compare(value, env.get(left)) < 0;
    }

    @Override
    public Supplier<Boolean> lte(@NonNull String left, @NonNull Object value, Map<String, Object> env) {
        comparableCheck(left, value, env.get(left));
        return () -> compare(value, env.get(left)) <= 0;
    }

    @Override
    public Supplier<Boolean> in(@NonNull String left, @NonNull Object value, Map<String, Object> env) {
        return () -> toSet(env.get(left)).contains(value);
    }

    @Override
    public Supplier<Boolean> notIn(@NonNull String left, @NonNull Object value, Map<String, Object> env) {
        return () -> !toSet(env.get(left)).contains(value);
    }

    @Override
    public Supplier<Boolean> contains(@NonNull String left, @NonNull Object value, Map<String, Object> env) {
        throw new UnsupportedOperationException("contains operation not supported");
    }

    protected void comparableCheck(String left, Object val0, Object val1) {
        if (!(val0 instanceof Comparable && val1 instanceof Comparable && val0.getClass().equals(val1.getClass()))) {
            throw new IllegalArgumentException("invalid value of " + left + ", value must be same class and implements Comparable");
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    protected int compare(Object val0, Object val1) {
        return ((Comparable) val0).compareTo(val1);
    }

    @SuppressWarnings({"rawtypes"})
    protected Set<Object> toSet(Object value) {
        Set<Object> set = new HashSet<>();
        if (value == null) {
            return set;
        } else if (value instanceof Collection<?>) {
            set.addAll((Collection) value);
            return set;
        } else if (value.getClass().isArray()) {
            Collections.addAll(set, (Object[]) value);
            return set;
        }
        set.add(value);
        return set;
    }
}
