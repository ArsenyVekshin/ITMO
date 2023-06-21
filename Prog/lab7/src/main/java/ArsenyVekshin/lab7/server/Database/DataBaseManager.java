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

    private Set<User> userSet; // коллекция пользователей
    private  Vector<Product> collection;
    private ZonedDateTime lastUpdateTime;
    private SQLManager sqlManager = new SQLManager();
    Lock lock = new ReentrantLock();

    public DataBaseManager(){}

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
        lock.lock();
        ResultSet resultSet = sqlManager.getRaw("SELECT product.*, users.username FROM product JOIN users ON product.user_id = users.id ORDER BY name ASC;");
        if (resultSet == null) return;
        collection.clear();

        try{
            while(resultSet.next()){
                Product product = new Product();
                HashMap<String, Object> values = product.getValues();
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
        lock.lock();
        ResultSet resultSet = sqlManager.getRaw("SELECT username, password FROM users;");
        if (resultSet == null) return;
        userSet.clear();

        try{
            while(resultSet.next()){
                userSet.add(new User(resultSet.getString("login"),
                        resultSet.getString("password")));
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
        lock.lock();
        loadCollectionFromBase();
        loadUsersListFromBase();
        lock.unlock();
    }

    public void insert(Product product){
        lock.lock();
        try{
            String out ="INSERT INTO product values " + getElemValuesLine(product) + ";";

            PreparedStatement sql = sqlManager.getConnection().prepareStatement(out);
            if(!sqlManager.send(sql))
                throw new SQLExecuteErrorException("Не удалось добавить элемент");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }

    public void delete(Product product){
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
        lock.lock();
        try{
            delete(product);
            insert(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }

    private String getElemValuesLine(Entity elem){
        String line = "(";
        HashMap<String, Object> values = elem.getValues();
        for(String key : values.keySet())
            line += key + "=" + values.get(key) + ",";
        line = line.substring(0, line.length()-1);
        line += ")";
        return line;
    }

    public void addUser(User user){
        lock.lock();
        try{
            String out ="INSERT INTO users values " + getElemValuesLine(user) + ";";

            PreparedStatement sql = sqlManager.getConnection().prepareStatement(out);
            if(!sqlManager.send(sql))
                throw new SQLExecuteErrorException("Не удалось добавить элемент");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }




}
