package ArsenyVekshin.lab7.common.security;

import ArsenyVekshin.lab7.common.collectionElems.data.Entity;
import ArsenyVekshin.lab7.common.collectionElems.data.SQLTableElem;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.function.Supplier;

import static ArsenyVekshin.lab7.common.security.Encoder.getSHAString;

public class User extends Entity implements Serializable, SQLTableElem {
    private String login; //также является уникальным ключом
    private String password; // закодированный пароль

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
        this.password = getSHAString(password);
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null || obj.getClass() != this.getClass()) return false;
        User u = (User) obj;
        return Objects.equals(getPassword(), u.getPassword()) &&
                Objects.equals(getLogin(), u.getLogin());
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
        if(values.containsKey("password")) this.password = (String) values.get("password");
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

    @Override
    public String genValuesLine() {
        String out = "(";
        out += "login = \'" + login + "\'";
        out += ", password = \'" + password + "\')";
        return out;
    }

    @Override
    public void parseValuesLine(HashMap<String, Object> values) {
        login = (String)values.get("login");
        password = (String)values.get("password");
    }
}
