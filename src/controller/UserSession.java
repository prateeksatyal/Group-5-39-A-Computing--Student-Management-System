package controller;

import model.UserData;

public class UserSession {
    private static UserData currentUser;

    public static UserData getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserData user) {
        currentUser = user;
    }

    public static void clear() {
        currentUser = null;
    }
}
