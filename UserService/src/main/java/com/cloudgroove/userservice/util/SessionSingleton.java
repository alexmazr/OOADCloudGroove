package com.cloudgroove.userservice.util;

import com.cloudgroove.userservice.entity.User;

public class SessionSingleton
{
    private static User userInstance;
    private SessionSingleton() {};

    public static User getInstance ()
    {
        return userInstance;
    }

    public static void loginNewUser (User newUser)
    {
        userInstance = newUser;
    }
}
