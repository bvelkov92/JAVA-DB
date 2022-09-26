#0 CREATE DB 'minions'
CREATE DATABASE `minions`;
USE `minions`;
#01. Create Tables
CREATE TABLE `minions`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(255),
age INT
);

CREATE TABLE `towns`(
town_id INT PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(255) NOT NULL
);

#02. Alter Minions Table
ALTER TABLE `minions`
ADD COLUMN `town_id` INT NOT NULL,
ADD CONSTRAINT fk_minions_towns
FOREIGN KEY (`town_id`)
REFERENCES  `towns` (`id`);

#03. Insert Records in Both Tables
INSERT INTO `towns` (`id`, `name`)
VALUES (1,'Sofia'), (2, 'Plovdiv'),(3, 'Varna');

INSERT INTO `minions`(`id`, `name`, `age`, `town_id`)
VALUES (1, 'Kevin', 22,1),
(2,'Bob',15,3),
(3, 'Steward', NULL, 2);

#04. Truncate Table Minions
TRUNCATE TABLE `minions`;

#05. Drop All Tables
DROP TABLE `minions`, `towns`; 

#06. Create Table People
CREATE TABLE `people`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(255) NOT NULL, 
`picture` BLOB,
`height` DOUBLE (10, 2),
`weight` DOUBLE(10, 2),
`gender` CHAR (1) NOT NULL,
`birthdate` DATE NOT NULL, 
`biography` TEXT 
);

INSERT INTO `people`(`name`, `gender`, `birthdate`)
VALUES 
('Boris', 'm', DATE(NOW())),
('Aleksander', 'm', DATE(NOW())),
('Dancho', 'm', DATE(NOW())),
('Peter', 'm', DATE(NOW())),
('Desi', 'f', DATE(NOW()));

#07. Create Table Users
CREATE TABLE `users`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`username` VARCHAR(30) NOT NULL,
`password` VARCHAR(26) NOT NULL,
`profile_picture` BLOB,
`last_login_time` TIME,
`is_deleted` BOOLEAN
);

INSERT INTO `users` (`username`, `password`)
VALUES
('Pesho1', '1234'),
('Ivan2', 'gotinsam'),
('test','Test1234'),
('test1','test234'),
('test3','iVanGligan3');

#08. Change Primary Key
ALTER TABLE `users`
DROP PRIMARY KEY,
ADD PRIMARY KEY pk_users(`id`,`username`);

#9. Set Default Value of a Field
ALTER TABLE `users`
MODIFY COLUMN `last_login_time` DATETIME DEFAULT NOW();

#10. Set Unique Field
ALTER TABLE `users`
DROP PRIMARY KEY,
ADD CONSTRAINT pk_users
PRIMARY KEY `users` (`id`),
MODIFY COLUMN `username` VARCHAR(30) UNIQUE;

#11. Movies Database
CREATE DATABASE `Movies`;

USE `movies`;
CREATE TABLE `directors`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`director_name` VARCHAR(50) NOT NULL, 
`notes` VARCHAR(10000)
);

CREATE TABLE `genres`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`genre_name` VARCHAR(50) NOT NULL, 
`notes` VARCHAR(10000)
);

CREATE TABLE `categories`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`category_name` VARCHAR(50) NOT NULL, 
`notes` VARCHAR(10000)
);

CREATE TABLE `movies`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
`title` VARCHAR(50) NOT NULL, 
`director_id` INT,
`copyright_year` DATE,
`length` TIME,
`genre_id` INT,
`category_id` INT,
`rating` VARCHAR(50), 
`notes` VARCHAR(10000)
);

INSERT INTO `movies`(`title`)
VALUE
('Harry Potter'),
('Garfield'),
('StarWars'),
('The Wolf and the seven goats'),
('Piggy');

INSERT INTO `directors`(`director_name`)
VALUE
('Dimityr Dimitrov'),
('Georgi Georgiev'),
('Petyr Petrov'),
('Vasil Vasilev'),
('Mihail Mihailov');

INSERT INTO `genres` (`genre_name`)
VALUE 
('Peshko'),
('Goshko'),
('Mishko'),
('Fishko'),
('Rishko'),
('Strishko');

INSERT INTO `categories` (`category_name`)
VALUE
('Comedy'),
('Scaries'),
('Triller'),
('18+'),
('Animations');



#12. Car Rental Database
CREATE DATABASE `car_rental`;
USE `car_rental`;

CREATE TABLE `categories`(
`id` INT PRIMARY KEY AUTO_INCREMENT,
 `category` VARCHAR(50) NOT NULL,
 `daily_rate` INT,
 `weekly_rate` INT,
 `monthly_rate` INT,
 `weekend_rate` INT);
 
CREATE TABLE `cars`(
`id`INT PRIMARY KEY AUTO_INCREMENT,
 `plate_number` INT,
 `make` DATE,
 `model` VARCHAR(50) NOT NULL,
 `car_year` DATE, 
 `category_id` INT, 
 `doors` INT(1),
 `picture` BLOB,
 `car_condition` VARCHAR(10), 
 `available` VARCHAR (10) NOT NULL);
 
CREATE TABLE `employees`(
 `id`INT PRIMARY KEY AUTO_INCREMENT,
 `first_name` VARCHAR(50) NOT NULL, 
 `last_name` VARCHAR(50) NOT NULL, 
 `title` VARCHAR(50), 
 `notes` VARCHAR(50)
);

CREATE TABLE `customers`(
	`id`INT PRIMARY KEY AUTO_INCREMENT,
    `driver_licence_number` INT, 
    `full_name` VARCHAR(50) NOT NULL, 
    `address` VARCHAR(255), 
    `city` VARCHAR(255), 
    `zip_code` INT(7), 
    `notes` VARCHAR (255));
    
CREATE TABLE `rental_orders`(
`id`INT PRIMARY KEY AUTO_INCREMENT, 
`employee_id` INT NOT NULL, 
`customer_id` INT , 
`car_id` INT NOT NULL, 
`car_condition` VARCHAR(10), 
`tank_level` VARCHAR(20) ,
 `kilometrage_start` INT, 
 `kilometrage_end` INT,
 `total_kilometrage` INT,
 `start_date` DATE, 
 `end_date` DATE, 
 `total_days` INT, 
 `rate_applied` VARCHAR(50), 
 `tax_rate` VARCHAR(50) , 
 `order_status` VARCHAR(50) , 
 `notes` VARCHAR(255)
);

 INSERT INTO `categories`(`category`)
 VALUE
 ('Truck'),
 ('Car'),
 ('Bus');
 
 INSERT INTO `cars`(`model`, `available`)
 VALUE
 ('BMW', 'Yes'),
 ('AUDI', 'No'),
 ('Tesla', 'No');
 
INSERT INTO `employees`(`first_name`, `last_name`)
 VALUE
 ('Georgi', 'Petrov'),
 ('Petar', 'Georgiev'),
 ('Dimityr', 'Petrov');
 
 INSERT INTO `customers`(`full_name`)
 VALUE
 ('Misho'),
 ('Pesho'),
 ('Rado');

INSERT INTO `rental_orders`(`employee_id`, `car_id`)
 VALUE
 ('1', '2'),
 ('2', '4'),
 ('15', '16');
 
 
 #13. Basic Insert
 CREATE DATABASE `soft_uni`;
 
 USE `soft_uni`;
 
 CREATE TABLE `towns`(
 `id` INT PRIMARY KEY AUTO_INCREMENT,
 `name` VARCHAR(50)NOT NULL
 );
 
 CREATE TABLE `addresses`(
 `id` INT PRIMARY KEY AUTO_INCREMENT,
 `address_text` VARCHAR(50),
 `town_id` INT
 );
 
 CREATE TABLE `departments`(
 `id` INT PRIMARY KEY AUTO_INCREMENT,
 `name` VARCHAR(50) NOT NULL
 );
 
 
 CREATE TABLE `employees`(
 `id` INT PRIMARY KEY AUTO_INCREMENT,
 `first_name` VARCHAR(255) NOT NULL,
 `middle_name` VARCHAR(255) NOT NULL,
 `last_name` VARCHAR(255) NOT NULL,
 `job_title` VARCHAR(255) NOT NULL,
 `department_id` VARCHAR(50) NOT NULL,
 `hire_date` VARCHAR(50) NOT NULL,
 `salary` DOUBLE NOT NULL,
 `address_id` VARCHAR(50)
 );
 
 INSERT INTO `towns` (`name`)
VALUE
('Sofia'),
('Plovdiv'),
('Varna'),
('Burgas');

INSERT INTO `departments` (`name`)
VALUE
('Engineering'),
('Sales'),
('Marketing'),
('Software Development'),
('Quality Assurance');


INSERT INTO `employees` (`first_name`, `middle_name`,`last_name`, `job_title`, `department_id`, `hire_date`, `salary`)
VALUE
('Ivan', 'Ivanov', 'Ivanov', '.NET Developer', 'Software Development', '01/02/2013', '3500.00'), 
('Petar', 'Petrov', 'Petrov', 'Senior Engineer', 'Engineering', '02/03/2004', '4000.00'), 
('Maria', 'Petrova', 'Ivanova', 'Intern', 'Quality Assurance', '28/08/2016', '525.25'), 
('Georgi', 'Terziev', 'Ivanov', 'CEO', 'Sales', '09/12/2007', '3000.00'), 
('Peter', 'Pan', 'Pan', 'Intern', 'Marketing', '28/08/2016', '599.88');

#14. Basic Select All Fields
SELECT * FROM `towns`;
SELECT * FROM `departments`;
SELECT * FROM `employees`;

#15. Basic Select All Fields and Order Them
SELECT * FROM `towns`
ORDER BY `name`;

SELECT * FROM `departments`
ORDER BY `name`;

SELECT * FROM `employees`
ORDER BY `salary` DESC;

#16. Basic Select Some Fields
SELECT `name` FROM `towns`
ORDER BY `name`;

SELECT `name` FROM `departments`
ORDER BY `name`;

SELECT `first_name`,`last_name`,`job_title`, `salary` FROM `employees`
ORDER BY `salary` DESC;

#17. Increase Employees Salary
UPDATE `employees`
SET `salary` = `salary` * 1.10;

SELECT `salary` FROM `employees`;



