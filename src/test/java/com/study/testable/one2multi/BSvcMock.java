package com.study.testable.one2multi;

import com.alibaba.testable.core.annotation.MockMethod;

public class BSvcMock {

    @MockMethod(targetClass = String.class, targetMethod = "format")
    public String b_format(String format, Object... args) {
        return "b_mock";
    }

}
