package com.moonyue.sleeve.core;

import com.auth0.jwt.interfaces.Claim;
import com.moonyue.sleeve.model.User;

import java.util.HashMap;
import java.util.Map;

public class LocalUser {

    private static final ThreadLocal<Map<String, Object>> localUser = new ThreadLocal<>();

    public static void setLocalUser(User user, Integer scope){
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("scope", scope);
        LocalUser.localUser.set(map);
    }

    public static User getUser(){
        return (User) localUser.get().get("user");
    }

    public static Integer getScope(){
        return (Integer) localUser.get().get("scope");
    }

    public static void clear(){
        localUser.remove();
    }
}
