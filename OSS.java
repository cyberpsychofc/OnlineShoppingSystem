import java.sql.*;
import java.util.*;

public class OSS{
    public static int count_sales;
    public static int count_users;
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/OSS","root","");

            System.out.println("Choose :" );
            System.out.println("1. PURCHASE ITEM\n2. SALES HISTORY\n3. SIGN UP");

            Scanner sc = new Scanner(System.in);
            int inputval = sc.nextInt();
            Statement stmt = con.createStatement();

            switch (inputval){
                case 1:
                    System.out.println("Enter Credentials : ");
                    System.out.print("UserID : ");
                    String id = sc.next();
                    System.out.print("Password : ");
                    String pass = sc.next();
                    System.out.println("SELECT pass from cust_cred where id=" + id);

                    ResultSet login = stmt.executeQuery("SELECT pass FROM cust_cred WHERE id=" + "'" + id + "';");

                    while (login.next()){
                        if(pass.equals(login.getString(1))){
                            System.out.println("Login Successful!");
                            System.out.println("\n\nEnter the Serial No. of Selected Item ");

                            Statement post = con.createStatement();

                            ResultSet res = post.executeQuery("SELECT * FROM item;");
                            System.out.println("List of Items is as follows :");
                            while(res.next()){
                                System.out.println(res.getInt(1)+"\t"+res.getString(2)+"\t"+res.getInt(3));
                            }
                            System.out.print("Enter Choice : ");
                            int choice = sc.nextInt();
                            System.out.print("Enter Quantity : ");
                            int quantity = sc.nextInt();

                            System.out.println("\n\nInvoice :");

                            ResultSet c = post.executeQuery("SELECT COUNT(*) FROM sales;");
                            while (c.next()){
                                count_sales = c.getInt(1);
                            }

                            ResultSet bill = post.executeQuery("SELECT name,price FROM item where sr=" + choice + ";");
                            while (bill.next()){

                                Statement invoice = con.createStatement();
                                String item = bill.getString(1);
                                int total = (bill.getInt(2) * quantity);
                                System.out.println("User ID : " + id);
                                System.out.println("Item Purchased : " + item);
                                System.out.println("Quantity : " + quantity);
                                System.out.println("Grand Total : " + total);

                                int temp = invoice.executeUpdate("INSERT INTO sales(`sr`, `id`, `item`, `quantity`, `total`) " + "VALUES("+ (count_sales + 1) + ",'" + id +"','" + item + "',"+ quantity + ","+ total+");");
                            }

                        }
                        else{
                            System.out.println("Login Unsuccessful!");
                        }
                    }
                    break;

                case 2:
                    ResultSet sales = stmt.executeQuery("SELECT * FROM sales;");
                    while (sales.next()){
                        System.out.println(sales.getInt(1)+"\t"+ sales.getString(2)+"\t"+ sales.getString(3) + "\t"+ sales.getInt(4) + "\t"+ sales.getInt(5));
                    }
                    break;
                case 3:
                    String user_id,passkey;
                    System.out.println("Enter New Account Details :");
                    System.out.print("User ID : ");
                    user_id = sc.next();
                    System.out.print("Password : ");
                    passkey = sc.next();

                    ResultSet c = stmt.executeQuery("SELECT COUNT(*) FROM cust_cred;");
                    while (c.next()){
                        count_users = c.getInt(1);
                    }
                    int temp = stmt.executeUpdate("INSERT INTO cust_cred(`sr`, `id`, `pass`) " + "VALUES("+ (count_users + 1) + ",'" + user_id +"','" + passkey + "');");
                    System.out.println("Account Created Successfully!\nNavigate to Purchase");
                    break;
            }
            con.close();

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
