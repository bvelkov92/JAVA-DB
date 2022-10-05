USE `soft_uni`;

#01. Employee Address
SELECT 
emp.`employee_id`,
emp.`job_title`,
adr.`address_id`,
adr.`address_text`
 FROM `employees` emp
JOIN `addresses` adr 
ON emp.`address_id`= adr.`address_id`
ORDER BY adr.`address_id`
LIMIT 5;

#----------------------------------------------------

#02. Addresses with Towns
SELECT e.`first_name`,
 e.`last_name`,
 t.`name`,
 a.`address_text`
FROM `employees` e
JOIN `towns` t 
 JOIN `addresses` a
 ON t.`town_id`= a.`town_id` 
 ON e.`address_id` = a.`address_id`
 ORDER BY e.`first_name`, e.`last_name`
 LIMIT 5;

#-----------------------------------------------
#03. Sales Employee
SELECT e.`employee_id`,
e.`first_name`,
e.`last_name`,
d.`name`
FROM 
`employees` e
JOIN
`departments` d
ON e.`department_id` = d.`department_id`
WHERE d.`name` = 'Sales'
ORDER BY e.`employee_id` DESC;

#----------------------------------
#04. Employee Departments
SELECT 
e.`employee_id`,
e.`first_name`,
e.`salary`,
d.`name`
FROM `employees` e
JOIN `departments` d
ON e.`department_id` = d.`department_id`
WHERE e.`salary`>15000
ORDER BY d.`department_id` DESC
LIMIT 5;

#----------------------------------
#05. Employees Without Project
SELECT 
e.`employee_id`,
e.`first_name`
FROM `employees` e
LEFT JOIN `employees_projects` ep
ON e.`employee_id` = ep.`employee_id` 
WHERE ep.`project_id` IS NULL
ORDER BY e.`employee_id` DESC
LIMIT 3;

#06. Employees Hired After
SELECT e.`first_name`, e.`last_name`, e.`hire_date`, d.`name` AS 'dept_name'
FROM `employees` e
JOIN `departments` d
ON e.`department_id` = d.`department_id`
WHERE d.`name` = 'Sales' OR d.`name` = 'Finance' AND e.`hire_date` > 1999-01-01
ORDER BY e.`hire_date`;

#07. Employees with Project
SELECT 
e.`employee_id`,
e.`first_name`,
p.`name`
FROM `employees` e
JOIN `employees_projects` ep ON e.`employee_id` = ep.`employee_id`
JOIN `projects` p
ON p.`project_id` = ep.`project_id`
WHERE DATE(p.`start_date`) > '2002-08-13' AND p.`end_date` IS NULL
ORDER BY e.`first_name`, p.`name`
LIMIT 5; 

#08. Employee 24
SELECT e.`employee_id`,
e.`first_name`,
IF (DATE(p.`start_date`)>='2005-01-01', NULL, p.`name`) AS 'project_name'
FROM `employees` e
JOIN `employees_projects` ep
ON e.`employee_id` = ep.`employee_id`
JOIN `projects` p
ON p.`project_id` = ep.`project_id`
WHERE e.`employee_id` = 24
ORDER BY p.`name`;

#09. Employee Manager
SELECT	e.`employee_id`,
e.`first_name`,
e.`manager_id`,
e2.`first_name`
FROM `employees` e
JOIN `employees` e2
ON e.`manager_id` = e2.`employee_id`
WHERE e.`manager_id` =3 OR e.`manager_id` = 7
ORDER BY e.`first_name`;

#10. Employee Summary
SELECT 
e.`employee_id`,
CONCAT_WS(' ', e.`first_name`, e.`last_name`) AS 'employee_name',
CONCAT_WS(' ', emp.`first_name`, emp.`last_name`) AS 'manager_name',
d.`name`
from `employees` e
JOIN `employees` emp ON  e.`manager_id` = emp.`employee_id`
JOIN  `departments` d
ON e.`department_id` = d.`department_id`
ORDER BY e.`employee_id`
LIMIT 5;

#11. Min Average Salary
SELECT AVG(`salary`) AS 'min_average_salary' FROM `employees`
GROUP BY `department_id`
ORDER BY `min_average_salary`
LIMIT 1;

#12. Highest Peaks in Bulgaria
USE `geography`;
SELECT 
c.`country_code`,
m.`mountain_range`,
p.`peak_name`,
p.`elevation`
FROM `countries` c
JOIN `mountains_countries` mc ON c.`country_code` = mc.`country_code`
JOIN `mountains` m ON mc.`mountain_id` = m.`id`
JOIN `peaks` p ON p.`mountain_id` = m.`id`
WHERE mc.`country_code` = 'BG' AND p.`elevation` > 2835
ORDER BY p.`elevation` DESC;

#13. Count Mountain Ranges
SELECT DISTINCT( 
c.`country_code`
),
COUNT(m.`mountain_range`) AS 'mountain_range'
FROM `countries` c
JOIN `mountains_countries` mc ON c.`country_code` = mc.`country_code`
JOIN `mountains` m ON mc.`mountain_id` = m.`id`
GROUP BY c.`country_code`
HAVING c.`country_code` = 'BG' OR c.`country_code` = 'US' OR c.`country_code` = 'RU'
ORDER BY `mountain_range` DESC;

#14. Countries with Rivers
SELECT 
c.`country_name`,
r. `river_name`
FROM `countries` c
LEFT JOIN `countries_rivers` cr ON c.`country_code` = cr.`country_code`
LEFT JOIN `rivers` r ON r.`id` = cr.`river_id`
JOIN `continents` cont ON cont.`continent_code` = c.`continent_code`
WHERE cont.`continent_name` = 'Africa'
ORDER BY c.`country_name`
LIMIT 5;

#15

#16. Countries without any Mountains
SELECT COUNT(c.`country_name`) AS 'country_count'
FROM `countries` c
LEFT JOIN `mountains_countries` mc ON c.`country_code` = mc.`country_code`
LEFT JOIN `mountains` m ON mc.`mountain_id` = m.`id`
WHERE m.`mountain_range` IS NULL;

#17. Highest Peak and Longest River by Country
SELECT c.`country_name`, 
MAX( p.`elevation`) AS 'highest_peak_elevation',
 MAX(r.`length`) AS 'longest_river_length'
FROM `countries` c
LEFT JOIN `mountains_countries` mc ON c.`country_code` = mc.`country_code`
LEFT JOIN `peaks` p ON p.`mountain_id` = mc.`mountain_id`
LEFT JOIN `countries_rivers` cr ON c.`country_code` = cr.`country_code`
LEFT JOIN `rivers` r ON r.`id` = cr.`river_id`
GROUP BY c.`country_name`
ORDER BY `highest_peak_elevation` DESC, `longest_river_length` DESC,c.`country_name`
LIMIT 5;

