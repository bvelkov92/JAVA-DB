USE `restaurant`;
#1. Departments Info
SELECT `department_id`, COUNT(`id`) AS 'Number of employees' FROM `employees`
GROUP BY `department_id`;

#2. Average Salary
SELECT `department_id`, ROUND(AVG(`salary`),2) FROM `employees`
GROUP BY `department_id`;

#3. Minimum Salary
SELECT `department_id`, ROUND(MIN(`salary`),2 ) AS 'minSalary' FROM `employees`
GROUP BY `department_id`
HAVING  `minSalary` > 800;

#4. Appetizers Count
SELECT COUNT(*) FROM `products`
WHERE `category_id`=2 AND `price` >8;

#5. Menu Prices
SELECT `category_id`, 
ROUND(AVG(`price`),2)AS 'Average Price',
ROUND(MIN(`price`),2)AS 'Cheapest Product',
ROUND(MAX(`price`),2)AS 'Most Expensive Product'
  FROM `products`
GROUP BY `category_id`