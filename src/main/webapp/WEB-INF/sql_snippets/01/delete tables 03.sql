use `hb-03-one-to-many`;

set foreign_key_checks=0;
drop table if exists instructor_detail;
drop table if exists instructor;
drop table if exists `course`;
drop table if exists review;
drop table if exists student;
drop table if exists course_student;
set foreign_key_checks=1;