# Library Management System

## Description

A library management system, built in Java. Aims to ease the process of borrowing and 
returning books by keeping a record of the books, the library members, the transactions 
and the reservations made in every branch library.

## Main features
* Keep a record of all the branch libraries, locations, books
* View detailed information of the items recorded
* Add branch libraries, locations, library members, books, book copies, categories to the record
* Update the information 
* Delete the information
* Record a check in of a book copy
* Record a check out of a book copy
* Make a reservation for a book that is not currently available
* Calculate the penalty for being late with the check out of a book
* Find easily if a library member is allowed to borrow a book or not (has overdue books or
has already borrowed the maximum number allowed)

## Object types
* Library member
* Branch library (eg. Sediul Central, Facultatea de Litere, etc)
* Location (eg. Sala de lectura, Depozit I, etc)
* Category
* Book
* Book copy
* Reservation
* Transaction
  * Check in
  * Check out

## Classes Diagram
<p>
  <img src="diagram/ClassesDiagram.png" alt="Classes Diagram">
</p>
