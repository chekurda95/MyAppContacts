package com.example.myappcontacts.data.dao.contacts.db;

import java.util.UUID;

//модель контакта
public class ContactsModel {
    private UUID mContactId;
    private String mFirstName;
    private String mLastName;
    private String mCompanyName;
    private String mTelNumber;
    private String mPhotoUri;
    private String mAddress;


    public ContactsModel() {
        mContactId = UUID.randomUUID();
    }

    public ContactsModel(UUID contactId) {
        mContactId = contactId;
    }

    public UUID getContactId() {
        return mContactId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getTelNumber() {
        return mTelNumber;
    }

    public void setTelNumber(String telNumber) {
        mTelNumber = telNumber;
    }

    public String getPhotoUri() {
        return mPhotoUri;
    }

    public void setPhotoUri(String photoUri) {
        mPhotoUri = photoUri;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
