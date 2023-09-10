package org.tmyvv.simplerule.expr.function;

import java.util.Map;

public interface ExprFunction {

    String name();

    String sqlApply(String left, Object value, Map<String, Object> env);
}
