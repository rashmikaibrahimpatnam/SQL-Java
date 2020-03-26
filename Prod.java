package mysql_select_Data;

//class that holds the supplier data, units sold and sale value
public class Prod {
	String supplier;
	String units;
	String sale_val;
	public Prod(String supplier, String units, String sale_val) {
		super();
		this.supplier = supplier;
		this.units = units;
		this.sale_val = sale_val;
	}

}
