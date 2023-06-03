create table Users (
    ID serial primary key,
    Username varchar(255) NOT NULL,
    PasswordHash varchar(255) NOT NULL,
    Salt varchar(255) NOT NULL
);

create table Schedules(
    ScheduleID serial primary key,
    UserID int not null,
    constraint fk_user
                      FOREIGN KEY (UserID)
                            REFERENCES users(ID)
);

create table ScheduleTasks(
    ID serial primary key,
    ScheduleID int not null,
    Name varchar(50) not null,
    DurationMinutes int not null,
    StartTime Date,
    EndTime Date,
    constraint fk_ScheduleID
         FOREIGN KEY (ScheduleID)
         REFERENCES Schedules(ScheduleID)
);
alter table Schedules
    rename column UserID to user_id;
insert into Schedules values (1,2)
select *
from Schedules
select * from schedules;
drop table scheduletasks;
drop  table  schedule_activities


drop table users

select * from schedules