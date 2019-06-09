package DataStore;

import DataObject.Customer;
import DataObject.Employee;
import DataObject.User;
import DataSource.DataSource;
import DataSource.FileDataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class UserList extends DataList{ 
    int id;
    String type;
    String firstName;
    String lastName;
    String address;
    
    
    public UserList(DataSource source){
        this.source = source;
        list = new HashMap<Integer,User>();
        objectType = "User";
        initialize();
    }
    
    final private void initialize(){
        String accessMethod = source.getAccessMethod();
        if(accessMethod.equals("Collections")){
            //Creates an initial employee if the access method is Collections
            this.createUser(1,"Employee","Cyrus","Money","CMoney@money.com");
        }
        else if(accessMethod.equals("Files")){
            //The format of a file is id,type,firstName,lastName,email,password; for employees
            //id,type,firstName,lastName,email,password,street address,city,state,zip code; for customers
            try{
                inputStream = new FileReader(((FileDataSource)source).getUserDataSource());
                int c;
                ArrayList<Character> fileInput = new ArrayList<>();
                while ((c = inputStream.read()) != -1) {
                    fileInput.add((char)c);                   
                }
                String userInfo = "";
                for(c = 0;c < fileInput.size();c++){
                   if(fileInput.get(c) == ';'){
                        String [] userParts = userInfo.split(",");
                        createUser(Integer.parseInt(userParts[0]), userParts[1], userParts[2], userParts[3], userParts[4]);
                        setPassword(Integer.parseInt(userParts[0]),"password",userParts[5]);
                        if(userParts[1].equals("Customer")){
                           String userAddress;
                           userAddress = userParts[6] + "," + userParts[7] + "," + userParts[8] +  "," + userParts[9];
                           updateAddress(Integer.parseInt(userParts[0]), userAddress);
                        } 
                        userInfo = "";
                        c += 2;
                    } 
                    else{
                       userInfo += fileInput.get(c);
                    }
                   inputStream.close();
                }
            }catch(FileNotFoundException e){}
            catch(IOException io){}
        }     
    }
    
    public boolean createUser(int id, String type, String firstName, String lastName, String email, String password){
        User user = null;
        //If a user with the provided id already exists it returns false
        if(list.get(id)!=null){
            return false;
        } 
        //Otherwise it creates the user and returns true
        else {
            switch(type){
                case "Employee":
                    user = new Employee(id,password);
                    break;
                case "Customer":
                    user = new Customer(id,password);
                    break;
            }
            //Sets the name and email of the user
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            this.list.put(id,user);
            return true;
        }
    }
        
    public boolean createUser(int id, String type, String firstName, String lastName, String email){
        User user = null;
        //If a user with the provided id already exists it returns false
        if(list.get(id)!=null){
            return false;
        } 
        //Otherwise it creates the user and returns true
        else {
            switch(type){
                case "Employee":
                    user = new Employee(id);
                    break;
                case "Customer":
                    user = new Customer(id);
                    break;
            }
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            this.list.put(id,user);
            return true;
        }
    }

    //Updates the address of the customer with the given id
    public void updateAddress(int id, String address) {
        Customer user = (Customer)list.get(id);
        user.setAddress(address);
    }
    
    public void setPassword(int id, String currentPassword,String newPassword){
        User user = (User)list.get(id);
        user.setPassword(currentPassword, newPassword);
    }
    
}
