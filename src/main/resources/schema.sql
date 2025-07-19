CREATE INDEX idx_locale ON translations (locale);
CREATE INDEX idx_key ON translations (translation_key);
CREATE INDEX idx_tag ON translation_tags (tag);