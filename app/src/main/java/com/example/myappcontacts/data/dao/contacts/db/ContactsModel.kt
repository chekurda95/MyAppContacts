package com.example.myappcontacts.data.dao.contacts.db

import java.util.*

data class ContactsModel(val contactId: UUID = UUID.randomUUID(),
                         var firstName: String = "",
                         var lastName: String = "",
                         var companyName: String = "",
                         var telNumber: String = "",
                         var photoUri: String = "",
                         var address: String = "")