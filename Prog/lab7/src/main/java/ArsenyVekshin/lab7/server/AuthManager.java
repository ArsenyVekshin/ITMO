package ArsenyVekshin.lab7.server;

import ArsenyVekshin.lab7.common.exceptions.AccessRightsException;
import ArsenyVekshin.lab7.common.security.User;

import java.util.HashSet;
import java.util.Set;

public class AuthManager {

    private User masterUser; // пользователь администратора
    private Set<User> userSet = new HashSet<>(); // коллекция пользователей

    public AuthManager(){
        addDefaultUsers();
    }

    private void addDefaultUsers(){
        masterUser = new User("admin", "admin");
        userSet.add(masterUser);
        userSet.add(new User("user1", "user1"));
        userSet.add(new User("user2", "user2"));
        userSet.add(new User("user3", "user3"));
    }

    public boolean isAuthorised(User user){
        //return userSet.contains(user);
        if(user.equals(masterUser)) return true;
        for(User _user: userSet){
            if(_user.equals(user))
                return true;
        }
        return false;
    }

    /***
     * Добавить нового пользователя
     * @param masterUser логин того, кто пытается добавить
     * @param newUser новый пользователь
     * @throws AccessRightsException пользователь не имеет на это прав
     */
    public void addUser(User masterUser, User newUser) throws AccessRightsException {
        if(!this.masterUser.equals(masterUser))
            throw new AccessRightsException("добавление новых пользователей доступно только администраторам");
        userSet.add(newUser);
    }

    public Set<User> getUserSet() {
        return userSet;
    }

    public User getMasterUser() {
        return masterUser;
    }
}
