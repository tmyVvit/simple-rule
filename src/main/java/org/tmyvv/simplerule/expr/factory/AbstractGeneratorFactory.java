package org.tmyvv.simplerule.expr.factory;

import org.apache.commons.collections4.CollectionUtils;
import org.tmyvv.simplerule.expr.generator.ExprGenerator;
import org.tmyvv.simplerule.expr.node.ExprNode;
import org.tmyvv.simplerule.expr.node.OperationNode;
import org.tmyvv.simplerule.expr.node.RelationNode;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class AbstractGeneratorFactory<R, T extends ExprGenerator<R>> {

    public R eval(ExprNode node) {
        return eval(node, null);
    }

    public R eval(ExprNode node, Map<String, Object> env) {
        node.validate();
        if (node instanceof RelationNode) {
            return evalRelation((RelationNode) node, env);
        } else if (node instanceof OperationNode) {
            return evalOperation((OperationNode) node, env);
        }
        throw new IllegalArgumentException("unknown node type: " + node.getClass());
    }

    protected R evalOperation(OperationNode node, Map<String, Object> env) {
        applyFunc(node, env);
        T generator = getGenerator(node);
        return switch (node.getOperation()) {
            case EQ -> generator.eq(node.getLeft(), node.getRight());
            case NEQ -> generator.neq(node.getLeft(), node.getRight());
            case GT -> generator.gt(node.getLeft(), node.getRight());
            case GTE -> generator.gte(node.getLeft(), node.getRight());
            case LT -> generator.lt(node.getLeft(), node.getRight());
            case LTE -> generator.lte(node.getLeft(), node.getRight());
            case IN -> generator.in(node.getLeft(), node.getRight());
            case NOT_IN -> generator.notIn(node.getLeft(), node.getRight());
            case CONTAINS -> generator.contains(node.getLeft(), node.getRight());
            case EMPTY -> generator.empty(node.getLeft(), node.getRight());
            case NOT_EMPTY -> generator.notEmpty(node.getLeft(), node.getRight());
            case IS_NULL -> generator.isNull(node.getLeft(), node.getRight());
            case NOT_NULL -> generator.notNull(node.getLeft(), node.getRight());
            default -> throw new IllegalStateException("Unexpected value: " + node.getOperation());
        };
    }

    protected R evalRelation(RelationNode node, Map<String, Object> env) {
        if (CollectionUtils.isEmpty(node.getExprNodes())) {
            return null;
        }
        List<R> rs = node.getExprNodes()
                .stream()
                .map(n -> eval(n, env))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        return switch (node.getRelation()) {
            case AND -> and(env, rs);
            case OR -> or(env, rs);
            case NOT -> not(env, rs);
        };
    }

    abstract R and(Map<String, Object> env, List<R> rs);

    abstract R or(Map<String, Object> env, List<R> rs);

    abstract R not(Map<String, Object> env, List<R> rs);

    abstract void applyFunc(OperationNode node, Map<String, Object> env);

    abstract T getGenerator(OperationNode node);


}
