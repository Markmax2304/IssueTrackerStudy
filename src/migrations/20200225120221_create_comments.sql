CREATE TABLE IF NOT EXISTS comments (
    id  INTEGER AUTO_INCREMENT PRIMARY KEY,
    description  TEXT NOT NULL,
    created_at DATETIME,
    updated_at DATETIME,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    issue_id INTEGER NOT NULL,
    FOREIGN KEY (issue_id)
        REFERENCES issues(id)
        ON DELETE CASCADE
) ENGINE = InnoDB;