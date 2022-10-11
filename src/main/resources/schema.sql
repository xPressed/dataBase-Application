-- Database creation
# DROP DATABASE IF EXISTS restaurant;
CREATE DATABASE IF NOT EXISTS restaurant;
USE restaurant;

-- All tables creation
CREATE TABLE IF NOT EXISTS Dish
(
    ID_Dish              INTEGER NOT NULL AUTO_INCREMENT,
    Type				 VARCHAR(20) NOT NULL,
    Calories             INTEGER NOT NULL,
    Compound             VARCHAR(20) NOT NULL,
    Price                INTEGER NOT NULL,
    PRIMARY KEY (ID_Dish)
);



CREATE TABLE IF NOT EXISTS `Order`
(
    ID_Order             INTEGER NOT NULL AUTO_INCREMENT,
    ID_Dish              INTEGER NOT NULL,
    PRIMARY KEY (ID_Order),
    FOREIGN KEY R_8 (ID_Dish) REFERENCES Dish (ID_Dish)
);



CREATE TABLE IF NOT EXISTS Shift
(
    ID_Shift             INTEGER NOT NULL AUTO_INCREMENT,
    Type				 VARCHAR(20) NOT NULL,
    Timetable            VARCHAR(50) NOT NULL,
    PRIMARY KEY (ID_Shift)
);



CREATE TABLE IF NOT EXISTS Waiter
(
    ID_Waiter            INTEGER NOT NULL AUTO_INCREMENT,
    Surname              VARCHAR(20) NOT NULL,
    Name                 VARCHAR(20) NOT NULL,
    Patronymic           VARCHAR(20) NULL,
    Gender               VARCHAR(20) NOT NULL,
    Date_Of_Birth          DATE NOT NULL,
    ID_Shift             INTEGER NULL,
    PRIMARY KEY (ID_Waiter),
    FOREIGN KEY R_11 (ID_Shift) REFERENCES Shift (ID_Shift)
);



CREATE TABLE IF NOT EXISTS Distribution
(
    ID_Order             INTEGER NOT NULL,
    ID_Waiter            INTEGER NOT NULL,
    PRIMARY KEY (ID_Order,ID_Waiter),
    FOREIGN KEY R_7 (ID_Order) REFERENCES `Order` (ID_Order),
    FOREIGN KEY R_8 (ID_Waiter) REFERENCES Waiter (ID_Waiter)
);



CREATE TABLE IF NOT EXISTS Client
(
    ID_Client            INTEGER NOT NULL AUTO_INCREMENT,
    ID_Order             INTEGER NULL,
    Surname              VARCHAR(20) NOT NULL,
    Name                 VARCHAR(20) NOT NULL,
    Patronymic           VARCHAR(20) NULL,
    PRIMARY KEY (ID_Client),
    FOREIGN KEY R_5 (ID_Order) REFERENCES `Order` (ID_Order)
);



CREATE TABLE IF NOT EXISTS Menu
(
    ID_Menu              INTEGER NOT NULL AUTO_INCREMENT,
    Type                 VARCHAR(20) NOT NULL,
    PRIMARY KEY (ID_Menu)
);



CREATE TABLE IF NOT EXISTS Positions
(
    ID_Menu              INTEGER NOT NULL,
    ID_Dish              INTEGER NOT NULL,
    PRIMARY KEY (ID_Menu,ID_Dish),
    FOREIGN KEY R_3 (ID_Menu) REFERENCES Menu (ID_Menu),
    FOREIGN KEY R_4 (ID_Dish) REFERENCES Dish (ID_Dish)
);



CREATE TABLE IF NOT EXISTS User
(
    username            VARCHAR(100) NOT NULL,
    password            VARCHAR(100) NOT NULL,
    PRIMARY KEY (username)
);