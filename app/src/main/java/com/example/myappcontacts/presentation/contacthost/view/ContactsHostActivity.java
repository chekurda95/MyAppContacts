package com.example.myappcontacts.presentation.contacthost.view;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.example.myappcontacts.R;

public class ContactsHostActivity extends MvpAppCompatActivity implements IContactsHostView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);
    }
}
