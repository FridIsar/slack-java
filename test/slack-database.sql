CREATE TABLE users(
    id INT AUTO_INCREMENT,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    username VARCHAR(255),
    creation_date DATE,
    PRIMARY KEY(id)
);

CREATE TABLE channels (
    name VARCHAR(255) UNIQUE,
    admin_email VARCHAR(255),
    creation_date DATE,
    PRIMARY KEY(id),
    FOREIGN KEY (admin_email) REFERENCES users(email)
);

CREATE TABLE friends (
     usr1_id INT,
     usr2_id INT,
     PRIMARY KEY(usr1_id , usr2_id),
     FOREIGN KEY(usr1_id) REFERENCES users(id),
     FOREIGN KEY(usr2_id) REFERENCES users(id)
);

CREATE TABLE posts (
     id INT AUTO_INCREMENT,
     channel_name VARCHAR(255),
     usr_email VARCHAR(255),
     PRIMARY KEY(channel_name,id),
     FOREIGN KEY(channel_name) REFERENCES channels(name),
     FOREIGN KEY(usr_email) REFERENCES users(email)
);

CREATE TABLE members (
     channel_name VARCHAR(255),
     user_id INT,
     nickname VARCHAR(255),
     PRIMARY KEY(channel_name, user_email),
     FOREIGN KEY(user_id) REFERENCES users(id),
     FOREIGN KEY(channel_name) REFERENCES channels(name)
);
