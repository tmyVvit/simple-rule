package org.tmyvv.simplerule.expr.generator;

public interface ExprGenerator<R> {

    R isNull(String left, Object value);

    R notNull(String left, Object value);

    R empty(String left, Object value);

    R notEmpty(String left, Object value);

    R eq(String left, Object value);

    R neq(String left, Object value);

    R gt(String left, Object value);

    R gte(String left, Object value);

    R lt(String left, Object value);

    R lte(String left, Object value);

    R in(String left, Object value);

    R notIn(String left, Object value);

    R contains(String left, Object value);

}
