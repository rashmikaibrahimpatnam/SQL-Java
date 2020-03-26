package mysql_select_Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

import javax.xml.transform.dom.DOMSource;

//class that connects to the given database
public class ConnectToDB {


	public static void connectDB(String file_name, String from_date, String to_date) {
		//method that connects to the database

		Connection connect = null; // the link to the database
		Statement statement = null; // used to form an SQL query
		ResultSet cust_resultSet = null; // receives results from a customer SQL query
		ResultSet prod_resultSet = null; // receives results from a product SQL query
		ResultSet supp_resultSet = null; // receives results from a supplier SQL query

		Properties identity = new Properties(); // Hides info from other users.
		MyIdentity credentials = new MyIdentity(); // Class to fetch credentials. 

		String db_Username;
		String db_Password;
		String db_Name;

		credentials.setIdentity(identity); 

		db_Username = identity.getProperty("user"); //fetch user name from the identity file
		db_Password = identity.getProperty("password"); //fetch password from the identity file
		db_Name = identity.getProperty("database");    //fetch database name from the identity file

		try {
			// loads sql driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// connection to the Database
			connect = DriverManager.getConnection("jdbc:mysql://db.cs.dal.ca:3306?serverTimezone=UTC&useSSL=false",
					db_Username, db_Password);

			// instance that sends queries to the database.
			statement = connect.createStatement();

			// database that needs to be accessed
			statement.executeQuery("use " + db_Name + ";");

			// functionality that sets the root of the tree and adds the duration 
			Period.setRoot(file_name, from_date, to_date);
			
			// functionality that fetches the customer summary as per the duration 
			Customer.customerquery(file_name, from_date, to_date, statement, cust_resultSet);
			
			// functionality that fetches the product summary as per the duration 
			Product.productquery(file_name, from_date, to_date, statement, prod_resultSet);
			
			// functionality that fetches the supplier summary as per the duration 
			Supplier.supplierquery(file_name, from_date, to_date, statement, supp_resultSet);
			Period.doc.setXmlStandalone(true); //to omit standalone tag
			DOMSource domSource = new DOMSource(Period.doc);		
			XmlConfig.xmlTrans(file_name, domSource);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			

			// Closing the resultSet, statements, and connections that are open and
			// holding resources.
			try {
				if ((cust_resultSet != null) || (prod_resultSet != null) || (supp_resultSet != null))  {
					cust_resultSet.close();
					prod_resultSet.close();
					supp_resultSet.close();
				}

				if (statement != null) {
					statement.close();
				}

				if (connect != null) {
					connect.close();
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	}
}
