package org.tmyvv.simplerule.expr.generator.sql;

import lombok.NonNull;

import java.text.MessageFormat;
import java.util.Map;

public class StringSqlExprGenerator extends BaseSqlExprGenerator {

    protected static final MessageFormat STR_EMPTY = new MessageFormat("({0} is null or {0} = '''')");
    protected static final MessageFormat STR_NOT_EMPTY = new MessageFormat("({0} is not null and {0} != '''')");

    @Override
    public String empty(@NonNull String left, Object value, Map<String, Object> env) {
        return STR_EMPTY.format(new Object[]{left});
    }

    @Override
    public String notEmpty(@NonNull String left, Object value, Map<String, Object> env) {
        return STR_NOT_EMPTY.format(new Object[]{left});
    }
}
