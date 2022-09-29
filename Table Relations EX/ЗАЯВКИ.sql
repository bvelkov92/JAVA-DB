CREATE DATABASE `one_to_one_relationship`;
USE `one_to_one_relationship`;

#01. One-To-One Relationship
CREATE TABLE `people`(
`person_id` INT PRIMARY KEY AUTO_INCREMENT,
`first_name` VARCHAR(50) NOT NULL,
`salary` DECIMAL(10,2) NOT NULL DEFAULT 0,
`passport_id` INT UNIQUE
);

CREATE TABLE `passports`(
`passport_id` INT PRIMARY KEY UNIQUE NOT NULL,
`passport_number` VARCHAR(50) NOT NULL 
);

INSERT INTO `people`(`first_name`, `salary`, `passport_id`)
VALUE ('Roberto','43300.00',102),
( 'Tom', '56100.004', 103),
('Yana', '60200.00', 101);

INSERT INTO `passports` (`passport_id`,`passport_number`)
VALUE 
(101,'N34FG21B'),
(102,'K65LO4R7'),
(103,'ZE657QP2');

ALTER TABLE `people`
ADD CONSTRAINT fk_people_passports
FOREIGN KEY(`passport_id`)
REFERENCES passports(`passport_id`);










#02. One-To-Many Relationship

CREATE DATABASE `one_to_many_relationship`;
USE `one_to_many_relationship`;

CREATE TABLE `manufacturers`(
`manufacturer_id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL,
`established_on` DATE NOT NULL);

CREATE TABLE `models`(
`model_id` INT PRIMARY KEY UNIQUE,
`name` VARCHAR(50) NOT NULL,
`manufacturer_id` INT NOT NULL 
);

INSERT INTO `manufacturers`(`name`, `established_on`)
VALUES
('BMW', '1916-03-01'),
('Tesla', '2003-01-01'),
('Lada', '1966-05-01');

INSERT INTO `models`(`model_id`, `name`, `manufacturer_id`)
VALUE
(101,'X1',1),
(102,'i6',1),
(103,'Model S',	2),
(104,'Model X',	2),
(105,'Model 3',	2),
(106,'Nova',3);

ALTER TABLE `models`
ADD CONSTRAINT fk_models_manufacturers
FOREIGN KEY (`manufacturer_id`)
REFERENCES manufacturers(`manufacturer_id`);

#03. Many-To-Many Relationship
CREATE DATABASE `many_to_many_relationship`;
USE `many_to_many_relationship`;

CREATE TABLE `exams` (
`exam_id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR (50) NOT NULL);

CREATE TABLE `students`(
`student_id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL
);

CREATE TABLE `students_exams`(
`student_id` INT NOT NULL,
`exam_id` INT NOT NULL		
);

INSERT INTO `students`(`name`)
VALUES
('Mila'),
('Toni'),
('Ron');

INSERT INTO `exams`(`exam_id`, `name`)
VALUES
(101,'Spring MVC'),
(102, 'Neo4j'),
(103, 'Oracle 11g');

INSERT INTO `students_exams`(`student_id`,`exam_id`)
VALUES
(1,	101),
(1,	102),
(2,	101),
(3,	103),
(2,	102),
(2,	103);

ALTER TABLE `students_exams`
ADD CONSTRAINT fk_student_exams
PRIMARY KEY (`student_id`,`exam_id`);

ALTER TABLE `students_exams`
ADD CONSTRAINT fk_student_exams_students
FOREIGN KEY (`student_id`)
REFERENCES students(`student_id`);

ALTER TABLE `students_exams`
ADD CONSTRAINT fk_student_exams_exams
FOREIGN KEY (`exam_id`)
REFERENCES exams(`exam_id`);

#04. Self-Referencing
CREATE DATABASE `self_racing`;
USE `self_racing`;


CREATE TABLE `teachers`(
`teacher_id` INT PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL,
`manager_id` INT);

INSERT INTO `teachers` (`teacher_id`, `name`, `manager_id`)
VALUES 
(101,	'John', NULL),
(102,	'Maya',	106),
(103,'Silvia', 106),
(104,'Ted', 105),
(105, 'Mark', 101),
(106, 'Greta', 101);


ALTER TABLE `teachers`
ADD CONSTRAINT fk_teachers_manager_id
FOREIGN KEY (`manager_id`)
REFERENCES teachers(`teacher_id`);

#05. Online Store Database
#-------------------------- CREATE  AND USE DATABASE-------------------------------------
CREATE DATABASE `store_db1`;
USE `store_db1`;
#-------------------------- CREATE TABLES-------------------------------------

CREATE TABLE `cities` (
    `city_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(50) 
);

CREATE TABLE `customers`(
`customer_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50), 
`birthday` DATE,
`city_id` INT(11)
);

CREATE TABLE `orders`(
`order_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`customer_id` INT(11)
);
#-------------------------------------------------------------------------------
CREATE TABLE `items`(
`item_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50),
`item_type_id` INT(11)
);

CREATE TABLE `item_types`(
`item_type_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`name` VARCHAR(50) 
);

CREATE TABLE `order_items`(
`order_id` INT(11),
`item_id` INT(11),
 CONSTRAINT pk_order_items
 PRIMARY KEY (`order_id`, `item_id`));
#--------------------------------------------------------------------------------

#-------------------------- ADD FOREIGN KEYS-------------------------------------
ALTER TABLE `customers`
ADD CONSTRAINT fk_customers_cities
FOREIGN KEY (`city_id`)
REFERENCES cities(`city_id`);

ALTER TABLE `orders`
ADD CONSTRAINT fk_orders_customers
FOREIGN KEY (`customer_id`)
REFERENCES customers(`customer_id`);


ALTER TABLE `order_items`
ADD CONSTRAINT fk_order_items_oredrs
FOREIGN KEY (`order_id`)
REFERENCES orders(`order_id`);

ALTER TABLE `order_items`
ADD CONSTRAINT fk_order_items_items
FOREIGN KEY (`item_id`)
REFERENCES items(`item_id`);

ALTER TABLE `items`
ADD CONSTRAINT fk_item_item_type
FOREIGN KEY (`item_type_id`)
REFERENCES item_types(`item_type_id`);

#06. University Database

#------------- CREATE DATABASE AND USE IT -------------

CREATE DATABASE `university_db`;
USE `university_db`;

#-------------------CREATE TABLES -------------------------------

CREATE TABLE `subjects`(
`subject_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`subject_name` VARCHAR(50) 
);

CREATE TABLE `agenda`(
`student_id`  INT(11),
`subject_id` INT(11),
CONSTRAINT pk_agenda   #<------------------ADD PRIMARY KEYS
PRIMARY KEY(`student_id`, `subject_id`) 
);

CREATE TABLE `students`(
`student_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`student_number` VARCHAR(12),
`student_name` VARCHAR(50),
`major_id` INT(11) 
);

CREATE TABLE `majors`(
`major_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`name`  VARCHAR(50)
);

CREATE TABLE `payments`(
`payment_id` INT(11) PRIMARY KEY AUTO_INCREMENT,
`payment_date` DATE,
`payment_amount` DECIMAL(8,2),
`student_id` INT(11)
);

#------------------ CREATE FOREIGN KEYS-----------------

ALTER TABLE `agenda`
ADD CONSTRAINT fk_agenda_subjects
FOREIGN KEY (`subject_id`)
REFERENCES subjects(`subject_id`); 

ALTER TABLE `agenda`
ADD CONSTRAINT fk_agenda_students
FOREIGN KEY (`student_id`)
REFERENCES students(`student_id`);

ALTER TABLE `students`
ADD CONSTRAINT fk_students_majors
FOREIGN KEY (`major_id`)
REFERENCES majors(`major_id`);

ALTER TABLE `payments`
ADD CONSTRAINT fk_payments_students
FOREIGN KEY (`student_id`)
REFERENCES students(`student_id`);

#09. Peaks in Rila
USE `geography`;
SELECT 
m.`mountain_range`,
p.`peak_name`,
p.`elevation` AS 'peak_elevation'
 FROM `mountains` m
JOIN `peaks` p
ON  p.`mountain_id` = m.`id`
WHERE m.`mountain_range` = 'Rila'
ORDER BY p.`elevation` DESC;