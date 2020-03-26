package mysql_select_Data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class Product {

	//class that fetches the product summary over a period

	static Map<String,ArrayList<String>> mp = new HashMap<>(); //map that stores product related data
	static Map<String,ArrayList<Prod>> mp2 = new HashMap<>();  //map that stores product related data

	public static void productquery(String xmlFile, String from_date, String to_date, Statement statement,
			ResultSet resultSet)
	{
		// functionality that fetches the product summary over a period

		try {

			//accessing the document 
			Document doc = Period.doc;
			//fetching the first child
			Node root = doc.getFirstChild();

			// element that holds all the product data 
			Element product_results = doc.createElement("product_list");
			root.appendChild(product_results);

			//query that fetches the summary for the given period
			String prod_query = "select CategoryName,products.ProductName," + "suppliers.CompanyName as Supplier, "
					+ "sum(Quantity) as units_sold, "
					+ "sum(orderdetails.UnitPrice*Quantity-Discount) as value_of_Product " + "from orderdetails "
					+ "inner join products on orderdetails.ProductID = products.ProductID inner join categories on products.CategoryID = categories.CategoryID inner join suppliers on products.SupplierID = suppliers.SupplierID inner join orders on orderdetails.OrderID = orders.OrderID  "
					+ "where OrderDate " + "BETWEEN " + "'" + from_date + "'" + " AND " + "'" + to_date + "'"
					+ " GROUP BY products.ProductID " + "ORDER BY CategoryName;";

			//Stores the query output 
			resultSet = statement.executeQuery(prod_query);
			//format data as required
			organize_data(resultSet);


			for(String cat_nam : mp.keySet())
			{
				//element for category
				Element cat_data = doc.createElement("category");
				product_results.appendChild(cat_data);

				//element for category name
				Element cat_name = doc.createElement("category_name");
				cat_data.appendChild(cat_name);

				//append category name as child
				cat_name.appendChild(doc.createTextNode(cat_nam));

				ArrayList<String> array = mp.get(cat_nam); //array list to store different categories

				for(int lst=0;lst<array.size();lst++)
				{
					ArrayList<Prod> pd = mp2.get(array.get(lst)); //array list to store summary data

					//element for product
					Element product = doc.createElement("product");
					cat_data.appendChild(product);

					//element for product name
					Element product_name = doc.createElement("product_name");
					product_name.appendChild(doc.createTextNode(array.get(lst).toString()));

					//append product name as child
					product.appendChild(product_name);

					//element for supplier name
					Element supplier_name = doc.createElement("supplier_name");

					//append supplier name as child
					supplier_name.appendChild(doc.createTextNode(pd.get(0).supplier));
					product.appendChild(supplier_name);

					//element for units sold
					Element units_sold = doc.createElement("units_sold");

					//append units sold as child
					units_sold.appendChild(doc.createTextNode(pd.get(0).units));
					product.appendChild(units_sold);

					//element for sale value
					Element sale_value = doc.createElement("sale_value");

					//append sale value as child
					sale_value.appendChild(doc.createTextNode(pd.get(0).sale_val));
					product.appendChild(sale_value);

					cat_data.appendChild(product);
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Map<String, ArrayList<String>> organize_data(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub

		while(resultSet.next())
		{
			if (mp.containsKey(resultSet.getString("CategoryName").toString()))
			{
				ArrayList<String> alt = mp.get(resultSet.getString("CategoryName"));
				alt.add(resultSet.getString("ProductName"));
				mp.put(resultSet.getString("CategoryName"), alt);
				ArrayList<Prod> ar2 = new ArrayList<Prod>();
				String supplier = resultSet.getString("Supplier");
				String units = resultSet.getString("units_sold");
				String sale = resultSet.getString("value_of_Product");
				Prod p = new Prod(supplier,units,sale);
				ar2.add(p);
				mp2.put(resultSet.getString("ProductName"), ar2);
			}
			else
			{
				ArrayList<String> arr = new ArrayList<String>();
				arr.add(resultSet.getString("ProductName"));
				mp.put(resultSet.getString("CategoryName"), arr);
				ArrayList<Prod> ar2 = new ArrayList<Prod>();
				String supplier = resultSet.getString("Supplier");
				String units = resultSet.getString("units_sold");
				String sale = resultSet.getString("value_of_Product");
				Prod p = new Prod(supplier,units,sale);
				ar2.add(p);
				mp2.put(resultSet.getString("ProductName"), ar2);
			}
		}


		return mp;

	}


}
