package dev.shuktika.authorization.controller;

import dev.shuktika.authorization.entity.Pensioner;
import dev.shuktika.authorization.model.PensionerDetails;
import dev.shuktika.authorization.service.PensionerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
    public ResponseEntity<PensionerDetails> authenticate() {
        return ResponseEntity.ok(pensionerService.getPensioner());
    }

    @PostMapping("/pensioners")
    public ResponseEntity<List<Pensioner>> addPensioners(@RequestBody List<Pensioner> pensioners) {
        return new ResponseEntity<>(pensionerService.addPensioners(pensioners), HttpStatus.CREATED);
    }
}
