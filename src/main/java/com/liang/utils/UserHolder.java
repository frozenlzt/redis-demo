package com.liang.utils;

import com.liang.dto.UserDTO;

public class UserHolder {
    private static final ThreadLocal<UserDTO> tl=new ThreadLocal<>();
    public static void saveUser(UserDTO user){
        tl.set(user);
    };
    public static UserDTO getUser(){ return tl.get();}
    public static void deleteUser(){tl.remove();}
}
