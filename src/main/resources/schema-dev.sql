DROP TABLE IF EXISTS DISEASE;
DROP TABLE IF EXISTS NOTIFICATION_SETTING;
DROP TABLE IF EXISTS NOTIFICATION;
DROP TABLE IF EXISTS USER_FCM_TOKEN;
DROP TABLE IF EXISTS NOTIFICATION_TYPE;
DROP TABLE IF EXISTS DEVICE;
DROP TABLE IF EXISTS HEALTH_METRIC;
DROP TABLE IF EXISTS DANGJANG_CLUB;
DROP TABLE IF EXISTS POINT_HISTORY;
DROP TABLE IF EXISTS PURCHASE_HISTORY;
DROP TABLE IF EXISTS POINT_PRODUCT;
DROP TABLE IF EXISTS USER_POINT;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS SHEDLOCK;
DROP TABLE IF EXISTS VERSION;

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
    `ACCESSED_AT`         date        NOT NULL,
    `HEALTH_CONNECT`      varchar(50) NOT NULL,
    `INACTIVATED_AT`      date,
    PRIMARY KEY (`OAUTH_ID`),
    UNIQUE (`NICKNAME`)
);

CREATE TABLE `DANGJANG_CLUB`
(
    `OAUTH_ID`   varchar(50) NOT NULL,
    `START_DATE` date        NOT NULL,
    PRIMARY KEY (`OAUTH_ID`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`) ON DELETE CASCADE
);

CREATE TABLE `HEALTH_METRIC`
(
    `OAUTH_ID`   varchar(50) NOT NULL,
    `CREATED_AT` date        NOT NULL,
    `TYPE`       varchar(20) NOT NULL,
    `GROUP_CODE` varchar(20) NOT NULL,
    `UNIT`       varchar(20) NOT NULL,
    PRIMARY KEY (`OAUTH_ID`, `CREATED_AT`, `TYPE`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`) ON DELETE CASCADE
);

CREATE TABLE `DEVICE`
(
    `OAUTH_ID` varchar(50) NOT NULL,
    `CODE`     varchar(20) NOT NULL,
    PRIMARY KEY (`OAUTH_ID`, `CODE`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`) ON DELETE CASCADE
);

CREATE TABLE `NOTIFICATION_SETTING`
(
    `OAUTH_ID` varchar(50) NOT NULL,
    `CODE`     varchar(20) NOT NULL,
    PRIMARY KEY (`OAUTH_ID`, `CODE`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`) ON DELETE CASCADE
);

CREATE TABLE `DISEASE`
(
    `OAUTH_ID` varchar(50) NOT NULL,
    `CODE`     varchar(20) NOT NULL,
    PRIMARY KEY (`OAUTH_ID`, `CODE`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`) ON DELETE CASCADE
);

CREATE TABLE `POINT_PRODUCT`
(
    `PRODUCT_NAME` varchar(255) NOT NULL,
    `POINT`        int,
    `TYPE`         varchar(20)  NOT NULL,
    `DESCRIPTION`  varchar(255),
    PRIMARY KEY (`PRODUCT_NAME`)
);

CREATE TABLE `POINT_HISTORY`
(
    `OAUTH_ID`      varchar(50)  NOT NULL,
    `CREATED_AT`    dateTime     NOT NULL,
    `PRODUCT_NAME`  varchar(255) NOT NULL,
    `CHANGE_POINT`  int,
    `BALANCE_POINT` int,
    PRIMARY KEY (`OAUTH_ID`, `CREATED_AT`, `PRODUCT_NAME`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`) ON DELETE CASCADE,
    FOREIGN KEY (`PRODUCT_NAME`) REFERENCES POINT_PRODUCT (`PRODUCT_NAME`)
);

CREATE TABLE `USER_POINT`
(
    `OAUTH_ID` varchar(50) NOT NULL,
    `POINT`    int,
    `VERSION`  bigint,
    PRIMARY KEY (`OAUTH_ID`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`) ON DELETE CASCADE
);

CREATE TABLE `PURCHASE_HISTORY`
(
    `OAUTH_ID`     varchar(50)  NOT NULL,
    `PRODUCT_NAME` varchar(255) NOT NULL,
    `CREATED_AT`   dateTime     NOT NULL,
    `PHONE`        varchar(15)  NOT NULL,
    `NAME`         varchar(20)  NOT NULL,
    `COMMENT`      varchar(255),
    `COMPLETED`    boolean,
    PRIMARY KEY (`OAUTH_ID`, `PRODUCT_NAME`, `CREATED_AT`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`) ON DELETE CASCADE,
    FOREIGN KEY (`PRODUCT_NAME`) REFERENCES POINT_PRODUCT (`PRODUCT_NAME`)
);


CREATE TABLE `USER_FCM_TOKEN`
(
    `OAUTH_ID`   varchar(50)  NOT NULL,
    `FCM_TOKEN`  varchar(255) NOT NULL,
    `CREATED_AT` dateTime     NOT NULL,
    PRIMARY KEY (`FCM_TOKEN`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`) ON DELETE CASCADE
);

CREATE TABLE `NOTIFICATION_TYPE`
(
    `TYPE` varchar(20) NOT NULL,
    PRIMARY KEY (`TYPE`)
);

CREATE TABLE `NOTIFICATION`
(
    `NOTIFICATION_ID` bigint       NOT NULL AUTO_INCREMENT,
    `TITLE`           varchar(255) NOT NULL,
    `CONTENT`         varchar(255) NOT NULL,
    `OAUTH_ID`        varchar(50)  NOT NULL,
    `TYPE`            varchar(20)  NOT NULL,
    `CREATED_AT`      dateTime     NOT NULL,
    `IS_READ`         boolean      NOT NULL,
    PRIMARY KEY (`NOTIFICATION_ID`),
    FOREIGN KEY (`OAUTH_ID`) REFERENCES USERS (`OAUTH_ID`) ON DELETE CASCADE,
    FOREIGN KEY (`TYPE`) REFERENCES NOTIFICATION_TYPE (`TYPE`)
);

CREATE TABLE `SHEDLOCK`
(
    `NAME`       VARCHAR(64),
    `LOCK_UNTIL` TIMESTAMP(3) NULL,
    `LOCKED_AT`  TIMESTAMP(3) NULL,
    `LOCKED_BY`  VARCHAR(255),
    PRIMARY KEY (NAME)
);

CREATE TABLE `VERSION`
(
    `VERSION_ID`     bigint      NOT NULL AUTO_INCREMENT,
    `MIN_VERSION`    varchar(10) NOT NULL,
    `LATEST_VERSION` varchar(10) NOT NULL,
    `CREATED_AT`     datetime    NOT NULL,
    `UPDATED_AT`     datetime    NOT NULL,
    PRIMARY KEY (`VERSION_ID`)
)