DROP DATABASE IF EXISTS fenixdb;

CREATE DATABASE IF NOT EXISTS fenixdb;

USE fenixdb;

CREATE TABLE client
(
    id Int PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(25) UNIQUE,
    email VARCHAR(50),
    password_hashed VARCHAR(128),
    bio VARCHAR(250)
);

CREATE TABLE tag
(
    id Int PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(30),
    description VARCHAR(250)
);

	CREATE TABLE game
	(
	    id Int PRIMARY KEY AUTO_INCREMENT,
	    title VARCHAR(50) UNIQUE,
	    dev_id Int,
	    description VARCHAR(250),
	    tamano_mb DECIMAL(10,2),
	    downloads Int,
	    price DECIMAL(6,2)
);

CREATE TABLE game_tag
(
    game_id Int NOT NULL,
    tag_id Int NOT NULL,
    PRIMARY KEY(game_id, tag_id),
    CONSTRAINT fk_game_tag_game
        FOREIGN KEY (game_id)
            REFERENCES game(id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT fk_game_tag_tag
        FOREIGN KEY (tag_id)
            REFERENCES tag(id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);

CREATE TABLE purchase
(
    id Int PRIMARY KEY AUTO_INCREMENT,
    client_id Int NOT NULL,
    game_id Int NOT NULL,
    CONSTRAINT fk_purchase_client
        FOREIGN KEY (client_id)
            REFERENCES client(id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE,
    CONSTRAINT fk_purchase_game
        FOREIGN KEY (game_id)
            REFERENCES game(id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);

CREATE TABLE teaser
(
    id Int AUTO_INCREMENT,
    game_id Int NOT NULL,
    file_name VARCHAR(255),
    type VARCHAR(50),
    PRIMARY KEY(id, game_id),
    CONSTRAINT fk_teaser_game
        FOREIGN KEY (game_id)
            REFERENCES game(id)
            ON DELETE RESTRICT
            ON UPDATE CASCADE
);



























