/*
 * 	Author: Chris Neal
 * 	Date:	11/13/14
 * 	Course:	CSC174
 * 	Assign:	Hw3
 */

package neal_Chris_hw3;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class Neal_Chris_hw3 {

	public static void main(String args []) {

		//specify the proper database, user name and password
		String url = "jdbc:mysql://athena.ecs.csus.edu/cs174137";
		/* The proper way to do this would have included a funciton to grab
		   text input from the user. Not implemented due to time constraints.
		*/
		String username = "NOPE";
		String password = "NOPE";

		//instantiate and display results!
		Neal_Chris_hw3 db = new Neal_Chris_hw3();
		db.displayData(url, username, password);
	}

	public void displayData(String urlStr,String username,String password) {

		 try
		  {
			 //Start up JDBC
			 Class.forName("com.mysql.jdbc.Driver").newInstance();

			 //Connect to our specified database from Main()
			 Connection conn = DriverManager.getConnection(urlStr,username,password);
	         System.out.println ("Connected to the MySQL database!");

	         /*
	          * 1)	Display all the items (item_ID) that were rented by �John Smith�.
	          */
	          System.out.println(" ");
	          System.out.println("=-=-=-=-=-=- Question 1 -=-=-=-=-=-=");

	          //Instantiate a new statement for this connection as well as a result from this connection.
			  Statement stmt1 = conn.createStatement();
			  ResultSet rs1=null;

			  //Mimics a simple select statement!
			  rs1=stmt1.executeQuery("select Item_ID "
			  					   + "from Item as I, Customer as C "
			  		               + "where C.Customer_ID = I.Customer_ID_Rent and "
			  		               + "      C.Name = 'John Smith' ");

			  //While there is output for our statement, display it.
			  while (rs1.next())
			  {
				  System.out.println("Item ID:"+rs1.getString(1));

				  //Only want the item_id yes? Not the entire table?
				  //System.out.println("Date Acquired"+rs1.getString(2));
				  //System.out.println("Rented By:"+rs1.getString(3));
				  //System.out.println("Rent Date:"+rs1.getString(4));
				  //System.out.println("Return Date:"+rs1.getString(5));
				  //System.out.println("Title:"+rs1.getString(6));
				  //System.out.println("Release Date:"+rs1.getString(7));
			  }

			  //Statment complete, close it.
			  stmt1.close();


			  /*
			   * 2)	Query the �Film_Sound_Track� view (created in homework2) to display all the tuples in that view.
			   */
			  System.out.println(" ");
			  System.out.println("=-=-=-=-=-=- Question 2 -=-=-=-=-=-=");

			  //create another statement and result for this connection
			  Statement stmt11 = conn.createStatement();
			  ResultSet rs11=null;

			  //mimic SQL input, select our view.
			  rs11=stmt11.executeQuery("select * "
			  					   + "from Film_Sound_Track");

			  //while there are results, show them.
			  while (rs11.next())
			  {
				  System.out.println("Title:"+rs11.getString(1));
				  System.out.println("Item ID:"+rs11.getString(2));
				  System.out.println("Director's Name:"+rs11.getString(3));
			  }

			  //close statement.
			  stmt11.close();

			  /*
			   * 3)	Call �Num_Of_Film_Rented� function (created in homework2). Given customer ID=12,
			   * display the number of films rented.  You need to insert data to the database before
			   * you call this function, such that the function can returns at least one result.
			   */
			  System.out.println(" ");
			  System.out.println("=-=-=-=-=-=- Question 3 -=-=-=-=-=-=");

			  //yet again, create a new statement and result.
			  Statement stmt111 = conn.createStatement();
			  ResultSet rs111=null;

			  //call our function, just like SQL syntax.
			  rs111=stmt111.executeQuery("SELECT Num_Of_Film_Rented('12') ");

			  //while there are results, show the number of films rented by customer 12.
			  while (rs111.next())
			  {
				  System.out.println("Number of films rented: "+rs111.getString(1));
			  }

			  //close this statement.
			  stmt111.close();

	         //Close the connection to the database.
			 System.out.println(" ");
			 conn.close();
			 System.out.println("Disconnected!");


		  }
		 //catch any errors.
		  catch (Exception e)
		  {
			  e.printStackTrace();
		  }
	}

}
