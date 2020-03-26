package mysql_select_Data;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

//class that fetches the start date and end date of the period specified and stores in the DOM document
public class Period {

	//create a new Document 
	static Document doc = XmlConfig.xmlDoc();

	public static void setRoot(String xmlFile, String start_date, String end_date) {
		try {

			//set root of the tree
			Element root = doc.createElement("year_end_summary");
			doc.appendChild(root);

			//element for year
			Element year = doc.createElement("year");
			root.appendChild(year);

			//element for start date
			Element from_date = doc.createElement("start_date");
			//append start date as child
			from_date.appendChild(doc.createTextNode(start_date));
			year.appendChild(from_date);

			//element for end date
			Element to_date = doc.createElement("end_date");
			//append end date as child
			to_date.appendChild(doc.createTextNode(end_date));
			year.appendChild(to_date);

			root.appendChild(year);


		} catch (Exception e) {
			e.getMessage();
		}
	}
}
