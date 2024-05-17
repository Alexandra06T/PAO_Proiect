DROP TABLE IF EXISTS checkout, checkin, libraryms.transaction, reservation, librarymember, bookcopy, location, branchlibrary, book, category;

CREATE TABLE `libraryms`.`category`
(
    `categoryIndex` INT NOT NULL,
    `name`  VARCHAR(50) NULL,
    PRIMARY KEY (`categoryIndex`)
);

CREATE TABLE `libraryms`.`book` (
                                       `ISBN` VARCHAR(13) NOT NULL,
                                       `title` VARCHAR(50) NULL,
                                       `authors` VARCHAR(300) NULL,
                                       `publishingHouse` VARCHAR(50) NULL,
                                       `publishedDate` INT NULL,
                                       `numberOfPages` INT NULL,
                                       `categoryID` INT NOT NULL,
                                       PRIMARY KEY (`ISBN`),
                                       FOREIGN KEY (`categoryID`) REFERENCES category (`categoryIndex`) ON DELETE CASCADE );

CREATE TABLE `libraryms`.`branchlibrary` (
                                    `ID` INT NOT NULL AUTO_INCREMENT,
                                    `name` VARCHAR(50) NULL,
                                    `address` VARCHAR(200) NULL,
                                    PRIMARY KEY (`ID`)
                                    );


CREATE TABLE `libraryms`.`location` (
                                    `ID` INT NOT NULL AUTO_INCREMENT,
                                    `name` VARCHAR(50) NULL,
                                    `branchlibraryID` INT NOT NULL  ,
                                    PRIMARY KEY (`ID`),
                                    FOREIGN KEY (`branchlibraryID`) REFERENCES branchlibrary (`ID`) ON DELETE CASCADE);


CREATE TABLE `libraryms`.`bookcopy` (
                                    `ID` INT NOT NULL AUTO_INCREMENT,
                                    `barcode` VARCHAR(50) NULL,
                                    `index` VARCHAR(50) NULL,
                                    `available` BOOLEAN NULL,
                                    `bookID` VARCHAR(13) NOT NULL,
                                    `locationID` INT NOT NULL,
                                    PRIMARY KEY (`ID`),
                                    FOREIGN KEY (`bookID`) REFERENCES book (`ISBN`) ON DELETE CASCADE,
                                    FOREIGN KEY (locationID) REFERENCES location (`ID`));

CREATE TABLE `libraryms`.`librarymember` (
                                    `ID` INT NOT NULL AUTO_INCREMENT,
                                    `name` VARCHAR(70) NULL,
                                    `emailaddress` VARCHAR(100) NULL,
                                    `phonenumber` VARCHAR(15) NULL,
                                    `address` VARCHAR(100) NULL,
                                    PRIMARY KEY (`ID`));

CREATE TABLE `libraryms`.`reservation` (
                                    `ID` INT NOT NULL AUTO_INCREMENT,
                                    `expirydate` DATE NULL,
                                    `bookID` VARCHAR(50) NOT NULL,
                                    `librarymemberID` INT NOT NULL ,
                                    `pickuplocation` INT NOT NULL,
                                    PRIMARY KEY (`ID`),
                                    FOREIGN KEY (`bookID`) REFERENCES book (`ISBN`) ON DELETE CASCADE ,
                                    FOREIGN KEY (`librarymemberID`) REFERENCES librarymember (`ID`) ON DELETE CASCADE,
                                    FOREIGN KEY (`pickuplocation`) REFERENCES location (`ID`) ON DELETE CASCADE);

CREATE TABLE `libraryms`.`transaction` (
                                    `ID` INT NOT NULL AUTO_INCREMENT,
                                    `date` DATE NULL,
                                    `transactiontype` VARCHAR(50) NOT NULL,
                                    `librarymemberID` INT NOT NULL,
                                    `bookcopyID` INT NOT NULL,
                                    PRIMARY KEY (`ID`),
                                    FOREIGN KEY (`librarymemberID`) REFERENCES librarymember (`ID`) ON DELETE CASCADE,
                                    FOREIGN KEY (`bookcopyID`) REFERENCES bookcopy (`ID`) ON DELETE CASCADE);

CREATE TABLE `libraryms`.`checkin` (
                                    `checkinID` INT PRIMARY KEY REFERENCES transaction (`ID`) ON DELETE CASCADE,
                                    `numberdays` INT NULL,
                                    `checkedout` BOOLEAN NULL DEFAULT FALSE,
                                    `type` VARCHAR(50) NULL
                                    );
CREATE TABLE `libraryms`.`checkout` (
                                       `checkoutID` INT PRIMARY KEY REFERENCES transaction (`ID`) ON DELETE CASCADE,
                                       `bookstatus` VARCHAR(50) NULL,
                                       `overduedays` INT NULL,
                                       `penalty` DOUBLE NULL,
                                       `checkinID` INT NOT NULL UNIQUE,
                                       FOREIGN KEY (`checkinID`) REFERENCES checkin (`checkinID`) ON DELETE CASCADE
);