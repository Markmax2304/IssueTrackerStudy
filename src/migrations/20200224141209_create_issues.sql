CREATE TABLE IF NOT EXISTS issues (
	id	INTEGER AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(512) NOT NULL,
	description	TEXT,
	critical INTEGER NOT NULL DEFAULT 0,
	priority  INTEGER NOT NULL DEFAULT 0,
	steps TEXT,
	expected TEXT,
	actual TEXT,
	executor_id	INTEGER,
	status  INTEGER NOT NULL DEFAULT 0,
    created_at DATETIME,
    updated_at DATETIME,
    user_id INTEGER NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,
    FOREIGN KEY (executor_id)
        REFERENCES users(id)
) ENGINE = InnoDB;