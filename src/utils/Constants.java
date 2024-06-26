package utils;

public class Constants {
    public final static int maxNrBorrowDays = 30;
    public final static int maxNrBorrowedBooks = 4;
    public final static String CHECKIN = "check in";
    public final static String CHECKOUT = "check out";
    public final static double penaltyPerDay = 5;

    // conexiune baza de date
    public static final String JDBC_DRIVER = "jdbc:mysql://localhost/libraryms";
    public static final String JDBC_PWD = "R091sor#30D3c";
    public static final String JDBC_USER = "root";

    // fisier audit
    public static final String AUDIT_FILE = "audit.csv";
}
