package ru.tvey.cloudserverapp.security;

public class SecurityConstants {
    public static final String SECRET_KEY = "bQeThWmZq4t7w!z$c&F)j@ncRFUjXN3r5u8x/A?D*G-KaPdSgVkYp3s6v9y$B&E)"; //Your secret should always be strong (uppercase, lowercase, numbers, symbols) so that nobody can potentially decode the signature.
    public static final int TOKEN_EXPIRATION = 7200000; // 7200000 milliseconds = 7200 seconds = 2 hours.
    public static final String BEARER = "Bearer "; // Authorization : "Bearer " + Token
    public static final String AUTHORIZATION = "Authorization"; // "Authorization" : Bearer Token
    public static final String REGISTER_PATH = "cloud/user/register"; // Public path that clients can use to register.
    public static final String AES_ENCRYPTION_KEY = "PHMKjaGtW3pVmYQ8F6H5NR8DXThFAmkV";
    public static final String CACHE_SECRET = "123dSdDsa&*dsa$";

    public static final String VECTOR = "1231deda21329701";

}
