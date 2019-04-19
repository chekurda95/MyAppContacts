package com.example.myappcontacts.data.providers.database.databaseutils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.myappcontacts.data.providers.database.databaseutils.ContactsDbSchema.*;

public class ContactsBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "contactsBase.db";
    private static final int VERSION = 1;


    public ContactsBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + ContactsTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                ContactsTable.Cols.UUID + ", " +
                ContactsTable.Cols.FIRST_NAME + ", " +
                ContactsTable.Cols.LAST_NAME + ", " +
                ContactsTable.Cols.COMPANY_NAME + ", " +
                ContactsTable.Cols.TEL_NUMBER +
                ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
