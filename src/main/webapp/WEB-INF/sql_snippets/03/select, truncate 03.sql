use `hb-03-one-to-many`;

select * from instructor;
select * from instructor_detail;
select * from course;
select * from review;
select * from student;
select * from course_student;

select * from course c where c.instructor_id=3;
select * from instructor i JOIN course where i.id=3;

set foreign_key_checks=0;
truncate instructor;
truncate instructor_detail;
truncate course;
set foreign_key_checks=1;