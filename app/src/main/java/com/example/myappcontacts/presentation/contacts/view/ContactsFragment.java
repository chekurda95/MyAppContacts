package com.example.myappcontacts.presentation.contacts.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.myappcontacts.R;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.presentation.contacts.presenter.ContactsPresenter;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ContactsFragment extends MvpAppCompatFragment implements IContactsView {
    private static final String ARG_CONTACT_ID = "contactId";

    private UUID mContactId;
    private Unbinder mUnbinder;

    @InjectPresenter
    ContactsPresenter mContactsPresenter;

    @BindView(R.id.contact_photo)
    SimpleDraweeView mPhotoImageView;

    @BindView(R.id.contacts_text_views)
    LinearLayout mTextViews;
    @BindView(R.id.name_text_view)
    TextView mNameTextView;
    @BindView(R.id.company_text_view)
    TextView mCompanyTextView;
    @BindView(R.id.tel_number_text_view)
    TextView mTelNumberTextView;

    @BindView(R.id.contacts_edit_texts)
    LinearLayout mEditTexts;
    @BindView(R.id.first_name_edit_text)
    EditText mFirstNameEditText;
    @BindView(R.id.last_name_edit_text)
    EditText mLastNameEditText;
    @BindView(R.id.company_name_edit_text)
    EditText mCompanyNameEditText;
    @BindView(R.id.tel_number_edit_text)
    EditText mTelNumberEditText;

    MenuItem mEdit_Save_Button;


    public ContactsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContactId = UUID.fromString(getArguments().getString(ARG_CONTACT_ID));
        }

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        mUnbinder = ButterKnife.bind(this, view);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contacts_menu, menu);
        mEdit_Save_Button = menu.findItem(R.id.edit_save_changes);
        mContactsPresenter.loadContact(mContactId);
        Log.i("MY_TAG2", mEdit_Save_Button == null ? "null" : "ne null");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit_save_changes:
                mContactsPresenter.onEditSaveButtonClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void showContact(ContactsModel contactsModel) {
        String firstName = contactsModel.getFirstName();
        String lastName = contactsModel.getLastName();
        if (!firstName.equals("") || !lastName.equals("")) {
            mNameTextView.setText(getString(R.string.contact_name, contactsModel.getLastName(), contactsModel.getFirstName()));
        } else {
            mNameTextView.setText(getString(R.string.no_name));
        }
        String companyName = contactsModel.getCompanyName();
        if (companyName.equals("")) {
            mCompanyTextView.setVisibility(View.GONE);
        } else {
            mCompanyTextView.setVisibility(View.VISIBLE);
            mCompanyTextView.setText(contactsModel.getCompanyName());
        }
        String telNumber = contactsModel.getTelNumber();
        if (!telNumber.equals("")) {
            mTelNumberTextView.setText(contactsModel.getTelNumber());
            mTelNumberTextView.setTextColor(Color.BLACK);
        } else {
            mTelNumberTextView.setText(getString(R.string.no_telephone));
            mTelNumberTextView.setTextColor(Color.RED);
        }
    }

    private void editContact(ContactsModel contactsModel) {
        mFirstNameEditText.setText(contactsModel.getFirstName());
        mLastNameEditText.setText(contactsModel.getLastName());
        mCompanyNameEditText.setText(contactsModel.getCompanyName());
        mTelNumberEditText.setText(contactsModel.getTelNumber());
    }

    private void updateMenuUI(boolean edit){
        if(mEdit_Save_Button!=null) {
            if (!edit) {
                mEdit_Save_Button.setTitle(R.string.edit_contact);
                mEdit_Save_Button.setIcon(getResources().getDrawable(R.drawable.ic_edit_contact));
            } else {
                mEdit_Save_Button.setIcon(getResources().getDrawable(R.drawable.ic_save_changes));
                mEdit_Save_Button.setTitle(R.string.save_contact);
            }
        }
    }

    public void updateUI(ContactsModel contactsModel, boolean edit) {
        updateMenuUI(edit);
        Log.i("MY_TAG2", "updateUI сработал");
        if (edit) {
            mTextViews.setVisibility(View.GONE);
            mEditTexts.setVisibility(View.VISIBLE);
            mTelNumberTextView.setVisibility(View.GONE);
            mTelNumberEditText.setVisibility(View.VISIBLE);
            editContact(contactsModel);
        } else {
            mTextViews.setVisibility(View.VISIBLE);
            mEditTexts.setVisibility(View.GONE);
            mTelNumberTextView.setVisibility(View.VISIBLE);
            mTelNumberEditText.setVisibility(View.GONE);
            showContact(contactsModel);
        }
    }

    @Override
    public void updateContact() {
        if(mTelNumberEditText.getText().toString().equals("")){
            mTelNumberEditText.setHintTextColor(Color.RED);
        } else {
            ContactsModel contactsModel = new ContactsModel(mContactId);
            contactsModel.setFirstName(mFirstNameEditText.getText().toString());
            contactsModel.setLastName(mLastNameEditText.getText().toString());
            contactsModel.setCompanyName(mCompanyNameEditText.getText().toString());
            contactsModel.setTelNumber(mTelNumberEditText.getText().toString());
            mContactsPresenter.updateContact(contactsModel);
        }
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
