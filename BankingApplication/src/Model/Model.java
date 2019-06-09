package Model;

import DataObject.Account;
import DataObject.Customer;
import DataObject.ExitUser;
import DataObject.User;
import DataStore.AccountList;
import DataStore.UserList;
import java.math.BigDecimal;

public class Model {
    
    //Data Lists
    UserList userList;
    AccountList accounts;
    
    public Model(UserList userList, AccountList accounts){
        this.userList = userList;
        this.accounts = accounts;
    }
    
    //User management methods
    public void createUser(int id, String type, String firstName, String lastName, String email){
        userList.createUser(id, type, firstName,lastName,email);
    }
    
    public void createAccount(int id, int customerId, BigDecimal amount){
        User user = userList.get(customerId);
        Account account = null;
        //Checks if the user doesn't exists
        if(user == null){
            System.out.println("User " + customerId + " does not exist.");
        }
        //Checks that the user is not an Employee
        else if(!user.getUserType().equals("Employee")) {
            //If the user is not an employee it creates an account with the given values
            account = accounts.createAccount(id, customerId, amount);
            Customer c = (Customer)user;
            c.addAccount(account);
        //Otherwise it says that the user is an employee
        } else {
            System.out.println("User " + customerId + " is an employee and can't have an account.");
        }
    }
    
    public void deleteAccount(int id){
        Account account = (Account)accounts.get(id);
        int customerId = account.getCustomerId();
        Customer c = (Customer)userList.get(customerId);
        
        //Deletes the account from accounts
        accounts.remove(id);
        //Deletes the account from its owner's account list
        c.remove(id);
    }

    public void updateAddress(int id,String address) {
        userList.updateAddress(id,address);
    }

    //Prints the account information of all accounts owned by a customer
    public void printUserAccounts(int userId) {
        Customer customer = (Customer)userList.get(userId);
        customer.printUserAccounts();
    }
    
    //Account management methods
    public void depositMoney(int id, BigDecimal amount, int currentUserId) {
        //Checks that the current user owns the account before depositing into it
        try{
            Customer customer = userList.get(currentUserId);
            Account account = customer.getAccount(id);
            account.deposit(amount);  
        }catch(NullPointerException np){
            System.out.println("You do not own account " + id );
        }
    }

    public void withdrawMoney(int id, BigDecimal amount, int currentUserId) {
        //Checks that the current user owns the account before withdrawing from it
        try{
            Customer customer = userList.get(currentUserId);
            Account account = customer.getAccount(id);
            account.withdraw(amount);  
        }catch(NullPointerException np){
            System.out.println("You do not own account " + id );
        }
    }

    public void transferMoney(int withdrawId, int depositId, BigDecimal amount, int currentUserId) {
        //Checks that the current user owns the account withdrawn from before withdrawing from it
        try{
            Customer customer = userList.get(currentUserId);
            Account account = customer.getAccount(withdrawId);
            //If the amount was successfully withdrawn from the first account, its deposited in the second account
            if(account.withdraw(amount)){
                account = accounts.get(depositId);
                account.deposit(amount);
            }  
        }catch(NullPointerException np){
            System.out.println("You do not own account " + withdrawId );
        }
    }
    
    //Logging methods
    public User login(int id, String password){
        User user = userList.get(id);
        
        if(user == null){
            System.out.println("This user does not exist.");
            return user;
        }
        else{
            if(user.comparePassword(password)){
                return user;
            }
            else{
                System.out.println("You entered the wrong password.");
                user = null;
                return user;
            }
        }
    }
    
    public User logout(){
        userList.save();
        accounts.save();
        User user = null;
        return user;
    }
    
    public User quit(){
        userList.save();
        accounts.save();
        User user = new ExitUser();
        return user;
    }
}
