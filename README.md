## Library Manager
A simple console-based Library Management System built with Java and SQLite.

## Description
This project allows users to manage a library though a console application.
Users can add, search, delete, borrow , and return books.
All information is stored in a SQLite database using JDBC.

## Features
1)Add a new book
2)Show all books
3)Search books by title
4)Update book information
5)Delete books
6)Borrow books
7)Return borrowed books
8)Display book availability
9)input validation
10)logging using SLF4J

## Technologies Used
1)Java
2)SQLite
3)JDBC
4)SLF4J

## Database
The project uses two tables:
## books
stores all books information
Fields:
1)id
2)title
3)author
4)year
5)genre
6)isbn
## borrowed_books
Stores borrowing records.
Fields:
1)id
2)book_id
3)status

## Future Improvement
1)Search by author or ISBN
2)Due date for borrowed books
3)Better database relationship

## Author
Niki Meratnia
