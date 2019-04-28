package com.example.myappcontacts.data.providers.database.databaseutils

class ContactsDBSchema {
    object ContactsTable {
        const val NAME = "contacts"

        object Cols {
            const val UUID = "uuid"
            const val FIRST_NAME = "firstName"
            const val LAST_NAME = "lastName"
            const val COMPANY_NAME = "companyName"
            const val TEL_NUMBER = "telNumber"
            const val PHOTO_URI = "photoUri"
            const val ADDRESS = "address"
        }
    }
}