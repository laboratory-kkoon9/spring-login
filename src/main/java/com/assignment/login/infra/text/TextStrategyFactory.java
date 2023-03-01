package com.assignment.login.infra.text;

import com.assignment.login.infra.text.enumtype.TextStrategyName;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class TextStrategyFactory {
    private Map<TextStrategyName, TextStrategy> strategies;

    public TextStrategyFactory(Set<TextStrategy> strategySet) {
        createStrategy(strategySet);
    }

    public TextStrategy findStrategy(TextStrategyName strategyName) {
        return strategies.get(strategyName);
    }

    private void createStrategy(Set<TextStrategy> strategySet) {
        strategies = new HashMap<>();
        strategySet.forEach(
                strategy ->strategies.put(strategy.getStrategyName(), strategy));
    }
}
