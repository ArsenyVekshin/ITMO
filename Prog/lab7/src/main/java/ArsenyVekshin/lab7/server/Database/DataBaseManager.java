package ArsenyVekshin.lab7.server.Database;

import ArsenyVekshin.lab7.common.builder.Builder;
import ArsenyVekshin.lab7.common.builder.ObjTree;
import ArsenyVekshin.lab7.common.collectionElems.data.Product;
import ArsenyVekshin.lab7.common.security.User;
import ArsenyVekshin.lab7.server.AuthManager;
import ArsenyVekshin.lab7.server.collection.Storage;

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

    private ObjTree userTree = new ObjTree(User.class);
    private ObjTree productTree = new ObjTree(Product.class);
    private final boolean ENABLE = true;
    private AuthManager userSet; // коллекция пользователей
    private Storage collection;
    private ZonedDateTime lastUpdateTime;
    private SQLManager sqlManager;
    Lock lock = new ReentrantLock();


    public DataBaseManager(String password){
        sqlManager = new SQLManager(password);
    }

    public DataBaseManager(Storage collection, AuthManager userSet) {
        this.collection = collection;
        this.userSet = userSet;
    }

    public void setDatabasePass(String pass){
        sqlManager.setDatabasePass(pass);
    }

    public void setCollection(Storage collection) {
        this.collection = collection;
    }

    public void setUserSet(AuthManager userSet) {
        this.userSet = userSet;
    }

    /**
     * Считать коллекцию с базы данных
     */
    public void loadCollectionFromBase(){
        if(!ENABLE) return;
        lock.lock();
        ResultSet resultSet = sqlManager.getRaw("SELECT * FROM products;");
        if (resultSet == null) return;
        collection.clear();

        try{
            while(resultSet.next()){
                collection.add(Builder.buildBySQL(productTree, resultSet));
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

        try{
            while(resultSet.next()){
                userSet.add(Builder.buildBySQL(userTree, resultSet));
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
            String out ="INSERT INTO products(" +
                    Builder.genObjTableHeader(productTree) + ") values (" +
                    Builder.genObjTableLine(productTree, product) + ");";

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
            boolean result = sqlManager.sendRaw("DELETE FROM products WHERE id=" + product.getId() +";");
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
            boolean result = sqlManager.sendRaw("truncate table products;");
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
            String out ="INSERT INTO users(" +
                    Builder.genObjTableHeader(userTree) + ") values (" +
                    Builder.genObjTableLine(userTree, user) + ");";


            PreparedStatement sql = sqlManager.getConnection().prepareStatement(out);
            if(!sqlManager.send(sql))
                throw new SQLExecuteErrorException("Не удалось добавить элемент");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        lock.unlock();
    }




}
