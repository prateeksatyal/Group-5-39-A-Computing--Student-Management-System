package controller;

import model.UserData;

/**
 * Singleton session holder for the currently authenticated user.
 * Provides null-safe role helpers so controllers never need to call
 * getCurrentUser().getRole() with a potential NPE.
 */
public class UserSession {
    private static UserData currentUser;

    public static UserData getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(UserData user) {
        currentUser = user;
    }

    /** Returns the role string from the current session, or "" if not logged in. */
    public static String getRole() {
        return currentUser != null ? currentUser.getRole() : "";
    }

    /** Returns true when the logged-in user has the Admin role. */
    public static boolean isAdmin() {
        return "Admin".equalsIgnoreCase(getRole()) || "Administrator".equalsIgnoreCase(getRole());
    }

    /** Returns true when the logged-in user has the Teacher role. */
    public static boolean isTeacher() {
        return "Teacher".equalsIgnoreCase(getRole());
    }

    /** Returns true when the logged-in user has the Student role. */
    public static boolean isStudent() {
        return "Student".equalsIgnoreCase(getRole());
    }

    public static void clear() {
        currentUser = null;
    }

    /** @deprecated Use {@link #clear()} instead. Kept for backward compatibility. */
    public static void clearSession() {
        clear();
    }
}
