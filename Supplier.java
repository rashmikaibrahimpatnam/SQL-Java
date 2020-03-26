package mysql_select_Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Supplier {
	//class that fetches the supplier summary over a period

	public static void supplierquery(String xmlFile, String from_date, String to_date, Statement statement,
			ResultSet resultSet) {
		// functionality that fetches the supplier summary over a period

		try {
			//accessing the document 
			Document doc = Period.doc;
			//fetching the first child
			Node root = doc.getFirstChild();

			// element that holds all the supplier data 
			Element supplier_results = doc.createElement("supplier_list");
			root.appendChild(supplier_results);

			//query that fetches the summary for the given period
			String supplier_query = "select suppliers.CompanyName as Supplier , "
					+ "REPLACE(REPLACE(REPLACE(address, CHAR(13),' '), CHAR(10),' '), CHAR(9),' ') as street_address, " + "suppliers.City, " + "suppliers.Region, "
					+ "suppliers.PostalCode, " + "suppliers.Country, "
					+ "count(Distinct products.ProductID) as num_products_sold, "
					+ "sum(orderdetails.UnitPrice*Quantity-Discount) as total_value " + "from orderdetails "
					+ "inner join products on orderdetails.ProductID = products.ProductID inner join suppliers on suppliers.SupplierID = products.SupplierID inner join orders on orderdetails.OrderID = orders.OrderID "
					+ "where OrderDate " + "BETWEEN " + "'" + from_date + "'" + " AND " + "'" + to_date + "'"
					+ " GROUP BY CompanyName " + "ORDER BY CompanyName;";

			//Stores the query output 
			resultSet = statement.executeQuery(supplier_query);
			while (resultSet.next()) {

				//element for supplier
				Element sup_data = doc.createElement("supplier");
				supplier_results.appendChild(sup_data);

				//element for supplier name
				Element sup_name = doc.createElement("supplier_name");
				sup_data.appendChild(sup_name);
				//append supplier name value as child
				sup_name.appendChild(doc.createTextNode(resultSet.getString("Supplier")));

				//element for address 
				Element sup_address = doc.createElement("address");
				sup_data.appendChild(sup_address);

				//element to hold the actual address 
				Element street_address = doc.createElement("street_address");
				//append address value as child
				street_address.appendChild(doc.createTextNode(resultSet.getString("street_address")));
				sup_address.appendChild(street_address);

				//element to hold the city
				Element city = doc.createElement("city");
				//append city value as child
				city.appendChild(doc.createTextNode(resultSet.getString("City")));
				sup_address.appendChild(city);

				//element to hold the region
				Element region = doc.createElement("region");

				//if region is null from db, adding a " "
				if (resultSet.getString("Region") == null) {
					region.appendChild(doc.createTextNode(" "));
					sup_address.appendChild(region);

				} else {
					region.appendChild(doc.createTextNode(resultSet.getString("Region")));
					//append region value as child
					sup_address.appendChild(region);

				}

				//element to hold postal_code
				Element postal_code = doc.createElement("postal_code");

				//if postal_code is null from db, adding a " "
				if (resultSet.getString("PostalCode") == null) {
					postal_code.appendChild(doc.createTextNode(" "));
					sup_address.appendChild(postal_code);

				} else {
					postal_code.appendChild(doc.createTextNode(resultSet.getString("PostalCode")));
					//append postal_code value as child
					sup_address.appendChild(postal_code);

				}

				//element to hold country
				Element country = doc.createElement("country");
				//append city value as child
				country.appendChild(doc.createTextNode(resultSet.getString("Country")));

				sup_address.appendChild(country);
				sup_data.appendChild(sup_address);

				//element to hold number of products
				Element num_prod = doc.createElement("num_products");

				//append number of products as child
				num_prod.appendChild(doc.createTextNode(resultSet.getString("num_products_sold")));
				sup_data.appendChild(num_prod);

				//element to hold product value
				Element prod_value = doc.createElement("product_value");

				//append product value as child
				prod_value.appendChild(doc.createTextNode(resultSet.getString("total_value")));
				sup_data.appendChild(prod_value);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
