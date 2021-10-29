package ru.digitalleague.TestSpringSecurityApp.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.digitalleague.TestSpringSecurityApp.entity.UserEntity;

import java.util.Collection;
import java.util.Collections;

/**
 * Часть стандартной реализации Spring Security
 * Данный класс реализовывает {@link UserDetails}, который трансформирует объекты из базы данных в объекты, подходящие для Spring Security
 */
@Component
public class JwtUserDetails implements UserDetails {

    private String login;
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static JwtUserDetails fromUserEntityToJwtUserDetails(UserEntity userEntity) {
        JwtUserDetails jwtUserDetails = new JwtUserDetails();
        jwtUserDetails.login = userEntity.getLogin();
        jwtUserDetails.password = userEntity.getPassword();
        jwtUserDetails.grantedAuthorities = Collections.singletonList(
                new SimpleGrantedAuthority(userEntity.getRole().getName())
        );
        return jwtUserDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
