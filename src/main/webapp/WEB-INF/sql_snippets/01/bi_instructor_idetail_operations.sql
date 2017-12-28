use `hb-01-one-to-one-uni`;
select * from bi_instructor_detail;
select * from bi_instructor;
set foreign_key_checks=0;
drop table  `hb-01-one-to-one-uni`.`bi_instructor`;
drop table  `hb-01-one-to-one-uni`.`bi_instructor_detail`;
set foreign_key_checks=1;