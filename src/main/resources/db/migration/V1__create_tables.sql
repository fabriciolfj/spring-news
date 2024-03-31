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