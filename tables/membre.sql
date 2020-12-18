CREATE TABLE (
id_chat BIGINT,
id_user varchar(255),
primary key(id_chat, id_pers),
foreign key(id_user) references user(id),
foreign key(id_chat) references chat(id)
);
