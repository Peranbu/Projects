CREATE DATABASE jdbcforyoutube;
USE jdbcforyoutube;
SHOW TABLES;
DESCRIBE course;

CREATE TABLE student (rno INT PRIMARY KEY,name VARCHAR(20) NOT NULL,location VARCHAR(20) NOT NULL
);
DESCRIBE student;

SELECT * FROM student;
INSERT INTO student (rno, name, location) VALUES
(1, 'me', 'bangalore'),
(2, 'you', 'chennai'),
(3, 'we', 'india');

insert into student values(22,'peranbu','vellore');

select * from student;