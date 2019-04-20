package com.example.myappcontacts.presentation.contactslist.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.myappcontacts.presentation.contactslist.adapters.ContactsListRecyclerAdapter;
import com.example.myappcontacts.presentation.contactslist.presenter.ContactsListPresenter;

import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ContactsListFragment extends MvpAppCompatFragment implements IContactsListView, ContactsListRecyclerAdapter.OnItemClickListener {

    private Unbinder mUnbinder;
    private ContactsListRecyclerAdapter mContactsListRecyclerAdapter;

    @InjectPresenter
    ContactsListPresenter mContactsListPresenter;

    @BindView(R.id.contacts_recycler_view)
    RecyclerView mContactsListRecyclerView;

    public static ContactsListFragment newInstance() {
        return new ContactsListFragment();
    }

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
                mContactsListPresenter.addContact();
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
        mContactsListRecyclerAdapter.updateList(contactsList);
    }

    @Override
    public void onItemClick(UUID contactId) {
        mContactsListPresenter.onContactItemClicked(contactId);
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }
}