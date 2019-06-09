package DataObject;

public class Employee extends User{
    
    public Employee(int id){
        objectId = id;
        this.setPassword("password");
        userType = "Employee";
    }
    
    public Employee(int id, String password){
        objectId = id;
        this.setPassword(password);
        userType = "Employee";
    }
    
    //Displays the info of the account on the console
    @Override
    public void printObjectInfo() {
        String s = "User Id: " + this.objectId + " User Type: " + this.userType + " Name: " + this.firstName + " " + this.lastName;
        s += " Email: " + this.email;
        System.out.println(s);
    } 

    //Gets a String containing the info of the account as stored in a file
    @Override
    public String objectStringForFile() {
        String s = this.objectId + "," + this.userType + "," + this.firstName + "," + this.lastName;
        s += "," + this.email + "," + this.password + ";";
        return s;
    }
}
