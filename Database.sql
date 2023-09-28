CREATE TABLE cust_cred (
    sr INT PRIMARY KEY,
    id VARCHAR(50),
    pass VARCHAR(50)
);
CREATE TABLE item (
    sr INT PRIMARY KEY,
    name VARCHAR(50),
    price VARCHAR(50)
);

INSERT INTO `item`(`sr`, `name`, `price`) 
VALUES (1,'iPhone 15',10000),
       (2,'MacBook',150000),
       (3,'Safari',2500);

CREATE TABLE sales (
    sr INT PRIMARY KEY,
    id VARCHAR(50),
    item VARCHAR(50),
    quantity INT,
    total INT
);
