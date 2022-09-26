USE `soft_uni`;

#01. Find Names of All Employees by First Name
SELECT `first_name`, `last_name` FROM `employees`
WHERE UPPER(SUBSTRING(`first_name`, 1,2)='Sa');

#02. Find Names of All Employees by Last Name  --- НЕ решена
SELECT `first_name`, `last_name` FROM `employees`
WHERE `last_name` LIKE '%ei%'
ORDER BY `employee_id`;

#03. Find First Names of All Employess
SELECT `first_name` FROM `employees`
WHERE `department_id` IN(3,10) 
AND 
YEAR (`hire_date`) BETWEEN 1995 AND 2005
ORDER BY `employee_id`; 

#04. Find All Employees Except Engineers
SELECT `first_name`, `last_name` FROM `employees`
WHERE `job_title` NOT LIKE '%Engineer%'
ORDER BY `employee_id`;

#05. Find Towns with Name Length
SELECT `name` FROM `towns`
WHERE char_length(`name`)= 5 OR char_length(`name`)= 6
ORDER BY `name`;

#06. Find Towns Starting With
SELECT `town_id`,`name` FROM `towns`
WHERE SUBSTRING(`name`, 1,1)='m'
OR SUBSTRING(`name`, 1,1)='k'
OR SUBSTRING(`name`, 1,1)='b'
OR SUBSTRING(`name`, 1,1)='e'
ORDER BY `name`;

#07. Find Towns Not Starting With
SELECT `town_id`,`name` FROM `towns`
WHERE NOT SUBSTRING(`name`, 1,1)='r'
AND NOT SUBSTRING(`name`, 1,1)='b'
AND NOT SUBSTRING(`name`, 1,1)='d'
ORDER BY `name`;

#08. Create View Employees Hired After
CREATE VIEW `v_employees_hired_after_2000`AS
SELECT `first_name`, `last_name` FROM `employees`
WHERE YEAR(`hire_date`) >2000;

SELECT * FROM `v_employees_hired_after_2000`;

#09. Length of Last Name
SELECT `first_name`, `last_name` FROM `employees`
WHERE char_length(`last_name`)=5;


USE `geography`;
#10. Countries Holding 'A'
SELECT `country_name`, `iso_code` FROM `countries`
WHERE  `country_name` LIKE ('%a%a%a%')
ORDER BY `iso_code`;

#11. Mix of Peak and River Names
SELECT `peak_name`, `river_name`, CONCAT(LOWER(`peak_name`),SUBSTRING(LOWER(`river_name`),2)) AS mix 
FROM `peaks`, `rivers`
WHERE RIGHT(`peak_name`,1) = LEFT (`river_name`,1)
ORDER BY `mix`;

USE `diablo`;
#12. Games From 2011 and 2012 Year
SELECT `name`, date_format( `start`, '%Y-%m-%d') AS 'start' FROM `games`
WHERE YEAR(`start`) BETWEEN 2011 AND 2012
ORDER BY `start`, `name`
LIMIT 50;

#13. User Email Providers
SELECT `user_name`, SUBSTRING(`email`, locate('@', `email`)+1) AS 'email provider' FROM `users`
ORDER BY `email provider`, `user_name`;

#14. Get Users with IP Address Like Pattern
SELECT `user_name`, `ip_address` FROM `users`
WHERE `ip_address` LIKE "___.1%.%.___"
ORDER BY `user_name`;

#15. Show All Games with Duration
SELECT `name` AS `game`,
 CASE
 WHEN HOUR(`start`)>=0 AND HOUR(`start`)<12 THEN 'Morning'
 WHEN  HOUR(`start`)>=12 AND HOUR(`start`)<18 THEN 'Afternoon'
 ELSE "Evening"
 END
 AS `Part of the Day`, 
 CASE
 WHEN `duration`<=3 THEN 'Extra Short'
 WHEN `duration` <=6 THEN 'Short'
 WHEN `duration`<=10 THEN 'Long'
 ELSE 'Extra Long'
 END
 AS 'Duration'
 FROM `games`;

USE `orders`;
#16. Orders Table
SELECT `product_name`, `order_date`, 
adddate(`order_date`, interval 3 day) AS `pay_due`, 
adddate(`order_date`, interval 1 month) AS `delivery_due` 
FROM `orders`;