package org.tmyvv.simplerule.expr.generator;

import lombok.NonNull;

import java.text.MessageFormat;

public class StringSqlExprGenerator extends BaseSqlExprGenerator {

    protected static final MessageFormat STR_EMPTY = new MessageFormat("({0} is null or {0} = '''')");
    protected static final MessageFormat STR_NOT_EMPTY = new MessageFormat("({0} is not null and {0} != '''')");

    @Override
    public String empty(@NonNull String left, Object value) {
        return STR_EMPTY.format(new Object[]{left});
    }

    @Override
    public String notEmpty(@NonNull String left, Object value) {
        return STR_NOT_EMPTY.format(new Object[]{left});
    }
}
