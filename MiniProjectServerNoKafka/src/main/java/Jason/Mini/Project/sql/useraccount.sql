create database registration;
use registration;

create table account(
	user_id int auto_increment,
    is_enabled BOOLEAN,
    email varchar(128),
    username varchar(128),
    password varchar(128),
    last_login varchar(128),
    num_failed_login_attempt int,
    primary key (user_id)
);