package mysql_select_Data;

import java.text.SimpleDateFormat;
import java.util.Date;

//class that takes inputs from the user
public class Inputs {

	public static boolean inputs(String start_date, String end_date, String file_name) {
		//returns false if any of the parameters are null or empty
		if ((start_date.trim().equals("")) || (end_date.trim().equals("")) || (file_name.trim().equals("")))
			return false;
		else {

			String set_pattern = "yyyy-MM-dd"; //accepted date pattern
			SimpleDateFormat sdf = new SimpleDateFormat();
			sdf.applyPattern(set_pattern); 
			try {
				sdf.setLenient(false);
				Date f_date = sdf.parse(start_date); //parse the given start date
				Date t_date = sdf.parse(end_date);   //parse the given end date
				//start date cannot be greater than end date
				if (f_date.after(t_date))
					return false;
				else
				{
					//if all conditions are satisfied, then perform the operations
					ConnectToDB.connectDB(file_name,start_date, end_date);
					return true;
				}
			}catch(Exception e)
			{
				e.getMessage();
			}




			return false;

		}
	}
}
