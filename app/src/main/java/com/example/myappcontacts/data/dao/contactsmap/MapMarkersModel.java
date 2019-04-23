package com.example.myappcontacts.data.dao.contactsmap;

import java.util.UUID;

public class MapMarkersModel {
    UUID mContactId;
    String mName;

    String mPhotoUri;
    String mAddress;

    public MapMarkersModel(UUID contactId, String name, String photoUri, String address) {
        mContactId = contactId;
        mName = name;
        mPhotoUri = photoUri;
        mAddress = address;
    }

    public UUID getContactsId() {
        return mContactId;
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }
}
