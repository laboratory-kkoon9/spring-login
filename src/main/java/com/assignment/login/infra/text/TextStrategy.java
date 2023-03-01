package com.assignment.login.infra.text;

import com.assignment.login.infra.text.enumtype.TextStrategyName;

public interface TextStrategy {
    TextStrategyName getStrategyName();
    String authenticate(String phone);

}
