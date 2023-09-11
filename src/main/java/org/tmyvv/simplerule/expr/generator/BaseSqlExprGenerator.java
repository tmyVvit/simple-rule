package org.tmyvv.simplerule.expr.generator;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;
import org.tmyvv.simplerule.expr.util.FormatUtil;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class BaseSqlExprGenerator implements ExprGenerator<String> {

    protected static final String QUOTE = "'";
    protected static final String LEFT_PARENTHESIS = "(";
    protected static final String RIGHT_PARENTHESIS = ")";
    protected static final String COMMA = ",";
    protected static final MessageFormat EQ = new MessageFormat("{0} = {1}");
    protected static final MessageFormat NEQ = new MessageFormat("{0} != {1}");
    protected static final MessageFormat IS_NULL = new MessageFormat("{0} is null");
    protected static final MessageFormat NOT_NULL = new MessageFormat("{0} is not null");
    protected static final MessageFormat EMPTY = new MessageFormat("{0} = ''''");
    protected static final MessageFormat NOT_EMPTY = new MessageFormat("{0} != ''''");
    protected static final MessageFormat GT = new MessageFormat("{0} > {1}");
    protected static final MessageFormat GTE = new MessageFormat("{0} >= {1}");
    protected static final MessageFormat LT = new MessageFormat("{0} < {1}");
    protected static final MessageFormat LTE = new MessageFormat("{0} <= {1}");
    protected static final MessageFormat IN = new MessageFormat("{0} in {1}");
    protected static final MessageFormat NOT_IN = new MessageFormat("{0} not in {1}");
    protected static final MessageFormat LIKE = new MessageFormat("{0} like %{1}%");


    @Override
    public String isNull(@NonNull String left, Object value) {
        return IS_NULL.format(new Object[]{left});
    }

    @Override
    public String notNull(@NonNull String left, Object value) {
        return NOT_NULL.format(new Object[]{left});
    }

    @Override
    public String empty(@NonNull String left, Object value) {
        return EMPTY.format(new Object[]{left});
    }

    @Override
    public String notEmpty(@NonNull String left, Object value) {
        return NOT_EMPTY.format(new Object[]{left});
    }

    @Override
    public String eq(@NonNull String left, Object value) {
        if (value == null) {
            return isNull(left, value);
        } else {
            return EQ.format(new Object[]{left, parseValue(value)});
        }
    }

    @Override
    public String neq(@NonNull String left, Object value) {
        if (value == null) {
            return notNull(left, value);
        } else {
            return NEQ.format(new Object[]{left, parseValue(value)});
        }
    }

    @Override
    public String gt(@NonNull String left, @NonNull Object value) {
        return GT.format(new Object[]{left, parseValue(value)});
    }

    @Override
    public String gte(@NonNull String left, @NonNull Object value) {
        return GTE.format(new Object[]{left, parseValue(value)});
    }

    @Override
    public String lt(@NonNull String left, @NonNull Object value) {
        return LT.format(new Object[]{left, parseValue(value)});
    }

    @Override
    public String lte(@NonNull String left, @NonNull Object value) {
        return LTE.format(new Object[]{left, parseValue(value)});
    }

    @Override
    public String in(@NonNull String left, @NonNull Object value) {
        String parsedValue = parseValue(value);
        if (!wrappedWithParenthesis(parsedValue)) {
            parsedValue = LEFT_PARENTHESIS + parsedValue + RIGHT_PARENTHESIS;
        }
        return IN.format(new Object[]{left, parsedValue});
    }

    @Override
    public String notIn(@NonNull String left, @NonNull Object value) {
        String parsedValue = parseValue(value);
        if (!wrappedWithParenthesis(parsedValue)) {
            parsedValue = FormatUtil.PARENTHESIS_FORMAT.format(parsedValue);
        }
        return NOT_IN.format(new Object[]{left, parsedValue});
    }

    @Override
    public String contains(@NonNull String left, @NonNull Object value) {
        return LIKE.format(new Object[]{left, parseValue(value)});
    }

    protected String parseValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return value.toString();
        } else if (value instanceof Collection<?>) {
            return ((Collection<?>) value).stream()
                    .map(this::parseValue)
                    .collect(Collectors.joining(COMMA, LEFT_PARENTHESIS, RIGHT_PARENTHESIS));
        } else if (value.getClass().isArray()) {
            return Arrays.stream((Object[]) value)
                    .map(this::parseValue)
                    .collect(Collectors.joining(COMMA, LEFT_PARENTHESIS, RIGHT_PARENTHESIS));
        }
        return StringUtils.wrapIfMissing(value.toString(), QUOTE);
    }

    private boolean wrappedWithParenthesis(String value) {
        return StringUtils.startsWith(value, LEFT_PARENTHESIS) && StringUtils.endsWith(value, RIGHT_PARENTHESIS);
    }
}
