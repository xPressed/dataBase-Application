-- Insertions
INSERT INTO Menu (Type)
VALUES
    ('Adult'),
    ('Child'),
    ('Vine card');

INSERT INTO Dish (Type, Calories, Compound, Price)
VALUES
    ('For Children', 228, 'Compound 46', 200),
    ('For Adults', 722, 'Compound 31', 322),
    ('Vine "Bordeaux"', 123, 'Compound 321', 333);

INSERT INTO Positions (ID_Menu, ID_Dish)
VALUES
    ((SELECT ID_Menu FROM Menu WHERE Type = 'Adult'), (SELECT ID_Dish FROM Dish WHERE Type = 'For Adults')),
    ((SELECT ID_Menu FROM Menu WHERE Type = 'Child'), (SELECT ID_Dish FROM Dish WHERE Type = 'For Children')),
    ((SELECT ID_Menu FROM Menu WHERE Type = 'Vine card'), (SELECT ID_Dish FROM Dish WHERE Type = 'Vine "Bordeaux"'));

INSERT INTO `Order` (ID_Dish)
VALUES
    ((SELECT ID_Dish FROM Dish WHERE Type = 'For Children')),
    ((SELECT ID_Dish FROM Dish WHERE Type = 'For Children')),
    ((SELECT ID_Dish FROM Dish WHERE Type = 'For Adults')),
    ((SELECT ID_Dish FROM Dish WHERE Type = 'Vine "Bordeaux"'));

INSERT INTO Shift (Type, Timetable)
VALUES
    ('First', 'MON, WED, FRI'),
    ('Second', 'TUE, THU, SAT');

INSERT INTO Waiter (Surname, Name, Patronymic, Gender, Date_Of_Birth, ID_Shift)
VALUES
    ('Ivanov', 'Ivan', 'Ivanovich', 'M', '1111-01-01', (SELECT ID_Shift FROM Shift WHERE Type = 'First')),
    ('Petrov', 'Petr', 'Petrovich', 'M', '2222-02-02', (SELECT ID_Shift FROM Shift WHERE Type = 'Second')),
    ('Sidorova', 'Anna', 'Andreevna', 'F', '3333-03-03', (SELECT ID_Shift FROM Shift WHERE Type = 'First')),
    ('Melnikova', 'Eva', 'Viktorovna', 'F', '4444-04-04', (SELECT ID_Shift FROM Shift WHERE Type = 'Second'));

INSERT INTO Distribution (ID_Order, ID_Waiter)
VALUES
    (1, (SELECT ID_Waiter FROM Waiter WHERE Surname = 'Petrov')),
    (2, (SELECT ID_Waiter FROM Waiter WHERE Surname = 'Petrov')),
    (3, (SELECT ID_Waiter FROM Waiter WHERE Surname = 'Melnikova')),
    (4, (SELECT ID_Waiter FROM Waiter WHERE Surname = 'Melnikova'));

INSERT INTO Client (ID_Order, Surname, Name, Patronymic)
VALUES
    (1, 'Pushkin', 'Alexandr', 'Sergeevich'),
    (2, 'Lermontov', 'Mihail', 'Yurievich'),
    (3, 'Block', 'Alexandr', 'Alexandrovich'),
    (4, 'Tolstoy', 'Lev', 'Nikolaevich');