package ArsenyVekshin.lab7.server.Database;

import ArsenyVekshin.lab7.common.collectionElems.data.Entity;
import ArsenyVekshin.lab7.common.collectionElems.data.Product;
import ArsenyVekshin.lab7.common.security.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DataBaseManager {

    private final boolean ENABLE = true;
    private Set<User> userSet; // коллекция пользователей
    private  Vector<Product> collection;
    private ZonedDateTime lastUpdateTime;
    private SQLManager sqlManager;
    Lock lock = new ReentrantLock();

    public DataBaseManager(String password){
        sqlManager = new SQLManager(password);
    }

    public DataBaseManager(Vector<Product> collection, Set<User> userSet) {
        this.collection = collection;
        this.userSet = userSet;
    }

    public void setDatabasePass(String pass){
        sqlManager.setDatabasePass(pass);
    }

    public void setCollection(Vector<Product> collection) {
        this.collection = collection;
    }

    public void setUserSet(Set<User> userSet) {
        this.userSet = userSet;
    }

    /**
     * Считать коллекцию с базы данных
     */
    public void loadCollectionFromBase(){
        if(!ENABLE) return;
        lock.lock();
        ResultSet resultSet = sqlManager.getRaw("SELECT * FROM product;");
        if (resultSet == null) return;
        collection.clear();

        HashMap<String, Object> values = new HashMap<>();
        values.put("id", null);
        values.put("name", null);
        values.put("creationDate", null);
        values.put("coordinates_x", null);
        values.put("coordinates_y", null);
        values.put("price", null);
        values.put("unitOfMeasure", null);
        values.put("manufacturer_id", null);
        values.put("manufacturer_name", null);
        values.put("manufacturer_annualTurnover", null);
        values.put("manufacturer_type", null);
        values.put("manufacturer_street", null);
        values.put("manufacturer_zipCode", null);
        values.put("owner", null);

        try{
            while(resultSet.next()){
                Product product = new Product();
                for(String key : values.keySet()){
                    values.put(key, resultSet.getString(key));
                }
                product.init(values);
                collection.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.lastUpdateTime = ZonedDateTime.now();
        lock.unlock();
    }

    /**
     * Считать данные всех пользователей с базы данных
     */
    public void loadUsersListFromBase(){
        if(!ENABLE) return;
        lock.lock();
        ResultSet resultSet = sqlManager.getRaw("SELECT * FROM users;");
        if (resultSet == null) return;
        userSet.clear();

        HashMap<String, Object> values = new HashMap<>();
        values.put("login", null);
        values.put("password", null);

        try{
            while(resultSet.next()){
                User user = new User();
                for(String key : values.keySet()){
                    values.put(key, resultSet.getString(key));
                }
                userSet.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        this.lastUpdateTime = ZonedDateTime.now();
        lock.unlock();
    }

    /**
     * Считать пользователей и коллекцию с базы данных
     */
    public void updateAll(){
        if(!ENABLE) return;
        lock.lock();
        loadCollectionFromBase();
        loadUsersListFromBase();
        lock.unlock();
    }

    public void insert(Product product){
        if(!ENABLE) return;
        lock.lock();
        try{
            String out ="INSERT INTO product values " + product.genValuesLine() + ";";

            PreparedStatement sql = sqlManager.getConnection().prepareStatement(out);
            if(!sqlManager.send(sql))
                throw new SQLExecuteErrorException("Не удалось добавить элемент");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }

    public void delete(Product product){
        if(!ENABLE) return;
        lock.lock();
        try{
            boolean result = sqlManager.sendRaw("DELETE FROM product WHERE id=" + product.getId() +";");
            if(!result)
                throw new SQLExecuteErrorException("Не удалось удалить элемент");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }

    public void clear(){
        if(!ENABLE) return;
        lock.lock();
        try{
            boolean result = sqlManager.sendRaw("DELETE * FROM product;");
            if(!result)
                throw new SQLExecuteErrorException("Не удалось очистить коллекцию");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }

    public void update(Product product){
        if(!ENABLE) return;
        lock.lock();
        try{
            delete(product);
            insert(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }

    public void addUser(User user){
        if(!ENABLE) return;
        lock.lock();
        try{
            String out ="INSERT INTO users values " + user.genValuesLine() + ";";

            PreparedStatement sql = sqlManager.getConnection().prepareStatement(out);
            if(!sqlManager.send(sql))
                throw new SQLExecuteErrorException("Не удалось добавить элемент");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }




}
