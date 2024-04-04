-- Insert genres
INSERT INTO genre (name) VALUES ('Action');
INSERT INTO genre (name) VALUES ('Comedy');
INSERT INTO genre (name) VALUES ('Drama');
INSERT INTO genre (name) VALUES ('Horror');


-- Insert TV shows associated with genres
INSERT INTO tv_show (title, genre_id) VALUES ('Show 1', (SELECT id FROM genre WHERE name = 'Drama'));
INSERT INTO tv_show (title, genre_id) VALUES ('Show 2', (SELECT id FROM genre WHERE name = 'Horror'));
INSERT INTO tv_show (title, genre_id) VALUES ('Show 3', (SELECT id FROM genre WHERE name = 'Comedy'));
INSERT INTO tv_show (title, genre_id) VALUES ('Show 4', (SELECT id FROM genre WHERE name = 'Action'));
INSERT INTO tv_show (title, genre_id) VALUES ('Show 5', (SELECT id FROM genre WHERE name = 'Horror'));
INSERT INTO tv_show (title, genre_id) VALUES ('Show 6', (SELECT id FROM genre WHERE name = 'Comedy'));
INSERT INTO tv_show (title, genre_id) VALUES ('Show 7', (SELECT id FROM genre WHERE name = 'Drama'));
INSERT INTO tv_show (title, genre_id) VALUES ('Show 8', (SELECT id FROM genre WHERE name = 'Horror'));
INSERT INTO tv_show (title, genre_id) VALUES ('Show 9', (SELECT id FROM genre WHERE name = 'Drama'));
INSERT INTO tv_show (title, genre_id) VALUES ('Show 10', (SELECT id FROM genre WHERE name = 'Action'));
INSERT INTO tv_show (title, genre_id) VALUES ('Show 11', (SELECT id FROM genre WHERE name = 'Comedy'));