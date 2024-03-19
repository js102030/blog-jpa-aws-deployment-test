package com.estsoft.blogjpa.domain.user.service;

import com.estsoft.blogjpa.domain.user.dto.JoinRequest;
import com.estsoft.blogjpa.domain.user.entity.User;
import com.estsoft.blogjpa.domain.user.enumType.Role;
import com.estsoft.blogjpa.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User save(JoinRequest joinRequest) {
        log.info("save()");

        if (userRepository.existsByEmail(joinRequest.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 email입니다.");
        }

        return userRepository.save(
                User.builder()
                        .email(joinRequest.getEmail())
                        .password(passwordEncoder.encode(joinRequest.getPassword()))
                        .role(Role.USER)
                        .build()
        );
    }
}
