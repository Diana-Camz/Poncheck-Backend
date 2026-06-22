CREATE TABLE user (
      id_user BIGINT PRIMARY KEY AUTO_INCREMENT,
      name VARCHAR(100) NOT NULL,
      username VARCHAR(50) NOT NULL UNIQUE,
      password VARCHAR(100) NOT NULL,
      role VARCHAR(50) NOT NULL DEFAULT 'SELLER',
      refresh_token VARCHAR(255),
      active TINYINT NOT NULL DEFAULT 1,
      business_id BIGINT
);