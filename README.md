# Employee Database App (Java + JDBC + MySQL)

## Description
This is a simple **CRUD application** built using **Java JDBC** with **MySQL**.  
It allows you to:
- Add a new employee  
- View all employees  
- Update employee details  
- Delete an employee  

---

##  Requirements
- Java JDK 8 or higher  
- MySQL Server installed  
- JDBC Driver for MySQL (Connector/J)  

---

##  Database Setup
1. Open MySQL and create database:
   ```sql
   CREATE DATABASE employee_db;
   USE employee_db;
   CREATE TABLE employees (
       id INT PRIMARY KEY,
       name VARCHAR(100),
       salary DOUBLE
   );
