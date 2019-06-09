package Bank;

import View.CustomerView;
import View.EmployeeView;
import Controller.Controller;
import View.LoginView;
import DataObject.User;
import DataSource.*;
import DataStore.AccountList;
import DataStore.UserList;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class BankingApplication {
        
    public static void main(String[] args){
        
        //Variables used to collect DataSource info from Properties.txt
        String line;
        String dataAccessMethod = "";
        String userDataSource = "";
        String accountsDataSource = "";
        BufferedReader propertiesReader = null;
        
        //Contains information on the sources of data (collections, files or databases)
        DataSource source = null;
        
        //DataStore objects that store the various data objects
        UserList users = null;
        AccountList accounts = null;
        
        //Database connection
        String connectionUrl = null;
        Connection con = null;
        
        //Reads the data access method from Properties.txt
        try{
            FileReader properties = new FileReader("C:\\itai\\Jobs\\CapGemini\\Training\\Projects\\Project1\\Properties.txt");
            propertiesReader = new BufferedReader(properties);
            
            try{
                line = propertiesReader.readLine();
                for(int i =0; i < line.length(); i++){
                    if(line.charAt(i) == '='){
                        dataAccessMethod = line.substring(i + 2);
                        break;
                    }
                }
            }catch(IOException io){
                //If Properties.txt is empty then it creates the default DataSource
                // Default says that the access method is Collections
                System.out.println("Properties.txt was empty, default data source used.");
                source = new CollectionDataSource();
            }
        }catch(FileNotFoundException e){
            //If Properties.txt is not found then it creates the default DataSource
            // Default says that the access method is Collections
            System.out.println("Didn't find Properties.txt, default data source used.");
            source = new CollectionDataSource();
        }
     
        switch (dataAccessMethod){
            //Creates the default DataSource that says the access method is Collections
            //Passes the information to the 
            case "Collections":
                source = new CollectionDataSource();
                users = new UserList(source);
                accounts = new AccountList(source, users);
                break;
            //Data is stored in and retrieved from files
            case "Files":
                try{
                    //Gets the url to the file containing user info
                    line = propertiesReader.readLine();
                    for(int i =0; i < line.length(); i++){
                        if(line.charAt(i) == '='){
                            userDataSource = line.substring(i + 2);
                            break;
                        }
                    }
                    //Gets the url to the file containing accounts info
                    line = propertiesReader.readLine();
                    for(int i =0; i < line.length(); i++){
                        if(line.charAt(i) == '='){
                            accountsDataSource = line.substring(i + 2);
                            break;
                        }
                    }
                    source = new FileDataSource(userDataSource,accountsDataSource);
                    users = new UserList(source);
                    accounts = new AccountList(source,users);
                }catch(IOException io){
                    //If the source strings are empty then they are initialized to the appropriate values
                    if(userDataSource.equals("")){
                        userDataSource = "C:\\itai\\Jobs\\CapGemini\\Training\\Projects\\Project1\\UserList.txt";
                    }
                    if(accountsDataSource.equals("")){
                        accountsDataSource = "C:\\itai\\Jobs\\CapGemini\\Training\\Projects\\Project1\\AccountsList.txt";
                    }
                    source = new FileDataSource(userDataSource,accountsDataSource);
                    users = new UserList(source);
                    accounts = new AccountList(source,users);
                }
                break;
            case "Database":
                try{
                    line = propertiesReader.readLine();
                    for(int i =0; i < line.length(); i++){
                        if(line.charAt(i) == '='){
                            connectionUrl = line.substring(i + 2);
                            break;
                        }
                    }
                    try {
                        con = DriverManager.getConnection(connectionUrl,"root","mysql");
                        System.out.println(connectionUrl + " found.");
                        con.close();
                        source = new DatabaseDataSource(connectionUrl);
                    }catch(Exception in){
                        System.out.println(connectionUrl + " not found.");
                        source = new CollectionDataSource();
                    }
                }catch(Exception out){
                    System.out.println("Nothing left in file");
                } 
            default:
                source = new CollectionDataSource();
                users = new UserList(source);
                accounts = new AccountList(source, users);
                break;
        }
       
        Controller control = new Controller(users, accounts, source);
        Scanner scnr = new Scanner(System.in);
        
        LoginView loginView = new LoginView(scnr, control);
        EmployeeView employeeView = new EmployeeView(scnr,control);
        CustomerView customerView = new CustomerView(scnr, control);
        
        User currentUser = null;
        
        do{
            //Displays the login view
            if(currentUser == null){
               //loginView returns a user that is given to currentUser
               currentUser = loginView.printView();
               //Sets currentUser to the user that logged in
               control.setCurrentUser(currentUser);
            }
            //Displays the employee view if the current user is an employee
            else if(currentUser.getUserType().equals("Employee")){
                //employeeView returns a user that is given to currentUser
                currentUser = employeeView.printView(); 
                //Sets currentUser to null(for logout) or of type Exit(for quit)
                control.setCurrentUser(currentUser);
            }
            //Displays the customer view if the current user is a customer
            else if(currentUser.getUserType().equals("Customer")){
                //customerView returns a user that is given to currentUser
                currentUser = customerView.printView(); 
                //Sets currentUser to null(for logout) or of type Exit(for quit)
                control.setCurrentUser(currentUser);
            }
        //Loop ends when the currentUser's type is Exit
        }while(currentUser == null || !currentUser.getUserType().equals("Exit"));   
    }
}
