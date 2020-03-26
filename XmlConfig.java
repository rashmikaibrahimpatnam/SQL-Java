package mysql_select_Data;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

//class that holds all the configuration to build xml doc

public class XmlConfig {


	public static Document xmlDoc()
	//Functionality that accesses the Document Builder to parse xml into DOM 
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//create a new document 
		Document doc = builder.newDocument();
		return doc;
	}


	public static void xmlTrans(String xmlFile, DOMSource domSource)
	//Functionality that transforms the data using DOM 
	//and appends to a file with proper indentation
	{

		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException | TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		transformer.setOutputProperty(OutputKeys.METHOD, "xml");  //type of format
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no"); //omit declaration
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); //encoding data
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");     //parameter to set indentation
		transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); //type of document
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");  //indent amount
		Result result = new StreamResult(xmlFile); //stream the result
		try {
			transformer.transform(domSource, result); 
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
