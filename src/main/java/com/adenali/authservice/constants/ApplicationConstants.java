package com.adenali.authservice.constants;


public final class ApplicationConstants {

    // Read from environment variable, fallback to default if not set
    public static final String JWT_SECRET_KEY = "JWT_SECRET";
    public static final String JWT_SECRET_DEFAULT_VALUE = System.getenv().getOrDefault(
            JWT_SECRET_KEY,
            ""
    );

    public static final String JWT_HEADER = "Authorization";
}
