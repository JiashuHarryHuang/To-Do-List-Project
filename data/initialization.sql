DROP TABLE IF EXISTS user;
CREATE TABLE user (
	id bigint(20) NOT NULL,
	username varchar(64) NOT NULL UNIQUE,
	password varchar(64) NOT NULL,
	PRIMARY KEY (id) USING BTREE
);

INSERT INTO user (id, username, password) VALUES (1, 'harry', 'e10adc3949ba59abbe56e057f20f883e');

DROP TABLE IF EXISTS assignment;
CREATE TABLE assignment (
	id bigint(20) NOT NULL,
	user_id bigint(20) NOT NULL,
	assignment_name varchar(256) NOT NULL,
	due_date datetime NOT NULL,
	-- 1 = Incomplete, 0 = Completed, 2 = Important
	status TINYINT NOT NULL DEFAULT 1,
	PRIMARY KEY (id) USING BTREE
);

INSERT INTO assignment(id, user_id, assignment_name, due_date) VALUES (1, 1, 'to do list', '2022-06-18 09:16:52');