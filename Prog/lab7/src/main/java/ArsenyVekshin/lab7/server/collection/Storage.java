package ArsenyVekshin.lab7.server.collection;

import ArsenyVekshin.lab7.common.builder.Builder;
import ArsenyVekshin.lab7.common.builder.ObjTree;
import ArsenyVekshin.lab7.common.collectionElems.CSVOperator;
import ArsenyVekshin.lab7.common.collectionElems.data.*;
import ArsenyVekshin.lab7.common.collectionElems.exceptions.InvalidValueEntered;
import ArsenyVekshin.lab7.common.collectionElems.exceptions.NoneValueFromCSV;
import ArsenyVekshin.lab7.common.collectionElems.exceptions.WrongID;
import ArsenyVekshin.lab7.common.ui.file.FileInputHandler;
import ArsenyVekshin.lab7.common.ui.file.FileOutputHandler;
import ArsenyVekshin.lab7.server.Database.DataBaseManager;
import ArsenyVekshin.lab7.server.Database.SQLManager;
import ArsenyVekshin.lab7.server.Server;

import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ArsenyVekshin.lab7.common.tools.Comparators.compareFields;

/**
 Main collection manager
 includes:
    - functions for collection-operations cmd
    - CSV load/save functions
 */
public class Storage<T extends Object> implements CSVOperator {

    private static DataBaseManager dataBaseManager;

    public String fileName = "none"; //default value
    private volatile static Vector<Product> collection = new Vector<>();
    public volatile static String path = null;
    private volatile static ZonedDateTime creationTime;
    private volatile static int usersCounter = 0;

    private static final String defaultFieldForComp = "id";

    public static final ObjTree productTree = new ObjTree(Product.class);

    public static ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public static void setCreationTime(ZonedDateTime creationTime) {
        Storage.creationTime = creationTime;
    }

    private static Lock lock = new ReentrantLock();


    public Storage(DataBaseManager dataBaseManager) {
        dataBaseManager.setCollection(collection);
        init();
    }

    /**
     * Init collection save-path and automatically load
     */
    public void init() {
        try {
            collection = new Vector<>();
            creationTime = ZonedDateTime.now();
            if (path== null) {
                path = new File(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent() + File.separatorChar + "sysFiles" + File.separatorChar + "data.csv";
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
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * append random-values to collection
     * @throws InvalidValueEntered
     */
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
            System.out.println(e.getMessage());//System.out.println(e.getMessage());//e.printStackTrace();
        }
    }

    public Product getElemById(int id) throws WrongID {
        return collection.get(findIdx(id));
    }

    /**
     * Generate string with collection meta-data
     * @return String data to print
     */
    public static String info(){
        String out = "";
        out += "contains classes: " + Product.class.getName() + "\n";
        out += "database path: " + path + "\n";
        out += "created at: " + creationTime.toString() + "\n";
        out += "positions num: " + usersCounter;
        return out;
    }

    /**
     * Generate string with collection elements
     * @return
     */
    public static String show(){
        if(collection.size() == 0 ) return "collection is empty";
        StringBuilder out = new StringBuilder();
        for (Product product : collection) {
            out.append(product.toString()).append("\n");
        }
        return out.toString();
    }

    public static String showPart(int num, int partSize){
        if(num*partSize > collection.size()) return "";
        StringBuilder out = new StringBuilder();
        for(int i = num*partSize; i<(num+1)*partSize; i++){
            if(i>=collection.size() || collection.get(i)==null) continue;
            out.append(collection.get(i).toString()).append("\n");
        }
        return out.toString();
    }

    /**
     * Find collection-index of entered id
     * @param id product-id to find
     * @return collection-index
     * @throws WrongID
     */
    private static int findIdx(int id) throws WrongID {
        for (int i = 0; i < collection.toArray().length; i++) {
            if (id == collection.get(i).getId()) return i;
        }
        throw new WrongID((long) id);
    }

    /**
     * find collection size
     * @return collection size
     */
    public static long getElementsCount() {
        return collection.size();
    }

    /**
     * add element to collection without id-generation
     * @param product Product-obj to add
     */
    public static void add(Product product) {
        collection.add(product);
    }

    /**
     * add element to collection with auto id-generation
     * @param product Product-obj to add
     */
    public static void addNew(Product product) {
        collection.add(product);
        dataBaseManager.insert(product);
        collection.lastElement().generateID(usersCounter);
        usersCounter++;

    }

    /**
     * add element to collection with auto id-generation
     * @param tree map-container with product-fields values
     */
    public static void addNew(HashMap<String, Object> tree) {
        collection.add(new Product(tree));
        dataBaseManager.insert(new Product(tree));
        collection.lastElement().generateID(usersCounter);
        usersCounter++;

    }

    /**
     * Update fields on set-id collection elem
     * @param id id of product to update
     * @param product new value for elem
     * @throws WrongID
     */
    public void update(long id, Product product) throws WrongID {
        lock.lock();
        try {
            int _idx = findIdx((int)id);
            collection.set(_idx, product);
            dataBaseManager.update(product);
        } catch (WrongID e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }

    /**
     * remove elem from collection by id
     * @param id product-id to delete
     * @throws WrongID
     */
    public void remove(int id) throws WrongID {
        lock.lock();
        try {
            int _idx = findIdx(id);
            dataBaseManager.delete(collection.get(_idx));
            collection.remove(_idx);
        } catch (WrongID e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }

    /**
     * clear collection
     */
    public static void clear() {
        lock.lock();
        collection.clear();
        usersCounter = 1;
        lock.unlock();
    }

    /**
     * swap 2 elems in collection
     * @param idx1 elem 1
     * @param idx2 elem 2
     * @throws CloneNotSupportedException
     */
    private static void swapPosition(int idx1, int idx2) throws CloneNotSupportedException {
        if (Integer.max(idx1, idx2) >= collection.size()) collection.setSize(Integer.max(idx1, idx2));
        Product buff = collection.get(idx1).clone();
        collection.set(idx1, collection.get(idx2).clone());
        collection.set(idx2, buff);
    }

    /**
     * insert new product to position
     * @param idx needable position
     * @param product new product
     * @throws WrongID
     * @throws CloneNotSupportedException
     */
    public static void insertToPosition(int idx, Product product) throws WrongID, CloneNotSupportedException {
        lock.lock();
        if (idx < collection.size()) {
            collection.add(product);
            dataBaseManager.insert(product);
            swapPosition(collection.size() - 1, idx);
        } else {
            collection.set(idx, product);
        }
        lock.unlock();
    }

    /**
     * sort collection by column
     * @param field field to compare (id/name/price)
     */
    public void sortBy(String field) {
        lock.lock();
        collection.sort((f1, f2) -> {
            try {
                compareFields((T) f1.getClass().getDeclaredField(field).get(f1),
                        (T) f2.getClass().getDeclaredField(field).get(f2));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println(e.getMessage());//System.out.println(e.getMessage());//e.printStackTrace();
            }
            return 0;
        });
        lock.unlock();
    }

    /**
     * default collection sort-function
     */
    public void sort(){
        lock.lock();
        collection.sort(Comparator.naturalOrder());
        lock.unlock();
    }

    /**
     * find max elem from collection (sort param = default)
     * @return product on max position
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Product max() throws NoSuchFieldException, IllegalAccessException {
        if(collection.isEmpty()) return null;
        Product _max = collection.stream().max(Comparator.comparingInt(Product::getId)).get();
        return _max;
    }

    /**
     * add new elem to collection if it upper than all (sort param = default)
     * @param o new product
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void addIfMax(Product o){
        lock.lock();
        try {
            if(max().compareTo(o) < 0) return;
            add(o);
            dataBaseManager.insert(o);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            System.out.println(e.getMessage());//System.out.println(e.getMessage());//e.printStackTrace();
        }
        lock.unlock();
    }

    /**
     * remove all elems which are greater than entered
     * @param o  new product
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void removeGreater(Product o) throws NoSuchFieldException, IllegalAccessException {
        lock.lock();
        Product _max = max();
        if(_max != null) {
            try {
                dataBaseManager.delete(_max);
                remove(_max.getId());
            } catch (WrongID e) {
                System.out.println(e.getMessage());
            }
        }
        lock.unlock();
    }

    /**
     * remove all elems with the same unitOfMeasure
     * @param unitOfMeasure
     */
    public static void removeSameUnitOfMeasure(UnitOfMeasure unitOfMeasure){
        lock.lock();
        for (int i=0; i<collection.size(); i++){
            if(collection.get(i) == null || collection.get(i).getUnitOfMeasure() != unitOfMeasure) continue;
            dataBaseManager.delete(collection.get(i));
            collection.remove(i);
        }
        lock.unlock();
    }

    /**
     * generate string with sorted prices of all elems
     * @return string for print
     */
    public static String getPrices(){
        if(collection.isEmpty()) return "collection is empty";
        return Arrays.toString(collection.stream().sorted().map(Product::getPrice).toArray());
    }

    /**
     * find all elems prices sum
     * @return prices sum
     */
    public static float getPricesSum(){
        if(collection.isEmpty()) return 0;
        return collection.stream().sorted().map(Product::getPrice).reduce(0f, Float::sum);
    }

    public void load(){
        dataBaseManager.loadCollectionFromBase();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(Product a : collection) out.append(a.toString());
        return out.toString();
    }
}
