package com.assignment.login.intra.text;

import com.assignment.login.infra.text.TextStrategy;
import com.assignment.login.infra.text.TextStrategyFactory;
import com.assignment.login.infra.text.enumtype.TextStrategyName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
class TextStrategyTest {
        @Autowired
        private TextStrategyFactory strategyFactory;

        @ParameterizedTest
        @EnumSource(value = TextStrategyName.class, names = {"NONE"})
        @DisplayName("TextStrategy name(None)이 정상적으로 리턴되는지 확인한다.")
        void strategy_getStrategyName_test(TextStrategyName textStrategyName) {
            // given
            TextStrategy strategy = this.strategyFactory.findStrategy(textStrategyName);

            // when
            TextStrategyName strategyName = strategy.getStrategyName();

            // then
            assertThat(strategyName).isEqualTo(textStrategyName);
        }

    @ParameterizedTest
    @EnumSource(value = TextStrategyName.class, names = {"NONE"})
    @DisplayName("6자리의 랜덤한 값이 정상적으로 리턴되는지 확인한다.")
    void strategy_authenticate_test(TextStrategyName textStrategyName) {
        // given
        TextStrategy strategy = this.strategyFactory.findStrategy(textStrategyName);
        String givenPhone = "010-8012-7226";

        // when
        String number = strategy.authenticate(givenPhone);

        // then
        assertThat(number.length()).isEqualTo(6);
    }
}
