CREATE TABLE CUSTOMER
(   SSN                 CHAR(9) NOT NULL,
    Customer_Name       VARCHAR(30) NOT NULL,
	Street              VARCHAR(20),
	City                VARCHAR(10),
	State               VARCHAR(10),
	Zip                 CHAR(5),
	Phone               CHAR(10),
	
	PRIMARY KEY(SSN),
	) ENGINE = INNODB;
	
CREATE TABLE OWNS
(   SSN                 CHAR(9) NOT NULL,
    License	            VARCHAR(15) NOT NULL,
	PRIMARY KEY(SSN, License)
	) ENGINE = INNODB;
	
CREATE TABLE CAR
(   License             VARCHAR(15) NOT NULL,
    Model               VARCHAR(15),
	Year                INT,
	PolicyNo_Ref        INT NOT NULL,
	PRIMARY KEY(License)
	) ENGINE = INNODB;
	
CREATE TABLE POLICY
(   PolicyNo            INT NOT NULL,
    Policy_Rate	        DECIMAL(15,2),
	Policy_Details      VARCHAR(100),
	PRIMARY KEY(PolicyNo)
	) ENGINE = INNODB;
	
ALTER TABLE CAR ADD FOREIGN KEY(PolicyNo_Ref) REFERENCES POLICY(PolicyNo); 

CREATE TABLE HAD
(   License             VARCHAR(15) NOT NULL,
    AccidentNo          INT NOT NULL,
	PRIMARY KEY(License, AccidentNo)
	) ENGINE = INNODB;

CREATE TABLE ACCIDENT
(	Accident_no         INT NOT NULL,
    Driver_name         VARCHAR(30),
    Accident_Date       DATE,
	Amount_of_Damage    DECIMAL(15,2),
	Accident_Details    VARCHAR(100),
	PRIMARY KEY(Accident_no)
	) ENGINE = INNODB;
	
ALTER TABLE OWNS ADD FOREIGN KEY(SSN) REFERENCES CUSTOMER(SSN); 
ALTER TABLE OWNS ADD FOREIGN KEY(License) REFERENCES CAR(License);

ALTER TABLE HAD ADD FOREIGN KEY(License) REFERENCES CAR(License); 
ALTER TABLE HAD ADD FOREIGN KEY(AccidentNo) REFERENCES ACCIDENT(Accident_No);
	
	