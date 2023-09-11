package org.tmyvv.simplerule.expr.generator;

import java.util.Map;

public interface ExprGenerator<R> {

    R isNull(String left, Object value, Map<String, Object> env);

    R notNull(String left, Object value, Map<String, Object> env);

    R empty(String left, Object value, Map<String, Object> env);

    R notEmpty(String left, Object value, Map<String, Object> env);

    R eq(String left, Object value, Map<String, Object> env);

    R neq(String left, Object value, Map<String, Object> env);

    R gt(String left, Object value, Map<String, Object> env);

    R gte(String left, Object value, Map<String, Object> env);

    R lt(String left, Object value, Map<String, Object> env);

    R lte(String left, Object value, Map<String, Object> env);

    R in(String left, Object value, Map<String, Object> env);

    R notIn(String left, Object value, Map<String, Object> env);

    R contains(String left, Object value, Map<String, Object> env);

}
