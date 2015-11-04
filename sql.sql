CREATE schema drustvena_mreza;
CREATE TABLE drustvena_mreza.user (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  password_hash varchar(60) NOT NULL,
  email varchar(45) NOT NULL,
  registration_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  first_name varchar(45) NOT NULL,
  middle_name varchar(45) DEFAULT NULL,
  last_name varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) 