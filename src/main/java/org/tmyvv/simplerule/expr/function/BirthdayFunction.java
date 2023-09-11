package org.tmyvv.simplerule.expr.function;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class BirthdayFunction implements ExprFunction {

    private static final DateTimeFormatter MMDD = DateTimeFormatter.ofPattern("MMdd");

    @Override
    public String name() {
        return "birthday";
    }

    @Override
    public String sqlApply(String left, Object value, Map<String, Object> env) {
        return MessageFormat.format("if(substr({0}, 4, 4) = ''{1}'', ''Y'', ''N'')", left, currentDay());
    }

    @Override
    public String apply(String left, Object value, Map<String, Object> env) {
        String funcLeft = wrap(left);
        String envVal = (String) env.get(left);
        if (envVal == null || envVal.length() < 8) {
            env.put(funcLeft, "N");
            return funcLeft;
        }
        String funcVal = currentDay().equals(envVal.substring(4, 8)) ? "Y" : "N";
        env.put(funcLeft, funcVal);
        return funcLeft;
    }

    private static String currentDay() {
        return MMDD.format(LocalDate.now());
    }
}
