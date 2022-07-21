package dev.shuktika.authorization.controller;

import dev.shuktika.authorization.model.JWTResponse;
import dev.shuktika.authorization.service.PensionerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authorization")
public class AuthorizationController {
    private final PensionerService pensionerService;

    @PostMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        response.sendRedirect("/login");
    }

    @GetMapping("/authenticate")
    public ResponseEntity<JWTResponse> authenticate() {
        return ResponseEntity.ok(pensionerService.getPensioner());
    }
}
