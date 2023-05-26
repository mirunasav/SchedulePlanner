create table Users (
    ID serial primary key,
    Username varchar(255) NOT NULL,
    PasswordHash varchar(255) NOT NULL,
    Salt varchar(255) NOT NULL
)

create sequence