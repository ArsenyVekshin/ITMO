package ArsenyVekshin.lab7.common.security;

import ArsenyVekshin.lab7.common.collectionElems.data.Entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.function.Supplier;

import static ArsenyVekshin.lab7.common.security.Encoder.getSHA;

public class User extends Entity implements Serializable {
    private String login; //также является уникальным ключом
    private byte[] password; // закодированный пароль

    /***
     * Конструктор класса
     * @param login логин
     * @param password пароль(НЕ закодированный)
     */
    public User(String login, String password){
        this.login = login;
        setPassword(password);
    }

    public User() {}

    public void setPassword(String password) {
        this.password = getSHA(password);
    }

    public byte[] getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass()) return false;
        User u = (User) obj;
        return getPassword() == u.getPassword() &&
                getLogin() == u.getLogin();
    }

    @Override
    public String toString() {
        return  getLogin();
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public void init(HashMap<String, Object> values) {
        if(values.containsKey("login")) this.login = (String) values.get("login");
        if(values.containsKey("password")) this.password = (byte[]) values.get("password");
    }

    @Override
    public HashMap<String, Object> getValues() {
        HashMap<String, Object> values = new HashMap<>();
        values.put("login", login);
        values.put("password", password);
        return values;
    }

    @Override
    public Supplier<Entity> getConstructorReference() {
        return User::new;
    }
}
