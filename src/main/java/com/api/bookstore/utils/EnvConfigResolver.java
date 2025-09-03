package com.api.bookstore.utils;

public class EnvConfigResolver {

    private static  String url;
    private static  String email;
    private static  String password;

    public static void resolved() {
        String env = ConfigurationManager.get("env").trim().toLowerCase();

        switch (env) {
            case "qa":
                url = ConfigurationManager.get("qa.url");
                email = ConfigurationManager.get("qa.email");
                password = ConfigurationManager.get("qa.password");
                break;

            case "dev":
                url = ConfigurationManager.get("dev.url");
                email = ConfigurationManager.get("dev.email");
                password = ConfigurationManager.get("dev.password");
                break;

            case "prod":
                url = ConfigurationManager.get("prod.url");
                email = ConfigurationManager.get("prod.email");
                password = ConfigurationManager.get("prod.password");
                break;

            default:
                throw new IllegalArgumentException("Invalid environment: " + env);
        }
    }

    public static String getUrl() {
    	resolved();
        return url;
    }

    public static String getEmail() {
    	resolved();
        return email;
    }

    public static String getPassword() {
    	resolved();
        return password;
    }
}
