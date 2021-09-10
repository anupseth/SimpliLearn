drop table if exists class
drop table if exists instructor
drop table if exists Student
drop table if exists Subject
drop table if exists Teacher
create table class (id integer not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
create table instructor (id integer not null auto_increment, country varchar(255), email varchar(255), name varchar(255), primary key (id)) engine=InnoDB
create table Student (id integer not null auto_increment, name varchar(255), fk_classes integer, primary key (id)) engine=InnoDB
create table Subject (id integer not null auto_increment, name varchar(255), fk_class integer, fk_teacher integer, primary key (id)) engine=InnoDB
create table Teacher (id integer not null auto_increment, name varchar(255), primary key (id)) engine=InnoDB
alter table Student add constraint FK2wyo0ph3qxqej4o75n58h6iw2 foreign key (fk_classes) references class (id)
alter table Subject add constraint FKe10f2043tt2khxhyu9b4g7syw foreign key (fk_class) references class (id)
alter table Subject add constraint FK78k121owj8y38y8h9xi1jk08h foreign key (fk_teacher) references Teacher (id)

insert into class values (11,'TestCalss1');