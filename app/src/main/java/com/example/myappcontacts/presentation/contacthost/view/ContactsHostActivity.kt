package com.example.myappcontacts.presentation.contacthost.view

import android.os.Bundle

import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.myappcontacts.R

class ContactsHostActivity : MvpAppCompatActivity(), IContactsHostView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
    }
}
