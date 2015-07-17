DELIMITER $
CREATE PROCEDURE List_Music_Albums(cust_ID INTEGER, cust_Name VARCHAR(30))
BEGIN	
	SELECT M.Item_ID, M.Singer_Name
	FROM Music_Album AS M, Music_CD AS MC, Item AS I, Customer as C
	WHERE M.Item_ID = MC.Item_ID AND MC.Item_ID = I.Item_ID AND I.Customer_ID_Rent = C.Customer_ID AND C.Customer_ID = cust_ID AND C.Name = cust_Name;
END;
$
DELIMITER;
