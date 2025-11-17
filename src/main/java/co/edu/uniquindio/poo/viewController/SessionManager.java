package co.edu.uniquindio.poo.viewController;

import co.edu.uniquindio.poo.model.Usuario;
import co.edu.uniquindio.poo.model.Administrador;

public class SessionManager {
    private static SessionManager instance;
    private static Usuario currentUser;
    private static Administrador currentAdmin;
    
    private SessionManager() {}
    
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }
    
    public static void setCurrentUser(Usuario user) {
        currentUser = user;
        currentAdmin = null;
    }
    
    public static void setCurrentAdmin(Administrador admin) {
        currentAdmin = admin;
        currentUser = null;
    }
    
    public static Usuario getCurrentUser() {
        return currentUser;
    }
    
    public static Administrador getCurrentAdmin() {
        return currentAdmin;
    }
    
    public static void logout() {
        currentUser = null;
        currentAdmin = null;
        NavigationController.navigateToLogin();
    }
    
    public static boolean isUserLoggedIn() {
        return currentUser != null;
    }
    
    public static boolean isAdminLoggedIn() {
        return currentAdmin != null;
    }
    
    public String getCurrentUserId() {
        if (currentUser != null) {
            return currentUser.getIdUsuario();
        }
        if (currentAdmin != null) {
            return currentAdmin.getIdAdmin();
        }
        return null;
    }
    
    public String getCurrentUserName() {
        if (currentUser != null) {
            return currentUser.getNombreCompleto();
        }
        if (currentAdmin != null) {
            return currentAdmin.getNombre();
        }
        return "Usuario";
    }
    
    public String getCurrentUserType() {
        if (currentUser != null) {
            return "USUARIO";
        }
        if (currentAdmin != null) {
            return "ADMINISTRADOR";
        }
        return "NONE";
    }
}
