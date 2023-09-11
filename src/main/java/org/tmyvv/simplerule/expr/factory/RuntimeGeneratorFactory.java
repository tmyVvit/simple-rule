package org.tmyvv.simplerule.expr.factory;

import org.tmyvv.simplerule.expr.enums.DataTypeEnum;
import org.tmyvv.simplerule.expr.function.ExprFunction;
import org.tmyvv.simplerule.expr.generator.runtime.RuntimeExprGenerator;
import org.tmyvv.simplerule.expr.generator.runtime.StringRuntimeExprGenerator;
import org.tmyvv.simplerule.expr.node.ExprNode;
import org.tmyvv.simplerule.expr.node.OperationNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class RuntimeGeneratorFactory extends AbstractGeneratorFactory<Supplier<Boolean>, RuntimeExprGenerator>{

    public static final RuntimeGeneratorFactory INSTANCE = new RuntimeGeneratorFactory();
    private static final Map<DataTypeEnum, RuntimeExprGenerator> GENERATOR_MAP = new HashMap<>();
    private static final RuntimeExprGenerator DEFAULT = new RuntimeExprGenerator();

    static {
        GENERATOR_MAP.put(DataTypeEnum.STRING, new StringRuntimeExprGenerator());
        GENERATOR_MAP.put(DataTypeEnum.ENUM, new StringRuntimeExprGenerator());
    }

    @Override
    public Supplier<Boolean> eval(ExprNode node) {
        throw new UnsupportedOperationException();
    }

    @Override
    Supplier<Boolean> and(Map<String, Object> env, List<Supplier<Boolean>> suppliers) {
        for (Supplier<Boolean> supplier : suppliers) {
            if (!supplier.get()) {
                return () -> false;
            }
        }
        return () -> true;
    }

    @Override
    Supplier<Boolean> or(Map<String, Object> env, List<Supplier<Boolean>> suppliers) {
        for (Supplier<Boolean> supplier : suppliers) {
            if (supplier.get()) {
                return () -> true;
            }
        }
        return () -> false;
    }

    @Override
    Supplier<Boolean> not(Map<String, Object> env, List<Supplier<Boolean>> suppliers) {
        return () -> !and(env, suppliers).get();
    }

    @Override
    void applyFunc(OperationNode node, Map<String, Object> env) {
        if (node.getFunc() == null) {
            return;
        }
        ExprFunction func = FunctionFactory.get(node.getFunc());
        if (func == null) {
            throw new IllegalArgumentException("function not found: " + node.getFunc());
        }
        String funcResult = func.apply(node.getLeft(), node.getRight(), env);
        node.setLeft(funcResult);
    }

    @Override
    RuntimeExprGenerator getGenerator(OperationNode node) {
        return GENERATOR_MAP.getOrDefault(node.getDataType(), DEFAULT);
    }
}
