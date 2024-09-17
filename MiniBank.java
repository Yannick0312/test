import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;
public class MiniBank {
    private Connection conn;
   public static void main(String[] args) throws Exception {
        final String url = "jdbc:oracle:thin:@//localhost:1521/MF19";
        final String user = "mf_manager";
        final String password = "mf_manager";

         try {
            MiniBank miniBankAdministration = new MiniBank(url, user, password);
            miniBankAdministration.startMiniBankAdministration();
         } catch (Exception e) {
            System.out.println("Connection couldnt be established: " + e.getMessage());
            return;
         }
    }

    public MiniBank(String url, String user, String password) throws SQLException {
        this.conn = DriverManager.getConnection(url, user, password);
        new MiniBankController(conn);
        System.out.println("Connected!");
        System.out.println("Welcome to the MiniBank!");
    }

    /*
     * Erstes switch case zum anmelden
     * case1: Admin
     * case2: vorhandener Nutzer
     * case3: neuer nutzer
     */

     public void startMiniBankAdministration() {
        Scanner sc = new Scanner(System.in);
        MiniBankController bankController;
    
        try {
            boolean running = true;
            bankController = new MiniBankController(conn);
            while (running) {
                System.out.println("");
                System.out.println("Welcome to the miniBank");
                System.out.println("1. Insert new Customer");
                System.out.println("2. Pay out of Account");
                System.out.println("3. Pay in to Account");
                System.out.println("4. Show specific Customer data");
                System.out.println("5. Show all Customer data");
                System.out.println("6. Change Customer data");
                System.out.println("7. Stop program");
    
                int option = sc.nextInt();
                sc.nextLine(); 
    
                switch (option) {
                    case 1:
                        bankController.addCustomer(sc);
                        break;
                    case 2:
                        bankController.doPayOut(sc);
                        break;
                    case 3:
                        bankController.doPayIn(sc);
                        break;
                    case 4:
                        bankController.showCustomerData(sc);
                        break;
                    case 5:
                        bankController.showAllCustomerData();
                        break;
                    case 6:
                        bankController.changeCustomerData(sc);
                        break;
                    case 7:
                        System.out.println("Stopping the Program...");
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid Input, please try again!");
                }            
            }
        } catch (Exception e) {
            System.out.println("A database error occurred!: " + e.getMessage());
        } finally {
            sc.close();
        }
    }    
}
