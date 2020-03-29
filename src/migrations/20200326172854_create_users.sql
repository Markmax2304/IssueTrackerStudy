CREATE TABLE IF NOT EXISTS users (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(512) NOT NULL,
  email VARCHAR(512) NOT NULL,
  password TEXT NOT NULL,
  CONSTRAINT uc_user_email UNIQUE(email),
    INDEX idx_user_email (email)
) ENGINE = InnoDB;