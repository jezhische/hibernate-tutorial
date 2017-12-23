use hb_student_tracker;
drop table if exists instructor;
create table `instructor` (
`id` int(11) not null auto_increment,
first_name varchar(45) default null,
last_name varchar(45) default null,
instructor_detail_id int(11) default null,
primary key(`id`)
)