package com.assignment.login.infra.redis;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class RedisService {
    // embeded redis를 사용하려고 했으나, M1에서는 다른 설정이 더 필요해서 Map으로 대체하였습니다.
    private final Map<String, String> authenticateNumbers = new HashMap<>();

    public String get(String key) {
        return authenticateNumbers.get(key);
    }


    public void set(String key, String value) {
        authenticateNumbers.put(key, value);
    }

    public void delete(String key) {
        authenticateNumbers.remove(key);
    }
}
