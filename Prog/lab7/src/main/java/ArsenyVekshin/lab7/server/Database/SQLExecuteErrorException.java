package ArsenyVekshin.lab7.server.Database;

public class SQLExecuteErrorException extends Exception{
    public SQLExecuteErrorException(String mes){
        super("В запросе к БД возникла ошибка: " + mes);
    }
}
