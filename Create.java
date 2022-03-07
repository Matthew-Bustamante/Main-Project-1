import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;
import java.util.Scanner;


public class Create {
    private static CustOrder firstInstance = null;

    // Constuctor for CustOrder
    private Create() {
    }   

    /**
     * Creates new instance of Create and if there is already an instance
     * running, will not do anything
     *
     * @return returns the instance that's running or creates a new one
     */
    
    public void createNewInv(Connection c) {

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



    /**
     * will atempt to update the prod_quantity of a product with a matching product_id
     * based on how much of a product is being purchased in an oncoming order
     * 
     * Is called in the customerOrder method
     * 
     * @param c
     * @param amountBought
     * @param productId
     */
    public void updateProducts(Connection c, int amountBought, String productId) {

        try {

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


    /**
     * SQL Query that create a new oder entry in the customer orders database
     * 
     * @param c Database connection to be used
     */
    public void createOrder(Connection c) {

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
            updateProducts(c, Integer.parseInt(amount), productId);

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Records successfully created");
        //System.out.println("Products List Updated");

    }


    /**
     * Method to connect to remote database server
     * 
     * @return returns the connection if established
     */
    /*public Connection connect() {
        Connection conn = null;

        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://52.91.137.51:5432/products", "postgres", "cS3250!");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        if (conn != null) {
            System.out.println("Successfully Connected to database");
        } else {
            System.out.println("Connection Failed");
        }

        return conn;
    }*/
}
