use registration;

create table countryinfo(
	id INT NOT NULL AUTO_INCREMENT,
    country_name varchar(128),
    currency_name varchar(128),
    currency_symbol varchar(128),
    area int,
    capital varchar(128),
    region varchar(128),
    subregion varchar(128),
    languages varchar(128),
    maps varchar(128),
    population int,
    flags varchar(128),
    date Date,
    primary key (id),
    username varchar(128) references account(username)
);