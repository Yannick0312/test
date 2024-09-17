--------------------------------------------------- Sequences
-- For each ID
CREATE SEQUENCE seq_customerID START WITH 4 INCREMENT BY 1; 
CREATE SEQUENCE seq_accountID START WITH 5 INCREMENT BY 1;

--COMMIT;
--------------------------------------------------- Tables
-- Customer Table
CREATE TABLE YA_Customer (
    ID INT DEFAULT seq_customerID PRIMARY KEY NOT NULL,
    Surname VARCHAR(50) NOT NULL,
    Name VARCHAR(50) NOT NULL,
    Gender VARCHAR(50) NOT NULL,
    Birthday DATE NOT NULL
);

-- Account Table
CREATE TABLE YA_Account (
    ID INT DEFAULT seq_accountID PRIMARY KEY NOT NULL,
    Balance INT NOT NULL,
    CostumerID INT NOT NULL,
    FOREIGN KEY (CostumerID) REFERENCES YA_Customers(CostumerID)
);

--COMMIT;
--------------------------------------------------- Testdata
-- Customer Testdata
INSERT ALL 
    INTO YA_Customer (ID, Surname, Name, Gender, Birthday) VALUES (1, 'Meier', 'Norbert', 'M', TO_DATE('1980-01-01', 'YYYY-MM-DD'))
    INTO YA_Customer (ID, Surname, Name, Gender, Birthday) VALUES (2, 'Müller', 'Klaus', 'M', TO_DATE('1997-07-12', 'YYYY-MM-DD'))                           
    INTO YA_Customer (ID, Surname, Name, Gender, Birthday) VALUES (3, 'Berger', 'Andrea', 'W', TO_DATE('1955-05-25', 'YYYY-MM-DD'))
SELECT * FROM dual;

-- Accounts Testdata
INSERT ALL 
    INTO YA_Account (ID, Balance, CustomerID) VALUES (1, 2000, 1)
    INTO YA_Account (ID, Balance, CustomerID) VALUES (2, 2000, 2)
    INTO YA_Account (ID, Balance, CustomerID) VALUES (3, 2000, 3)
    INTO YA_Account (ID, Balance, CustomerID) VALUES (4, 2000, 3)
SELECT * FROM dual;

COMMIT;
-------------------------------------------------- Deletion

--DROP SEQUENCE seq_customerID;
--DROP SEQUENCE seq_accountID;
--DROP TABLE YA_Customer;
--DROP TABLE YA_Account;

--COMMIT;