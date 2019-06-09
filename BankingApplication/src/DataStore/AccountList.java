package DataStore;

import DataObject.Account;
import DataObject.Customer;
import DataSource.DataSource;
import DataSource.FileDataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;


public class AccountList extends DataList{
    
    //Used to create the general account list
    public AccountList(DataSource source, UserList users){
        this.source = source;
        list = new HashMap<Integer, Account>();
        objectType = "Account";
        initialize(users);
    }
    
    //Used to create user specific account lists
    public AccountList(){
        this.source = null;
        list = new HashMap<Integer, Account>();
    }
    
    final private void initialize(UserList users) {
        String accessMethod = source.getAccessMethod();
        if(accessMethod.equals("Files")){
            //The format of a file is id,customerId, amount;
            try{
                inputStream = new FileReader(((FileDataSource)source).getAccountsDataSource());
                int c;
                ArrayList<Character> fileInput = new ArrayList<>();
                while ((c = inputStream.read()) != -1) {
                    fileInput.add((char)c);                   
                }
                //Creates the accounts
                String accountInfo = "";
                for(c = 0;c < fileInput.size();c++){
                   if(fileInput.get(c) == ';'){
                        String [] accountParts = accountInfo.split(",");
                        BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(accountParts[2]));
                        createAccount(Integer.parseInt(accountParts[0]), Integer.parseInt(accountParts[1]), amount);
                        
                        //Adds the account to the User's account list
                        Customer customer = users.get(Integer.parseInt(accountParts[1]));
                        customer.addAccount((Account)list.get(Integer.parseInt(accountParts[0])));
                        accountInfo = "";
                        c += 2;
                   }
                   else{
                       accountInfo += fileInput.get(c);
                    }
                   inputStream.close();
                }
            }catch(FileNotFoundException e){}
            catch(IOException io){}
        }
    }

    public Account createAccount(int id, int customerId, BigDecimal amount) {
        Account account = null;
        //If a user with the provided id already exists it returns false
        if(list.get(id)!=null){
            System.out.println("The account with the id " + id + " already exists.");
        } 
        else {
            account = new Account(id, customerId,amount);
            this.list.put(id,account);
        }
        return account;
    }

    //Removes the account with the id from the list
    public void remove(int id) {
        list.remove(id);
    }
}
