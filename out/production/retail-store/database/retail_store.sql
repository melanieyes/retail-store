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
INSERT INTO Users (Username, Password, Role)
VALUES ('melanie', 'database', 'admin');



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

-- Employees Table

CREATE TABLE Employees (
    EmpID INT AUTO_INCREMENT PRIMARY KEY,
    FName VARCHAR(100) NOT NULL,
    LName VARCHAR(100) NOT NULL,
    Role VARCHAR(50) NOT NULL
    -- only 3 employees are enough : 'Manager', 'Cashier', 'Inventory Clerk'
);


-- Orders Table
CREATE TABLE Orders (
    OrdID INT AUTO_INCREMENT PRIMARY KEY,
    CustID INT,
    EmpID INT,
    OrdDate DATE,
    StatusID INT DEFAULT 1,
    TotAmt DECIMAL(12, 2) DEFAULT 0,
    FOREIGN KEY (CustID) REFERENCES Customers(CustID) ON DELETE SET NULL,
    FOREIGN KEY (EmpID) REFERENCES Employees(EmpID) ON DELETE SET NULL
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

-- Combined Lookup Table for Status and Movement Types
-- Combined Lookup Table for Status and Movement Types
CREATE TABLE Lookup (
    TypeID INT AUTO_INCREMENT PRIMARY KEY,
    Category VARCHAR(50) NOT NULL,  -- e.g., 'OrderStatus', 'SuppOrderStatus', 'MovType'
    TypeName VARCHAR(50) NOT NULL,
    UNIQUE (Category, TypeName)     -- Ensure unique combination of Category and TypeName
);

-- Insert Default Values into Lookup Table
INSERT INTO Lookup (Category, TypeName) VALUES
    ('OrderStatus', 'Pending'),
    ('OrderStatus', 'Shipped'),
    ('OrderStatus', 'Delivered'),
    ('OrderStatus', 'Canceled'),
    ('SuppOrderStatus', 'Pending'),
    ('SuppOrderStatus', 'Completed'),
    ('MovType', 'Stock In'),
    ('MovType', 'Stock Out');

USE retail_store;


-- Insert value into table
INSERT INTO ProdCateg (ProdCatID, CatName) VALUES
    (1, 'Electronics'),
    (2, 'Apparel'),
    (3, 'Home & Kitchen'),
    (4, 'Books'),
    (5, 'Sports & Outdoors'),
    (6, 'Toys & Games'),
    (7, 'Health & Beauty');

-- 3. Insert Data into Suppliers

INSERT INTO Suppliers (SuppID, SuppName, ContactP, Phone, Address, Status) VALUES
    (1, 'TechWorld Inc.', 'Alice Johnson', '555-1234', '123 Tech Avenue, Silicon Valley, CA', 'Active'),
    (2, 'FashionHub LLC', 'Bob Smith', '555-5678', '456 Fashion Street, New York, NY', 'Active'),
    (3, 'HomeGoodies Co.', 'Carol White', '555-9012', '789 Home Blvd, Austin, TX', 'Active'),
    (4, 'BookBarn', 'David Lee', '555-3456', '321 Book Road, Boston, MA', 'Active'),
    (5, 'Sportify', 'Eva Green', '555-7890', '654 Sport Lane, Denver, CO', 'Active'),
    (6, 'StylePoint', 'Grace Lee', '555-4321', '654 Style Avenue, Los Angeles, CA', 'Active'); -- Added Supplier 6

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

-- 5. Insert Data into Employees
-- Note: Employee IDs (EmpID) are auto-incremented; thus, they are omitted.
INSERT INTO Employees (EmpID, FName, LName, Role) VALUES
    (1, 'Lana', 'Condor', 'Manager'),
    (2, 'Tom', 'Hanks', 'Cashier'),
    (3, 'Brad', 'Pitt', 'Inventory Clerk'),
    (4, 'Cate', 'Blanchett', 'Cashier'),
    (5, 'Keira', 'Knightley', 'Inventory Clerk');

-- 6. Insert Data into Products
-- ----------------------------
-- Note: Product IDs (ProdsID) are auto-incremented; thus, they are omitted.
INSERT INTO Products (ProdsID, ProdsName, ProdCatID, Price, QtyStock, SuppID) VALUES
    (1, 'Smartphone XYZ', 1, 699.99, 50, 1),
    (2, 'Laptop ABC', 1, 1199.99, 30, 1),
    (3, 'Men\'s T-Shirt', 2, 19.99, 100, 2),
    (4, 'Women\'s Jeans', 2, 49.99, 80, 2),
    (5, 'Blender 3000', 3, 89.99, 40, 3),
    (6, 'Cookware Set', 3, 129.99, 25, 3),
    (7, 'Novel: The Great Adventure', 4, 14.99, 60, 4),
    (8, 'Children\'s Story Book', 4, 9.99, 75, 4),
    (9, 'Yoga Mat', 5, 29.99, 90, 5),
    (10, 'Football', 5, 24.99, 120, 5),
    (11, 'Harry Potter Boxset', 4, 64.99, 60, 4),
    (12, 'Mystery of the Old Manor', 4, 19.99, 50, 4),
    (13, 'Science Explained: A Beginner\'s Guide', 4, 24.99, 40, 4),
    (14, 'Historical Events of the 20th Century', 4, 29.99, 35, 4),
    (15, 'Cooking Made Easy: 100 Recipes', 4, 34.99, 60, 4),
    (16, 'Fantasy Realm: The Dragon\'s Tale', 4, 22.99, 45, 4),
    (17, 'Self-Improvement: Steps to Success', 4, 18.99, 70, 4),
    (18, 'Travel Diaries: Around the World', 4, 27.99, 30, 4),
    (19, 'Art of Photography', 4, 31.99, 25, 4),
    (20, 'Beginner\'s Guide to Programming', 4, 39.99, 20, 4),
    (21, 'Children\'s Encyclopedia', 4, 15.99, 80, 4),
    (22, 'Wireless Headphones', 1, 149.99, 60, 1),
    (23, 'Designer Jacket', 2, 89.99, 40, 2),
    (24, 'Air Fryer Deluxe', 3, 129.99, 35, 3),
    (25, 'Board Game: Strategy Quest', 5, 39.99, 70, 5),
    (26, 'Lipstick Set', 7, 24.99, 150, 6),
    (27, 'Face Cream', 7, 19.99, 200, 6),
    (28, 'Designer Scarf', 2, 29.99, 150, 2);

-- 7. Insert Data into Orders-
-- Note: Order IDs (OrdID) are auto-incremented; thus, they are omitted.
INSERT INTO Orders (OrdID, CustID, EmpID, OrdDate, StatusID, TotAmt) VALUES
    (1, 1, 2, '2024-04-01', 1, 719.98),  -- Pending
    (2, 2, 4, '2024-04-03', 2, 49.99),  -- Shipped
    (3, 3, 2, '2024-04-05', 3, 14.99),  -- Delivered
    (4, 4, 5, '2024-04-07', 4, 89.99),  -- Canceled
    (5, 5, 2, '2024-04-09', 1, 54.98),  -- Pending
    (6, 6, 2, '2024-04-11', 2, 149.99),  -- Shipped
    (7, 7, 3, '2024-04-12', 1, 89.99),  -- Pending
    (8, 8, 4, '2024-04-13', 3, 24.99),   -- Delivered
    (9, 9, 5, '2024-04-14', 4, 39.99),   -- Canceled
    (10, 10, 1, '2024-04-15', 1, 129.99); -- Pending

-- 8. Insert Data into Order Items (OrdItem)
-- Note: Order Item IDs (OrdItemID) are auto-incremented; thus, they are omitted.
INSERT INTO OrdItem (OrdItemID, OrdID, ProdsID, Qty, UnitPrice, TotalPrice) VALUES
    -- Order 1
    (1, 1, 1, 1, 699.99, 699.99),
    (2, 1, 3, 1, 19.99, 19.99),
    
    -- Order 2
    (3, 2, 4, 1, 49.99, 49.99),
    
    -- Order 3
    (4, 3, 7, 1, 14.99, 14.99),
    
    -- Order 4
    (5, 4, 5, 1, 89.99, 89.99),
    
    -- Order 5
    (6, 5, 9, 2, 29.99, 59.98),
    
    -- Order 6
    (7, 6, 22, 1, 149.99, 149.99),    -- Wireless Headphones
    
    -- Order 7
    (8, 7, 23, 1, 89.99, 89.99),      -- Designer Jacket
    
    -- Order 8
    (9, 8, 26, 1, 24.99, 24.99),     -- Lipstick Set
    
    -- Order 9
    (10, 9, 25, 1, 39.99, 39.99),     -- Board Game: Strategy Quest
    
    -- Order 10
    (11, 10, 24, 1, 129.99, 129.99),  -- Air Fryer Deluxe
    
    -- Additional Order Items
    (12, 6, 23, 2, 89.99, 179.98),     -- Designer Jacket x2
    (13, 7, 26, 1, 24.99, 24.99),      -- Lipstick Set
    (14, 7, 27, 1, 19.99, 19.99),      -- Face Cream
    (15, 10, 28, 1, 29.99, 29.99);     -- Designer Scarf

-- 9. Insert Data into Supplier Orders (SuppOrder)
-- Note: Supplier Order IDs (SuppOrdID) are auto-incremented; thus, they are omitted.
INSERT INTO SuppOrder (SuppOrdID, SuppID, OrdDate, TotCost, StatusID) VALUES
    (1, 1, '2024-03-15', 1899.98, 6),  -- Completed, Supplier 1: TechWorld Inc.
    (2, 2, '2024-03-20', 1499.80, 5),  -- Pending, Supplier 2: FashionHub LLC
    (3, 3, '2024-03-25', 399.90, 6),   -- Completed, Supplier 3: HomeGoodies Co.
    (4, 4, '2024-03-30', 239.85, 5),   -- Pending, Supplier 4: BookBarn
    (5, 5, '2024-04-02', 299.70, 6),   -- Completed, Supplier 5: Sportify
    (6, 6, '2024-04-10', 449.95, 6),   -- Completed, Supplier 6: StylePoint
    (7, 2, '2024-04-11', 899.80, 5),   -- Pending, Supplier 2: FashionHub LLC
    (8, 3, '2024-04-12', 259.90, 6),   -- Completed, Supplier 3: HomeGoodies Co.
    (9, 5, '2024-04-13', 499.80, 5),   -- Pending, Supplier 5: Sportify
    (10, 6, '2024-04-14', 299.85, 6);  -- Completed, Supplier 6: StylePoint

-- 10. Insert Data into Supplier Order Items (SuppOrdItem)
-- Note: Supplier Order Item IDs (SuppOrdItemID) are auto-incremented; thus, they are omitted.
INSERT INTO SuppOrdItem (SuppOrdItemID, SuppOrdID, ProdsID, Qty, UnitPrice, TotalPrice) VALUES
    -- Supplier Order 1 (TechWorld Inc.)
    (1, 1, 1, 2, 699.99, 1399.98),
    (2, 1, 2, 1, 1199.99, 1199.99),
    
    -- Supplier Order 2 (FashionHub LLC)
    (3, 2, 3, 50, 17.99, 899.50),
    (4, 2, 4, 30, 44.99, 1349.70),
    
    -- Supplier Order 3 (HomeGoodies Co.)
    (5, 3, 5, 4, 89.99, 359.96),
    (6, 3, 6, 2, 129.99, 259.98),
    
    -- Supplier Order 4 (BookBarn)
    (7, 4, 7, 20, 14.99, 299.80),
    (8, 4, 8, 30, 9.99, 299.70),
    
    -- Supplier Order 5 (Sportify)
    (9, 5, 9, 10, 29.99, 299.90),
    (10, 5, 10, 10, 24.99, 249.90),
    
    -- Supplier Order 6 (StylePoint)
    (11, 6, 26, 100, 22.49, 2249.00),
    (12, 6, 27, 50, 19.99, 999.50),
    
    -- Supplier Order 7 (FashionHub LLC)
    (13, 7, 28, 40, 29.99, 1199.60),
    
    -- Supplier Order 8 (HomeGoodies Co.)
    (14, 8, 24, 10, 129.99, 1299.90),
    (15, 8, 6, 5, 129.99, 649.95),
    
    -- Supplier Order 9 (Sportify)
    (16, 9, 9, 20, 27.99, 559.80),
    (17, 9, 10, 10, 24.99, 249.90),
    
    -- Supplier Order 10 (StylePoint)
    (18, 10, 26, 50, 22.49, 1124.50),
    (19, 10, 28, 30, 29.99, 899.70);

-- 11. Insert Additional Products Needed for SuppOrdItem
-- These products are required for Supplier Order Items 16-19
-- Ensure that these products exist before referencing them in SuppOrdItem
INSERT INTO Products (ProdsName, ProdCatID, Price, QtyStock, SuppID) VALUES
    ('Face Cream', 7, 19.99, 200, 6),              -- Health & Beauty, StylePoint
    ('Designer Scarf', 2, 29.99, 150, 2);          -- Apparel, FashionHub LLC

-- Note: After inserting the above products, ProdsID will be auto-assigned.
-- Adjust SuppOrdItem references if necessary based on the actual ProdsID assigned.

-- This is the end of my database initialization script
-- ===================================================================


-- Testing
-- Verify Products with Categories and Suppliers
-- SELECT 
--     p.ProdsID, 
--     p.ProdsName, 
--     c.CatName, 
--     s.SuppName, 
--     p.Price, 
--     p.QtyStock
-- FROM 
--     Products p
-- JOIN 
--     ProdCateg c ON p.ProdCatID = c.ProdCatID
-- JOIN 
--     Suppliers s ON p.SuppID = s.SuppID; 



