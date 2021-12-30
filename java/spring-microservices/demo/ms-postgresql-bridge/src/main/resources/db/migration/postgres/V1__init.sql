CREATE TABLE tweets (
    id bigint primary key,
    user_alias varchar(200),
    email varchar(200),
    text varchar(2000),
    retweet boolean,
    favorite_count int,
    registration_date timestamptz
);