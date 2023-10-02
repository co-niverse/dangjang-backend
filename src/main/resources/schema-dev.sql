DROP TABLE IF EXISTS DISEASE;
DROP TABLE IF EXISTS NOTIFICATION;
DROP TABLE IF EXISTS DEVICE;
DROP TABLE IF EXISTS HEALTH_METRIC;
-- DROP TABLE IF EXISTS CODE;
DROP TABLE IF EXISTS DANGJANG_CLUB;
DROP TABLE IF EXISTS USERS;

CREATE TABLE `USERS`
(
    `OAUTH_ID`            varchar(50) NOT NULL,
    `NICKNAME`            varchar(8)  NOT NULL,
    `OAUTH_PROVIDER`      varchar(10) NOT NULL,
    `GENDER`              varchar(1)  NOT NULL,
    `BIRTHDAY`            date        NOT NULL,
    `ACTIVITY_AMOUNT`     varchar(10) NOT NULL,
    `HEIGHT`              int         NOT NULL,
    `ROLE`                varchar(10) NOT NULL,
    `RECOMMENDED_CALORIE` int         NOT NULL,
    `STATUS`              varchar(10) NOT NULL,
    `CREATED_AT`          datetime    NOT NULL,
    `UPDATED_AT`          datetime    NOT NULL,
    `PROFILE_IMAGE_PATH`  varchar(255),
    `DIABETIC`            boolean     NOT NULL,
    `DIABETES_YEAR`       int,
    `MEDICINE`            boolean,
    `INJECTION`           boolean,
    PRIMARY KEY (`OAUTH_ID`),
    UNIQUE (`NICKNAME`)
);

CREATE TABLE `DANGJANG_CLUB`
(
    `OAUTH_ID`   varchar(50) NOT NULL,
    `START_DATE` date        NOT NULL,
    PRIMARY KEY (`OAUTH_ID`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`)
);

-- CREATE TABLE `CODE`
-- (
--     `CODE`       varchar(20) NOT NULL,
--     `GROUP_CODE` varchar(20),
--     `ENG_NAME`   varchar(20) NOT NULL,
--     `KOR_NAME`   varchar(20) NOT NULL,
--     PRIMARY KEY (`CODE`)
-- );

CREATE TABLE `HEALTH_METRIC`
(
    `OAUTH_ID`   varchar(50) NOT NULL,
    `CREATED_AT` date        NOT NULL,
    `TYPE`       varchar(20) NOT NULL,
    `GROUP_CODE` varchar(20) NOT NULL,
    `UNIT`       varchar(20) NOT NULL,
    PRIMARY KEY (`OAUTH_ID`, `CREATED_AT`, `TYPE`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`)
    -- FOREIGN KEY (`CODE`) REFERENCES CODE (`CODE`)
);

CREATE TABLE `DEVICE`
(
    `OAUTH_ID` varchar(50) NOT NULL,
    `CODE`     varchar(20) NOT NULL,
    PRIMARY KEY (`OAUTH_ID`, `CODE`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`)
    -- FOREIGN KEY (`CODE`) REFERENCES CODE (`CODE`)
);

CREATE TABLE `NOTIFICATION`
(
    `OAUTH_ID` varchar(50) NOT NULL,
    `CODE`     varchar(20) NOT NULL,
    PRIMARY KEY (`OAUTH_ID`, `CODE`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`)
    -- FOREIGN KEY (`CODE`) REFERENCES CODE (`CODE`)
);

CREATE TABLE `DISEASE`
(
    `OAUTH_ID` varchar(50) NOT NULL,
    `CODE`     varchar(20) NOT NULL,
    PRIMARY KEY (`OAUTH_ID`, `CODE`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`)
-- FOREIGN KEY (`CODE`) REFERENCES CODE (`CODE`)
);
