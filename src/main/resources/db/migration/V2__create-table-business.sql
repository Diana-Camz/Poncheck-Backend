CREATE TABLE business (
      id_business BIGINT PRIMARY KEY AUTO_INCREMENT,
      name VARCHAR(255) NOT NULL,
      business_code VARCHAR(255) NOT NULL UNIQUE,
      active TINYINT(1) NOT NULL DEFAULT 1,
      phone VARCHAR(255),
      email VARCHAR(255),
      address VARCHAR(255),
      description VARCHAR(255),
      logo_url VARCHAR(255),
      created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
      owner_id BIGINT,

      CONSTRAINT fk_business_owner
          FOREIGN KEY (owner_id) REFERENCES user(id_user)
);