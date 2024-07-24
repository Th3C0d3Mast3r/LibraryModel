# Library Management System

A simple and efficient Library Management System developed using Java and MySQL. This system allows students to:- 
- LOG IN
- VIEW AVAILABLE BOOKS
- LEND BOOKS
- RETURN BOOKS

It also provides a Secret Interface for the ```LibraryAdmin``` to Add new students to the database.

## Features

- **User Authentication**: Secure login for students.
- **Book Inventory Management**: View, lend, and return books.
- **Admin Functionality**: Add new students to the library system.
- **Database Integration**: Uses MySQL to store and manage data.

## Prerequisites

- **Java**: Ensure you have JDK installed. [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
- **MySQL**: Ensure MySQL server is installed and running. [Download MySQL](https://dev.mysql.com/downloads/mysql/)
- **JDBC Driver**: MySQL Connector/J. [Download JDBC Driver](https://dev.mysql.com/downloads/connector/j/)

## Database Setup

1. Create the database and tables using the following SQL script:

    ```sql
    create database if not exists CollegeLibrary;
    use CollegeLibrary;

    create table if not exists StudentLog(
        uid int primary key,
        studentName varchar(30),
        studentMail varchar(50),
        passwords varchar(15)
    );

    create table if not exists BookLog (
        uid int,
        bookName varchar(30),
        ISBNcode varchar(15),
        issueDate date,
        bookStatus varchar(10),
        foreign key (uid) references StudentLog(uid)
    );
    ALTER TABLE BookLog ADD COLUMN returnDate date;

    create table if not exists BookInventory (
        bookName varchar(30),
        ISBNcode varchar(15) primary key,
        quantity int,
        lastIssuedDate date,
        status varchar(10) check (status in ('ISSUED', 'AVAILABLE'))
    );

    /* Adding some example books */
    insert into BookInventory (bookName, ISBNcode, quantity, lastIssuedDate, status) values
    ("Effective Java", "978-0134685991", 10, NULL, "AVAILABLE"),
    ("Clean Code", "978-0132350884", 5, '2024-07-20', "ISSUED"),
    ("The Pragmatic Programmer", "978-0135957059", 7, '2024-07-18', "ISSUED"),
    ("Introduction to Algorithms", "978-0262033848", 3, '2024-07-22', "ISSUED"),
    ("Design Patterns", "978-0201633610", 4, NULL, "AVAILABLE");

    select * from BookInventory; /* Viewing the new BookInventory table */

    /* Insert example users */
    insert into StudentLog
    (uid, studentName, studentMail, passwords)
    values
    (1, "Student1", "student1@institute.com", "password"),
    (2, "Student2", "student2@institute.com", "qwerty");

    select * from StudentLog; /* View StudentLog */
    select * from BookLog; /* View BookLog */	
    select * from BookInventory; /* View BookInventory */

    /* (- - - - - CAUTIOUS- DO NOT EXECUTE WITHOUT PERMISSION - - - - - -) */
    /* (this is to clear the table and make it new and blank before new use/demonstration) */
    set SQL_SAFE_UPDATES=0;
    delete from BookLog;
    delete from StudentLog;
    delete from BookInventory;
    ```

2. Make sure to update your MySQL connection details in the code (username and password).

## How to Run

1. Clone the repository:

    ```sh
    git clone https://github.com/yourusername/college-library-system.git
    cd college-library-system
    ```

2. Compile and run the Java program:

    ```sh
    javac -cp .:mysql-connector-java-8.0.23.jar LibraryModel/*.java
    java -cp .:mysql-connector-java-8.0.23.jar LibraryModel.Main
    ```

3. Follow the prompts to log in as a student or add new students as an admin.

## Project Structure

```
├── LibraryModel/
│   ├── Main.java
│   ├── AddStudent.java
│   ├── LendBook.java
│   ├── ReturnBook.java
│   ├── ViewBooks.java
├── README.md
└── mysql-connector-java-8.0.23.jar
```

## Classes Overview

- **Main.java**: Handles user login, authentication, and menu navigation.
- **AddStudent.java**: Allows admin to add new students to the database.
- **LendBook.java**: Manages the process of lending a book to a student and updates the database accordingly.
- **ReturnBook.java**: Manages the process of returning a book and updates the database accordingly.
- **ViewBooks.java**: Displays the book inventory.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Contributing

```Contributions are Welcome to Make this Project Better :) ```
