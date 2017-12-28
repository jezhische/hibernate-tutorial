use `hb-01-one-to-one-uni`;
-- SET FOREIGN_KEY_CHECKS=0 отключает проверку (ограничение) внешних ключей,
-- которое не позволяет удалить таблицу, связанную с другой через внешний ключ.
set foreign_key_checks=0;
drop table if exists bi_instructor_detail;
create table `bi_instructor_detail` (
`id` int(11) not null auto_increment,
`bi_youtube_channel` varchar(128) default null,
`bi_hobby` varchar(45) default null,
primary key(`id`)
) 
engine=innodb auto_increment=1 default charset=latin1;

drop table if exists bi_instructor;
create table `bi_instructor` (
`id` int(11) not null auto_increment,
bi_first_name varchar(45) default null,
bi_last_name varchar(45) default null,
bi_instructor_detail_id int(11) default null,
primary key(`id`),
key `FK_DETAIL_idx` (`bi_instructor_detail_id`),
-- "ограничение `FK_DETAIL` представляет из себя: 
-- внешний ключ(поле `instructor_detail_id`),
-- который ссылается на таблицу `instructor_detail`(на поле `id`)"
constraint `FK_DETAIL` foreign key (`bi_instructor_detail_id`) 
references `bi_instructor_detail` (`id`)
on delete no action on update no action
) 
engine=innodb auto_increment=1 default charset=latin1;
set foreign_key_checks=1;