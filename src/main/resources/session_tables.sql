create table SPRING_SESSION
(
    PRIMARY_ID            CHAR(36) not null,
    SESSION_ID            CHAR(36) not null,
    CREATION_TIME         BIGINT   not null,
    LAST_ACCESS_TIME      BIGINT   not null,
    MAX_INACTIVE_INTERVAL INTEGER  not null,
    EXPIRY_TIME           BIGINT   not null,
    PRINCIPAL_NAME        VARCHAR(300),
    constraint SPRING_SESSION_PK
        primary key (PRIMARY_ID)
);
create unique index SPRING_SESSION_IX1 on SPRING_SESSION (SESSION_ID);
create index SPRING_SESSION_IX2 on SPRING_SESSION (EXPIRY_TIME);
create index SPRING_SESSION_IX3 on SPRING_SESSION (PRINCIPAL_NAME);

create table SPRING_SESSION_ATTRIBUTES
(
    SESSION_PRIMARY_ID CHAR(36)     not null,
    ATTRIBUTE_NAME     VARCHAR(200) not null,
    ATTRIBUTE_BYTES    VARBINARY    not null,
    constraint SPRING_SESSION_ATTRIBUTES_PK
        primary key (SESSION_PRIMARY_ID, ATTRIBUTE_NAME),
    constraint SPRING_SESSION_ATTRIBUTES_FK
        foreign key (SESSION_PRIMARY_ID) references SPRING_SESSION (PRIMARY_ID)
            on delete cascade
);