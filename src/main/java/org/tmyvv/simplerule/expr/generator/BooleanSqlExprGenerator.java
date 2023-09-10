package org.tmyvv.simplerule.expr.generator;

import lombok.NonNull;

import java.text.MessageFormat;

public class BooleanSqlExprGenerator extends BaseSqlExprGenerator {

    protected static final String YES = "Y";
    protected static final String NO = "N";

    protected static final MessageFormat EQ_NO = new MessageFormat("({0} = ''N'' or {0} is null or {0} = '''')");

    @Override
    public String eq(@NonNull String left, @NonNull Object value) {
        if (NO.equals(value)) {
            return EQ_NO.format(new Object[]{left});
        } else {
            return super.eq(left, value);
        }
    }
}
