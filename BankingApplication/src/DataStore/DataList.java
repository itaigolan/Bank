package DataStore;

import DataObject.DataObject;
import DataSource.DataSource;
import DataSource.FileDataSource;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class DataList{
    
    DataSource source;
    HashMap list;
    FileReader inputStream = null;
    String objectType;
    
    public <O extends DataObject> O get(int id){
        O object = (O)this.list.get(id);
        return object;
    }
    
    //Adds an instance of type DataObject to list with its id as its key
    public <O extends DataObject> void add(O object){
        this.list.put(object.getId(),object);
    }
    
    //Prints the info on every object in the list
    public <O extends DataObject> void printList() {
        list.forEach((key, value) -> {
            O object = (O)value;
            object.printObjectInfo();
        });
    }
    
    public <O extends DataObject> void save() {
        if(source.getAccessMethod().equals("Files")){
            FileWriter listWriter = null;
            try {
                if(objectType.equals("User")){
                    listWriter = new FileWriter(((FileDataSource)source).getUserDataSource());
                }   
                if(objectType.equals("Account")){
                    listWriter = new FileWriter(((FileDataSource)source).getAccountsDataSource());
                }
                String str = "";
                Iterator<Entry<Integer, O>> it = list.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Integer, O> pair = (Map.Entry<Integer, O>) it.next();
                        str+= pair.getValue().objectStringForFile() + "\r\n";
		}
                listWriter.write(str); 
                listWriter.close();
            }catch(IOException ex){}
        }
    }
}