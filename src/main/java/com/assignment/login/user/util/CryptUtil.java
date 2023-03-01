package com.assignment.login.user.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CryptUtil {
    public static String encode(final String word) {
        return new BCryptPasswordEncoder().encode(word);
    }

    public static Boolean matches(final CharSequence rawWord, final String encodedWord) {
        return new BCryptPasswordEncoder().matches(rawWord,encodedWord);
    }
}
