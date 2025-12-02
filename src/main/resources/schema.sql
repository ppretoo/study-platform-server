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
CREATE TABLE IF NOT EXISTS tasks (
                                     id         INTEGER PRIMARY KEY AUTOINCREMENT,
                                     group_id   INTEGER NOT NULL,
                                     title      TEXT NOT NULL,
                                     description TEXT,
                                     status     TEXT NOT NULL DEFAULT 'TODO',   -- napr. TODO / IN_PROGRESS / DONE
                                     deadline   TEXT,                           -- dátum ako string, nech je to jednoduché
                                     created_at TEXT DEFAULT CURRENT_TIMESTAMP,
                                     FOREIGN KEY (group_id) REFERENCES study_groups(id)
);

CREATE TABLE IF NOT EXISTS resources (
                                         id          INTEGER PRIMARY KEY AUTOINCREMENT,
                                         group_id    INTEGER NOT NULL,
                                         name        TEXT NOT NULL,      -- názov materiálu
                                         type        TEXT,               -- napr. LINK, NOTE, FILE...
                                         url         TEXT NOT NULL,      -- link alebo cesta k súboru
                                         uploaded_by INTEGER,            -- id používateľa (môže zostať NULL)
                                         uploaded_at TEXT DEFAULT CURRENT_TIMESTAMP,
                                         FOREIGN KEY (group_id) REFERENCES study_groups(id),
                                         FOREIGN KEY (uploaded_by) REFERENCES users(id)
);
