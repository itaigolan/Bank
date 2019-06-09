package DataObject;

abstract public class User extends DataObject{
    
    //Attributes of Customers and Employees
    protected String password;
    String userType;
    String firstName;
    String lastName;
    String email;
    
    //Getter methods
    public String getUserType(){
        return userType;
    }
    
    //Setter methods
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    //Sets the initial password of the user
    final protected void setPassword(String password){
        this.password = password;
    }
    
    //Sets the password of the user after the initial user creation
    //Requires user to reenter their current password before their password can be reset
    public void setPassword(String currentPassword, String newPassword){
        if(comparePassword(currentPassword)){
            password = newPassword;
        }
        else{
            System.out.println("The password you entered was incorrect.");
        }
    }
    
    //Compares the password the user entered to the actual password
    public boolean comparePassword(String pass){
        return password.equals(pass);
    }
}
