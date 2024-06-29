package com.hackWeb.hackWeb.util;

public class PasswordValidator {

    public static String validatePassword(String password) {
        if (password.length() < 6) {
            return "La contraseña debe tener al menos 6 caracteres.";
        }
//        if (!password.matches(".*[A-Z].*")) {
//            return "La contraseña debe contener al menos una letra mayúscula.";
//        }
//        if (!password.matches(".*[a-z].*")) {
//            return "La contraseña debe contener al menos una letra minúscula.";
//        }
//        if (!password.matches(".*\\d.*")) {
//            return "La contraseña debe contener al menos un número.";
//        }
//        if (!password.matches(".*[!@#$%^&*].*")) {
//            return "La contraseña debe contener al menos un carácter especial (!@#$%^&*).";
//        }
        return null;
    }
}
