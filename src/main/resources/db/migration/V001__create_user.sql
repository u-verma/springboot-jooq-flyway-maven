DROP TABLE IF EXISTS USER_CREDENTIALS;

DROP TABLE IF EXISTS USER_PROFILE;

CREATE TABLE USER_PROFILE
(
    user_id       VARCHAR(255) NOT NULL,
    full_name     VARCHAR(255) NOT NULL,
    email_id      VARCHAR(255),
    date_of_birth DATE,
    status        VARCHAR(255),
    created_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at    TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_id)
);

CREATE TABLE USER_CREDENTIAL
(
    user_id              VARCHAR(255) NOT NULL,
    user_name            VARCHAR(255) NOT NULL,
    password             VARCHAR(255),
    password_expiry_date DATE,
    created_at           TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    updated_at           TIMESTAMPTZ  NOT NULL DEFAULT NOW(),
    PRIMARY KEY (user_name),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES USER_PROFILE (user_id)
);