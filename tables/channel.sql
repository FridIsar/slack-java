Create table channel(
	name varchar(255),
    emailAdmin varchar(255),
    createdAt date,
    primary key (titre),
    foreign key (emailAdmin) references user(email)
)

