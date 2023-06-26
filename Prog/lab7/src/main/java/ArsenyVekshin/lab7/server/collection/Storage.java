package ArsenyVekshin.lab7.server.collection;

import ArsenyVekshin.lab7.common.builder.ObjTree;
import ArsenyVekshin.lab7.common.collectionElems.CSVOperator;
import ArsenyVekshin.lab7.common.collectionElems.data.*;
import ArsenyVekshin.lab7.common.collectionElems.exceptions.WrongID;
import ArsenyVekshin.lab7.common.exceptions.AccessRightsException;
import ArsenyVekshin.lab7.server.AuthManager;
import ArsenyVekshin.lab7.server.Database.DataBaseManager;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static ArsenyVekshin.lab7.common.tools.Comparators.compareFields;

/**
 Main collection manager
 includes:
    - functions for collection-operations cmd
    - CSV load/save functions
 */
public class Storage<T extends Object> implements CSVOperator {

    private AuthManager authManager;
    private static DataBaseManager dataBaseManager;

    public String fileName = "none"; //default value
    private volatile static Vector<Product> collection = new Vector<>();
    private volatile static ZonedDateTime creationTime;
    private volatile static int nextId = 0;

    private static final String defaultFieldForComp = "id";

    public static final ObjTree productTree = new ObjTree(Product.class);

    public static ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public static void setCreationTime(ZonedDateTime creationTime) {
        Storage.creationTime = creationTime;
    }

    private static ReadWriteLock lock = new ReentrantReadWriteLock();


    public Storage(DataBaseManager dataBaseManager, AuthManager authManager) {
        this.dataBaseManager = dataBaseManager;
        this.authManager = authManager;
        init();
    }

    /**
     * Init collection save-path and automatically load
     */
    public void init() {
        collection = new Vector<>();
        creationTime = ZonedDateTime.now();
        nextId = findMaxId();
    }

    public static int findMaxId() {
        return collection.stream()
                .max(Comparator.comparing(Product::getId))
                .map(Product::getId)
                .orElse(0);
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
        out += "created at: " + creationTime.toString() + "\n";
        out += "positions num: " + collection.size();
        return out;
    }

/*    public static String show(){
        if(collection.size() == 0 ) return "collection is empty";
        StringBuilder out = new StringBuilder();
        ObjTree tree = new ObjTree(Product.class);
        out.append(Builder.genObjTableHeader(tree)).append("\n");
        for (Product product : collection) {
            out.append(Builder.genObjTableLine(tree, product)).append("\n");

        }
        return out.toString();
    }*/
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
   /* public static String showPart(int num, int partSize){
        if(num*partSize > collection.size()) return "";
        ObjTree tree = new ObjTree(Product.class);
        StringBuilder out = new StringBuilder();
        out.append(Builder.genObjTableHeader(tree)).append("\n");
        for(int i = num*partSize; i<(num+1)*partSize; i++){
            if(i>=collection.size() || collection.get(i)==null) continue;
            out.append(Builder.genObjTableLine(tree, collection.get(i))).append("\n");
        }
        return out.toString();
    }*/

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
        lock.writeLock().lock();
        collection.add(product);
        collection.lastElement().generateID(nextId);
        dataBaseManager.insert(collection.lastElement());
        nextId++;
        lock.writeLock().unlock();lock.writeLock().unlock();
    }

    /**
     * add element to collection with auto id-generation
     * @param tree map-container with product-fields values
     */
    public static void addNew(HashMap<String, Object> tree) {
        collection.add(new Product(tree));
        dataBaseManager.insert(new Product(tree));
        collection.lastElement().generateID(nextId);
        nextId++;

    }

    /**
     * Владеет ли этот пользователь объектом?
     * @param id id объекта
     * @param redactor пользователь на проверку
     * @return bool
     */
    public boolean checkOwnership(long id, String redactor){
        lock.readLock().lock();
        try {
            if(authManager.getMasterUser().getLogin().equals(redactor)) return true;
            int _idx = findIdx((int)id);
            lock.readLock().unlock();
            return collection.get(_idx).getOwner().equals(redactor);
        } catch (WrongID e) {
            lock.readLock().unlock();
            return false;
        }
    }

    /**
     * Владеет ли этот пользователь объектом?
     * @param product новый объект (обязательно совпадение по id)
     * @param redactor пользователь на проверку
     * @return bool
     */
    public boolean checkOwnership(Product product, String redactor){
        try {
            if(authManager.getMasterUser().getLogin().equals(redactor)) return true;
            int _idx = findIdx(product.getId());
            return collection.get(_idx).getOwner().equals(redactor);
        } catch (WrongID e) {
            return false;
        }
    }

    /**
     * Update fields on set-id collection elem
     * @param id id of product to update
     * @param product new value for elem
     * @throws WrongID
     */
    public void update(long id, Product product, String redactor) throws WrongID, AccessRightsException {
        lock.writeLock().lock();

        int _idx = findIdx((int)id);
        if(!checkOwnership(collection.get(_idx), redactor)) {
            lock.writeLock().unlock();
            throw new AccessRightsException("изменение объекта доступно лишь создателю");
        }

        product.setId(collection.get(_idx).getId());
        collection.set(_idx, product);
        dataBaseManager.update(product);

        lock.writeLock().unlock();
    }

    /**
     * remove elem from collection by id
     * @param id product-id to delete
     * @throws WrongID
     */
    public void remove(int id, String redactor) throws WrongID, AccessRightsException {
        lock.writeLock().lock();

        int _idx = findIdx(id);
        if(!checkOwnership(collection.get(_idx), redactor)) {
            lock.writeLock().unlock();
            throw new AccessRightsException("изменение объекта доступно лишь создателю");
        }

        dataBaseManager.delete(collection.get(_idx));
        collection.remove(_idx);

        lock.writeLock().unlock();
    }

    /**
     * clear collection
     */
    public void clear(String redactor) {
        lock.writeLock().lock();
        for(Product product : collection){
            if(checkOwnership(product, redactor)){
                dataBaseManager.delete(product);
                collection.remove(product);
            }

        }
        nextId = findMaxId()+1;
        lock.writeLock().unlock();
    }

    /**
     * swap 2 elems in collection
     * @param idx1 elem 1
     * @param idx2 elem 2
     * @throws CloneNotSupportedException
     */
    private static void swapPosition(int idx1, int idx2) throws CloneNotSupportedException {
        lock.writeLock().lock();
        if (Integer.max(idx1, idx2) >= collection.size()) collection.setSize(Integer.max(idx1, idx2));
        Product buff = collection.get(idx1).clone();
        collection.set(idx1, collection.get(idx2).clone());
        collection.set(idx2, buff);
        lock.writeLock().unlock();
    }

    /**
     * insert new product to position
     * @param idx needable position
     * @param product new product
     * @throws WrongID
     * @throws CloneNotSupportedException
     */
    public static void insertToPosition(int idx, Product product) throws WrongID, CloneNotSupportedException {
        lock.writeLock().lock();
        if (idx < collection.size()) {
            addNew(product);
            dataBaseManager.insert(collection.lastElement());
            swapPosition(collection.size() - 1, idx);
        } else {
            collection.set(idx, product);
        }
        lock.writeLock().unlock();
    }

    /**
     * sort collection by column
     * @param field field to compare (id/name/price)
     */
    public void sortBy(String field) {
        lock.writeLock().lock();
        collection.sort((f1, f2) -> {
            try {
                compareFields((T) f1.getClass().getDeclaredField(field).get(f1),
                        (T) f2.getClass().getDeclaredField(field).get(f2));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.out.println(e.getMessage());//System.out.println(e.getMessage());//e.printStackTrace();
            }
            return 0;
        });
        lock.writeLock().unlock();
    }

    /**
     * default collection sort-function
     */
    public void sort(){
        lock.writeLock().lock();
        collection.sort(Comparator.naturalOrder());
        lock.writeLock().unlock();
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
        lock.writeLock().lock();
        try {
            if(max().compareTo(o) < 0) return;
            addNew(o);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            System.out.println(e.getMessage());
        }
        lock.writeLock().unlock();
    }

    /**
     * remove all elems which are greater than entered
     * @param o  new product
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void removeGreater(Product o, String redactor) throws NoSuchFieldException, IllegalAccessException, AccessRightsException, WrongID {
        lock.writeLock().lock();
        Product _max = max();
        if(_max != null) {
            remove(_max.getId(), redactor);
        }
        lock.writeLock().unlock();
    }

    /**
     * remove all elems with the same unitOfMeasure
     * @param unitOfMeasure
     */
    public void removeSameUnitOfMeasure(UnitOfMeasure unitOfMeasure, String redactor) throws AccessRightsException {
        lock.writeLock().lock();
        boolean accessErrorFlag = false;
        for (int i=0; i<collection.size(); i++){
            if(collection.get(i) == null || collection.get(i).getUnitOfMeasure() != unitOfMeasure) continue;

            if(!checkOwnership(collection.get(i), redactor))
                accessErrorFlag = true;

            dataBaseManager.delete(collection.get(i));
            collection.remove(i);
        }
        lock.writeLock().unlock();
        if(accessErrorFlag)
            throw new AccessRightsException("изменение объекта доступно лишь создателю");
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
        lock.writeLock().lock();
        dataBaseManager.loadCollectionFromBase();
        lock.writeLock().unlock();
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        for(Product a : collection) out.append(a.toString());
        return out.toString();
    }
}

