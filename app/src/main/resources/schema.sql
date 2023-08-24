-- shafs example schema
-- hardcoded in shafs.c

CREATE TABLE IF NOT EXISTS shafs (
    filepath VARCHAR(4096) PRIMARY KEY, -- sqlite doesnt care
    filehash CHAR(64),
    filesize INTEGER NOT NULL DEFAULT 0
);

