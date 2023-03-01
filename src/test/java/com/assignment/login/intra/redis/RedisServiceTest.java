package com.assignment.login.intra.redis;

import com.assignment.login.infra.redis.RedisService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.assignment.login.infra.text.util.RandomGenerator.rand;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = {RedisService.class})
class RedisServiceTest {
    @Autowired
    private RedisService redisService;

    @DisplayName("RedisService.set 테스트 코드")
    @Test
    void redisService_test1() {
        // given
        String givenKey = "01080127226";
        String givenValue = rand();

        // when
        redisService.set(givenKey, givenValue);
        String result = redisService.get(givenKey);

        // then
        assertThat(result).isEqualTo(givenValue);
    }

    @DisplayName("RedisService.delete 테스트 코드")
    @Test
    void redisService_test21() {
        // given
        String givenKey = "01080127226";
        String givenValue = rand();

        // when
        redisService.set(givenKey, givenValue);
        redisService.delete(givenKey);
        String result = redisService.get(givenKey);

        // then
        assertThat(result).isNull();
    }
}
