package View;

import Controller.Controller;
import DataObject.User;
import java.util.Scanner;

public class LoginView implements ViewInterface{

    Scanner scnr;
    Controller control;
    
    public LoginView(Scanner scnr,Controller control){
        this.control = control;
        this.scnr = scnr;
    }
    
    @Override
    public User printView() {
        int id = 0;
        String password = "";
        System.out.println("Welcome to the banking application, please login.");
        do{
            //Checks that the id is in the form of an int
            try{
            System.out.print("User ID: ");
            id = Integer.parseInt(scnr.nextLine());
            }catch(Exception e){
                System.out.println("Id must be in the form of an integer.");
                continue;
            }
            //Checks that the id is greater than 0
            if(id <= 0){
                System.out.println("Id has to be positive.");
                continue;
            }
            System.out.print("Password: ");
            password = scnr.nextLine();
        }while(id <= 0);
            return control.login(id, password);
    }
}
