package org.tmyvv;

import org.tmyvv.simplerule.expr.enums.DataTypeEnum;
import org.tmyvv.simplerule.expr.enums.OperationEnum;
import org.tmyvv.simplerule.expr.enums.RelationEnum;
import org.tmyvv.simplerule.expr.factory.SqlGeneratorFactory;
import org.tmyvv.simplerule.expr.node.OperationNode;
import org.tmyvv.simplerule.expr.node.RelationNode;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        RelationNode relationNode = new RelationNode();
        relationNode.setRelation(RelationEnum.AND);

        OperationNode op1 = new OperationNode();
        op1.setLeft("tag1");
        op1.setDataType(DataTypeEnum.ENUM);
        op1.setOperation(OperationEnum.NOT_NULL);

        OperationNode op2 = new OperationNode();
        op2.setLeft("tag2");
        op2.setDataType(DataTypeEnum.STRING);

        op2.setOperation(OperationEnum.EQ);
        op2.setRight("value2");

        RelationNode relationNode2 = new RelationNode();
        relationNode2.setRelation(RelationEnum.AND);

        OperationNode op3 = new OperationNode();
        op3.setLeft("tag3");
        op3.setDataType(DataTypeEnum.DATE);
        op3.setOperation(OperationEnum.EMPTY);

        OperationNode op4 = new OperationNode();
        op4.setLeft("tag4");
        op4.setDataType(DataTypeEnum.INT);
        op4.setOperation(OperationEnum.GT);
        op4.setRight(12);

        OperationNode op5 = new OperationNode();
        op5.setLeft("birthday(tag5)");
        op5.setDataType(DataTypeEnum.DATE);
        op5.setOperation(OperationEnum.EQ);
        op5.setRight("Y");
        relationNode2.setExprNodes(Arrays.asList(op3, op4, op5));
        relationNode.setExprNodes(Arrays.asList(op1, op2, relationNode2));

        String sqlExpr = SqlGeneratorFactory.INSTANCE.eval(relationNode, null);
        System.out.println(sqlExpr);

    }
}