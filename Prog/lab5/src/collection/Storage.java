package collection;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;

public class Storage{

    static Map<String, String> key2obj = new HashMap<String, String>();

    private void InitCSVTableNaming(){
        key2obj.put("id", "Product.id");
        key2obj.put("name", "Product.name");
        key2obj.put("coordinates", "Product.coordinates");
        key2obj.put("x coordinate", "Product.coordinates.x");
        key2obj.put("y coordinate", "Product.coordinates.y");
        key2obj.put("creation date", "Product.creationDate");
        key2obj.put("price", "Product.price");
        key2obj.put("unit of measure", "Product.unitOfMeasure");
        key2obj.put("manufacturer", "Product.manufacturer");
        key2obj.put("manufacturer id", "Product.manufacturer.id");
        key2obj.put("manufacturer name", "Product.manufacturer.name");
        key2obj.put("manufacturer annual turnover", "Product.manufacturer.annualTurnover");
        key2obj.put("manufacturer type", "Product.manufacturer.type");
        key2obj.put("manufacturer address", "Product.manufacturer.postalAddress");
        key2obj.put("manufacturer street", "Product.manufacturer.postalAddress.street");
        key2obj.put("manufacturer zipCode", "Product.manufacturer.postalAddress.zipCode");
    }
    String fileName = "none"; //default value
    private Vector<Product> collection;

    public void parseCSV() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileReader("D:\\Documents\\ITMO\\Prog\\lab5\\extFiles\\data.csv")); //TODO replace path
        String[] markup = scanner.nextLine().trim().split(";");

        while(scanner.hasNext()){
            String[] buff = scanner.nextLine().trim().split(";");
            for(byte i=0; i< buff.length; i++){
                collection.add(new Product());  //TODO: create default constructors
                String fieldId = key2obj.get(markup[i]);
                try {
                    Field field = collection.get(i).getClass().getDeclaredField(fieldId);
                    field.set(collection.get(i), (field.getType()) buff[i]);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }

            }

        }

    }


}
