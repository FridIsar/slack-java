CREATE TABLE AMIS(
id_pers1 varchar(255),
id_pers2 varchar(255),
primary key(id_pers1, id_pers2),
foreign key(id_pers1) references user(id),
foreign key(id_pers1) references user(id)
);