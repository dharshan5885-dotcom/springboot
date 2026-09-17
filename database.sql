CREATE DATABASE IF NOT EXISTS service_management;
USE service_management;

-- Spring Boot/JPA creates the service_request table automatically.
-- Optional sample data can be inserted after the application creates the table.

INSERT INTO service_request
(title, category, description, priority, status, customer_name, customer_email, created_date)
VALUES
('Internet not working', 'Internet', 'Internet connection is not working at home.', 'High', 'Pending', 'Arun', 'arun@example.com', NOW()),
('AC maintenance', 'Maintenance', 'Office AC requires routine maintenance.', 'Medium', 'In Progress', 'Kumar', 'kumar@example.com', NOW()),
('Water leakage', 'Plumbing', 'Water leakage near the kitchen pipe.', 'High', 'Resolved', 'Priya', 'priya@example.com', NOW());
