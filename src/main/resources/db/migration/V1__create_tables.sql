create table bookmarks
(
    id         bigserial primary key,
    title      varchar   not null,
    url        varchar   not null,
    created_at timestamp
);

create table product
(
    id      bigserial PRIMARY KEY,
    name    varchar(50),
    price   decimal
);


create table articles
(
    id          bigserial primary key,
    title       varchar(50),
    content     varchar(50),
    language    varchar(50)
);


CREATE TABLE student (
    student_id bigserial PRIMARY KEY,
    admit_year VARCHAR(4),
    address jsonb
);

CREATE INDEX idx_postcode ON student USING HASH((address->'postCode'));


CREATE TABLE Owner (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY
);

CREATE TABLE Pet (
                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                     birth_date DATE,
                     type VARCHAR(255),
                     owner_id BIGINT,
                     CONSTRAINT FK_Pet_Owner FOREIGN KEY (owner_id) REFERENCES Owner(id)
);