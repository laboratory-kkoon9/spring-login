package com.assignment.login.infra.text.strategy;

import com.assignment.login.infra.text.TextStrategy;
import com.assignment.login.infra.text.enumtype.TextStrategyName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.assignment.login.infra.text.util.RandomGenerator.rand;

@Slf4j
@Component
public class NoneTextStrategy implements TextStrategy {
    @Override
    public TextStrategyName getStrategyName() {
        return TextStrategyName.NONE;
    }

    @Override
    public String authenticate(String phone) {
        // TODO phone에 rand() 값을 보내주는 로직 추가
        return rand();
    }
}
