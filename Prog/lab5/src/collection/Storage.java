package collection;


import collection.data.Product;
import collection.exceptions.WrongID;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Vector;

public class Storage{
    public Storage(){}
    String fileName = "none"; //default value
    private static Vector<Product> collection;
    private static String path = "\"C:\\Users\\Арсений\\Documents\\ITMO\\Prog\\lab5\"";
    private static ZonedDateTime creationTime;
    public static int usersCounter = 0;

    public static ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public static void setCreationTime(ZonedDateTime creationTime) {
        Storage.creationTime = creationTime;
    }


    public void init(){
        try{
            collection = new Vector<>();
            creationTime = ZonedDateTime.now();
            path = System.getenv("lab5");
            if(path==null){
                System.out.println("""
                        ###########! WARNING !###########
                        Database location is not set 
                        Collection will be saved on default path
                        ~/lab5_data/data.csv
                        #################################
                        """);
                path = "~/lab5_data/data.csv";
            }
            //TODO: PARSE CSV FILE
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

    }


    private static int findIdx(int id) throws WrongID {
        for (int i=0; i<collection.toArray().length; i++){
            if(id == collection.get(i).getId()) return i;
        }
        throw new WrongID((long)id);
    }

    public static long getElementsCount() {
        return collection.size();
    }

    public static void add(Product product){
        collection.add(new Product());
        collection.lastElement().generateID(usersCounter);
        usersCounter++;

    }

    public static void update(Product product) throws WrongID {
        try{
           int _idx = findIdx(product.getId());
           collection.set(_idx, product);
        }
        catch (WrongID e){
            System.out.println(e.getMessage());
            System.out.println("try to create new object");
        }
    }

    public static void remove(int id) throws WrongID {
        try{
            int _idx = findIdx(id);
            collection.remove(_idx);
        }
        catch (WrongID e){
            System.out.println(e.getMessage());
        }
    }

    public static void clear() {
        collection.clear();
        usersCounter=1;
    }

    private static void swapPosition(int idx1, int idx2) throws CloneNotSupportedException {
        if(Integer.max(idx1, idx2)>=collection.size()) collection.setSize(Integer.max(idx1, idx2));
        Product buff = collection.get(idx1).clone();
        collection.set(idx1, collection.get(idx2).clone());
        collection.set(idx2, buff);
    }

    public static void insertToPosition(Product product, int idx) throws WrongID, CloneNotSupportedException {
        if(idx<collection.size()){
            collection.add(product);
            swapPosition(collection.size()-1, idx);
        }
        else{
            collection.set(idx, product);
        }
    }

//
//    String getValueByIDX(int id, String key) throws WrongID {
//
//        return "NONE";
//    }
}
