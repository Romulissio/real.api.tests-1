package config;

public class Comparators {

    /** Users sort comparators*/
    public static final String SORT_FIRST_NAME = "compareToFirstName";
    public static final String SORT_FIRST_NAME_AND_LAST_NAME = "compareToFirstNameAndLastName";
    public static final String SORT_LAST_NAME = "compareToLastName";

    /** Agency sort comparators*/
    public static final String AGENCY_SORT_NAME = "compareToName";
    public static final String AGENCY_SORT_INN = "compareToInn";
    public static final String AGENCY_SORT_ACTIVE = "compareToActive";
    public static final String AGENCY_SORT_CREATED_AT = "compareToCreatedAt";

    private Comparators() {
    }
}
