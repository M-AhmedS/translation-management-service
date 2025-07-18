CREATE TABLE translations (
    id SERIAL PRIMARY KEY,
    key VARCHAR(255) NOT NULL,
    locale VARCHAR(10) NOT NULL,
    value TEXT NOT NULL
);

CREATE INDEX idx_locale ON translations (locale);
CREATE INDEX idx_key ON translations (key);
CREATE TABLE translation_tags (
    id SERIAL PRIMARY KEY,
    translation_id INTEGER REFERENCES translations(id),
    tag VARCHAR(255) NOT NULL
);
CREATE INDEX idx_tag ON translation_tags (tag);