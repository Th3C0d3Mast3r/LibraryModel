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


/*(These are the 3 Users that are there for running the base Test in the code)*/
insert into StudentLog
(uid, studentName, studentMail, passwords)
values
(1, "Student1", "student1@institute.com", "password"),
(2, "Student2", "student1@institute.com", "qwerty");

select * from StudentLog; /*(for viewing StudentLog)*/
select * from BookLog; /*(for viewing BookLog)*/	
select * from BookInventory; /*(for viewing BookInventory)*/


/*(- - - - - CAUTIOUS- DO NOT EXECUTE WITHOUT PERMISSION - - - - - -)*/
/*(this is to clear the table and make it new and blank before new use/demonstration)*/
set SQL_SAFE_UPDATES=0;
delete from BookLog;
delete from StudentLog;
delete from BookInventory;