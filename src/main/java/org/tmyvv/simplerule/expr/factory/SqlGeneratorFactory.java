package org.tmyvv.simplerule.expr.factory;

import org.apache.commons.collections4.CollectionUtils;
import org.tmyvv.simplerule.expr.enums.DataTypeEnum;
import org.tmyvv.simplerule.expr.generator.BaseSqlExprGenerator;
import org.tmyvv.simplerule.expr.generator.BooleanSqlExprGenerator;
import org.tmyvv.simplerule.expr.generator.StringSqlExprGenerator;
import org.tmyvv.simplerule.expr.node.OperationNode;
import org.tmyvv.simplerule.expr.util.FormatUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlGeneratorFactory extends AbstractGeneratorFactory<String, BaseSqlExprGenerator> {

    private static final SqlGeneratorFactory INSTANCE = new SqlGeneratorFactory();
    private static final Map<DataTypeEnum, BaseSqlExprGenerator> GENERATOR_MAP = new HashMap<>();
    private static final BaseSqlExprGenerator DEFAULT = new BaseSqlExprGenerator();

    static {
        GENERATOR_MAP.put(DataTypeEnum.BOOL, new BooleanSqlExprGenerator());
        GENERATOR_MAP.put(DataTypeEnum.STRING, new StringSqlExprGenerator());
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
        return FormatUtil.PARENTHESIS_FORMAT.format(or(env, rs));
    }

    @Override
    void applyFunc(OperationNode node, Map<String, Object> env) {
        if (node.getFunc() != null) {
            String funcResult = FunctionFactory.get(node.getFunc()).sqlApply(node.getLeft(), node.getRight(), env);
            node.setLeft(funcResult);
        }
    }

    @Override
    BaseSqlExprGenerator getGenerator(OperationNode node) {
        return GENERATOR_MAP.getOrDefault(node.getDataType(), DEFAULT);
    }
}
