package com.example.myappcontacts.di.application;

import android.content.Context;

import com.example.myappcontacts.data.providers.database.IDataBaseProvider;
import com.example.myappcontacts.data.providers.database.MyStorIOSQLite.IMyStorIOSQLite;
import com.example.myappcontacts.data.providers.database.MyStorIOSQLite.MyStorIOSQLite;
import com.example.myappcontacts.data.providers.database.SQLiteDbProvider;
import com.example.myappcontacts.data.providers.database.databaseutils.ContactsBaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Context mContext;

    public AppModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    IMyStorIOSQLite provideMyStorIOSQLite(Context context) {
        return new MyStorIOSQLite(new ContactsBaseHelper(context));
    }

    @Provides
    @Singleton
    IDataBaseProvider provideDateBaseProvider(IMyStorIOSQLite myStorIOSQLite) {
        return new SQLiteDbProvider(myStorIOSQLite);
    }

}
