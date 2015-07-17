DELIMITER $
CREATE FUNCTION  Num_Of_Film_Rented(cust_ID INTEGER)
RETURNS INTEGER
BEGIN
	DECLARE Total_Number_Rented INTEGER;
	
	SELECT COUNT(*) INTO Total_Number_Rented 
	FROM Customer AS C, Film as F, Item as I 
	WHERE F.Item_ID = I.Item_ID AND I.Customer_ID_Rent = C.Customer_ID AND C.Customer_ID = cust_ID;
	
	RETURN Total_Number_Rented;
END;
$
DELIMITER;
	