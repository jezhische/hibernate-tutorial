use hb_student_tracker;
drop table if exists `dataEntity`;
create table `dataEntity` (
id int(11) not null auto_increment,
tname varchar(45) default null,
someDate datetime default null,
`timeStamp` datetime(3) DEFAULT CURRENT_TIMESTAMP(3),
-- MySQL converts TIMESTAMP values from the current time zone to UTC for storage, 
-- and back from UTC to the current time zone for retrieval. (This does not occur for other types such as DATETIME.)
primary key (id)
) engine=innodb auto_increment=1 default charset=latin1;