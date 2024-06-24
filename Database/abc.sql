-- SELECT TOP (1000) [question_id]
--       ,[type_id]
--       ,[topic_id]
--       ,[dimension_id]
--       ,[status]
--       ,[level]
--       ,[explaination]
--       ,[content]
--       ,[value]
--   FROM [QPS-SWP391].[dbo].[Question]

-- type_id 1 means single choice, 2 means multiple choice, 3 means fill in the blank
-- topic_id 1 means topic test,
-- dimension_id 1 means Basic Concept of subject Introduction to Programming
-- status 1 means active, 0 means inactive
-- level 1 means easy, 2 means medium, 3 means hard
-- value 1 means 1 point, 2 means 2 points, 3 means 3 points
-- content is the question content
-- explaination is the explaination of the question
-- question_id is the primary key of the table
INSERT INTO [QPS-SWP391].[dbo].[Question] ([type_id], [topic_id], [dimension_id], [status], [level], [explaination], [content], [value])
VALUES 
    (1, 1, 1, 1, 1, 'A programming language is a set of instructions written by a programmer to deliver instructions to the computer to perform and accomplish a task.', 'What is a programming language?', 1),
    (1, 1, 1, 1, 1, 'A programming language is a formal language comprising a set of instructions that produce various kinds of output.', 'What are the characteristics of a programming language?', 1),
    (2, 1, 1, 1, 1, 'A variable is a named storage location in memory that holds a value.', 'What is a variable?', 1),
    (3, 1, 1, 1, 2, 'An algorithm is a step-by-step procedure or formula for solving a problem.', 'Define an algorithm.', 2),
    (1, 1, 1, 1, 1, 'A loop is used to repeat a block of code as long as the loop condition is true.', 'What is the purpose of a loop?', 1),
    (2, 1, 1, 1, 1, 'A function is a block of organized, reusable code that is used to perform a single, related action.', 'What is a function?', 1),
    (1, 1, 1, 1, 2, 'Object-oriented programming (OOP) is a programming paradigm based on the concept of objects, which can contain data and code that manipulates the data.', 'What is Object-oriented programming?', 2),
    (1, 1, 1, 1, 1, 'An array is a collection of elements identified by index or key.', 'What is an array?', 1),
    (3, 1, 1, 1, 2, 'Inheritance is a feature of OOP that allows a new class to inherit properties and behavior from an existing class.', 'Explain inheritance in Object-oriented programming.', 2),
    (1, 1, 1, 1, 1, 'A syntax error is a mistake in the source code that makes it impossible to parse (and therefore impossible to compile).', 'What is a syntax error?', 1),
    (2, 1, 1, 1, 3, 'Polymorphism is a feature of OOP that allows methods to do different things based on the object it is acting upon.', 'What is polymorphism in Object-oriented programming?', 3),
    (1, 1, 1, 1, 2, 'Encapsulation is a principle of OOP that bundles the data with the methods that operate on the data, restricting direct access to some of the objects components.', 'What is encapsulation?', 2),
    (3, 1, 1, 1, 3, 'A compiler is a program that translates code written in a high-level programming language to a lower-level language to create an executable program.', 'What is a compiler?', 3),
    (1, 1, 1, 1, 1, 'An interpreter is a program that directly executes the instructions in the source code without requiring them to be compiled into a machine language program.', 'What is an interpreter?', 1),
    (2, 1, 1, 1, 2, 'A class is a blueprint for creating objects, providing initial values for state (member variables or fields), and implementations of behavior (member functions or methods).', 'What is a class in Object-oriented programming?', 2),
    (1, 1, 1, 1, 1, 'A conditional statement allows the program to choose different paths of execution based on the outcome of an expression or condition.', 'What is a conditional statement?', 1),
    (3, 1, 1, 1, 2, 'Recursion is a programming technique where a function calls itself directly or indirectly to solve a problem.', 'Explain recursion.', 2),
    (2, 1, 1, 1, 1, 'A data structure is a way of organizing and storing data so that it can be accessed and modified efficiently.', 'What is a data structure?', 1),
    (1, 1, 1, 1, 3, 'A stack is a linear data structure that follows the Last In First Out (LIFO) principle.', 'What is a stack?', 3),
    (2, 1, 1, 1, 1, 'A queue is a linear data structure that follows the First In First Out (FIFO) principle.', 'What is a queue?', 1);

INSERT INTO [QPS-SWP391].[dbo].[Question] ([type_id], [topic_id], [dimension_id], [status], [level], [content], [explaination], [value])
VALUES 
    (1, 1, 1, 1, 1, 'What is the purpose of a constructor in Object-oriented programming?', 'A constructor is a special method that is used to initialize objects. It is called automatically when an object is created.', 1),
    (2, 1, 1, 1, 2, 'What is the difference between stack and heap memory?', 'Stack memory is used for static memory allocation and stores local variables and function calls, while heap memory is used for dynamic memory allocation and stores objects and data structures.', 2),
    (3, 1, 1, 1, 1, 'What is the difference between primary key and foreign key?', 'A primary key is a unique identifier for a record in a table, while a foreign key is a field in a table that refers to the primary key of another table.', 1),
    (1, 1, 1, 1, 2, 'What is the difference between procedural programming and object-oriented programming?', 'Procedural programming focuses on procedures or functions that operate on data, while object-oriented programming focuses on objects that contain both data and behavior.', 2),
    (2, 1, 1, 1, 1, 'What is the difference between static and instance variables?', 'Static variables are shared among all instances of a class and are accessed using the class name, while instance variables are unique to each instance of a class.', 1),
    (1, 1, 1, 1, 3, 'What is the difference between abstract class and interface?', 'An abstract class can have both abstract and non-abstract methods, while an interface can only have abstract methods. A class can implement multiple interfaces, but can only inherit from one abstract class.', 3),
    (3, 1, 1, 1, 1, 'What is the difference between SQL and NoSQL databases?', 'SQL databases are relational databases that use structured query language for defining and manipulating the data, while NoSQL databases are non-relational databases that provide flexible schema and horizontal scalability.', 1),
    (1, 1, 1, 1, 2, 'What is the difference between primary key and unique key?', 'A primary key is a column or a set of columns that uniquely identifies a record in a table and cannot contain null values, while a unique key is a column or a set of columns that ensures the values are unique but can contain null values.', 2),
    (2, 1, 1, 1, 1, 'What is the difference between inner join and outer join?', 'An inner join returns only the matching rows from both tables, while an outer join returns all the rows from one table and the matching rows from the other table.', 1),
    (1, 1, 1, 1, 3, 'What is the difference between primary key and candidate key?', 'A primary key is a candidate key that is chosen to uniquely identify a record in a table, while a candidate key is a column or a set of columns that can uniquely identify a record in a table.', 3),
    (3, 1, 1, 1, 1, 'What is the difference between clustered and non-clustered index?', 'A clustered index determines the physical order of data in a table, while a non-clustered index is a separate structure that points to the physical location of data in a table.', 1),
    (1, 1, 1, 1, 2, 'What is the difference between primary key and super key?', 'A super key is a set of one or more columns that can uniquely identify a record in a table, while a primary key is a super key that is chosen to uniquely identify a record in a table.', 2),
    (2, 1, 1, 1, 1, 'What is the difference between left join and right join?', 'A left join returns all the rows from the left table and the matching rows from the right table, while a right join returns all the rows from the right table and the matching rows from the left table.', 1),
    (1, 1, 1, 1, 3, 'What is the difference between database and schema?', 'A database is a collection of related data that is organized and stored, while a schema is a logical container for database objects such as tables, views, and procedures.', 3),
    (3, 1, 1, 1, 1, 'What is the difference between primary key and surrogate key?', 'A primary key is a candidate key that is chosen to uniquely identify a record in a table, while a surrogate key is an artificially generated key that is used as a substitute for a natural key.', 1),
    (2, 1, 1, 1, 2, 'What is the difference between view and materialized view?', 'A view is a virtual table that is based on the result of a query, while a materialized view is a physical copy of the result of a query that is stored on disk.', 2),
    (1, 1, 1, 1, 1, 'What is the difference between primary key and foreign key constraint?', 'A primary key constraint ensures that the primary key values are unique and not null, while a foreign key constraint ensures that the values in the foreign key column match the values in the primary key column of the referenced table.', 1),
    (3, 1, 1, 1, 2, 'What is the difference between database and data warehouse?', 'A database is designed for transactional processing and supports real-time data access and updates, while a data warehouse is designed for analytical processing and supports complex queries and reporting.', 2),
    (2, 1, 1, 1, 1, 'What is the difference between stored procedure and function?', 'A stored procedure is a set of SQL statements that are stored in the database and can be executed as a single unit, while a function is a database object that returns a value based on the input parameters.', 1),
    (1, 1, 1, 1, 3, 'What is the difference between primary key and unique constraint?', 'A primary key constraint ensures that the primary key values are unique and not null, while a unique constraint ensures that the values in the column or set of columns are unique and can contain null values.', 3);

-- SELECT TOP (1000) [answer_id]
--       ,[question_id]
--       ,[content]
--       ,[is_correct]
--   FROM [QPS-SWP391].[dbo].[Answer]

-- answer_id is the primary key of the table
-- question_id is the foreign key that references the question table
-- content is the answer content
-- is_correct 1 means correct answer, 0 means incorrect answer
-- note: if single choice question then 1 correct answer, if multiple choice question then multiple correct answers
INSERT INTO [QPS-SWP391].[dbo].[Answer] ([question_id], [content], [is_correct])
VALUES
    -- For Question 1
    (1, 'A programming language is a set of instructions written by a programmer to deliver instructions to the computer to perform and accomplish a task.', 1),
    (1, 'A programming language is a type of computer.', 0),
    (1, 'A programming language is a set of rules for writing songs.', 0),
    (1, 'A programming language is a physical book.', 0),

    -- For Question 2
    (2, 'A programming language is a formal language comprising a set of instructions that produce various kinds of output.', 1),
    (2, 'A programming language is a type of fruit.', 0),
    (2, 'A programming language is a game.', 0),
    (2, 'A programming language is a style of clothing.', 0),

    -- For Question 3 (Multiple Choice)
    (3, 'A variable is a named storage location in memory that holds a value.', 1),
    (3, 'A variable is a type of insect.', 0),
    (3, 'A variable is a kind of fruit.', 0),
    (3, 'A variable is a named storage location on disk.', 0),

    -- For Question 4 (Fill in the Blank)
    (4, 'An algorithm is a step-by-step procedure or formula for solving a problem.', 1),

    -- For Question 5
    (5, 'A loop is used to repeat a block of code as long as the loop condition is true.', 1),
    (5, 'A loop is used to write songs.', 0),
    (5, 'A loop is a type of fruit.', 0),
    (5, 'A loop is used to draw shapes.', 0),

    -- For Question 6 (Multiple Choice)
    (6, 'A function is a block of organized, reusable code that is used to perform a single, related action.', 1),
    (6, 'A function is a block of code that is never reused.', 0),
    (6, 'A function is a type of variable.', 0),
    (6, 'A function is a command to delete data.', 0),

    -- For Question 7
    (7, 'Object-oriented programming (OOP) is a programming paradigm based on the concept of objects, which can contain data and code that manipulates the data.', 1),
    (7, 'Object-oriented programming is a type of hardware.', 0),
    (7, 'Object-oriented programming is a type of fruit.', 0),
    (7, 'Object-oriented programming is a style of clothing.', 0),

    -- For Question 8
    (8, 'An array is a collection of elements identified by index or key.', 1),
    (8, 'An array is a type of food.', 0),
    (8, 'An array is a type of variable.', 0),
    (8, 'An array is a style of music.', 0),

    -- For Question 9 (Fill in the Blank)
    (9, 'Inheritance is a feature of OOP that allows a new class to inherit properties and behavior from an existing class.', 1),

    -- For Question 10
    (10, 'A syntax error is a mistake in the source code that makes it impossible to parse (and therefore impossible to compile).', 1),
    (10, 'A syntax error is a correct code.', 0),
    (10, 'A syntax error is a style of writing.', 0),
    (10, 'A syntax error is a type of fruit.', 0),

    -- For Question 11 (Multiple Choice)
    (11, 'Polymorphism is a feature of OOP that allows methods to do different things based on the object it is acting upon.', 1),
    (11, 'Polymorphism is a type of variable.', 0),
    (11, 'Polymorphism is a type of loop.', 0),
    (11, 'Polymorphism is a feature of hardware.', 0),

    -- For Question 12
    (12, 'Encapsulation is a principle of OOP that bundles the data with the methods that operate on the data, restricting direct access to some of the objects components.', 1),
    (12, 'Encapsulation is a type of hardware.', 0),
    (12, 'Encapsulation is a style of writing.', 0),
    (12, 'Encapsulation is a type of food.', 0),

    -- For Question 13 (Fill in the Blank)
    (13, 'A compiler is a program that translates code written in a high-level programming language to a lower-level language to create an executable program.', 1),

    -- For Question 14
    (14, 'An interpreter is a program that directly executes the instructions in the source code without requiring them to be compiled into a machine language program.', 1),
    (14, 'An interpreter is a type of food.', 0),
    (14, 'An interpreter is a style of music.', 0),
    (14, 'An interpreter is a type of hardware.', 0),

    -- For Question 15 (Multiple Choice)
    (15, 'A class is a blueprint for creating objects, providing initial values for state (member variables or fields), and implementations of behavior (member functions or methods).', 1),
    (15, 'A class is a type of food.', 0),
    (15, 'A class is a style of writing.', 0),
    (15, 'A class is a type of hardware.', 0),

    -- For Question 16
    (16, 'A conditional statement allows the program to choose different paths of execution based on the outcome of an expression or condition.', 1),
    (16, 'A conditional statement is a type of food.', 0),
    (16, 'A conditional statement is a type of variable.', 0),
    (16, 'A conditional statement is a style of writing.', 0),

    -- For Question 17 (Fill in the Blank)
    (17, 'Recursion is a programming technique where a function calls itself directly or indirectly to solve a problem.', 1),

    -- For Question 18 (Multiple Choice)
    (18, 'A data structure is a way of organizing and storing data so that it can be accessed and modified efficiently.', 1),
    (18, 'A data structure is a type of variable.', 0),
    (18, 'A data structure is a style of writing.', 0),
    (18, 'A data structure is a type of hardware.', 0),

    -- For Question 19
    (19, 'A stack is a linear data structure that follows the Last In First Out (LIFO) principle.', 1),
    (19, 'A stack is a type of food.', 0),
    (19, 'A stack is a style of writing.', 0),
    (19, 'A stack is a type of hardware.', 0),

    -- For Question 20 (Multiple Choice)
    (20, 'A queue is a linear data structure that follows the First In First Out (FIFO) principle.', 1),
    (20, 'A queue is a type of food.', 0),
    (20, 'A queue is a style of writing.', 0),
    (20, 'A queue is a type of hardware.', 0),

    -- TO DO: Add answers for the remaining questions (from 21)
    -- For Question 21
    (21, 'A constructor is a special method that is used to initialize objects. It is called automatically when an object is created.', 1),
    (21, 'A constructor is a type of food.', 0),
    (21, 'A constructor is a style of writing.', 0),
    (21, 'A constructor is a type of hardware.', 0),

    -- For Question 22 (Fill in the Blank)
    (22, 'Stack memory is used for static memory allocation and stores local variables and function calls, while heap memory is used for dynamic memory allocation and stores objects and data structures.', 1),

    -- For Question 23
    (23, 'A primary key is a unique identifier for a record in a table, while a foreign key is a field in a table that refers to the primary key of another table.', 1),
    (23, 'A primary key is a type of food.', 0),
    (23, 'A primary key is a style of writing.', 0),
    (23, 'A primary key is a type of hardware.', 0),

    -- For Question 24 (Multiple Choice)
    (24, 'Procedural programming focuses on procedures or functions that operate on data, while object-oriented programming focuses on objects that contain both data and behavior.', 1),
    (24, 'Procedural programming is a type of food.', 0),
    (24, 'Procedural programming is a style of writing.', 0),
    (24, 'Procedural programming is a type of hardware.', 0),

    -- For Question 25
    (25, 'Static variables are shared among all instances of a class and are accessed using the class name, while instance variables are unique to each instance of a class.', 1),
    (25, 'Static variables are a type of food.', 0),
    (25, 'Static variables are a style of writing.', 0),
    (25, 'Static variables are a type of hardware.', 0),

    -- For Question 26 (Multiple Choice)
    (26, 'An abstract class can have both abstract and non-abstract methods, while an interface can only have abstract methods. A class can implement multiple interfaces, but can only inherit from one abstract class.', 1),
    (26, 'An abstract class is a type of food.', 0),
    (26, 'An abstract class is a style of writing.', 0),
    (26, 'An abstract class is a type of hardware.', 0),

    -- For Question 27
    (27, 'SQL databases are relational databases that use structured query language for defining and manipulating the data, while NoSQL databases are non-relational databases that provide flexible schema and horizontal scalability.', 1),
    (27, 'SQL databases are a type of food.', 0),
    (27, 'SQL databases are a style of writing.', 0),
    (27, 'SQL databases are a type of hardware.', 0),

    -- For Question 28
    (28, 'A primary key is a column or a set of columns that uniquely identifies a record in a table and cannot contain null values, while a unique key is a column or a set of columns that ensures the values are unique but can contain null values.', 1),
    (28, 'A primary key is a type of food.', 0),
    (28, 'A primary key is a style of writing.', 0),
    (28, 'A primary key is a type of hardware.', 0),

    -- For Question 29 (Multiple Choice)
    (29, 'A foreign key is a field in a table that refers to the primary key of another table, establishing a relationship between the two tables.', 1),
    (29, 'A foreign key is a type of food.', 0),
    (29, 'A foreign key is a style of writing.', 0),
    (29, 'A foreign key is a type of hardware.', 0),

    -- For Question 30
    (30, 'Normalization is the process of organizing data in a database to reduce redundancy and improve data integrity.', 1),
    (30, 'Normalization is a type of food.', 0),
    (30, 'Normalization is a style of writing.', 0),
    (30, 'Normalization is a type of hardware.', 0);

    -- TO DO: Add answers for the remaining questions (from 31 to 40)
    -- For Question 31
    (31, 'A primary key is a candidate key that is chosen to uniquely identify a record in a table, while a candidate key is a column or a set of columns that can uniquely identify a record in a table.', 1),
    (31, 'A primary key is a type of food.', 0),
    (31, 'A primary key is a style of writing.', 0),
    (31, 'A primary key is a type of hardware.', 0),

    -- For Question 32 (Multiple Choice)
    (32, 'An inner join returns only the matching rows from both tables, while an outer join returns all the rows from one table and the matching rows from the other table.', 1),
    (32, 'An inner join is a type of food.', 0),
    (32, 'An inner join is a style of writing.', 0),
    (32, 'An inner join is a type of hardware.', 0),

    -- For Question 33
    (33, 'A primary key is a candidate key that is chosen to uniquely identify a record in a table, while a candidate key is a column or a set of columns that can uniquely identify a record in a table.', 1),
    (33, 'A primary key is a type of food.', 0),
    (33, 'A primary key is a style of writing.', 0),
    (33, 'A primary key is a type of hardware.', 0),

    -- For Question 34 (Multiple Choice)
    (34, 'A clustered index determines the physical order of data in a table, while a non-clustered index is a separate structure that points to the physical location of data in a table.', 1),
    (34, 'A clustered index is a type of food.', 0),
    (34, 'A clustered index is a style of writing.', 0),
    (34, 'A clustered index is a type of hardware.', 0),

    -- For Question 35
    (35, 'A primary key is a candidate key that is chosen to uniquely identify a record in a table, while a candidate key is a column or a set of columns that can uniquely identify a record in a table.', 1),
    (35, 'A primary key is a type of food.', 0),
    (35, 'A primary key is a style of writing.', 0),
    (35, 'A primary key is a type of hardware.', 0),

    -- For Question 36 (Multiple Choice)
    (36, 'A super key is a set of one or more columns that can uniquely identify a record in a table, while a primary key is a super key that is chosen to uniquely identify a record in a table.', 1),
    (36, 'A super key is a type of food.', 0),
    (36, 'A super key is a style of writing.', 0),
    (36, 'A super key is a type of hardware.', 0),

    -- For Question 37
    (37, 'A left join returns all the rows from the left table and the matching rows from the right table, while a right join returns all the rows from the right table and the matching rows from the left table.', 1),
    (37, 'A left join is a type of food.', 0),
    (37, 'A left join is a style of writing.', 0),
    (37, 'A left join is a type of hardware.', 0),

    -- For Question 38 (Multiple Choice)
    (38, 'A database is a collection of related data that is organized and stored, while a schema is a logical container for database objects such as tables, views, and procedures.', 1),
    (38, 'A database is a type of food.', 0),
    (38, 'A database is a style of writing.', 0),
    (38, 'A database is a type of hardware.', 0),

    -- For Question 39
    (39, 'A primary key is a candidate key that is chosen to uniquely identify a record in a table, while a surrogate key is an artificially generated key that is used as a substitute for a natural key.', 1),
    (39, 'A primary key is a type of food.', 0),
    (39, 'A primary key is a style of writing.', 0),
    (39, 'A primary key is a type of hardware.', 0),

    -- For Question 40 (Multiple Choice)
    (40, 'A view is a virtual table that is based on the result of a query, while a materialized view is a physical copy of the result of a query that is stored on disk.', 1),
    (40, 'A view is a type of food.', 0),
    (40, 'A view is a style of writing.', 0),
    (40, 'A view is a type of hardware.', 0);

--  SELECT TOP (1000) [quiz_id]
--       ,[name]
--       ,[subject_id]
--       ,[duration]
--       ,[quiz_type]
--       ,[num_questions]
--       ,[level]
--       ,[status]
--       ,[pass_rate]
--       ,[description]
--       ,[created_at]
--       ,[updated_at]
--   FROM [QPS-SWP391].[dbo].[Quiz]   

-- quiz_id is the primary key of the table
-- name is the name of the quiz
-- subject_id is the foreign key that references the subject table
-- duration is the time limit for the quiz in minutes
-- quiz_type 1 means practice quiz, 2 means simulation exam quiz
-- num_questions is the number of questions in the quiz
-- level 1 means easy, 2 means medium, 3 means hard
-- status 1 means active, 0 means inactive
-- pass_rate is the passing rate for the quiz
-- description is the description of the quiz
-- created_at is the date and time the quiz was created
-- updated_at is the date and time the quiz was last updated

INSERT INTO [QPS-SWP391].[dbo].[Quiz] ([name], [subject_id], [duration], [quiz_type], [num_questions], [level], [status], [pass_rate], [description], [created_at], [updated_at])
VALUES
    ('Introduction to Programming Basics', 1, 30, 1, 10, 1, 1, 70, 'A basic quiz to test introductory concepts in programming.', GETDATE(), GETDATE()),
    ('Intermediate Programming Concepts', 1, 45, 1, 15, 2, 1, 75, 'A quiz covering intermediate-level programming concepts.', GETDATE(), GETDATE()),
    ('Advanced Programming Techniques', 1, 60, 1, 20, 3, 1, 80, 'An advanced quiz focusing on complex programming techniques.', GETDATE(), GETDATE()),
    ('Programming Practice Exam', 1, 90, 2, 25, 2, 1, 70, 'A simulation exam to practice programming skills.', GETDATE(), GETDATE()),
    ('Final Programming Simulation Exam', 1, 120, 2, 30, 3, 1, 85, 'A comprehensive simulation exam covering all programming topics.', GETDATE(), GETDATE());


-- SELECT TOP (1000) [quiz_id]
--       ,[question_id]
--       ,[order]
--   FROM [QPS-SWP391].[dbo].[Quiz_Question]

-- quiz_id is the foreign key of quiz
-- question_id is the foreign key of question
-- order is the order defined in the quiz

ALTER TABLE [QPS-SWP391].[dbo].[Quiz_Question]
ADD CONSTRAINT UC_Quiz_Question_Quiz_Order UNIQUE ([quiz_id], [order]);

INSERT INTO [QPS-SWP391].[dbo].[Quiz_Question] ([quiz_id], [question_id], [order])
VALUES
    -- Adding questions to 'Introduction to Programming Basics' quiz (quiz_id = 1)
    (1, 1, 1),
    (1, 2, 2),
    (1, 3, 3),
    (1, 4, 4),
    (1, 5, 5),
    (1, 6, 6),
    (1, 7, 7),
    (1, 8, 8),
    (1, 9, 9),
    (1, 10, 10),

    -- Adding questions to 'Intermediate Programming Concepts' quiz (quiz_id = 2)
    (2, 1, 1),
    (2, 2, 2),
    (2, 3, 3),
    (2, 5, 4),
    (2, 6, 5),
    (2, 7, 6),
    (2, 9, 7),
    (2, 10, 8),
    (2, 11, 9),
    (2, 12, 10),
    (2, 13, 11),
    (2, 14, 12),
    (2, 15, 13),
    (2, 16, 14),
    (2, 17, 15),

    -- Adding questions to 'Advanced Programming Techniques' quiz (quiz_id = 3)
    (3, 1, 1),
    (3, 2, 2),
    (3, 4, 3),
    (3, 6, 4),
    (3, 8, 5),
    (3, 9, 6),
    (3, 11, 7),
    (3, 13, 8),
    (3, 14, 9),
    (3, 15, 10),
    (3, 16, 11),
    (3, 17, 12),
    (3, 18, 13),
    (3, 19, 14),
    (3, 20, 15),
    (3, 3, 16),
    (3, 5, 17),
    (3, 7, 18),
    (3, 10, 19),
    (3, 12, 20),

    -- Adding questions to 'Programming Practice Exam' quiz (quiz_id = 4)
    (4, 1, 1),
    (4, 2, 2),
    (4, 3, 3),
    (4, 4, 4),
    (4, 5, 5),
    (4, 6, 6),
    (4, 7, 7),
    (4, 8, 8),
    (4, 9, 9),
    (4, 10, 10),
    (4, 11, 11),
    (4, 12, 12),
    (4, 13, 13),
    (4, 14, 14),
    (4, 15, 15),
    (4, 16, 16),
    (4, 17, 17),
    (4, 18, 18),
    (4, 19, 19),
    (4, 20, 20),
    (4, 1, 21),
    (4, 2, 22),
    (4, 3, 23),
    (4, 4, 24),
    (4, 5, 25),

    -- Adding questions to 'Final Programming Simulation Exam' quiz (quiz_id = 5)
    (5, 1, 1),
    (5, 2, 2),
    (5, 3, 3),
    (5, 4, 4),
    (5, 5, 5),
    (5, 6, 6),
    (5, 7, 7),
    (5, 8, 8),
    (5, 9, 9),
    (5, 10, 10),
    (5, 11, 11),
    (5, 12, 12),
    (5, 13, 13),
    (5, 14, 14),
    (5, 15, 15),
    (5, 16, 16),
    (5, 17, 17),
    (5, 18, 18),
    (5, 19, 19),
    (5, 20, 20),
    (5, 1, 21),
    (5, 2, 22),
    (5, 3, 23),
    (5, 4, 24),
    (5, 5, 25),
    (5, 6, 26),
    (5, 7, 27),
    (5, 8, 28),
    (5, 9, 29),
    (5, 10, 30);

-- SELECT TOP (1000) [record_id]
--       ,[user_id]
--       ,[quiz_id]
--       ,[created_at]
--       ,[score]
--       ,[finished_at]
--   FROM [QPS-SWP391].[dbo].[QuizRecord]

-- record_id is the key of the table
-- user id is foreign key to the user make the record
-- score is the result of user, calculated by the sum of value of correct answers

-- SELECT TOP (1000) [id]
--       ,[record_id]
--       ,[question_id]
--       ,[is_flagged]
--       ,[content]
--       ,[answer_id]
--   FROM [QPS-SWP391].[dbo].[RecordAnswer]

-- this is reference to the user answer in record
-- answer_id is for multiple/single choice question, if the question is fill in blank then the answer_id can be null, and check by content instead
-- content can also be null if answer_id is not null

-- update the data in record answer table so that the answer_id should be an answer that have the same question_id with record answer
-- and the content should be null
UPDATE [QPS-SWP391].[dbo].[RecordAnswer]
SET [answer_id] = (
    SELECT TOP 1 [answer_id]
    FROM [QPS-SWP391].[dbo].[Answer]
    WHERE [Answer].[question_id] = [RecordAnswer].[question_id]
    ORDER BY NEWID()
)
WHERE [content] IS NULL;

-- set some random record answer answer_id to the correct answer of the corresponding question

-- set all if content not null then answer_id should be null
UPDATE [QPS-SWP391].[dbo].[RecordAnswer]
SET [answer_id] = NULL
WHERE [content] IS NOT NULL;

-- /****** Script for SelectTopNRows command from SSMS  ******/
-- SELECT TOP (1000) [id]
--       ,[name]
--       ,[description]
--       ,[subject_id]
--   FROM [QPS-SWP391].[dbo].[SubjectTopic]

-- SELECT TOP (1000) [quiz_id]
--       ,[name]
--       ,[duration]
--       ,[quiz_type]
--       ,[num_questions]
--       ,[level]
--       ,[status]
--       ,[pass_rate]
--       ,[description]
--       ,[created_at]
--       ,[updated_at]
--       ,[subject_id]
--       ,[pass_condition]
--   FROM [QPS-SWP391].[dbo].[Quiz]

-- add more sample quiz for subject 2, subject 3, type 2, name inlcude 'Simulation Exam'
INSERT INTO [QPS-SWP391].[dbo].[Quiz] ([name], [subject_id], [duration], [quiz_type], [num_questions], [level], [status], [pass_rate], [description], [created_at], [updated_at])
VALUES
    ('Introduction to Data Structures', 2, 30, 1, 10, 1, 1, 70, 'A basic quiz to test introductory concepts in data structures.', GETDATE(), GETDATE()),
    ('Intermediate Data Structures Concepts', 2, 45, 1, 15, 2, 1, 75, 'A quiz covering intermediate-level data structures concepts.', GETDATE(), GETDATE()),
    ('Advanced Data Structures Techniques', 2, 60, 1, 20, 3, 1, 80, 'An advanced quiz focusing on complex data structures techniques.', GETDATE(), GETDATE()),
    ('Data Structures Practice Exam', 2, 90, 2, 25, 2, 1, 70, 'A simulation exam to practice data structures skills.', GETDATE(), GETDATE()),
    ('Final Data Structures Simulation Exam', 2, 120, 2, 20, 3, 1, 85, 'A comprehensive simulation exam covering all data structures topics.', GETDATE(), GETDATE()),
    ('Introduction to Database Management', 3, 30, 1, 10, 1, 1, 70, 'A basic quiz to test introductory concepts in database management.', GETDATE(), GETDATE()),
    ('Intermediate Database Management Concepts', 3, 45, 1, 15, 2, 1, 75, 'A quiz covering intermediate-level database management concepts.', GETDATE(), GETDATE()),
    ('Advanced Database Management Techniques', 3, 60, 1, 20, 3, 1, 80, 'An advanced quiz focusing on complex database management techniques.', GETDATE(), GETDATE()),
    ('Database Management Practice Exam', 3, 90, 2, 20, 2, 1, 70, 'A simulation exam to practice database management skills.', GETDATE(), GETDATE()),
    ('Final Database Management Simulation Exam', 3, 120, 2, 30, 3, 1, 85, 'A comprehensive simulation exam covering all database management topics.', GETDATE(), GETDATE());

-- add data to quiz_question for the new quiz. for each quiz, try to no duplicate question
-- for each quiz, add 10 questions from the subject topic that have the same subject_id with the quiz
-- if the subject topic have less than 10 questions, then add all questions from the subject topic
-- if the subject topic have more than 10 questions, then add random 10 questions from the subject topic
-- for each quiz, the order of the questions should be random
-- for each quiz, the order of the questions should be unique
-- for each quiz, add 10 questions from the subject dimension that have the same subject_id with the quiz
-- if the subject dimension have less than 10 questions, then add all questions from the subject dimension
-- if the subject dimension have more than 10 questions, then add random 10 questions from the subject dimension
-- for each quiz, the amount of quiz_question should be equal num_questions of the quiz

