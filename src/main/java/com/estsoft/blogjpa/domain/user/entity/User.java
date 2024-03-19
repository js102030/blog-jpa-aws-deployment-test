package com.estsoft.blogjpa.domain.user.entity;

import com.estsoft.blogjpa.domain.user.enumType.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;

    // email 정규표현식으로 검증
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식이 아닙니다.")
    @Column(nullable = false, unique = true)
    private String email;

    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{12,}$", message = "12자리 이상의 영문, 숫자, 특수문자를 혼합하여 사용해야 합니다.")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String email, String password, Role role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
////        return List.of(new SimpleGrantedAuthority("user"));
//        log.info("getAuthorities()");
//
//        Collection<GrantedAuthority> authorities = new ArrayList<>();
//
//        authorities.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return "ROLE_" + role;
//            }
//        });
//
//        return authorities;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    // 계정 만료 여부 반환 (true: 만료 안됨)
//    @Override
//    public boolean isAccountNonExpired() {
//
//        return true;
//    }
//
//    // 계정 잠금 여부 반환 (true: 잠금 안됨)
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    // 패스워드의 만료 여부 반환 (true: 만료 안됨)
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    // 계정 사용 여부 반환 (true: 사용 가능)
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
}