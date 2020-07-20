package com.diamond.repository;

import com.diamond.databases.model.tables.records.UserCredentialRecord;
import com.diamond.domain.UserCredential;
import com.diamond.domain.UserId;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.UUID;
import java.util.function.Function;

import static com.diamond.databases.model.tables.UserCredential.USER_CREDENTIAL;

@Repository
public class UserCredentialRepository {

    private final DSLContext dslContext;

    public UserCredentialRepository(DSLContext dslContext) {
        this.dslContext = dslContext;
    }

    public boolean save(UserCredential userCredential) {
        return 1 == dslContext
                .insertInto(USER_CREDENTIAL)
                .set(userCredentialToUserCredentialRecord.apply(userCredential))
                .onConflict()
                .doNothing()
                .execute();
    }

    public UserCredential fetch(String userName) {
        return userCredentialRecordToUserCredential.apply(
                dslContext.selectFrom(USER_CREDENTIAL)
                        .where(USER_CREDENTIAL.USER_NAME.eq(userName))
                        .fetchOne()
        );
    }

    public boolean delete(String userName) {
        return 1 == dslContext.deleteFrom(USER_CREDENTIAL)
                .where(USER_CREDENTIAL.USER_NAME.eq(userName))
                .execute();
    }

    private Function<UserCredentialRecord, UserCredential> userCredentialRecordToUserCredential
            = record -> UserCredential.builder()
            .userId(UserId.builder().value(UUID.fromString(record.getUserId())).build())
            .userName(record.getUserName())
            .password(record.getPassword())
            .passwordExpiryDate(record.getPasswordExpiryDate()).build();

    private Function<UserCredential, UserCredentialRecord> userCredentialToUserCredentialRecord
            = userCredential -> new UserCredentialRecord(
            userCredential.getUserId().getValue().toString(),
            userCredential.getUserName(),
            userCredential.getPassword(),
            userCredential.getPasswordExpiryDate(),
            OffsetDateTime.now(ZoneId.of("UTC")),
            OffsetDateTime.now(ZoneId.of("UTC"))
    );
}
