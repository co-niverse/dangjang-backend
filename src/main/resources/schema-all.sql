DROP TABLE IF EXISTS HEALTH_METRIC;
DROP TABLE IF EXISTS USERS;

CREATE TABLE `USERS`
(
    `OAUTH_ID`            varchar(50) NOT NULL,
    `OAUTH_PROVIDER`      varchar(10) NOT NULL,
    `NICKNAME`            varchar(15) NOT NULL,
    `GENDER`              varchar(1)  NOT NULL,
    `BIRTHDAY`            date        NOT NULL,
    `ACTIVITY_AMOUNT`     varchar(10) NOT NULL,
    `HEIGHT`              int         NOT NULL,
    `RECOMMENDED_CALORIE` int         NOT NULL,
    `STATUS`              varchar(10) NOT NULL,
    `CREATED_AT`          datetime    NOT NULL,
    `UPDATED_AT`          datetime    NOT NULL,
    `PROFILE_IMAGE_PATH`  varchar(255),
--     PRIMARY KEY (`OAUTH_ID`, `OAUTH_PROVIDER`),
    PRIMARY KEY (`OAUTH_ID`),
    UNIQUE (`NICKNAME`)
);

CREATE TABLE `HEALTH_METRIC`
(
    `OAUTH_ID`           varchar(50) NOT NULL,
    `CREATED_AT`         date        NOT NULL,
    `HEALTH_METRIC_CODE` varchar(30) NOT NULL,
    `HEALTH_METRIC_TYPE` varchar(30) NOT NULL,
    `UNIT`               varchar(20) NOT NULL,
--     `OAUTH_PROVIDER`     varchar(10) NOT NULL,
--     PRIMARY KEY (`CREATED_AT`, `HEALTH_METRIC_CODE`, `HEALTH_METRIC_TYPE`, `OAUTH_ID`, `OAUTH_PROVIDER`),
    PRIMARY KEY (`CREATED_AT`, `HEALTH_METRIC_CODE`, `HEALTH_METRIC_TYPE`, `OAUTH_ID`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`)
);
