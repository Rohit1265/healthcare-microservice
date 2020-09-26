CREATE SCHEMA centene;

CREATE TABLE centene.enrollee (
    id BIGINT(20) auto_increment primary key NOT NULL,
    name varchar(45) not null,
    phone_number BIGINT(20) null,
    activation_status BIT(1) default false,
    birth_date date,
    created_date datetime,
    updated_date datetime
);

CREATE TABLE centene.dependent (
    id BIGINT(20) auto_increment primary key NOT NULL,
    name varchar(45) not null,
    birth_date date,
    enrollee_id BIGINT(20),
    created_date datetime,
    updated_date datetime,
    CONSTRAINT enrollee_id FOREIGN KEY (enrollee_id) REFERENCES enrollee(id)
);
