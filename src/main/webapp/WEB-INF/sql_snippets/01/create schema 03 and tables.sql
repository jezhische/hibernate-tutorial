drop schema if exists `hb-03-one-to-many`;
create schema `hb-03-one-to-many`;

use `hb-03-one-to-many`;

set foreign_key_checks=0;
drop table if exists `instructor_detail`;
create table `instructor_detail` (
`id` int(11) not null auto_increment,
youtube_channel varchar(45) default null,
hobby varchar(45) default null,
primary key (id)
) engine=innodb auto_increment=1 default charset=latin1;

drop table if exists `instructor`;
create table `instructor` (
`id` int(11) not null auto_increment,
first_name varchar(45) default null,
last_name varchar(45) default null,
instructor_detail_id int(11) default null,

primary key (id),
key fk_detail_idx (`instructor_detail_id`),
constraint fk_detail foreign key (instructor_detail_id) references instructor_detail (id)
on delete no action on update no action
) engine=innodb auto_increment=1 default charset=latin1;

drop table if exists `course`;
create table `course` (
id int(11) not null auto_increment,
title varchar(128) default null,
instructor_id int(11),
primary key (id),
-- unique key prevents duplicate of corses title
unique key title_unique (title),
key fk_instructor_idx (instructor_id),
constraint fk_instructor foreign key(instructor_id) references instructor(id)
on delete no action on update no action
) engine=innodb auto_increment=10 default charset=latin1;

set foreign_key_checks=1;