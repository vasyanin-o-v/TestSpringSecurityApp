package ru.digitalleague.TestSpringSecurityApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.digitalleague.TestSpringSecurityApp.entity.UserEntity;
import ru.digitalleague.TestSpringSecurityApp.service.UserService;

/**
 * Вспомогательный класс для {@link UserDetails}
 * Реализовывает интерфейс {@link UserDetailsService} с единсвтенным методом - loadUserByUsername
 */
@Component
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Получение user по логину и конвертация его в JwtUserDetails
     */
    @Override
    public JwtUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.findByLogin(username);
        return JwtUserDetails.fromUserEntityToJwtUserDetails(userEntity);
    }
}
