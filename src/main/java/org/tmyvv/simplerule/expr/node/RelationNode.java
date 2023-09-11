package org.tmyvv.simplerule.expr.node;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.tmyvv.simplerule.expr.enums.RelationEnum;

import java.util.List;

@Getter
@Setter
public class RelationNode implements ExprNode {

    private RelationEnum relation;

    private List<ExprNode> exprNodes;

    @Override
    public void validate() {
        if (relation == null) {
            throw new IllegalArgumentException("relation is null");
        }
        if (CollectionUtils.isNotEmpty(exprNodes)) {
            exprNodes.forEach(ExprNode::validate);
        }
    }
}
