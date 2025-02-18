import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;

public class Create extends CrudOperator {
	/*
	 * Create Class Constructor
	 */
	public Create() {
		
	}

/*
* Creates inventory Item through inputs
*
*/

	public void createInventoryItem(/*String productID, String quantity, float wholeSale, float sale, String supplierID*/) {
		
		Connection c = null;
		c = CrudOperator.connect();
	        int quantityNum;
	        double wholesale;
	        double salesPrice;

	        Scanner sc = new Scanner(System.in);
	        System.out.println("Input Product ID: ");
	        String id = sc.next();
	        System.out.println("Input Quantity: ");
	        while (true) {
	            try {
	                quantityNum = sc.nextInt();
	                break;
	            } catch (Exception e) {
	                System.out.print("Invalid input. Please reenter: ");
	                sc.nextLine();
	            }
	        }
	        System.out.println("Input Wholesale Price: ");
	        while (true) {
	            try {
	                wholesale = sc.nextDouble();
	                break;
	            } catch (Exception e) {
	                System.out.print("Invalid input. Please reenter: ");
	                sc.nextLine();
	            }
	        }
	        System.out.println("Input Sales Price: ");
	        while (true) {
	            try {
	                salesPrice = sc.nextDouble();
	                break;
	            } catch (Exception e) {
	                System.out.print("Invalid input. Please reenter: ");
	                sc.nextLine();
	            }
	        }
	        System.out.println("Input Supplier ID: ");
	        String supplierId = sc.next();

	        try {
	            Statement stmt = null;
	            c.setAutoCommit(false);
	            stmt = c.createStatement();
	            String out = "INSERT INTO Products (product_id,quantity,wholesale_cost, sale_price, supplier_id)"
	                    + "VALUES('" + id + "',"
	                    + quantityNum + ","
	                    + wholesale + "," + salesPrice + ",'" + supplierId + "' );";
	            stmt.executeUpdate(out);

	            stmt.close();
	            c.commit();

	        } catch (Exception e) {
	            e.printStackTrace();
	            System.exit(1);
	        }

	        System.out.println("Records successfully created");
	    }
	}
/*
* Creates Customer order through inputs
*/
	
	public void createCustOrder(/*String date, String custEmail, int custLocation, String productId, int productQuantity*/) {
		 
		    
			Connection c = null;
			c = CrudOperator.connect();
		        // User Input for Date, Email, Location, ProdId, Amount purchased, and Date
		        Scanner sc = new Scanner(System.in);
		        System.out.println("Input Date (as mm/dd/yyyy): ");
		        String date = sc.next();

		        System.out.println("Input Customer Email: ");
		        String email = sc.next();

		        System.out.println("Input Customer location: ");
		        String location = sc.next();

		        System.out.println("Input Product Id: ");
		        String productId = sc.next();

		        System.out.println("Input Amount Being Purchased: ");
		        String amount = sc.next();

		        System.out.println("Input Time of Purchase (as hh:mm): ");
		        String time = sc.next();
		        sc.close();

		        // Sql querry that will atempt to add the user input into the Customer Orders table
		        try {
		            Statement stmt = null;

		            c.setAutoCommit(false);
		            stmt = c.createStatement();

		            String out = "INSERT INTO cust_orders (date, cust_email, cust_location, product_id, product_quantity)"
		                    + "VALUES('" + date + "','" + email +"'," + Integer.parseInt(location) + ",'" + productId +"'," + Integer.parseInt(amount) + ");";

		            stmt.executeUpdate(out);
		            stmt.close();
		            c.commit();

		            // updateProduct method 
		            updateProducts(Integer.parseInt(amount), productId);

		        } catch (Exception e) {
		            e.printStackTrace();
		            System.exit(1);
		        }

		        System.out.println("Records successfully created");
		        //System.out.println("Products List Updated");

		    }
	
/*
 * Update products method 
 */
	
	public void updateProducts(int amountBought, String productId) {

        try {
        	
            Connection c = null;
            c = CrudOperator.connect();

            //sql querry to try to get the current product quantity and setting it to previousAmount
            Statement stmt = null;
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT quantity FROM Products where product_id = "+productId+";" );
            int previousAmount = rs.getInt("quantity");
            rs.close();
            stmt.close();
            c.close();

            //subtracting current product quantity by order quantity
            int newAmount = previousAmount - amountBought;

            //sql querry to attempt to update the product quantity in the products table 
            stmt = c.createStatement();
            c.setAutoCommit(false);

            String out = ("UPDATE Products set quantity ="+newAmount+" where product_id = "+productId+";");
            stmt.executeUpdate(out);
            stmt.close();
            c.commit();

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

    }
		
}


