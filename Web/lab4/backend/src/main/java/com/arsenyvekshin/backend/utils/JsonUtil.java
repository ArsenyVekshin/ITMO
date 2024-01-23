package com.arsenyvekshin.backend.utils;


import com.arsenyvekshin.backend.entities.User;
import com.google.gson.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class JsonUtil {

    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) -> {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
                return new JsonPrimitive(src.format(formatter));
            })
            .registerTypeAdapter(User.class, (JsonSerializer<User>) (src, typeOfSrc, context) -> {
                String login = src.getLogin();
                return new JsonPrimitive(login);
            })
            .create();

     public static String jsonMessage(String mes){
         return "{ \"message\": \"" + mes + "\" }";
     }

    public static String jsonfield(String key, String mes){
        return "{ \"" + key + "\": \"" + mes + "\" }";
    }

}
