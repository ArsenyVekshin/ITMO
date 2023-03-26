package ArsenyVekshin.lab5.utils.builder;

@FunctionalInterface
public interface Convert<T> {
     T convert(Class<T> type, String value) throws Exception;
}
