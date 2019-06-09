package DataObject;

import DataStore.AccountList;

public class Customer extends User{
    AccountList accountList;
    
    //Parts of the customer's address
    String streetAddress;
    String city;
    String state;
    int zip;
    
    public Customer(int id){
        objectId = id;
        this.setPassword("password");
        userType = "Customer";
        accountList = new AccountList();
    }
    
    public Customer(int id, String password){
        objectId = id;
        this.setPassword(password);
        userType = "Customer";
        accountList = new AccountList();
    }
    
    //Setter methods
    public void setAddress(String address){
        //Splits the full address into its parts
        String [] addressParts = address.split(",");
        try{
            this.streetAddress = addressParts[0];
            this.city = addressParts[1];
            this.state = addressParts[2];
            this.zip = Integer.parseInt(addressParts[3]);
        }
        catch(NumberFormatException e){
        }
    }
    
    public Account getAccount(int id) {
        return accountList.get(id);
    }
    
    //Adds an account to the accountList
    public void addAccount(Account account) {
        accountList.add(account);
    }
    
    //Deletes the account with the given id from the Customer's account list
    public void remove(int id){
        accountList.remove(id);
    }
    
    //Prints the accounts in the accountList
    public void printUserAccounts(){
        accountList.printList();
    }
    
    //Displays the info of the account on the console
    @Override
    public void printObjectInfo() {
        String s = "User Id: " + this.objectId + " User Type: " + this.userType + " Name: " + this.firstName + " " + this.lastName;
        s += " Email: " + this.email + " Address: " + this.streetAddress + "," + this.city + "," + this.state + "," + this.zip;
        System.out.println(s);
    } 

    //Gets a String containing the info of the account as stored in a file
    @Override
    public String objectStringForFile() {
        String s = this.objectId + "," + this.userType + "," + this.firstName + "," + this.lastName;
        s += "," + this.email + "," + this.password ; 
        s +=  "," + this.streetAddress + "," + this.city + "," + this.state + "," + this.zip + ";";
        return s;
    }
}
