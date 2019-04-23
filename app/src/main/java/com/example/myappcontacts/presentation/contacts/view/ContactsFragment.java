package com.example.myappcontacts.presentation.contacts.view;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    public static final String ARG_CONTACT_ID = "contactId";

    private UUID mContactId;
    private Unbinder mUnbinder;

    @InjectPresenter
    ContactsPresenter mContactsPresenter;

    @BindView(R.id.contact_photo)
    SimpleDraweeView mPhotoImage;

    @BindView(R.id.contacts_text_views)
    LinearLayout mTextViews;
    @BindView(R.id.name_text_view)
    TextView mNameTextView;
    @BindView(R.id.company_text_view)
    TextView mCompanyTextView;
    @BindView(R.id.tel_number_text_view)
    TextView mTelNumberTextView;
    @BindView(R.id.address_text_view)
    TextView mAddressTextView;

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
    @BindView(R.id.address_edit_text)
    TextView mAddressEditText;

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
        mTelNumberEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mPhotoImage.setOnClickListener(v -> mContactsPresenter.onPhotoImageClicked());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contacts_menu, menu);
        mEdit_Save_Button = menu.findItem(R.id.edit_save_changes);
        mContactsPresenter.loadContact(mContactId);
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

    @Override
    public void updateUI(ContactsModel contactsModel, boolean edit) {
        Log.i("MY_TAG2", "updateUI сработал");

        updateMenuUI(edit);
        visibilityEdit(edit);
        if (edit) {
            editContact(contactsModel);
        } else {
            showContact(contactsModel);
        }
        if (contactsModel.getPhotoUri() != null) {
            mPhotoImage.setImageURI(Uri.parse(contactsModel.getPhotoUri()));
        }
    }

    private void visibilityEdit(boolean edit) {
        View[] editViews = {mEditTexts, mTelNumberEditText, mAddressEditText};
        View[] textViews = {mTextViews, mTelNumberTextView, mAddressTextView};
        int editViewVisibility = edit ? View.VISIBLE : View.GONE;
        int textViewVisibility = edit ? View.GONE : View.VISIBLE;
        for (View editView : editViews) {
            editView.setVisibility(editViewVisibility);
        }
        for (View textView :
                textViews) {
            textView.setVisibility(textViewVisibility);
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
        String address = contactsModel.getAddress();
        if (address.equals("")) {
            mAddressTextView.setText(getString(R.string.no_address));
        } else {
            mAddressTextView.setText(address);
        }
    }

    private void editContact(ContactsModel contactsModel) {
        mFirstNameEditText.setText(contactsModel.getFirstName());
        mLastNameEditText.setText(contactsModel.getLastName());
        mCompanyNameEditText.setText(contactsModel.getCompanyName());
        mTelNumberEditText.setText(contactsModel.getTelNumber());
        mAddressEditText.setText(contactsModel.getAddress());
    }

    private void updateMenuUI(boolean edit) {
        if (mEdit_Save_Button != null) {
            if (!edit) {
                mEdit_Save_Button.setTitle(R.string.edit_contact);
                mEdit_Save_Button.setIcon(getResources().getDrawable(R.drawable.ic_edit_contact));
            } else {
                mEdit_Save_Button.setIcon(getResources().getDrawable(R.drawable.ic_save_changes));
                mEdit_Save_Button.setTitle(R.string.save_contact);
            }
        }
    }

    @Override
    public void updateContact() {
        if (mTelNumberEditText.getText().toString().equals("")) {
            mTelNumberEditText.setHintTextColor(Color.RED);
        } else {
            ContactsModel contactsModel = new ContactsModel(mContactId);
            contactsModel.setFirstName(mFirstNameEditText.getText().toString());
            contactsModel.setLastName(mLastNameEditText.getText().toString());
            contactsModel.setCompanyName(mCompanyNameEditText.getText().toString());
            contactsModel.setTelNumber(mTelNumberEditText.getText().toString());
            contactsModel.setAddress(mAddressEditText.getText().toString());
            mContactsPresenter.updateContact(contactsModel);
        }
    }

    @Override
    public void updatePhoto() {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            Uri uri = data.getData();
            mPhotoImage.setImageURI(uri);
            mContactsPresenter.photoUriLoaded(uri.toString());
        }
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
