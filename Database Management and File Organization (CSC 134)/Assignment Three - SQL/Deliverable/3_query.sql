SELECT C.License, C.Model, C.Year, A.Amount_of_Damage
FROM CAR AS C, HAD AS H, ACCIDENT AS A
WHERE C.License=H.License AND H.AccidentNo=A.Accident_no;


SELECT License, Accident_no
FROM HAD AS H, ACCIDENT AS A
WHERE A.Accident_No>2 AND A.Accident_No=H.AccidentNo;


SELECT A.Driver_Name, A.Amount_of_Damage, H.License, C2.Model, O.SSN
FROM CUSTOMER AS C1, OWNS AS O, CAR AS C2, HAD AS H, ACCIDENT AS A 
WHERE C1.Customer_Name=A.Driver_Name AND (O.License=C2.License AND H.License = C2.License AND A.Accident_No=H.AccidentNo);


SELECT P.PolicyNo, P.Policy_Rate, P.Policy_Details
FROM POLICY AS P
WHERE P.Policy_Rate>(SELECT P2.Policy_Rate FROM POLICY AS P2 WHERE P2.PolicyNo=12);


SELECT MIN(P1.Policy_Rate), MAX(P1.Policy_Rate), AVG(P1.Policy_Rate)
FROM POLICY AS P1;


SELECT Accident_no, Driver_name, Accident_Date, MAX(Amount_of_Damage)
FROM ACCIDENT;


SELECT C1.Customer_Name, C1.City, C1.State, C2.License, C2.Year
FROM CUSTOMER AS C1, CAR AS C2, OWNS AS O
WHERE C1.SSN=O.SSN AND O.License=C2.License AND C2.Model='Honda Accord';


SELECT SSN, Customer_Name, Street, City, State, Zip
FROM CUSTOMER
WHERE City='Roseville' OR City='Sunrise';


SELECT PolicyNo_Ref, numCount
FROM( SELECT PolicyNo_Ref, 
      COUNT(PolicyNo_Ref) AS numCount 
	  FROM CAR 
	  GROUP BY PolicyNo_Ref ) as TEMP_TABLE 
WHERE numCount > 2;

