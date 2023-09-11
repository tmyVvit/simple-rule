package org.tmyvv.simplerule.expr.factory;

import org.apache.commons.collections4.CollectionUtils;
import org.tmyvv.simplerule.expr.enums.DataTypeEnum;
import org.tmyvv.simplerule.expr.function.ExprFunction;
import org.tmyvv.simplerule.expr.generator.sql.BaseSqlExprGenerator;
import org.tmyvv.simplerule.expr.generator.sql.BooleanSqlExprGenerator;
import org.tmyvv.simplerule.expr.generator.sql.StringSqlExprGenerator;
import org.tmyvv.simplerule.expr.node.OperationNode;
import org.tmyvv.simplerule.expr.util.FormatUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlGeneratorFactory extends AbstractGeneratorFactory<String, BaseSqlExprGenerator> {

    public static final SqlGeneratorFactory INSTANCE = new SqlGeneratorFactory();
    private static final Map<DataTypeEnum, BaseSqlExprGenerator> GENERATOR_MAP = new HashMap<>();
    private static final BaseSqlExprGenerator DEFAULT = new BaseSqlExprGenerator();

    static {
        GENERATOR_MAP.put(DataTypeEnum.BOOL, new BooleanSqlExprGenerator());
        GENERATOR_MAP.put(DataTypeEnum.STRING, new StringSqlExprGenerator());
        GENERATOR_MAP.put(DataTypeEnum.ENUM, new StringSqlExprGenerator());
    }

    @Override
    String and(Map<String, Object> env, List<String> rs) {
        if (CollectionUtils.isEmpty(rs)) {
            return null;
        }
        return rs.stream().collect(Collectors.joining(" AND ", "(", ")"));
    }

    @Override
    String or(Map<String, Object> env, List<String> rs) {
        if (CollectionUtils.isEmpty(rs)) {
            return null;
        }
        return rs.stream().collect(Collectors.joining(" OR ", "(", ")"));
    }

    @Override
    String not(Map<String, Object> env, List<String> rs) {
        return FormatUtil.PARENTHESIS_FORMAT.format(and(env, rs));
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
        String funcResult = func.sqlApply(node.getLeft(), node.getRight(), env);
        node.setLeft(funcResult);
    }

    @Override
    BaseSqlExprGenerator getGenerator(OperationNode node) {
        return GENERATOR_MAP.getOrDefault(node.getDataType(), DEFAULT);
    }
}
