# exemple pentru testare
INSERT INTO branchlibrary (name, address)
VALUES ('Facultatea de Matematica si Informatica', 'Strada Academiei, nr 14');
INSERT INTO branchlibrary (name, address)
VALUES ('Facultatea de Litere', 'Str. Edgar Quinet, nr 5-7');

INSERT INTO location (name, branchlibraryID)
VALUES ('Depozit I', 1);
INSERT INTO location (name, branchlibraryID)
VALUES ('Sala de lectura', 1);
INSERT INTO location (name, branchlibraryID)
VALUES ('Sala de lectura', 2);

INSERT INTO librarymember (name, emailaddress, phonenumber, address)
VALUES ('Ana Popescu', 'anap@gmail.com', '0723456789', 'Str. Mare, nr. 10');
INSERT INTO librarymember (name, emailaddress, phonenumber, address)
VALUES ('George Vasile', 'georgev@yahoo.com', '0764791052', 'Str. Florilor, nr. 23');
INSERT INTO librarymember (name, emailaddress, phonenumber, address)
VALUES ('Andreea Ionescu', 'andreea.ionescu@gmail.com', '0749284053', 'Str. Zorilor, nr. 15');

INSERT INTO category (`categoryIndex`, name)
VALUES (636, 'Literatura Universala');
INSERT INTO category (`categoryIndex`, name)
VALUES (600, 'Literatura Romana');
INSERT INTO category (`categoryIndex`, name)
VALUES (278, 'Algebra Liniara');

INSERT INTO book (ISBN, title, authors, publishingHouse, publishedDate, numberOfPages, categoryID)
VALUES ('973-748-092-7', 'Vanatorii de zmeie', 'Hosseini, Khaled', 'Niculescu', 2007, 404, 636);
INSERT INTO book (ISBN, title, authors, publishingHouse, publishedDate, numberOfPages, categoryID)
VALUES ('978-505-469-4', 'Inocentii', 'Parvulescu, Ioana', 'Humanitas', 2016, 340, 600);
INSERT INTO book (ISBN, title, authors, publishingHouse, publishedDate, numberOfPages, categoryID)
VALUES ('973-879-567-1', 'Algebra Liniara', 'Creanga, Ion;Haimovici, Corina', 'Editura de Stat Didactica si Pedagogica', 1962, 308, 278);
INSERT INTO book (ISBN, title, authors, publishingHouse, publishedDate, numberOfPages, categoryID)
VALUES ('973-950-559-7', 'Fratii Karamazov', 'Dostoevskij, Feodor Mihajlovich', 'Editura Victoria', 1993, 1300, 636);

INSERT INTO bookcopy (barcode, `index`, available, bookID, locationID)
VALUES ('7485054694', 'A.45889', true, '978-505-469-4', 3);
INSERT INTO bookcopy (barcode, `index`, available, bookID, locationID)
VALUES ('7485054694', 'A.45889', true, '978-505-469-4', 3);
INSERT INTO bookcopy (barcode, `index`, available, bookID, locationID)
VALUES ('9737480927', 'A.87236', false, '973-748-092-7', 3);
INSERT INTO bookcopy (barcode, `index`, available, bookID, locationID)
VALUES ('9739505597', 'A.64589', true, '973-950-559-7', 3);
INSERT INTO bookcopy (barcode, `index`, available, bookID, locationID)
VALUES ('9738795671', 'A.II35498', true, '973-879-567-1', 1);

INSERT INTO reservation (expirydate, bookID, librarymemberID, pickuplocation)
VALUES ('2024-05-05', '973-748-092-7', 1, 3);
INSERT INTO reservation (expirydate, bookID, librarymemberID, pickuplocation)
VALUES ('2024-05-23', '973-748-092-7', 3, 3);

INSERT INTO transaction (date, transactiontype, librarymemberID, bookcopyID)
VALUES ('2024-04-27', 'checkin', 1, 1);
INSERT INTO transaction (date, transactiontype, librarymemberID, bookcopyID)
VALUES ('2024-04-25', 'checkin', 1, 5);
INSERT INTO transaction (date, transactiontype, librarymemberID, bookcopyID)
VALUES ('2024-04-26', 'checkin', 2, 4);

INSERT INTO checkin (checkinID, numberdays, checkedout, type)
VALUES (1, 14, false, 'imprumut normal');
INSERT INTO checkin (checkinID, numberdays, checkedout, type)
VALUES (2, 1, false, 'imprumut in sala de lectura');
INSERT INTO checkin (checkinID, numberdays, checkedout, type)
VALUES (3, 14, false, 'imprumut normal');
