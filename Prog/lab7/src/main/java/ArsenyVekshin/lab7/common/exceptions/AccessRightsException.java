package ArsenyVekshin.lab7.common.exceptions;

public class AccessRightsException extends Exception{
    public AccessRightsException(String mes){super("У данного пользователя нет прав на это действие " + mes);}
}
