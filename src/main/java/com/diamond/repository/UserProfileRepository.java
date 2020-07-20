package com.diamond.repository;

import com.diamond.common.UserStatus;
import com.diamond.databases.model.tables.records.UserProfileRecord;
import com.diamond.domain.UserId;
import com.diamond.domain.UserProfile;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;
import java.util.function.Function;

import static com.diamond.databases.model.tables.UserProfile.USER_PROFILE;

@Repository
public class UserProfileRepository {

    private final DSLContext dslContext;

    public UserProfileRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public boolean save(UserProfile userProfile) {
        return 1 == dslContext
                .insertInto(USER_PROFILE)
                .set(UserProfileToUserProfileRecord.apply(userProfile))
                .onConflict()
                .doUpdate()
                .set(USER_PROFILE.STATUS, userProfile.getStatus().name())
                .execute();
    }

    public UserProfile fetch(String userId) {
        return UserProfileRecordToUserProfile.apply(
                dslContext.selectFrom(USER_PROFILE)
                        .where(USER_PROFILE.USER_ID.eq(userId))
                        .fetchOne()
        );
    }

    public boolean delete(String userId) {
        return 1 == dslContext.deleteFrom(USER_PROFILE)
                .where(USER_PROFILE.USER_ID.eq(userId))
                .execute();
    }

    private Function<UserProfileRecord, UserProfile> UserProfileRecordToUserProfile
            = record -> UserProfile.builder()
            .userId(UserId.builder().value(UUID.fromString(record.getUserId())).build())
            .fullName(record.getFullName())
            .emailId(record.getEmailId())
            .dateOfBirth(record.getDateOfBirth())
            .status(UserStatus.valueOf(record.getStatus())).build();

    private Function<UserProfile, UserProfileRecord> UserProfileToUserProfileRecord
            = userProfile -> new UserProfileRecord(
            userProfile.getUserId().getValue().toString(),
            userProfile.getFullName(),
            userProfile.getEmailId(),
            userProfile.getDateOfBirth(),
            userProfile.getStatus().name(),
            OffsetDateTime.now(ZoneId.of("UTC")),
            OffsetDateTime.now(ZoneId.of("UTC"))
    );
}
