use `golovach2013`; 

-- drop table if exists `s_user`;

create table `s_user` (
id int(11) not null auto_increment,
login varchar(45) default null,
`name` varchar(45) default null,
email varchar(45) default null,
primary key (`id`)
) engine=innodb auto_increment=1 default charset=latin1;