package View;

import Controller.Controller;
import DataObject.User;
import java.math.BigDecimal;
import java.util.Scanner;

public class EmployeeView implements ViewInterface{

    Scanner scnr;
    Controller control;
    
    public EmployeeView(Scanner scnr, Controller control){
        this.control = control;
        this.scnr = scnr;
    }
    
    @Override
    public User printView() {
        
        String operation;
        int id;
        int customerId;
        BigDecimal amount;
        
        do{
            System.out.println("Hello Employee you can type in:");
            System.out.println("a to open a new account");
            System.out.println("c to create a user");
            System.out.println("d to delete an account");
            System.out.println("l to logout");
            System.out.println("q to quit the application");
            System.out.println("What operation do you want to perform?");
            operation = scnr.nextLine();

            switch(operation){
                //Opens a new account
                case "a":
                    System.out.println("Enter the id of the new account: ");
                    //Ensures the id is an int
                    try{
                        id = Integer.parseInt(scnr.nextLine());
                    }catch(Exception e){
                        System.out.println("The account id must be an integer.");
                        break;
                    }
                    //Ensures id is larger than 0
                    if(id <= 0){
                        System.out.println("The id must be an larger than 0.");
                        break;
                    }
                    
                    System.out.println("Enter the id of the customer: ");
                    //Ensures the customerId is an int
                    try{
                        customerId = Integer.parseInt(scnr.nextLine());
                    }catch(Exception e){
                        System.out.println("The customer id must be an integer.");
                        break;
                    }
                    //Ensures customerId is larger than 0
                    if(customerId <= 0){
                        System.out.println("The customer id must be an larger than 0.");
                        break;
                    }
                    
                    System.out.println("Enter the starting balance: ");
                    //Ensures the amount is a compatible data type
                    try{
                        amount = BigDecimal.valueOf(Double.parseDouble(scnr.nextLine()));
                    }catch(Exception e){
                        System.out.println("The amount must be a number.");
                        break;
                    }
                    //Ensures amount is positive                
                    if(amount.doubleValue() < 0){
                        System.out.println("The amount can't be negative.");
                        break;
                    }
                    
                        control.createAccount(id,customerId,amount);
                    break;
                    
                //Creates a new user
                case "c":
                    System.out.println("Enter the id of the new user: ");
                    //Ensures the id is an int
                    try{
                        id = Integer.parseInt(scnr.nextLine());
                    }catch(Exception e){
                        System.out.println("The id must be an integer.");
                        break;
                    }
                    //Ensures that id is large enough
                    if(id <= 0){
                        System.out.println("The id must be an larger than 0.");
                        break;
                    }
                    
                    System.out.println("Enter the type of the new user (0 for employee or 1 for customer): ");
                    int userTypeId;
                    try{
                        userTypeId = Integer.parseInt(scnr.nextLine());
                    }catch(Exception e){
                        System.out.println("The userType must be 0 or 1.");
                        break;
                    }
                    
                    //Checks that type is customer or employee
                    String userType;
                    if(userTypeId == 0){
                        userType = "Employee";
                    }
                    if(userTypeId == 1){
                        userType = "Customer";
                    }
                    else{
                       System.out.println("The userType must be 0 or 1.");
                        break; 
                    }

                    System.out.println("Enter the first name of the new user: ");
                    String firstName = scnr.nextLine();
                    System.out.println("Enter the last name of the new user: ");
                    String lastName = scnr.nextLine();
                    System.out.println("Enter the email of the new user: ");
                    String email = scnr.nextLine();
                    
                    control.createUser(id,userType,firstName,lastName,email);
                    if(userType.equals("Customer")){
                        System.out.println("Enter the address the new customer.");
                        System.out.println("Street Address, City, State, Zip");
                        control.updateAddress(id,scnr.nextLine());
                    }
                    break;
                
                //Deletes an account
                case "d":
                    System.out.println("Enter the id of the account: ");
                    //Ensures the id is an int
                    try{
                        id = Integer.parseInt(scnr.nextLine());
                    }catch(Exception e){
                        System.out.println("The account id must be an integer.");
                        break;
                    }
                    //Ensures id is larger than 0
                    if(id <= 0){
                        System.out.println("The id must be an larger than 0.");
                        break;
                    }
                    
                    control.deleteAccount(id);
                    break;    
                    
                //Logout
                case "l":
                    return control.logout();
                    
                //Quit application
                case "q":
                    return control.quit();
            }
        //Exits the loop if user logsout or quits
        }while(!operation.equals("l") || !operation.equals("q"));
        //Returns a null user (which takes application back to login view)
        User user = null;
        return user;
    }
}
