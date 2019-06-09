package DataObject;

public class ExitUser extends User{
    //The ExitUser is used to quit the application
    public ExitUser(){
        userType = "Exit";
    }   

    @Override
    public void printObjectInfo() {
        System.out.println("ExitUser is used to quit the application.");
    }

    @Override
    public String objectStringForFile() {
        String s = "";
        System.out.println("ExitUser should not be written to file. It is used to quit the application.");
        return s;
    }
}
