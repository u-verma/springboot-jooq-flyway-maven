package com.diamond.domain;

import com.diamond.common.UserStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Builder
@Setter
public class UserProfile {
    private UserId userId;
    private String fullName;
    private String emailId;
    private LocalDate dateOfBirth;
    private UserStatus status;
}
