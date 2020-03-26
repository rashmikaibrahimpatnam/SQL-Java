package mysql_select_Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Customer {

	//class that fetches the customer summary over a period

	public static void customerquery(String xmlFile, String from_date, String to_date, Statement statement,
			ResultSet resultSet)
	{
		// functionality that fetches the customer summary over a period

		try {
			//accessing the document 
			Document doc = Period.doc;
			//fetching the first child
			Node root = doc.getFirstChild();

			// element that holds all the customer data 
			Element customer_results = doc.createElement("customer_list");
			root.appendChild(customer_results);

			//query that fetches the summary for the given period
			String cust_query = "select customers.ContactName as customer_name,"
					+ " REPLACE(REPLACE(REPLACE(address, CHAR(13),' '), CHAR(10),' '), CHAR(9),' ') as street_address, " + "customers.City, " + "customers.Region,"
					+ " customers.PostalCode," + " customers.Country, "
					+ "count(Distinct orders.OrderID) as number_of_orders,"
					+ " sum(UnitPrice*Quantity-Discount) as total_order_value " + "from customers "
					+ "inner join orders on customers.CustomerID = orders.CustomerID inner join orderdetails on orders.OrderID = orderdetails.OrderID "
					+ "where orders.OrderDate " + "BETWEEN " + "'" + from_date + "'" + " AND " + "'" + to_date + "'"
					+ " GROUP BY orders.CustomerID " + "ORDER BY customer_name;";

			//Stores the query output 
			resultSet = statement.executeQuery(cust_query);
			while (resultSet.next()) {

				//element for customer
				Element cust_data = doc.createElement("customer");
				customer_results.appendChild(cust_data);

				//element for customer name
				Element cust_name = doc.createElement("customer_name");
				cust_data.appendChild(cust_name);
				//append customer name as child
				cust_name.appendChild(doc.createTextNode(resultSet.getString("customer_name")));

				//element for address
				Element cust_address = doc.createElement("address");
				cust_data.appendChild(cust_address);

				//element for street address
				Element street_address = doc.createElement("street_address");

				//append street address as child
				street_address.appendChild(doc.createTextNode(resultSet.getString("street_address")));
				cust_address.appendChild(street_address);

				//element for city 
				Element city = doc.createElement("city");
				//append city as child
				city.appendChild(doc.createTextNode(resultSet.getString("City")));
				cust_address.appendChild(city);

				//element for region
				Element region = doc.createElement("region");
				//if region is null, append " " as child
				if (resultSet.getString("Region") == null) {
					region.appendChild(doc.createTextNode(" "));
					cust_address.appendChild(region);

				} else {
					//append region value as child
					region.appendChild(doc.createTextNode(resultSet.getString("Region")));
					cust_address.appendChild(region);

				}

				//element for postal_code
				Element postal_code = doc.createElement("postal_code");
				// if postal code is null, append " " as child
				if (resultSet.getString("PostalCode") == null) {
					postal_code.appendChild(doc.createTextNode(" "));
					cust_address.appendChild(postal_code);

				} else {
					//append postal code as child
					postal_code.appendChild(doc.createTextNode(resultSet.getString("PostalCode")));
					cust_address.appendChild(postal_code);

				}

				//element for country
				Element country = doc.createElement("country");
				//append country value as child
				country.appendChild(doc.createTextNode(resultSet.getString("Country")));

				cust_address.appendChild(country);
				cust_data.appendChild(cust_address);

				//element for number of orders
				Element num_orders = doc.createElement("num_orders");
				//append number of orders value as child 
				num_orders.appendChild(doc.createTextNode(resultSet.getString("number_of_orders")));
				cust_data.appendChild(num_orders);

				//element for order value
				Element order_value = doc.createElement("order_value");
				//append order value as child
				order_value.appendChild(doc.createTextNode(resultSet.getString("total_order_value")));
				cust_data.appendChild(order_value);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
