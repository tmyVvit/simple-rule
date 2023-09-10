package org.tmyvv.simplerule.expr.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum OperationEnum {

    @JsonProperty("=")
    EQ,

    @JsonProperty("!=")
    NEQ,

    @JsonProperty(">")
    GT,

    @JsonProperty(">=")
    GTE,

    @JsonProperty("<")
    LT,

    @JsonProperty("<=")
    LTE,

    @JsonProperty("in")
    IN,

    @JsonProperty("not_in")
    NOT_IN,

    @JsonProperty("empty")
    EMPTY,

    @JsonProperty("not_empty")
    NOT_EMPTY,

    @JsonProperty("contains")
    CONTAINS,

    @JsonProperty("is_null")
    IS_NULL,

    @JsonProperty("not_null")
    NOT_NULL,



}
