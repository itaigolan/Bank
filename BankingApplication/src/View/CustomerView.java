package View;

import Controller.Controller;
import DataObject.User;
import java.math.BigDecimal;
import java.util.Scanner;

public class CustomerView implements ViewInterface{

    Scanner scnr;
    Controller control;
    
    public CustomerView(Scanner scnr, Controller control){;
        this.control = control;
        this.scnr = scnr;
    }
    
    @Override
    public User printView() {
        String operation;
        
        do{
            System.out.println("Hello Customer you can type in:");
            System.out.println("d to deposit money into an account");
            System.out.println("w to withdraw money from an account");
            System.out.println("t to transfer money between two of your accounts");
            System.out.println("v to view accounts");
            System.out.println("l to logout");
            System.out.println("q to quit the application");
            System.out.println("What operation do you want to perform?");
            operation = scnr.nextLine();

            switch(operation){
                case "d":
                    System.out.println("Enter the id of the account you want to deposit money into: ");
                    int id = Integer.parseInt(scnr.nextLine());
                    System.out.println("Enter the amount of money you want to deposit into the account: ");
                    BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(scnr.nextLine()));
                    control.depositMoney(id, amount);
                    break;
                case "v":
                    System.out.println("Your accounts are: ");
                    control.printUsersAccounts();
                    break;
                case "w":
                    System.out.println("Enter the id of the account you want to withdraw money from: ");
                    id = Integer.parseInt(scnr.nextLine());
                    System.out.println("Enter the amount of money you want to withdraw from the account: ");
                    amount = BigDecimal.valueOf(Double.parseDouble(scnr.nextLine()));
                    control.withdrawMoney(id, amount);
                    break;
                case "t":
                    System.out.println("Enter the id of the account you want to withdraw money from: ");
                    id = Integer.parseInt(scnr.nextLine());
                    System.out.println("Enter the id of the account you want to deposit money into: ");
                    int id2 = Integer.parseInt(scnr.nextLine());
                    System.out.println("Enter the amount of money you want to transfer between the accounts: ");
                    amount = BigDecimal.valueOf(Double.parseDouble(scnr.nextLine()));
                    control.transferMoney(id, id2, amount);
                    break;
                case "l":
                    return control.logout();
                case "q":
                    return control.quit();
            }
        }while(!(operation.equals("l") || operation.equals("q")));
        User user = null;
        return user;
    }
}
