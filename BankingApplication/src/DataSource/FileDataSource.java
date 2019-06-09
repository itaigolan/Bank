package DataSource;

//Used when the source is files
public class FileDataSource extends DataSource{
    //The source of the data
    final private String userDataSource;
    final private String accountsDataSource;
    
    public FileDataSource(String userSource, String accountSource){
        super("File");
        userDataSource = userSource;
        accountsDataSource = accountSource;
    }

    public String getUserDataSource() {
        return userDataSource;
    }
    
    public String getAccountsDataSource() {
        return accountsDataSource;
    }
    
}
