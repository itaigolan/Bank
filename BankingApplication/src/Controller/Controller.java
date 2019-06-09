package Controller;

import DataObject.User;
import DataSource.DataSource;
import DataStore.AccountList;
import DataStore.UserList;
import java.math.BigDecimal;
import Model.Model;

public class Controller {
    
    Model model;
    User user;
    DataSource source;
    
    public Controller(UserList users, AccountList accounts, DataSource s){
        model = new Model(users, accounts);
        source = s;
    }
    
    public void setCurrentUser(User user){
        this.user = user;
    }

    //User management methods
    public void createUser(int id, String type, String firstName, String lastName, String email) {
        model.createUser(id, type, firstName,lastName,email);
    }

    public void createAccount(int id, int customerId, BigDecimal amount) {
        model.createAccount(id, customerId, amount);
    }
    
    public void deleteAccount(int id) {
        model.deleteAccount(id);
    }

    public void updateAddress(int id,String address) {
        model.updateAddress(id, address);
    }

    public void printUsersAccounts() {
        model.printUserAccounts(user.getId());
    }
    
    //Account management methods
    public void depositMoney(int id, BigDecimal amount) {
        model.depositMoney(id, amount, user.getId());
    }

    public void withdrawMoney(int id, BigDecimal amount) {
        model.withdrawMoney(id, amount, user.getId());
    }

    public void transferMoney(int withdrawId, int depositId, BigDecimal amount) {
        model.transferMoney(withdrawId, depositId, amount, user.getId());
    }
    
    //Logging methods
    public User login(int id, String pass){
        return model.login(id, pass);
    }
    
    public User logout(){
        return model.logout();
    }
    
    public User quit(){
        return model.quit();
    }
}
