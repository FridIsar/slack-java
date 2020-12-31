CREATE TABLE users(
    id INT AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    username VARCHAR(255),
    creation_date DATE,
    PRIMARY KEY(id)
);

CREATE TABLE channels (
    id INT AUTO_INCREMENT,
    name VARCHAR(255) UNIQUE,
    admin_id INT,
    creation_date DATE,
    PRIMARY KEY(id),
    FOREIGN KEY (admin_id) REFERENCES users(id)
);

CREATE TABLE usersChannelsRelation  (
    user_id INT NOT NULL,
    channel_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (channel_id) REFERENCES channels(id),
    UNIQUE (user_id, channel_id)
)

CREATE TABLE friends (
     usr1_id INT,
     usr2_id INT,
     PRIMARY KEY(usr1_id , usr2_id),
     FOREIGN KEY(usr1_id) REFERENCES users(id),
     FOREIGN KEY(usr2_id) REFERENCES users(id)
);

CREATE TABLE posts (
     id INT AUTO_INCREMENT,
     message TEXT,
     channel_id INT,
     user_id INT,
     sending_date DATE,
     modification_date DATE,
     with_attachment boolean,
     PRIMARY KEY(channel_id,id),
     FOREIGN KEY(channel_id) REFERENCES channels(id),
     FOREIGN KEY(usr_email) REFERENCES users(email)
);

CREATE TABLE members (
     channel_id INT,
     user_id INT,
     nickname VARCHAR(255),
     PRIMARY KEY(channel_id, user_email),
     FOREIGN KEY(user_id) REFERENCES users(id),
     FOREIGN KEY(channel_id) REFERENCES channels(id)
);
