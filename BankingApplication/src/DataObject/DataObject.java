package DataObject;

abstract public class DataObject {
    //The id of the object
    int objectId;
    
    //Returns the objectId
    public int getId() {
        return objectId;
    }
    //Displays the info of the object on the console
    public abstract void printObjectInfo();
    
    //Gets a String containing the info of the object as stored in a file
    public abstract String objectStringForFile();
}
