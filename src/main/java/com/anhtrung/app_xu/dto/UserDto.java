package com.anhtrung.app_xu.dto;

import com.anhtrung.app_xu.domain.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter @Builder
public class UserDto {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Set<Role> roles;
    private boolean enabled;
}


