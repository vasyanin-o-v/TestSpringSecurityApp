package ru.digitalleague.TestSpringSecurityApp.config.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import ru.digitalleague.TestSpringSecurityApp.config.JwtUserDetails;
import ru.digitalleague.TestSpringSecurityApp.config.JwtUserDetailsService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Класс фильтр, в котором происходит проверка токена пользователя
 */
@Component
public class JwtFilter extends GenericFilterBean {

    public static String AUTHORIZATION = "Authorization";

    private final JwtProvider jwtProvider;
    private final JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    public JwtFilter(JwtProvider jwtProvider, JwtUserDetailsService jwtUserDetailsService) {
        this.jwtProvider = jwtProvider;
        this.jwtUserDetailsService = jwtUserDetailsService;
    }

    /**
     * Проверка токена, создание объекта класса {@link UsernamePasswordAuthenticationToken} и передача его в SecurityContextHolder
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        if (token != null && jwtProvider.validateToken(token)) {
            String userLogin = jwtProvider.getLoginFromToken(token);
            JwtUserDetails jwtUserDetails = jwtUserDetailsService.loadUserByUsername(userLogin);
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(jwtUserDetails, null, jwtUserDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * Получение токена из запроса, в данном случае из хидера
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}
