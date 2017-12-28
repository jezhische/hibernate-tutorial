use `hb-01-one-to-one-uni`;
select * from instructor_detail;
select * from instructor;

select * from  instructor where instructor_detail_id=1;

set foreign_key_checks=0;
truncate instructor_detail;
truncate instructor;
set foreign_key_checks=1;

drop table instructor_detail;
drop table instructor;