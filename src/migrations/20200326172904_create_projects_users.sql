CREATE TABLE IF NOT EXISTS users_projects (
  id INTEGER AUTO_INCREMENT PRIMARY KEY,
  user_id INTEGER NOT NULL,
  project_id INTEGER NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users(id),
    FOREIGN KEY (project_id)
        REFERENCES projects(id)
) ENGINE = InnoDB;