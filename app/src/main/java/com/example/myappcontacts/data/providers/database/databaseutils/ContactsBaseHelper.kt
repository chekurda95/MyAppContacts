package com.example.myappcontacts.data.providers.database.databaseutils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.myappcontacts.data.providers.database.databaseutils.ContactsDBSchema.*


class ContactsBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    companion object {
        val DATABASE_NAME = "contactsBase.db"
        val VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table " + ContactsTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ContactsTable.Cols.UUID + ", " +
                ContactsTable.Cols.FIRST_NAME + ", " +
                ContactsTable.Cols.LAST_NAME + ", " +
                ContactsTable.Cols.COMPANY_NAME + ", " +
                ContactsTable.Cols.TEL_NUMBER + ", " +
                ContactsTable.Cols.PHOTO_URI + ", " +
                ContactsTable.Cols.ADDRESS +
                ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

}