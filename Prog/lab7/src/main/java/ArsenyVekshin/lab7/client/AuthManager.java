package ArsenyVekshin.lab7.client;

import ArsenyVekshin.lab7.common.exceptions.AccessRightsException;
import ArsenyVekshin.lab7.common.security.User;
import ArsenyVekshin.lab7.common.ui.InputHandler;
import ArsenyVekshin.lab7.common.ui.OutputHandler;
import ArsenyVekshin.lab7.common.ui.console.ConsoleInputHandler;
import ArsenyVekshin.lab7.common.ui.console.ConsoleOutputHandler;
import ArsenyVekshin.lab7.common.ui.exeptions.StreamBrooked;

import java.util.Set;

public class AuthManager {

    private User user;

    public void authDialogue(){
        authDialogue(new ConsoleInputHandler(), new ConsoleOutputHandler());
    }

    public void authDialogue(InputHandler inputHandler, OutputHandler outputHandler){
        user = genUser(inputHandler, outputHandler);
    }

    public User genUser(){
        return genUser(new ConsoleInputHandler(), new ConsoleOutputHandler());
    }

    public void logUser(){
        user = genUser();
    }

    /***
     * Диалог создания нового пользователя
     * @param inputHandler поток ввода
     * @param outputHandler поток вывода
     * @return новый пользователь
     */
    public User genUser(InputHandler inputHandler, OutputHandler outputHandler){
        User newUser = new User();
        try {
            outputHandler.println("Введите данные пользователя:");
            outputHandler.println("login:");
            newUser.setLogin(inputHandler.get());
            outputHandler.println("password:");
            newUser.setPassword(inputHandler.get());
        } catch (StreamBrooked e) {
            System.out.println(e.getMessage());
        }
        return newUser;
    }

    public User getUser() {
        return user;
    }
}
