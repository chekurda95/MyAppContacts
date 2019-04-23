package com.example.myappcontacts.presentation.contactslist.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.myappcontacts.R;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;
import com.example.myappcontacts.presentation.contacts.view.ContactsFragment;
import com.example.myappcontacts.presentation.contactslist.adapters.ContactsListRecyclerAdapter;
import com.example.myappcontacts.presentation.contactslist.adapters.SimpleItemTouchHelperCallback;
import com.example.myappcontacts.presentation.contactslist.presenter.ContactsListPresenter;

import java.util.List;
import java.util.UUID;

import androidx.navigation.fragment.NavHostFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class ContactsListFragment extends MvpAppCompatFragment implements IContactsListView, ContactsListRecyclerAdapter.OnItemListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private Unbinder mUnbinder;
    private ContactsListRecyclerAdapter mContactsListRecyclerAdapter;

    @InjectPresenter
    ContactsListPresenter mContactsListPresenter;

    @BindView(R.id.contacts_recycler_view)
    RecyclerView mContactsListRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        setHasOptionsMenu(true);

        //RecyclerView settings
        mContactsListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mContactsListRecyclerAdapter = new ContactsListRecyclerAdapter(this);
        mContactsListRecyclerView.setAdapter(mContactsListRecyclerAdapter);

        //ItemTouchHelper
        ItemTouchHelper.Callback callback =
                new SimpleItemTouchHelperCallback(mContactsListRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(mContactsListRecyclerView);

        mContactsListPresenter.loadContactsList();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_contacts_list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.add_new_contact):
                addContact();
                return true;
            case (R.id.show_on_map):
                mContactsListPresenter.onShowContactsMapClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void addContact() {
        mContactsListPresenter.addContact();
    }

    @Override
    public void deleteContact(UUID contactId) {
        mContactsListPresenter.deleteContact(contactId);
    }

    @Override
    public void updateContactsList(List<ContactsModel> contactsList) {
        Log.i("MY_TAG", "updateContactsList сработал");
        mContactsListRecyclerAdapter.updateList(contactsList);
    }

    @Override
    public void onItemClick(UUID contactId) {
        mContactsListPresenter.onContactItemClicked(contactId);
    }

    @Override
    public void onItemSwipe(UUID contactId) {
        mContactsListPresenter.onItemSwiped(contactId);
    }

    @Override
    public void openContact(UUID contactId) {
        Bundle args = new Bundle();
        args.putString(ContactsFragment.ARG_CONTACT_ID, contactId.toString());
        NavHostFragment.findNavController(this).navigate(R.id.contactsFragment, args);
    }

    @Override
    public void openMap() {
        if (hasLocationPermission()) {
            NavHostFragment.findNavController(this).navigate(R.id.contactsMapFragment);
        } else {
            requestPermissions();
        }
    }

    private void requestPermissions() {
        String[] LOCATION_PERMISSIONS = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermissions(LOCATION_PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE);
    }

    private boolean hasLocationPermission() {
        int result = checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openMap();
                    break;
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}
