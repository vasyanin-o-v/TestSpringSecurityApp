package ru.digitalleague.TestSpringSecurityApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.digitalleague.TestSpringSecurityApp.config.jwt.JwtProvider;
import ru.digitalleague.TestSpringSecurityApp.entity.AuthRequest;
import ru.digitalleague.TestSpringSecurityApp.entity.AuthResponse;
import ru.digitalleague.TestSpringSecurityApp.entity.RegistrationRequest;
import ru.digitalleague.TestSpringSecurityApp.entity.UserEntity;
import ru.digitalleague.TestSpringSecurityApp.service.UserService;

/**
 * Контроллер с регистрацией и авторизацией
 */
@RestController
public class AuthController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthController(UserService userService, JwtProvider jwtProvider) {
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        UserEntity user = new UserEntity();
        user.setPassword(request.getPassword());
        user.setLogin(request.getLogin());
        userService.saveUser(user);
        return ResponseEntity.ok().body("New user has been created: " + user.getLogin());
    }

    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> auth(@RequestBody AuthRequest request) {
        UserEntity user = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(user.getLogin(), user.getRole());
        return ResponseEntity.ok().body(new AuthResponse(token));
    }
}
