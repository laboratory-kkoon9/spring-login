package com.assignment.login.global.config;

import com.assignment.login.global.response.TokenDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.util.Date;

import static java.util.Collections.EMPTY_LIST;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenManager {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    private final static Long ACCESS_TOKEN_TIME_OUT = 1000L * 60 * 60; // 1시간

    public String generateToken(TokenDto tokenDto) {
        return newToken(tokenDto, ACCESS_TOKEN_TIME_OUT);
    }

    public String newToken(TokenDto token, Long expireTime) {
        return Jwts.builder()
                .setIssuedAt(new Date())
                .claim("userId", token.getUserId())
                .claim("email", token.getEmail())
                .setIssuer(jwtIssuer)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getJwtSecret(), SignatureAlgorithm.HS256)
                .compact();
    }

    public SecretKey getJwtSecret() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateToken(String token) {
        if (ObjectUtils.isEmpty(token)) {
            throw new IllegalArgumentException("[JWT Token Filter Error]: JWT Empty. Please check header.");
        }

        try {
            Jwts.parserBuilder()
                    .setSigningKey(getJwtSecret())
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            throw new IllegalArgumentException("[JWT Token Filter Error]: Not Sign. Please check header.");
        } catch (UnsupportedJwtException e) {
            throw new IllegalArgumentException("[JWT Token Filter Error]: Not Fount JWT token type. Please check header.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("[JWT Token Filter Error]: Wrong JWT token. Please check header.");
        } catch (Exception e) {
            throw new IllegalArgumentException("[JWT Token Filter Error]: " + e.getMessage());
        }
        return true;
    }

    public Authentication getAuthentication(String accessToken) {
        // 토큰 복호화
        Claims claims = parseClaims(accessToken);

        Long userId = Long.valueOf(claims.get("userId").toString());

        return new UsernamePasswordAuthenticationToken(userId, "", EMPTY_LIST);
    }

    public Claims parseClaims(String accessToken) {
        return Jwts.parserBuilder().setSigningKey(getJwtSecret()).build().parseClaimsJws(accessToken).getBody();
    }
}