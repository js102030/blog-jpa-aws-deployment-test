package com.estsoft.blogjpa.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
    private String email;
    private String password;

    public JoinRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
