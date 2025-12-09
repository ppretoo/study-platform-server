CREATE TABLE IF NOT EXISTS users (
                                     id            INTEGER PRIMARY KEY AUTOINCREMENT,
                                     name          TEXT NOT NULL,
                                     email         TEXT NOT NULL UNIQUE,
                                     password_hash TEXT NOT NULL
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
CREATE TABLE IF NOT EXISTS tasks (
                                     id         INTEGER PRIMARY KEY AUTOINCREMENT,
                                     group_id   INTEGER NOT NULL,
                                     title      TEXT NOT NULL,
                                     description TEXT,
                                     status     TEXT NOT NULL DEFAULT 'TODO',
                                     deadline   TEXT,
                                     created_at TEXT DEFAULT CURRENT_TIMESTAMP,
                                     FOREIGN KEY (group_id) REFERENCES study_groups(id)
);

CREATE TABLE IF NOT EXISTS resources (
                                         id          INTEGER PRIMARY KEY AUTOINCREMENT,
                                         group_id    INTEGER NOT NULL,
                                         name        TEXT NOT NULL,
                                         type        TEXT,
                                         url         TEXT NOT NULL,
                                         uploaded_by INTEGER,
                                         uploaded_at TEXT DEFAULT CURRENT_TIMESTAMP,
                                         FOREIGN KEY (group_id) REFERENCES study_groups(id),
                                         FOREIGN KEY (uploaded_by) REFERENCES users(id)
);
CREATE TABLE IF NOT EXISTS activity_log (
                                            id         INTEGER PRIMARY KEY AUTOINCREMENT,
                                            user_id    INTEGER,
                                            group_id   INTEGER,
                                            action     TEXT NOT NULL,
                                            details    TEXT,
                                            created_at TEXT DEFAULT CURRENT_TIMESTAMP,
                                            FOREIGN KEY (user_id) REFERENCES users(id),
                                            FOREIGN KEY (group_id) REFERENCES study_groups(id)
);
