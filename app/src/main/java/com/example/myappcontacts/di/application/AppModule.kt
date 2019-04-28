package com.example.myappcontacts.di.application

import android.content.Context
import com.example.myappcontacts.data.providers.database.IDataBaseProvider
import com.example.myappcontacts.data.providers.database.MyStorIOSQLite.IMyStorIOSQLite
import com.example.myappcontacts.data.providers.database.MyStorIOSQLite.MyStorIOSQLite
import com.example.myappcontacts.data.providers.database.SQLiteDbProvider
import com.example.myappcontacts.data.providers.database.databaseutils.ContactsBaseHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val context: Context) {

    @Provides
    @Singleton
    fun provideContext() = context

    @Provides
    @Singleton
    fun provideMyStorIOSQLite(context: Context): IMyStorIOSQLite {
        return MyStorIOSQLite(ContactsBaseHelper(context))
    }

    @Provides
    @Singleton
    fun provideDateBaseProvider(myStorIOSQLite: IMyStorIOSQLite): IDataBaseProvider {
        return SQLiteDbProvider(myStorIOSQLite)
    }
}