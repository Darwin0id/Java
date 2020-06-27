/* --------- PROJEKT: MOVIESCMS --------- */
USE master;
GO
DROP DATABASE IF EXISTS MoviesCMS
GO
CREATE DATABASE MoviesCMS
GO
USE MoviesCMS
/* -------------------------------------- */

GO

/* ---------- TABLICA: RAZINE ----------- */
CREATE TABLE AccountLevel
(
	IDAccLevel INT PRIMARY KEY IDENTITY,
	NameLevel NVARCHAR(50)
)
GO
INSERT INTO AccountLevel (NameLevel) VALUES ('Admin'), ('User')
GO
/* -------------------------------------- */

GO

/* ----------- TABLICA: RAČUN ----------- */
CREATE TABLE Account
(
	IDUser INT PRIMARY KEY IDENTITY,
	Username NVARCHAR(50),
	Password NVARCHAR(512),
	AccoutLevelID INT FOREIGN KEY REFERENCES AccountLevel(IDAccLevel)
)
GO
INSERT INTO Account (Username, Password, AccoutLevelID) VALUES
('admin', '145E9D9C114822D3FD8C8CAE045B241973135D1D096562984A0047F5C0F86B786BC3CC5D93DE22CE4AD773D96D270832669500D04ECB1574E0F06A9046178845', 1),
('user', '145E9D9C114822D3FD8C8CAE045B241973135D1D096562984A0047F5C0F86B786BC3CC5D93DE22CE4AD773D96D270832669500D04ECB1574E0F06A9046178845', 2)
/* -------------------------------------- */

GO

/* ----------- TABLICA: ŽANR ------------ */
CREATE TABLE Genre 
(
	IDGenre INT PRIMARY KEY IDENTITY,
	Name NVARCHAR(50),
	Active BIT DEFAULT 1
)
/* -------------------------------------- */