package ArsenyVekshin.lab7.common.security;

import java.io.Serializable;

import static ArsenyVekshin.lab7.common.security.Encoder.getSHA;

public class User  implements Serializable {
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
}
