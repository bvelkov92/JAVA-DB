USE `soft_uni`;

#1. Managers
SELECT 
e.`employee_id`, 
CONCAT_WS(' ', e.`first_name`, e.`last_name`) AS 'full_name',
d.`department_id`,
d.`name`
FROM `employees` e
JOIN
`departments` d 
ON  d.`manager_id`= e.`employee_id`
Order by e.`employee_id`
LIMIT 5;

#2. Towns and Addresses
SELECT 
t.`town_id`,
t.`name`,
a.`address_text`
FROM `addresses` a
JOIN
`towns` t ON a.`town_id` = t.`town_id`
WHERE 
t.`name` ='San Francisco' OR 
t.`name` ='Sofia' OR
t.`name` = 'Carnation'
ORDER BY t.`town_id`, a.`address_id`;

#3. Employees Without Managers
SELECT e.`employee_id`,
e.`first_name`,
e.`last_name`,
d.`department_id`,
e.`salary`
FROM `employees` e
JOIN 
`departments` d ON d.`department_id` = e.`department_id`
WHERE e.`manager_id` IS NULL;

#4. High Salary
SELECT COUNT(*) FROM `employees`
WHERE `salary` > (SELECT AVG(`salary`) FROM `employees`);