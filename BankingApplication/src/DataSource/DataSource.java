package DataSource;

public abstract class DataSource {
    
    //The way that the data is accessed (Collections, Files, Databases)
    final private String dataAccessMethod;
    
    DataSource(String method){
        dataAccessMethod = method;
    }
    

    //Getter methods
    public String getAccessMethod() {
        return dataAccessMethod;
    }

}
