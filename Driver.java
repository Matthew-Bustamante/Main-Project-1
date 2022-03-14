import java.util.*;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Driver class for the program
 *
 */

public class Driver {

    public static void main(String[] args) {

        // Creates new instance on start up
        Crud crud = Crud.getInstance();
        CustOrder order = CustOrder.getInstance();
        //creates Read object
        Read reader = new Read();
        Delete deleter = new Delete();

        Scanner sc = new Scanner(System.in);
        String id;
        String email;
        int quant;
        Connection connInventory = null;
        Connection connCustomer = null;
        connInventory = crud.connect();
        connCustomer = order.connect();

        String optionNum = "";

        /**
         * input loop for user to navigate through
         * menu and options and call each specific function
         */
        while (!optionNum.equals("8")) {
            menu();
            System.out.print("Enter option #: ");
            optionNum = sc.next();

            switch (optionNum) {
                case "1":
                    try {
                        crud.create(connInventory);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "2":
                    System.out.println("Input Product ID: ");
                    id = sc.next();
                    try {
                        reader.read(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;

                case "3":
                    System.out.print("Enter id to update: ");
                    id = sc.next();
                    try {
                        crud.update(connInventory, id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "4":
                    System.out.print("Enter id: ");
                    id = sc.next();
                    try {
                        deleter.delete(id);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;

                case "5" :
                    try{
                        order.createOrder(connCustomer);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                    
                case "6" :
                	System.out.print("Enter Email: ");
                	id = sc.next();
                	try {
                		reader.readCustOrder(id);
                	} catch (Exception e) {
                		e.printStackTrace();
                	}
                	break;
                	
                	
                case "7" :
                	System.out.print("Enter Id: ");
                	id = sc.next();
                	System.out.print("Enter Email: ");
                	email = sc.next();
                	try {
                		deleter.deleteOrder(id, email);

                	} catch (Exception e) {
                		e.printStackTrace();
                	}
                	break;
                                  
                case "8":
                    System.out.println("Goodbye");
                    break;

                default:
                    System.out.println("Invalid option");
                    break;

            }

        }
        try {
            connInventory.close();
            connCustomer.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Method to print out menu options for user
     */
    private static void menu() {
        System.out.println("1) Create Inventory Item");
        System.out.println("2) Read Inventory Item");
        System.out.println("3) Update Inventory Item");
        System.out.println("4) Delete Inventory Item");
        System.out.println("5) Create Customer Order");
        System.out.println("6) Read Customer Order");
        System.out.println("7) Delete Customer Order");
        System.out.println("8) Quit");
    }
}