package ArsenyVekshin.lab6.client.utils.builder;

@FunctionalInterface
public interface Convert<T> {
     /**
      * Convert value String-->fieldType
      * @param type field type
      * @param value entered value
      * @return converted value
      * @throws Exception
      */
     T convert(Class<T> type, String value) throws Exception;
}
