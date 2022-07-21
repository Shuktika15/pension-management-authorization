package dev.shuktika.authorization.service;

import dev.shuktika.authorization.model.JWTResponse;
import dev.shuktika.authorization.repository.PensionerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PensionerService implements UserDetailsService {
    private final PensionerRepository pensionerRepository;
    private final PasswordEncoder passwordEncoder;

    public JWTResponse getPensioner() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        var pensioner = pensionerRepository.findById(Long.valueOf(username))
                .orElseThrow(() -> {
                    var errorMsg = String.format("PensionerService.getPensioner () :" +
                            "Pensioner not found with aadhar number %s", username);
                    log.error(errorMsg);
                    return new UsernameNotFoundException(errorMsg);
                });
        return JWTResponse.builder()
                .aadharNumber(pensioner.getAadharNumber())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return pensionerRepository.findById(Long.parseLong(username)).orElseThrow(() -> {
            String errMsg = String.format("Pensioner with Aadhar Number %s not found", username);
            log.error(errMsg);
            return new UsernameNotFoundException(errMsg);
        });
    }

}
