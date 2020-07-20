package com.diamond.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserCredential {
    private UserId userId;
    private String userName;
    private String password;
    private LocalDate passwordExpiryDate;
}
