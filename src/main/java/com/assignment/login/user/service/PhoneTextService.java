package com.assignment.login.user.service;

import com.assignment.login.infra.text.TextStrategy;
import com.assignment.login.infra.text.TextStrategyFactory;
import com.assignment.login.infra.text.enumtype.TextStrategyName;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PhoneTextService {
    private final TextStrategyFactory strategyFactory;

    public String sendAuthenticateNumber(final String phone) {
        final TextStrategy strategy = this.decideTextStrategy();
        return strategy.authenticate(phone);
    }

    private TextStrategy decideTextStrategy() {
        // TODO 문자 송신 관련 API를 어느 업체 쓸 건지에 따라 로직 추가
        return this.strategyFactory.findStrategy(TextStrategyName.NONE);
    }
}
