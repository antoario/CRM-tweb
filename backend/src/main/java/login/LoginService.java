package login;

import jakarta.servlet.http.HttpSession;

public class LoginService {
    private final static String SESSION_USER_KEY = "user";

    /**
     * Restituisce l'username dell'utente che ha fatto login in questa sessione o stringa vuota se l'utente non esiste.
     */
    public static String getCurrentLogin(HttpSession session) {
        if (session.getAttribute(SESSION_USER_KEY) == null) return "";
        return (String) session.getAttribute(SESSION_USER_KEY);
    }

    /**
     * Effettua il login di "email" in questa sessione e restituisce true o false(in base al successo).
     */
    public static boolean doLogIn(HttpSession session, String email) {
        if (session.getAttribute(SESSION_USER_KEY) == null) {
            session.setAttribute(SESSION_USER_KEY, email);
            session.setMaxInactiveInterval(5);
            return true;
        }
        String loggedUser = (String) session.getAttribute(SESSION_USER_KEY);
        return loggedUser.equals(email);
    }

    /**
     * Effettua il logout di "email" in questa sessione e restituisce true o false(in base al successo).
     */
    public static boolean doLogOut(HttpSession session, String email) {
        if (session.getAttribute(SESSION_USER_KEY) == null) return true;
        if (((String) session.getAttribute(SESSION_USER_KEY)).equals(email)) {
            session.invalidate();
            return true;
        }
        return false;
    }
}
