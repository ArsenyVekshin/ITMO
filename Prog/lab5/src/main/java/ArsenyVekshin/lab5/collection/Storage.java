package ArsenyVekshin.lab5.collection;

import ArsenyVekshin.lab5.Main;
import ArsenyVekshin.lab5.collection.data.*;
import ArsenyVekshin.lab5.collection.exceptions.*;
import ArsenyVekshin.lab5.ui.exeptions.StreamBrooked;
import ArsenyVekshin.lab5.ui.file.FileInputHandler;
import ArsenyVekshin.lab5.ui.file.FileOutputHandler;
import ArsenyVekshin.lab5.utils.builder.Builder;
import ArsenyVekshin.lab5.utils.builder.ObjTree;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.*;

import static ArsenyVekshin.lab5.tools.Comparators.compareFields;

public class Storage <T extends Object> implements CSVOperator {
    public Storage() {
    }

    public String fileName = "none"; //default value
    private static Vector<Product> collection;
    public static String path;
    private static ZonedDateTime creationTime;
    private static int usersCounter = 0;

    private static final String defaultFieldForComp = "id";

    public static final ObjTree productTree = new ObjTree(Product.class);

    public static ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public static void setCreationTime(ZonedDateTime creationTime) {
        Storage.creationTime = creationTime;
    }

    public void init() {
        try {
            collection = new Vector<>();
            creationTime = ZonedDateTime.now();
            if (System.getenv("lab5") == null) {
                path = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile().getParent() + File.separatorChar + "sysFiles" + File.separatorChar;
                System.out.println("""
                        ###########! WARNING !###########
                        Directory for interaction-files is not set 
                        They will be saved on default directory"""
                        + "\n" + path +  "\n" +
                        """
                        #################################
                        """);

            }
            else{
                System.out.println("""
                        #################################
                        Directory for interaction-files set at"""
                        + "\n" + path +  "\n" +
                        """
                        #################################
                        """);
            }
            load();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void fillRandom() throws InvalidValueEntered {
        try {
            addNew(new Product(0, "test", new Coordinates(1, 1), 55, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "test1", new Coordinates(2, 1), 1000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "1test", new Coordinates(12, 1), 3000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "3test", new Coordinates(1, 1), 1000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "t2est", new Coordinates(1, 1), 1000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "test", new Coordinates(1, 1), 1000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "434test", new Coordinates(1, 1), 7000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "56yhbftest", new Coordinates(1, 1), 1000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "test1", new Coordinates(1, 1), 1000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "ssgtest", new Coordinates(1, 1), 9000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, " test", new Coordinates(1, 1), 1000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "test2", new Coordinates(1, 1), 800, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
            addNew(new Product(0, "test3", new Coordinates(1, 1), 1000, UnitOfMeasure.KILOGRAMS, new Organization(0, "testOrg", 1000, OrganizationType.DEFAULT, new Address("1111", "2222"))));
        } catch (InvalidValueEntered e) {
            e.printStackTrace();
        }
    }

    public static String info(){
        String out = "";
        out += "contains classes: " + Product.class.getName() + "\n";
        out += "database path: " + path + "\n";
        out += "created at: " + creationTime.toString() + "\n";
        out += "positions num: " + usersCounter;
        return out;
    }

    public static String show(){
        StringBuilder out = new StringBuilder();
        for (Product product : collection) {
            out.append(product.toString()).append("\n");
        }
        return out.toString();
    }

    private static int findIdx(int id) throws WrongID {
        for (int i = 0; i < collection.toArray().length; i++) {
            if (id == collection.get(i).getId()) return i;
        }
        throw new WrongID((long) id);
    }

    public static long getElementsCount() {
        return collection.size();
    }

    public static void add(Product product) {
        collection.add(product);
    }

    public static void addNew(Product product) {
        collection.add(product);
        collection.lastElement().generateID(usersCounter);
        usersCounter++;

    }

    public static void addNew(HashMap<String, Object> tree) {
        collection.add(new Product(tree));
        collection.lastElement().generateID(usersCounter);
        usersCounter++;

    }


    public static void update(long id, Product product) throws WrongID {
        try {
            int _idx = findIdx((int)id);
            collection.set(_idx, product);
        } catch (WrongID e) {
            e.printStackTrace();
        }
    }

    public static void remove(int id) throws WrongID {
        try {
            int _idx = findIdx(id);
            collection.remove(_idx);
        } catch (WrongID e) {
            System.out.println(e.getMessage());
        }
    }

    public static void clear() {
        collection.clear();
        usersCounter = 1;
    }

    private static void swapPosition(int idx1, int idx2) throws CloneNotSupportedException {
        if (Integer.max(idx1, idx2) >= collection.size()) collection.setSize(Integer.max(idx1, idx2));
        Product buff = collection.get(idx1).clone();
        collection.set(idx1, collection.get(idx2).clone());
        collection.set(idx2, buff);
    }

    public static void insertToPosition(int idx, Product product) throws WrongID, CloneNotSupportedException {
        if (idx < collection.size()) {
            collection.add(product);
            swapPosition(collection.size() - 1, idx);
        } else {
            collection.set(idx, product);
        }
    }



    public void sortBy(String field) {
        collection.sort((f1, f2) -> {
            try {
                compareFields((T) f1.getClass().getDeclaredField(field).get(f1),
                        (T) f2.getClass().getDeclaredField(field).get(f2));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return 0;
        });
    }

    public void sort(){
        sortBy(defaultFieldForComp);
    }

    private Product max() throws NoSuchFieldException, IllegalAccessException {
        Product _max = new Product();
        try {
            for (int i = 0; i < collection.size(); i++) {
                Product a = collection.get(i);
                if (compareFields((T) _max.getClass().getDeclaredField(defaultFieldForComp).get(_max),
                        (T) a.getClass().getDeclaredField(defaultFieldForComp).get(a)) < 0) {
                    _max = a;
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        return _max;
    }

    public void addIfMax(Product o) throws NoSuchFieldException, IllegalAccessException {
        try {
            T a = (T) o.getClass().getDeclaredField(defaultFieldForComp).get(o);
            for (int i = 0; i < collection.size(); i++) {
                Product b = collection.get(i);
                if (b == null) continue;
                if (compareFields(a, (T) b.getClass().getDeclaredField(defaultFieldForComp).get(b)) < 0) {
                    collection.remove(i);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

    }

    public void removeGreater(Product o) throws NoSuchFieldException, IllegalAccessException {
        try {
            T a = (T) o.getClass().getDeclaredField(defaultFieldForComp).get(o);
            for (int i=0; i<collection.size(); i++){
                Product b = collection.get(i);
                if(b==null) continue;
                if(compareFields(a, (T) b.getClass().getDeclaredField(defaultFieldForComp).get(b))<0){
                    collection.remove(i);
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }

    }

    public static void removeSameUnitOfMeasure(UnitOfMeasure unitOfMeasure){
        for (int i=0; i<collection.size(); i++){
            if(collection.get(i) == null || collection.get(i).getUnitOfMeasure() != unitOfMeasure) continue;
            collection.remove(i);
        }
    }

    public static String getPrices(){
        if(collection.isEmpty()) return "collection is null";
        float[] array = new float[collection.size()];
        for (int i=0; i<collection.size(); i++){
            if(collection.get(i) == null) continue;
            array[i] = collection.get(i).getPrice();
        }
        Arrays.sort(array);
        return Arrays.toString(array);
    }

    public static float getPricesSum(){
        float out = 0;
        for (Product product : collection) {
            if (product == null) continue;
            out += product.getPrice();
        }
        return out;
    }


    /*public void parseCSV(String input) throws NoneValueFromCSV {
        if(input.isEmpty()) throw new NoneValueFromCSV("FILE CONTAINS NULL");
        collection.clear();
        String[] data = input.split("\n");

        String[] markup = data[0].split(", ");

        Map<String, String> buff = new HashMap<>();
        for (String key : markup) buff.put(key, "");

        for(int j=1; j < data.length; j++){
            try{
                HashMap<String, Object> values = new HashMap<>();

                String[] line = data[j].split(", ");
                if(line.length < markup.length) throw new WrongCSVLine(data[j]);
                for(int i=0; i < line.length; i++) buff.put(markup[i], line[i]);

                for(ObjTree field : new ObjTree(Product.class).getFields()) {
                    if(!field.isMayNull() && !buff.containsKey(field.getFieldName())) throw new CSVFileBroken(path);

                    if(field.isEnum()) {
                        if (field.getEnumContains().containsKey(buff.get(field.getFieldName())))
                            values.put(field.getFieldName(), field.getEnumContains().get(buff.get(field.getFieldName())));
                        else
                            throw new IllegalArgumentException();
                    }




                }
            } catch (WrongCSVLine e) {
                throw new RuntimeException(e);
            } catch (CSVFileBroken e) {
                throw new RuntimeException(e);
            }
        }
    }*/

/*    public void parseCSV(String input) throws NoneValueFromCSV {
        if(input.isEmpty()) throw new NoneValueFromCSV("FILE CONTAINS NULL");
        collection.clear();
        String[] data = input.split("\n");

        String[] markup = data[0].split(", ");
        Map<String, String> dataMap = new HashMap<String, String>();

        for (String s : markup) {
            dataMap.put(s, "");
        }


        for(int i=1; i < data.length; i++){
            if (data[i].isEmpty()) continue;
            String[] line = data[i].split(", ");

//            System.out.print("DEBUG: ");
//            System.out.println(data[i]);
//            for (String a : line) System.out.print(" \"" + a + "\",");
//            System.out.println(line.length);

            try{
                if(line.length != 13)
                    throw new NoneValueFromCSV(data[i] + " - line " + i+1);

                for(int j=0; j < markup.length; j++){
                    dataMap.replace(markup[j], line[j]);
                }

//                HashMap<String, Object> values = new HashMap<>();
//                for (String key : dataMap.keySet()){
//                    values.put(key, productTree.)
//                }
                add(new Product(

                        Integer.parseInt(dataMap.get("id")),
                        dataMap.get("name"),
                        new Coordinates(
                                Float.parseFloat(dataMap.get("x")),
                                Float.parseFloat(dataMap.get("y"))),
                        Float.parseFloat(dataMap.get("price")),
                        UnitOfMeasure.valueOf(dataMap.get("unitOfMeasure")),
                        new Organization(
                                Long.parseLong(dataMap.get("manufacturer id")),
                                dataMap.get("manufacturer name"),
                                Double.parseDouble(dataMap.get("manufacturer annualTurnover")),
                                OrganizationType.valueOf(dataMap.get("manufacturer type")),
                                new Address(dataMap.get("manufacturer street"),
                                            dataMap.get("manufacturer zipCode"))
                        )
                ));
            } catch (Exception e) {
                System.out.println("Error in csv-parse string: ");
                System.out.print("\t");
                for (String a : line) System.out.print(" \"" + a + "\",");
                System.out.println("err type=" + e.getMessage());
            }
        }
    }*/


    public void parseCSV(String input) throws NoneValueFromCSV {
        if (input.isEmpty()) throw new NoneValueFromCSV("FILE CONTAINS NULL");
        collection.clear();
        String[] data = input.split("\n");

        String[] markup = data[0].split(", ");
        HashMap<String, String> dataMap = new HashMap<String, String>();

        for (String s : markup) {
            dataMap.put(s, "");
        }

        for(int i=1; i < data.length; i++) {
            if (data[i].isEmpty()) continue;
            String[] line = data[i].split(", ");


            try {
                if (line.length != 13)
                    throw new NoneValueFromCSV(data[i] + " - line " + (i + 1));

                for (int j = 0; j < markup.length; j++) {
                    dataMap.replace(markup[j], line[j]);
                }

                Builder builder = new Builder();
                Product newElem = builder.buildByString(productTree, dataMap);
                if(newElem == null) System.out.println("error in csv-import: " + data[i]);
                else add(newElem);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String generateCSV() {
        StringBuilder out = new StringBuilder("id" +
                ", name" +
                ", coordinates x" +
                ", coordinates y" +
                ", creationDate" +
                ", price" +
                ", unitOfMeasure" +
                ", manufacturer id" +
                ", manufacturer name" +
                ", manufacturer annualTurnover" +
                ", manufacturer type" +
                ", manufacturer postalAddress street" +
                ", manufacturer postalAddress zipCode" + "\n");

        for (Product product : collection) {
            if (product == null) continue;
            out.append(product.generateCSV() + "\n");
        }
        return out.toString();
    }

    public void save(){
        try {
            FileOutputHandler file = new FileOutputHandler(path + "data.csv");
            file.println(generateCSV());
            file.close();
            System.out.println("file saved at: " + path + "data.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(){
        load(path + "data.csv");
    }
    public void load(String newPath){
        try {
            FileInputHandler file = new FileInputHandler(newPath);
            StringBuilder buff = new StringBuilder();
            while (file.hasNextLine())
                buff.append(file.get() + "\n");
            parseCSV(buff.toString());
            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(Product a : collection) out.append(a.toString());
        return out.toString();
    }
}

