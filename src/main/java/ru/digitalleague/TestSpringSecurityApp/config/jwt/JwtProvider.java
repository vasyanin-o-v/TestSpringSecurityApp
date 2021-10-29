package ru.digitalleague.TestSpringSecurityApp.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.digitalleague.TestSpringSecurityApp.config.SecurityConfig;
import ru.digitalleague.TestSpringSecurityApp.entity.RoleEntity;

import java.util.Date;

/**
 * Класс, который реализует логику создания и валидации токена
 */
@Component
public class JwtProvider {

    private static final long HOUR = 3600*1000;

    /**
     * Кодовое слово для подписи в JWT
     */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Создание токена
     */
    public String generateToken(String login, RoleEntity role) {
        Claims claims = Jwts.claims().setSubject(login);
        claims.put("role", role.getName());

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        Date expirationDate = new Date(now.getTime() + HOUR);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .setHeaderParam("typ", "JWT")
                .compact();
    }

    /**
     * Валидация токена
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Извлечение информации о пользователе из токена, в данном случае - логина
     * Логин помещен в Subject в методе generateToken
     */
    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    /**
     * Бин для кодировки пароля
     * Перенесен из {@link SecurityConfig}, чтобы избавить от циклической зависимости бинов
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
