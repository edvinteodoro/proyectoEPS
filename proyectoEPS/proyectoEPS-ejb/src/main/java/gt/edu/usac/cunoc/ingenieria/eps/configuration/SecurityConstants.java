package gt.edu.usac.cunoc.ingenieria.eps.configuration;

public class SecurityConstants{

    public static final String DATASOURCE_LOOKUP = "${'" + Constants.JDBC_RESOURCE + "'}";
    public static final String CALLER_QUERY = "select password from USER where userId = ?"; //get pass by userId
    public static final String GROUPS_QUERY = "select r.name FROM ROL r,USER u where u.userId=?";
    public static final String PBKDF_ITERATIONS = "Pbkdf2PasswordHash.Iterations=3072";
    public static final String PBKDF_ALGORITHM = "Pbkdf2PasswordHash.Algorithm=PBKDF2WithHmacSHA256";
    public static final String PBKDF_SALT_SIZE = "Pbkdf2PasswordHash.SaltSizeBytes=64";
}
