package org.tmyvv.simplerule.expr.function;

import java.text.MessageFormat;
import java.util.Map;

public class BirthdayFunction implements ExprFunction {

    @Override
    public String name() {
        return "birthday";
    }

    @Override
    public String sqlApply(String left, Object value, Map<String, Object> env) {
        return MessageFormat.format("if(substr({0}, 4, 4) = {1}, 'Y', 'N')", left, "0911");
    }
}
