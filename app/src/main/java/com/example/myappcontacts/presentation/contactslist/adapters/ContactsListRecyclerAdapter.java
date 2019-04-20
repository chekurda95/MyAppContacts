package com.example.myappcontacts.presentation.contactslist.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myappcontacts.R;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsListRecyclerAdapter extends RecyclerView.Adapter<ContactsListRecyclerAdapter.ContactsHolder> {
    private List<ContactsModel> mContactsList;
    private OnItemClickListener mOnItemClickListener;


    public ContactsListRecyclerAdapter(OnItemClickListener onItemClickListener){
        mContactsList = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ContactsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_contacts_list, viewGroup, false);
        return new ContactsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsHolder contactsHolder, int i) {
        contactsHolder.bindContact(mContactsList.get(i));
    }

    @Override
    public int getItemCount() {
        return mContactsList.size();
    }

    public void updateList(List<ContactsModel> contactsList){
        mContactsList.clear();
        mContactsList.addAll(contactsList);
        notifyDataSetChanged();
    }

    class ContactsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private UUID mContactId;

        @BindView(R.id.contact_name)
        TextView mContactNameTextView;

        public ContactsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bindContact(ContactsModel contactsModel){
            mContactId = contactsModel.getContactId();
            String name = contactsModel.getLastName() + " " + contactsModel.getFirstName();
            mContactNameTextView.setText(name);
        }

        @Override
        public void onClick(View v) {
            mOnItemClickListener.onItemClick(mContactId);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(UUID contactId);
    }
}
