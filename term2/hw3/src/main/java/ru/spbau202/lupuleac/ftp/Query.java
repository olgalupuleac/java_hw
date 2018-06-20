package ru.spbau202.lupuleac.ftp;

/**
 * Enum which represents a query in FTP.
 */
public enum Query {
    EXIT,
    LIST,
    GET;

    /**
     * Transforms integer to query
     *
     * @param x is an integer to be transformed (should be 0, 1, 2)
     * @return LIST in case of 1, GET in case of 2,
     * EXIT in case of 0, or null if the integer is incorrect
     */
    public static Query fromInteger(int x) {
        switch (x) {
            case 0:
                return EXIT;
            case 1:
                return LIST;
            case 2:
                return GET;
            default:
                return null;
        }
    }

}
