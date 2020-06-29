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
-- TABLICA
CREATE TABLE AccountLevel
(
	IDAccLevel INT PRIMARY KEY IDENTITY,
	NameLevel NVARCHAR(50)
)

GO

-- INSERT
INSERT INTO AccountLevel (NameLevel) VALUES ('Admin'), ('User')
/* -------------------------------------- */

GO

/* ----------- TABLICA: RAČUN ----------- */
-- TABLICA
CREATE TABLE Account
(
	IDUser INT PRIMARY KEY IDENTITY,
	Username NVARCHAR(50),
	Password NVARCHAR(512),
	AccountLevelID INT FOREIGN KEY REFERENCES AccountLevel(IDAccLevel)
)

GO

-- INSERT
INSERT INTO Account (Username, Password, AccountLevelID) VALUES
('admin', '145E9D9C114822D3FD8C8CAE045B241973135D1D096562984A0047F5C0F86B786BC3CC5D93DE22CE4AD773D96D270832669500D04ECB1574E0F06A9046178845', 1),
('user', '145E9D9C114822D3FD8C8CAE045B241973135D1D096562984A0047F5C0F86B786BC3CC5D93DE22CE4AD773D96D270832669500D04ECB1574E0F06A9046178845', 2)

GO

-- PRIJAVA KORISNIKA
CREATE PROCEDURE authorizationUser
	@UserName NVARCHAR(50),
	@Password NVARCHAR(512)
AS
IF EXISTS (SELECT UserName FROM Account WHERE UserName = @UserName AND Password = @Password)
	BEGIN
		SELECT UserName, AccountLevelID AS 'UserLevel' FROM Account WHERE UserName = @UserName AND Password = @Password
	END
-- EXEC authorizationUser 'admin', '145E9D9C114822D3FD8C8CAE045B241973135D1D096562984A0047F5C0F86B786BC3CC5D93DE22CE4AD773D96D270832669500D04ECB1574E0F06A9046178845'

GO

-- REGISTRACIJA KORISNIKA
CREATE PROCEDURE createUser
	@UserName NVARCHAR(50),
	@Password NVARCHAR(512),
	@ID INT OUT
AS
IF EXISTS (SELECT UserName FROM Account WHERE UserName = @UserName AND Password = @Password)
	BEGIN
		SET @ID = 0
		RETURN (1)
	END
ELSE
	BEGIN
		INSERT INTO Account (UserName, Password, AccountLevelID) VALUES (@UserName, @Password, 2)
		SET @ID = @@identity
	END
/* -------------------------------------- */

GO

/* ----------- TABLICA: ŽANR ------------ */
CREATE TABLE Genre 
(
	IDGenre INT PRIMARY KEY IDENTITY,
	Name NVARCHAR(50),
	Active BIT DEFAULT 1
)

GO

-- DOHVATI ŽANROVE PO ID FILMOVA
CREATE PROCEDURE selectGenresByMovieID
	@MovieID INT
AS
SELECT g.IDGenre AS IDZanr, g.Name AS Zanr FROM GenreMovie AS gm
INNER JOIN Genre AS g ON gm.GenreID = g.IDGenre
WHERE gm.MovieID = @MovieID

GO

-- DODAJ ŽANROVE
CREATE PROCEDURE createGenres
	@Name NVARCHAR(50),
	@GenreID INT OUT
AS
IF EXISTS (SELECT IDGenre FROM Genre WHERE Name = @Name AND Active = 1)
	BEGIN
		SET @GenreID = (SELECT IDGenre FROM Genre WHERE Name = @Name AND Active = 1)
		RETURN (1)
	END
ELSE
	BEGIN
		INSERT INTO Genre(Name, Active) VALUES (@Name, 1)
		SET @GenreID = @@identity
		RETURN (2)
	END

GO

-- ODABERI ŽANROVE
CREATE PROCEDURE selectGenres
AS
SELECT IDGenre AS IDZanr, Name AS Zanr FROM Genre WHERE Active = 1
GO
CREATE PROCEDURE createGenre
	@Name NVARCHAR(50),
	@GenreID INT OUT
AS
IF EXISTS (SELECT IDGenre FROM Genre WHERE Name = @Name AND Active = 1)
	BEGIN
		SET @GenreID = 0
		RETURN (1)
	END
ELSE
	BEGIN
		INSERT INTO Genre(Name, Active) VALUES (@Name, 1)
		SET @GenreID = @@identity
		RETURN (2)
	END
GO

-- ODABERI ŽANR
CREATE PROCEDURE selectGenre
	@IDGenre INT
AS
SELECT IDGenre AS IDZanr, Name AS Zanr FROM Genre WHERE IDGenre = @IDGenre AND Active = 1

GO

-- BRISANJE ŽANRA
CREATE PROCEDURE deleteGenre
	@IDGenre INT,
	@ID INT OUT
AS
UPDATE Genre SET Active = 0 WHERE IDGenre = @IDGenre
SET @ID = 0

GO

-- AŽURIRAJ ŽANR
CREATE PROCEDURE updateGenre
	@IDGenre INT,
	@Name NVARCHAR(50),
	@ID INT OUT
AS
UPDATE Genre SET Name = @Name WHERE IDGenre = @IDGenre AND Active = 1
SET @ID = 0

/* -------------------------------------- */

GO

/* ----------- TABLICA: ŽANR ------------ */
CREATE TABLE EmployeeRole
(
	IDRole INT PRIMARY KEY IDENTITY,
	Name NVARCHAR(50)
)
GO
INSERT INTO EmployeeRole (Name) VALUES ('Redatelj'), ('Glumac')
/* -------------------------------------- */

GO

/* ---------- TABLICA: GLUMCI ----------- */
CREATE TABLE Employee
(
	IDEmployee INT PRIMARY KEY IDENTITY,
	FirstName NVARCHAR(50),
	LastName NVARCHAR(50),
	Active BIT,
	RoleID INT FOREIGN KEY REFERENCES EmployeeRole(IDRole)
)

GO

-- ODABIR GLUMCA PO ID FILMA
CREATE PROCEDURE selectActrosByMovieID
	@MovieID INT
AS
SELECT e.IDEmployee AS IDGlumac, e.FirstName AS GlumacIme, e.LastName AS GlumacPrezime FROM Employee AS e
INNER JOIN EmployeeMovie AS em ON e.IDEmployee = em.EmployeeID
WHERE em.MovieID = @MovieID AND e.RoleID = 2 AND e.Active = 1

GO

-- DODAJ GLUMCA
CREATE PROCEDURE createActor
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50),
	@ID INT OUT
AS
IF EXISTS (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 2)
	BEGIN
		SET @ID = 0
		RETURN (1)
	END
ELSE IF (LEN(@FirstName) > 0 AND LEN(@LastName) > 0)
	BEGIN
		INSERT INTO Employee(FirstName, LastName, RoleID, Active) VALUES (@FirstName, @LastName, 2, 1)
		SET @ID = @@identity
		RETURN (2)
	END

GO

-- ODABERI GLUMCA
CREATE PROCEDURE selectPerson
	@IDPerson INT
AS
SELECT e.IDEmployee AS IDGlumac, e.FirstName AS GlumacIme, e.LastName AS GlumacPrezime, er.Name AS Tip FROM Employee AS e
INNER JOIN EmployeeRole AS er ON e.RoleID = er.IDRole
WHERE e.IDEmployee = @IDPerson

GO

-- AŽURIRAJ GLUMCA
CREATE PROCEDURE updatePerson
	@IDPerson INT,
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50),
	@NewFirstName NVARCHAR(50),
	@NewLastName NVARCHAR(50),
	@ID INT OUT
AS
IF EXISTS (SELECT IDEmployee FROM Employee WHERE IDEmployee = @IDPerson AND Active = 1)
	BEGIN
		UPDATE Employee SET FirstName = @NewFirstName, LastName = @NewLastName WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1
		SET @ID = (SELECT RoleID FROM Employee WHERE IDEmployee = @IDPerson AND Active = 1)
		RETURN (1)
	END
	
GO

-- BRIŠI GLUMCA
CREATE PROCEDURE deletePerson
	@IDPerson INT,
	@ID INT OUT
AS
IF EXISTS (SELECT IDEmployee FROM Employee WHERE IDEmployee = @IDPerson AND Active = 1)
	BEGIN
		UPDATE Employee SET Active = 0 WHERE IDEmployee = @IDPerson
		SET @ID = (SELECT RoleID FROM Employee WHERE IDEmployee = @IDPerson AND Active = 0)
		RETURN (1)
	END
	
GO

-- ODABERI GLUMCA
CREATE PROCEDURE selectActors
AS
SELECT IDEmployee AS IDGlumac, FirstName AS GlumacIme, LastName AS GlumacPrezime FROM Employee WHERE Active = 1 AND RoleID = 2

GO

-- ODABERI GLUMCA
CREATE PROCEDURE selectDirectors
AS
SELECT IDEmployee AS IDRedatelj, FirstName AS RedateljIme, LastName AS RedateljPrezime FROM Employee WHERE Active = 1 AND RoleID = 1
GO
CREATE PROCEDURE createDirector
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50),
	@ID INT OUT
AS
IF EXISTS (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 1)
	BEGIN
		SET @ID = 0
		RETURN (1)
	END
ELSE
	BEGIN
		INSERT INTO Employee(FirstName, LastName, RoleID, Active) VALUES (@FirstName, @LastName, 1, 1)
		SET @ID = @@identity
		RETURN (2)
	END

GO

-- ODABERI GLUMCA
CREATE PROCEDURE selectEmployee
AS
SELECT DISTINCT e.IDEmployee AS IDGlumac, e.FirstName AS GlumacIme, e.LastName AS GlumacPrezime, er.Name AS Tip FROM Employee AS e
INNER JOIN EmployeeRole AS er ON e.RoleID = er.IDRole
WHERE Active = 1

GO

-- DODAJ REDATELJA
CREATE PROCEDURE createDirectors
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50),
	@DirectorID INT OUT
AS
IF EXISTS (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 1)
	BEGIN
		SET @DirectorID = (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 1)
		RETURN (1)
	END
ELSE IF NOT EXISTS (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 1)
	BEGIN
		INSERT INTO Employee(FirstName, LastName, RoleID, Active) VALUES (@FirstName, @LastName, 1, 1)
		SET @DirectorID = @@identity
		RETURN (2)
	END
ELSE IF EXISTS (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 2)
	BEGIN
		SET @DirectorID = (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 2)
		RETURN (3)
	END
ELSE
	BEGIN
		INSERT INTO Employee(FirstName, LastName, RoleID, Active) VALUES (@FirstName, @LastName, 1, 1)
		SET @DirectorID = @@identity
		RETURN (4)
	END
GO

/* -------------------------------------- */

GO

/* ---------- TABLICA: FILMOVI ----------- */
CREATE TABLE Movie
(
	IDMovie INT PRIMARY KEY IDENTITY,
	Title NVARCHAR(100),
	Description NVARCHAR(500),
	Duration INT,
	ImagePath NVARCHAR(100),
	Active BIT
)

GO

-- DOHVATI FILMOVE
CREATE PROCEDURE selectMovies
AS
SELECT DISTINCT m.IDMovie AS ID, m.Title AS Naslov, m.Description AS Opis, m.ImagePath AS Slika, m.Duration AS Trajanje 
FROM Movie AS m WHERE m.Active = 1

GO

-- ODABIR GLUMCA PO ID FILMA
CREATE PROCEDURE selectDirectorsByMovieID
	@MovieID INT
AS
SELECT e.IDEmployee AS IDRedatelj, e.FirstName AS RedateljIme, e.LastName AS RedateljPrezime FROM Employee AS e
INNER JOIN EmployeeMovie AS em ON e.IDEmployee = em.EmployeeID
WHERE em.MovieID = @MovieID AND e.RoleID = 1 AND e.Active = 1

GO

-- KREIRAJ FILMOVE
CREATE PROCEDURE createMovies
	@Title NVARCHAR(50),
	@Description NVARCHAR(500),
	@Duration INT,
	@Picture NVARCHAR(50),
	@ID INT OUT
AS
BEGIN
	IF EXISTS (SELECT Title FROM Movie WHERE Title = @Title AND Description = @Description AND Duration = @Duration AND ImagePath = @Picture AND Active = 1)
		BEGIN
			SET @ID = 0
			RETURN (1)
		END
	ELSE
		BEGIN
			INSERT INTO Movie (Title, Description, Duration, ImagePath, Active) VALUES (@Title, @Description, @Duration, @Picture, 1)
			SET @ID = @@identity
			RETURN (2)
		END
END

GO

-- ODABERI FILM
CREATE PROCEDURE selectMovie
	@IDMovie INT
AS
SELECT IDMovie AS ID, Title AS Naslov, Description AS Opis, Duration AS Trajanje, ImagePath AS Slika FROM Movie WHERE IDMovie = @IDMovie AND Active = 1
GO
CREATE PROCEDURE createMovie
	@Title NVARCHAR(50),
	@Description NVARCHAR(50),
	@Duration INT,
	@ImagePath NVARCHAR(100),
	@IDMovie INT OUT
AS
IF EXISTS (SELECT IDMovie FROM Movie WHERE Title = @Title AND Description = @Description AND Duration = @Duration AND Active = 1)
	BEGIN
		SET @IDMovie = 0
		RETURN (1)
	END	
ELSE
	BEGIN
		INSERT INTO Movie(Title, Description, Duration, ImagePath, Active) VALUES (@Title, @Description, @Duration, @ImagePath, 1)
		SET @IDMovie = @@identity
		RETURN (2)
	END	

GO

-- OBRIŠI SVE FILMOVE
CREATE PROCEDURE deleteMovies
AS
UPDATE Movie SET Active = 0

GO

-- OBRIŠI FILM
CREATE PROCEDURE deleteMovie
	@IDMovie INT,
	@ID INT OUT
AS
UPDATE Movie SET Active = 0 WHERE IDMovie = @IDMovie
SET @ID = 0
GO
CREATE PROCEDURE updateMovie
	@IDMovie INT,
	@Title NVARCHAR(50),
	@Description NVARCHAR(50),
	@Duration INT,
	@ImagePath NVARCHAR(100)
AS
UPDATE Movie SET Title = @Title, Description = @Description, Duration = @Duration, ImagePath = @ImagePath WHERE IDMovie = @IDMovie

GO

-- ODABERI FILM PO NASLOVU
CREATE PROCEDURE selectMoviesTitle
AS
SELECT DISTINCT Title FROM Movie WHERE Active = 1

/* -------------------------------------- */

GO

/* --------- TABLICA: ŽANR-FILM ---------- */
CREATE TABLE GenreMovie
(
	IDGenreMovie INT PRIMARY KEY IDENTITY,
	MovieID INT FOREIGN KEY REFERENCES Movie(IDMovie),
	GenreID INT FOREIGN KEY REFERENCES Genre(IDGenre) 
)
/* -------------------------------------- */

GO

/* -------------------------------------- */
CREATE TABLE EmployeeMovie
(
	IDEmployeeMovie INT PRIMARY KEY IDENTITY,
	MovieID INT FOREIGN KEY REFERENCES Movie(IDMovie),
	EmployeeID INT FOREIGN KEY REFERENCES Employee(IDEmployee)
)

GO

-- DODJELI U MANY-2-MANY
CREATE PROCEDURE createMovieDirector
	@MovieID INT,
	@DirectorID INT
AS
INSERT INTO EmployeeMovie(MovieID, EmployeeID) VALUES (@MovieID, @DirectorID)


GO

--GOTO
CREATE PROCEDURE createActors
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50),
	@ActorID INT OUT
AS
IF EXISTS (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 2)
	BEGIN
		SET @ActorID = (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 2)
		RETURN (1)
	END
ELSE IF NOT EXISTS (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 2)
	BEGIN
		INSERT INTO Employee(FirstName, LastName, RoleID, Active) VALUES (@FirstName, @LastName, 2, 1) 
		SET @ActorID = @@identity
		RETURN (2)
	END
ELSE IF EXISTS (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 1)
	BEGIN
		SET @ActorID = (SELECT IDEmployee FROM Employee WHERE FirstName = @FirstName AND LastName = @LastName AND Active = 1 AND RoleID = 1)
		RETURN (3)
	END
ELSE
	BEGIN
		INSERT INTO Employee(FirstName, LastName, RoleID, Active) VALUES (@FirstName, @LastName, 2, 1)
		SET @ActorID = @@identity
		RETURN (4)
	END

GO

--
CREATE PROCEDURE createMovieActor
	@MovieID INT,
	@ActorID INT
AS
INSERT INTO EmployeeMovie(MovieID, EmployeeID) VALUES (@MovieID, @ActorID)
GO
/* -------------------------------------- */

CREATE PROCEDURE createMovieGenre
	@MovieID INT,
	@GenreID INT
AS
INSERT INTO GenreMovie(MovieID, GenreID) VALUES (@MovieID, @GenreID)
GO 



