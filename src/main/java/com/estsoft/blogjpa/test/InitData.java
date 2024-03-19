package com.estsoft.blogjpa.test;

import com.estsoft.blogjpa.domain.user.dto.JoinRequest;
import com.estsoft.blogjpa.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class InitData {
    private final UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        log.info("init()");
        JoinRequest joinRequest = new JoinRequest("esns79@naver.com", "wnstjr4231!");
        userService.save(joinRequest);
    }
}
