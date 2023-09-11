package org.tmyvv.simplerule.expr.factory;

import org.tmyvv.simplerule.expr.function.BirthdayFunction;
import org.tmyvv.simplerule.expr.function.ExprFunction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FunctionFactory {

    private static final Map<String, ExprFunction> FUNC = new ConcurrentHashMap<>();

    static {
        register(new BirthdayFunction());
    }

    public static void register(ExprFunction function) {
        FUNC.put(function.name(), function);
    }

    public static ExprFunction get(String name) {
        return FUNC.get(name);
    }

}
