package mysql_select_Data;

import java.util.Scanner;

//main class that takes inputs from the user and generates an xml file

public class MainUi {
	//main method
	public static void main(String args[])
	{
		//scanner class that takes input from the user
		Scanner sc = new Scanner(System.in);
		//fetch year end summary for the given duration
		System.out.println("enter start date");
		String start_date = sc.nextLine();
		System.out.println("enter end date");
		String end_date = sc.nextLine();
		//stores the summary in given file in xml format 
		System.out.println("enter file name");
		String file_name = sc.nextLine();
		//closing the scanner class
		sc.close();
        //access the methods in that class
		Inputs.inputs(start_date, end_date, file_name);
		
	}
}
