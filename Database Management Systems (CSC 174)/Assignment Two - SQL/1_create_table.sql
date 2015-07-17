CREATE TABLE Customer
(   Name                VARCHAR(30) NOT NULL,
    Address             VARCHAR(30) NOT NULL,
	Phone               VARCHAR(10),
	Customer_ID         INT NOT NULL,
	
	PRIMARY KEY(Customer_ID)
	) ENGINE = INNODB;
	
CREATE TABLE Item
(   Item_ID             INT NOT NULL,
    Date_Acquired       DATE,
	Customer_ID_Rent    INT NOT NULL,
	Rent_Date           DATE,
	Return_Date         DATE,
	Title               VARCHAR(30),
	Release_Date        DATE,
	
	PRIMARY KEY(Item_ID),
	FOREIGN KEY (Customer_ID_Rent) REFERENCES Customer(Customer_ID)  
	) ENGINE = INNODB;
		
CREATE TABLE Film
(   Item_ID             INT NOT NULL,
    Company             VARCHAR(30),
	Certificate         VARCHAR(30),
	
	PRIMARY KEY(Item_ID)
	) ENGINE = INNODB;
	
CREATE TABLE Music_CD
(   Item_ID            INT NOT NULL,
    No_Of_Sounds	   INT NOT NULL,
	
	PRIMARY KEY(Item_ID) 
	) ENGINE = INNODB;
	
CREATE TABLE Music_Album
(   Item_ID             INT NOT NULL,
    Singer_Name         VARCHAR(30),
	
	PRIMARY KEY(Item_ID) 
	) ENGINE = INNODB;

 CREATE TABLE Sound_Track
(	Item_ID             INT NOT NULL,
	Music_Director_Name VARCHAR(30),
    Film_Ref            INT NOT NULL,
    
    PRIMARY KEY(Item_ID), 
	FOREIGN KEY (Film_Ref) REFERENCES Film(Item_ID)  
) ENGINE = INNODB;

ALTER TABLE Music_CD ADD FOREIGN KEY(Item_ID) REFERENCES Item(Item_ID);  

ALTER TABLE Film ADD FOREIGN KEY(Item_ID)  REFERENCES Item(Item_ID);  

ALTER TABLE Music_CD ADD FOREIGN KEY(Item_ID) REFERENCES Item(Item_ID); 

ALTER TABLE Music_Album ADD FOREIGN KEY(Item_ID) REFERENCES Music_CD(Item_ID); 

ALTER TABLE Sound_Track ADD FOREIGN KEY(Item_ID) REFERENCES Music_CD(Item_ID);  


	
	