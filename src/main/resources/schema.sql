CREATE TABLE IF NOT EXISTS users (
                                     id    INTEGER PRIMARY KEY AUTOINCREMENT,
                                     name  TEXT NOT NULL,
                                     email TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS study_groups (
                                            id          INTEGER PRIMARY KEY AUTOINCREMENT,
                                            name        TEXT NOT NULL,
                                            description TEXT
);

CREATE TABLE IF NOT EXISTS memberships (
                                           id        INTEGER PRIMARY KEY AUTOINCREMENT,
                                           user_id   INTEGER NOT NULL,
                                           group_id  INTEGER NOT NULL,
                                           role      TEXT NOT NULL,
                                           joined_at TEXT DEFAULT CURRENT_TIMESTAMP,
                                           FOREIGN KEY (user_id)  REFERENCES users(id),
                                           FOREIGN KEY (group_id) REFERENCES study_groups(id)
);