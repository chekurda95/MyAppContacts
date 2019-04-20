package com.example.myappcontacts.presentation.contacthost.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.myappcontacts.R;
import com.example.myappcontacts.presentation.contacts.view.ContactsFragment;
import com.example.myappcontacts.presentation.contactslist.view.ContactsListFragment;

import java.util.UUID;

public class ContactsHostActivity extends MvpAppCompatActivity implements IContactsHostView {

    private static FragmentManager sFragmentManager; ////////////////////////test

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        sFragmentManager = getSupportFragmentManager();////////////////////test

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if(fragment == null){
            fragment = ContactsListFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }
    }

    public static void goToContact(UUID contactId){
        sFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ContactsFragment.newInstance(contactId))
                .commit();
    }
}
