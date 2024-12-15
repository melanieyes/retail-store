-- 1. Create the Database
CREATE DATABASE IF NOT EXISTS retail_store;
USE retail_store;

USE retail_store;
-- Administration
CREATE TABLE Users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    Username VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(100) NOT NULL,
    Role VARCHAR(50) DEFAULT 'user'
);

-- Admin keyinfo
INSERT INTO Users (UserId, Username, Password, Role) VALUES
(1, 'melanie', 'database', 'admin'),
(2, 'milan', 'access', 'cashier');

-- Customers Table
CREATE TABLE Customers (
    CustID INT AUTO_INCREMENT PRIMARY KEY,
    FName VARCHAR(100) NOT NULL,
    LName VARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE,
    Phone VARCHAR(20),
    Address TEXT,
    JoinDate DATE
);

-- Product Categories Table
CREATE TABLE ProdCateg (
    ProdCatID INT AUTO_INCREMENT PRIMARY KEY,
    CatName VARCHAR(50) NOT NULL UNIQUE
);

-- Suppliers Table
CREATE TABLE Suppliers (
    SuppID INT AUTO_INCREMENT PRIMARY KEY,
    SuppName VARCHAR(100) NOT NULL,
    ContactP VARCHAR(100),
    Phone VARCHAR(20),
    Address TEXT,
    Status VARCHAR(20) DEFAULT 'Active'
);

-- Products Table
CREATE TABLE Products (
    ProdsID INT AUTO_INCREMENT PRIMARY KEY,
    ProdsName VARCHAR(100) NOT NULL,
    ProdCatID INT,
    Price DECIMAL(12, 2) NOT NULL,
    QtyStock INT DEFAULT 0,
    SuppID INT,
    FOREIGN KEY (ProdCatID) REFERENCES ProdCateg(ProdCatID) ON DELETE SET NULL,
    FOREIGN KEY (SuppID) REFERENCES Suppliers(SuppID) ON DELETE SET NULL
);

-- Orders Table
CREATE TABLE Orders (
    OrdID INT AUTO_INCREMENT PRIMARY KEY,
    CustID INT,
    OrdDate DATE,
    StatusID INT DEFAULT 1,
    TotAmt DECIMAL(12, 2) DEFAULT 0,
    FOREIGN KEY (CustID) REFERENCES Customers(CustID) ON DELETE SET NULL
);

-- Order Items Table
CREATE TABLE OrdItem (
    OrdItemID INT AUTO_INCREMENT PRIMARY KEY,
    OrdID INT,
    ProdsID INT,
    Qty INT NOT NULL,
    UnitPrice DECIMAL(12, 2) NOT NULL,
    TotalPrice DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (OrdID) REFERENCES Orders(OrdID) ON DELETE CASCADE,
    FOREIGN KEY (ProdsID) REFERENCES Products(ProdsID) ON DELETE SET NULL
);

-- Supplier Orders Table
CREATE TABLE SuppOrder (
    SuppOrdID INT AUTO_INCREMENT PRIMARY KEY,
    SuppID INT,
    OrdDate DATE,
    TotCost DECIMAL(12, 2) DEFAULT 0,
    StatusID INT DEFAULT 1,
    FOREIGN KEY (SuppID) REFERENCES Suppliers(SuppID) ON DELETE SET NULL
);

-- Supplier Order Items Table
CREATE TABLE SuppOrdItem (
    SuppOrdItemID INT AUTO_INCREMENT PRIMARY KEY,
    SuppOrdID INT,
    ProdsID INT,
    Qty INT NOT NULL,
    UnitPrice DECIMAL(12, 2) NOT NULL,
    TotalPrice DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (SuppOrdID) REFERENCES SuppOrder(SuppOrdID) ON DELETE CASCADE,
    FOREIGN KEY (ProdsID) REFERENCES Products(ProdsID) ON DELETE SET NULL
);

CREATE TABLE Inventory (
    ProdsID INT PRIMARY KEY,                     -- Product ID as the primary key
    QtyInStock INT NOT NULL,                     -- Current quantity in stock
    FOREIGN KEY (ProdsID) REFERENCES Products(ProdsID) ON DELETE CASCADE
);


USE retail_store;

-- Insert value into table
INSERT INTO ProdCateg (ProdCatID, CatName) VALUES
    (1, 'Smartphones & Tablets'),
    (2, 'Laptops & Computers'),
    (3, 'Smart Home Devices'),
    (4, 'Audio & Accessories'),
    (5, 'Cameras & Photography'),
    (6, 'Gaming & VR'),
    (7, 'Drones & Robotics');


-- 3. Insert Data into Suppliers

INSERT INTO Suppliers (SuppID, SuppName, ContactP, Phone, Address, Status) VALUES
    (1, 'TechWorld Inc.', 'Alice Johnson', '555-1234', '123 Tech Avenue, Silicon Valley, CA', 'Active'),
    (7, 'TechGlobal Corp.', 'Daniel Carter', '555-2345', '789 Innovation Drive, Seattle, WA', 'Active'),
    (8, 'NextGen Tech', 'Sophia Brown', '555-6789', '456 Digital Way, Austin, TX', 'Active'),
    (9, 'CloudSolutions Ltd.', 'Michael Green', '555-5670', '321 Cloud Park, Denver, CO', 'Active'),
    (10, 'DataTech Partners', 'Olivia Taylor', '555-7891', '654 Data Lane, Boston, MA', 'Active'),
    (11, 'EYEAI', 'Emma Wilson', '555-8902', '987 AI Blvd, Palo Alto, CA', 'Active');


-- 4. Insert Data into Customers
-- Note: Customer IDs (CustID) are auto-incremented; thus, they are omitted.
INSERT INTO Customers (CustID, FName, LName, Email, Phone, Address, JoinDate) VALUES
    (1, 'John', 'Doe', 'john.doe@example.com', '555-1111', '100 Main Street, Springfield, IL', '2023-01-15'),
    (2, 'Jane', 'Smith', 'jane.smith@example.com', '555-2222', '200 Oak Avenue, Lincoln, NE', '2023-03-22'),
    (3, 'Mike', 'Brown', 'mike.brown@example.com', '555-3333', '300 Pine Road, Madison, WI', '2023-05-10'),
    (4, 'Emily', 'Davis', 'emily.davis@example.com', '555-4444', '400 Maple Street, Austin, TX', '2023-07-08'),
    (5, 'William', 'Wilson', 'will.wilson@example.com', '555-5555', '500 Cedar Blvd, Denver, CO', '2023-09-30'),
    (6, 'Olivia', 'Martinez', 'olivia.martinez@example.com', '555-6666', '600 Birch Street, Portland, OR', '2023-11-12'),
    (7, 'Liam', 'Garcia', 'liam.garcia@example.com', '555-7777', '700 Cedar Avenue, Miami, FL', '2023-12-05'),
    (8, 'Sophia', 'Anderson', 'sophia.anderson@example.com', '555-8888', '800 Walnut Road, Houston, TX', '2024-01-20'),
    (9, 'Noah', 'Thomas', 'noah.thomas@example.com', '555-9999', '900 Spruce Blvd, Phoenix, AZ', '2024-02-14'),
    (10, 'Ava', 'Jackson', 'ava.jackson@example.com', '555-0000', '1000 Pine Street, Orlando, FL', '2024-03-03');

-- 6. Insert Data into Products
INSERT INTO Products (ProdsID, ProdsName, SuppID, Price, QtyStock, ProdCatID) VALUES
    (1, 'Smartphone XYZ', 1, 699.99, 50, 1),
    (2, 'Laptop ABC', 1, 1199.99, 30, 1),
    (3, 'Wireless Headphones', 1, 149.99, 60, 1),
    (4, 'Tablet Pro 10.5', 1, 499.99, 40, 1),
    (5, 'Smartwatch Series 5', 1, 299.99, 70, 1),
    (6, 'Gaming Laptop Ultra', 1, 1599.99, 25, 1),
    (7, '4K Ultra HD Smart TV', 1, 999.99, 20, 1),
    (8, 'External Hard Drive 2TB', 1, 89.99, 100, 1),
    (9, 'Bluetooth Speaker X', 1, 129.99, 80, 1),
    (10, 'Wireless Gaming Mouse', 1, 79.99, 90, 1),
    (11, 'Mechanical Keyboard Pro', 1, 119.99, 60, 1),
    (12, 'Drone with HD Camera', 1, 499.99, 30, 1),
    (13, 'Virtual Reality Headset', 1, 349.99, 40, 1),
    (14, 'Smart Home Hub', 1, 199.99, 50, 1),
    (15, 'Noise-Canceling Headphones', 1, 249.99, 60, 1),
    (16, 'Portable Power Bank 20,000mAh', 1, 39.99, 150, 1),
    (17, 'Digital Camera Pro', 1, 1299.99, 15, 1),
    (18, '3D Printer', 1, 899.99, 10, 1),
    (19, 'Smart Thermostat', 1, 199.99, 25, 1),
    (20, 'Action Camera 4K', 1, 199.99, 35, 1),
    (21, 'Streaming Stick 4K', 1, 49.99, 100, 1),
    (22, 'Electric Scooter', 1, 499.99, 20, 1),
    (23, 'Smart Doorbell Camera', 1, 149.99, 40, 1),
    (24, 'Home Security System', 1, 399.99, 15, 1),
    (25, 'Wireless Charging Pad', 1, 29.99, 200, 1),
    (26, 'Portable Bluetooth Projector', 1, 299.99, 20, 1),
    (27, 'Smart Light Bulbs (4-Pack)', 1, 49.99, 150, 1),
    (28, 'Fitness Tracker Band', 1, 99.99, 80, 1);



-- 7. Insert Data into Orders-
-- Note: Order IDs (OrdID) are auto-incremented; thus, they are omitted.
INSERT INTO Orders (OrdID, CustID, OrdDate, StatusID, TotAmt) VALUES
    (1, 1,'2024-04-01', 1, 719.98),  -- Pending
    (2, 2, '2024-04-03', 2, 49.99),  -- Shipped
    (3, 3, '2024-04-05', 3, 14.99),  -- Delivered
    (4, 4, '2024-04-07', 4, 89.99);  -- Canceled


-- 8. Insert Data into Order Items (OrdItem)
-- Note: Order Item IDs (OrdItemID) are auto-incremented; thus, they are omitted.
-- Corrected Insert into OrdItem
INSERT INTO OrdItem (OrdItemID, OrdID, ProdsID, Qty, UnitPrice, TotalPrice) VALUES
    (1, 1, 1, 2, 699.99, 1399.98), -- 2 Smartphones for Order 1
    (2, 1, 3, 1, 149.99, 149.99), -- 1 Wireless Headphones for Order 1
    (3, 2, 2, 1, 1199.99, 1199.99), -- 1 Laptop for Order 2
    (4, 2, 8, 3, 89.99, 269.97), -- 3 External Hard Drives for Order 2
    (5, 3, 5, 1, 299.99, 299.99), -- 1 Smartwatch for Order 3
    (6, 3, 10, 2, 79.99, 159.98), -- 2 Wireless Gaming Mice for Order 3
    (7, 4, 7, 1, 999.99, 999.99), -- 1 4K Smart TV for Order 4
    (8, 4, 16, 4, 39.99, 159.96); -- 4 Portable Power Banks for Order 4



-- Insert Data into Supplier Orders (SuppOrder)
-- 
INSERT INTO SuppOrder (SuppOrdID, SuppID, OrdDate, TotCost, StatusID) VALUES
    (1, 1, '2024-10-10', 3499.95, 1), 
    (2, 1, '2024-10-11', 5099.87, 2), 
    (3, 1, '2024-10-12', 1499.88, 1);


-- 10. Insert Data into Supplier Order Items (SuppOrdItem)
-- Note: Supplier Order Item IDs (SuppOrdItemID) are auto-incremented; thus, they are omitted.
INSERT INTO SuppOrdItem (SuppOrdItemID, SuppOrdID, ProdsID, Qty, UnitPrice, TotalPrice) VALUES
    (1, 1, 1, 5, 699.99, 3499.95), -- 5 Smartphones in SuppOrdID 1
    (2, 2, 2, 3, 1199.99, 3599.97), -- 3 Laptops in SuppOrdID 2
    (3, 2, 3, 10, 149.99, 1499.90), -- 10 Wireless Headphones in SuppOrdID 2
    (4, 3, 5, 2, 299.99, 599.98), -- 2 Smartwatches in SuppOrdID 3
    (5, 3, 8, 10, 89.99, 899.90); -- 10 External Hard Drives in SuppOrdID 3



INSERT INTO Inventory (ProdsID, QtyInStock) VALUES
    (1, 50),   -- Smartphone XYZ
    (2, 30),   -- Laptop ABC
    (3, 60),   -- Wireless Headphones
    (4, 40),   -- Tablet Pro 10.5
    (5, 70),   -- Smartwatch Series 5
    (6, 25),   -- Gaming Laptop Ultra
    (7, 20),   -- 4K Ultra HD Smart TV
    (8, 100);  -- External Hard Drive 2TB



