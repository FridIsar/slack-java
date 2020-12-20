CREATE TABLE users (
    id INT,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    username VARCHAR(255),
    creation_date DATE,
    PRIMARY KEY(id)
);

CREATE TABLE channels (
    id INT,
    name VARCHAR(255) UNIQUE,
    admin_id INT,
    creation_date DATE,
    PRIMARY KEY(id),
    FOREIGN KEY (admin_id) REFERENCES users(id)
);

CREATE TABLE friends (
     pers1_id INT,
     pers2_id INT,
     PRIMARY KEY(pers1_id, pers2_id),
     FOREIGN KEY(pers1_id) REFERENCES users(id),
     FOREIGN KEY(pers2_id) REFERENCES users(id)
);

CREATE TABLE chats (
     id INT,
     PRIMARY KEY(id)
);

CREATE TABLE members (
     chat_id INT,
     user_id INT,
     PRIMARY KEY(chat_id, user_id),
     FOREIGN KEY(user_id) REFERENCES users(id),
     FOREIGN KEY(chat_id) REFERENCES chats(id)
);

