package ArsenyVekshin.lab7.server;

import ArsenyVekshin.lab7.common.exceptions.AccessRightsException;
import ArsenyVekshin.lab7.common.security.User;

import java.util.Set;

public class AuthManager {

    private User masterUser; // пользователь администратора
    private Set<User> userSet; // коллекция пользователей

    public boolean isAuthorised(User user){
        return userSet.contains(user);
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
}
