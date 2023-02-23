package collection;


import collection.data.*;
import collection.exceptions.CSVOperator;
import collection.exceptions.InvalidValueEntered;
import collection.exceptions.NoneValueFromCSV;
import collection.exceptions.WrongID;

import java.io.*;
import java.time.ZonedDateTime;
import java.util.*;

public class Storage <T> implements CSVOperator {
    public Storage() {
    }

    String fileName = "none"; //default value
    private static Vector<Product> collection;
    private static String path = "\"C:\\Users\\Арсений\\Documents\\ITMO\\Prog\\lab5\"";
    private static ZonedDateTime creationTime;
    private static int usersCounter = 0;

    private static final String defaultFieldForComp = "id";

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
            path = System.getenv("lab5");
            if (path == null) {
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
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


    public static void update(Product product) throws WrongID {
        try {
            int _idx = findIdx(product.getId());
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

    public static void insertToPosition(Product product, int idx) throws WrongID, CloneNotSupportedException {
        if (idx < collection.size()) {
            collection.add(product);
            swapPosition(collection.size() - 1, idx);
        } else {
            collection.set(idx, product);
        }
    }

    private static int compareStrings(String s1, String s2) {
        if (s1 == s2) return 0;
        for (int i = 0; i < s1.length() && i < s2.length(); i++) {
            if (s1.charAt(i) > s2.charAt(i)) return 1;
            if (s1.charAt(i) < s2.charAt(i)) return -1;
        }
        return 0;
    }

    public int compareFields(T f1, T f2){
        if (f1.getClass().equals(String.class)) {
            String a = (String) f1;
            String b = (String) f2;
            return compareStrings(a, b);
        }
        if (f1.getClass().equals(Number.class)) {
            if(f1.getClass().equals(Float.class) ||
                    f1.getClass().equals(Double.class)){
                float a = (float) f1;
                float b = (float) f2;

                if (a > b) return 1;
                if (a < b) return -1;
            }  else {
                long a = (long) f1;
                long b = (long) f2;

                if (a > b) return 1;
                if (a < b) return -1;
            }
        }
        return 0;
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


    //TODO ИСПРАВЬ КОСТЫЛЬ!!!
    @Override
    public void parseCSV(String input) throws NoneValueFromCSV, InvalidValueEntered {
        if(input.isEmpty()) throw new NoneValueFromCSV("FILE CONTAINS NULL");
        collection.clear();
        String[] data = input.split("\n");

        String[] markup = data[0].split(", ");
        Map<String, String> dataMap = new HashMap<String, String>();
        for(int i=1; i < markup.length; i++){
            dataMap.put(markup[i], "");
        }


        for(int i=1; i < data.length; i++){
            String[] line = data[i].split(", ");
            if(data[i].isEmpty() || line.length != 11)
                throw new NoneValueFromCSV(data[i] + " - line " + i+1);

            for(int j=1; j < markup.length; j++){
                dataMap.replace(markup[j], line[j]);
            }

            try{
                add(new Product(
                        Integer.parseInt(dataMap.get("id")),
                        dataMap.get("name"),
                        new Coordinates(
                                Integer.parseInt(dataMap.get("x")),
                                Integer.parseInt(dataMap.get("y"))),
                        //creation date -- line[4]
                        UnitOfMeasure.valueOf(dataMap.get("price")),
                        new Organization(
                                Integer.parseInt(dataMap.get("manufacturer id")),
                                dataMap.get("manufacturer name"),
                                Double.parseDouble(dataMap.get("manufacturer annualTurnover")),
                                OrganizationType.valueOf(dataMap.get("manufacturer type")),
                                new Address(dataMap.get("manufacturer street"),
                                            dataMap.get("manufacturer zipCode"))
                        )
                ));
            } catch (IllegalArgumentException | InvalidValueEntered e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String generateCSV() {
        StringBuilder out = new StringBuilder("id" +
                ", name" +
                ", x" +
                ", y" +
                ", creationDate" +
                ", price" +
                ", unitOfMeasure" +
                ", manufacturer id" +
                ", manufacturer name" +
                ", manufacturer annualTurnover" +
                ", manufacturer type" +
                ", manufacturer street" +
                ", manufacturer zipCode" + "\n");

        for (Product product : collection) {
            if (product == null) continue;
            out.append(product.generateCSV());
        }
        return out.toString();
    }

    public void save(){
        try {
            OutputStream file = new FileOutputStream(path);
            Writer stream = new OutputStreamWriter(file);
            stream.write(generateCSV());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(){
        try {
            InputStream file = new FileInputStream(path);
            Scanner stream = new Scanner(file);
            StringBuilder input = new StringBuilder();
            while (stream.hasNext())
                input.append(stream.next());
            parseCSV(input.toString());
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

