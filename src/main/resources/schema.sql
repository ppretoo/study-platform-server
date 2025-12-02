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
