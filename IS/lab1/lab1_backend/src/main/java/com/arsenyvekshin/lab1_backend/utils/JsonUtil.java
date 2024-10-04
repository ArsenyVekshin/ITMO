package com.arsenyvekshin.lab1_backend.utils;


import com.arsenyvekshin.lab1_backend.entities.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;

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
