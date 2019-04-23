package com.example.myappcontacts.data.providers.database.databaseutils;

public class ContactsDbSchema {
    public static final class ContactsTable {
        public static final String NAME = "contacts";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String FIRST_NAME = "firstName";
            public static final String LAST_NAME = "lastName";
            public static final String COMPANY_NAME = "companyName";
            public static final String TEL_NUMBER = "telNumber";
            public static final String PHOTO_URI = "photoUri";
            public static final String ADDRESS = "address";
        }
    }
}
