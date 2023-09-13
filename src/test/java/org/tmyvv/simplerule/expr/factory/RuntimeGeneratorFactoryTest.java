package org.tmyvv.simplerule.expr.factory;

import org.junit.jupiter.api.Test;
import org.tmyvv.simplerule.expr.enums.DataTypeEnum;
import org.tmyvv.simplerule.expr.enums.OperationEnum;
import org.tmyvv.simplerule.expr.enums.RelationEnum;
import org.tmyvv.simplerule.expr.node.OperationNode;
import org.tmyvv.simplerule.expr.node.RelationNode;
import org.tmyvv.simplerule.expr.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RuntimeGeneratorFactoryTest {

    @Test
    void test1() {
        RuntimeGeneratorFactory factory = RuntimeGeneratorFactory.INSTANCE;

        RelationNode rn = new RelationNode();
        rn.setRelation(RelationEnum.AND);
        rn.setExprNodes(new ArrayList<>());
        OperationNode node = new OperationNode();
        node.setLeft("tag");
        node.setDataType(DataTypeEnum.STRING);
        node.setOperation(OperationEnum.EMPTY);
        node.setRight("value");
        Map<String, Object> env = new HashMap<>();
        env.put("tag", "");

        OperationNode node2 = new OperationNode();
        node2.setLeft("birthday(tag2)");
        node2.setOperation(OperationEnum.EQ);
        node2.setRight("Y");
        node2.setDataType(DataTypeEnum.DATE);

        env.put("tag2", DateUtil.formatYYYYMMDD(LocalDate.now()));

        rn.getExprNodes().add(node);
        rn.getExprNodes().add(node2);
        Supplier<Boolean> supplier = factory.eval(rn, env);
        assertTrue(supplier.get());
    }

    @Test
    void test2() {
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
        op3.setDataType(DataTypeEnum.ENUM);
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

        Map<String, Object> env = new HashMap<>();
        env.put("tag1", "123");
        env.put("tag2", "value2");
        env.put("tag3", "");
        env.put("tag4", 10);
        env.put("tag5", DateUtil.formatYYYYMMDD(LocalDate.now()));

//        String sqlExpr = SqlGeneratorFactory.INSTANCE.eval(relationNode, env);
//        System.out.println(sqlExpr);
        Supplier<Boolean> expr = RuntimeGeneratorFactory.INSTANCE.eval(relationNode, env);
        assertTrue(expr.get());
    }
}
