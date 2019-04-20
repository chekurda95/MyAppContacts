package com.example.myappcontacts.presentation.contacts.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.myappcontacts.R;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.presentation.contacts.presenter.ContactsPresenter;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ContactsFragment extends MvpAppCompatFragment implements IContactsView {
    private static final String ARG_CONTACT_ID = "contactId";

    private UUID mContactId;
    private ContactsModel mContactsModel;
    private Unbinder mUnbinder;

    @InjectPresenter
    ContactsPresenter mContactsPresenter;

    @BindView(R.id.contact_first_name)
    EditText mFirstNameEditText;
    @BindView(R.id.contact_last_name)
    EditText mLastNameEditText;
    @BindView(R.id.contact_company_name)
    EditText mCompanyNameEditText;
    @BindView(R.id.contact_telephone_number)
    EditText mTelNumberEditText;


    public ContactsFragment() {
    }

    public static Fragment newInstance() {
        ContactsFragment fragment = new ContactsFragment();
        return fragment;
    }

    public static Fragment newInstance(UUID contactId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_CONTACT_ID, contactId);

        ContactsFragment fragment = new ContactsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContactId = (UUID) getArguments().getSerializable(ARG_CONTACT_ID);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        mContactsPresenter.loadContact(mContactId);

        mFirstNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mContactsModel != null) {
                    mContactsModel.setFirstName(s.toString());
                    updateContact();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mLastNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mContactsModel != null) {
                    mContactsModel.setLastName(s.toString());
                    updateContact();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCompanyNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mContactsModel != null) {
                    mContactsModel.setCompanyName(s.toString());
                    updateContact();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mTelNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mContactsModel != null) {
                    mContactsModel.setTelNumber(s.toString());
                    updateContact();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contacts_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showContact(ContactsModel contactsModel) {
        mContactsModel = contactsModel;
        mFirstNameEditText.setText(contactsModel.getFirstName());
        mLastNameEditText.setText(contactsModel.getLastName());
        mCompanyNameEditText.setText(contactsModel.getCompanyName());
        mTelNumberEditText.setText(contactsModel.getTelNumber());
    }

    @Override
    public void updateContact() {
        mContactsPresenter.updateContact(mContactsModel);
    }

    @Override
    public void onPause() {
        updateContact();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
