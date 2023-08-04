CREATE TABLE IF NOT EXISTS Users (
    userID INT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS UserProgress (
    userID INT,
    dateOfTask DATE,
    wordsLearned INT,
    PRIMARY KEY (userID, dateOfTask),
    FOREIGN KEY (userID) REFERENCES Users(userID)
    );

CREATE TABLE IF NOT EXISTS UserLearnedWords (
    userID INT,
    dateOfTask DATE,
    word VARCHAR(255) NOT NULL,
    explanation TEXT,
    PRIMARY KEY (userID, dateOfTask, word),
    FOREIGN KEY (userID) REFERENCES Users(userID)
    );
