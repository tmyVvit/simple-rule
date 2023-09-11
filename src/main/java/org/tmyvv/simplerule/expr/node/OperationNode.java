package org.tmyvv.simplerule.expr.node;

import lombok.Getter;
import lombok.Setter;
import org.tmyvv.simplerule.expr.enums.DataTypeEnum;
import org.tmyvv.simplerule.expr.enums.OperationEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
public class OperationNode implements ExprNode {

    private static final Pattern FUNC_PATTERN = Pattern.compile("(?<func>\\w+)\\((?<left>.*)\\)");

    private OperationEnum operation;

    private String func;

    private String left;

    private DataTypeEnum dataType;

    private Object right;

    public void validate() {
        if (operation == null) {
            throw new IllegalArgumentException("operation is null");
        }
        if (left == null) {
            throw new IllegalArgumentException("left is null");
        }
        if (dataType == null) {
            throw new IllegalArgumentException("dataType is null");
        }

        Matcher m = FUNC_PATTERN.matcher(left);
        if (m.matches()) {
            func = m.group("func");
            left = m.group("left");
        }
    }

}
