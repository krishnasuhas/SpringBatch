drop table IF EXISTS billionaires;

create TABLE billionaires (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  firstName VARCHAR(250) NOT NULL,
  lastName VARCHAR(250) NOT NULL,
  career VARCHAR(250) DEFAULT NULL
);