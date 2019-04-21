package com.example.myappcontacts.presentation.contactslist.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myappcontacts.R;
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsListRecyclerAdapter extends RecyclerView.Adapter<ContactsListRecyclerAdapter.ContactsHolder> implements ItemTouchHelperAdapter {
    private List<ContactsModel> mContactsList;
    private OnItemListener mOnItemListener;

    public ContactsListRecyclerAdapter(OnItemListener onItemListener) {
        mContactsList = new ArrayList<>();
        mOnItemListener = onItemListener;
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

    public void updateList(List<ContactsModel> contactsList) {
        mContactsList.clear();
        mContactsList.addAll(contactsList);
        notifyDataSetChanged();
        Log.i("MY_TAG", "notifyDataSetChanged() адаптера сработал");
    }

    @Override
    public void onItemDismiss(int position) {
        mOnItemListener.onItemSwipe(mContactsList.get(position).getContactId());
        mContactsList.remove(position);
        notifyDataSetChanged();
    }

    class ContactsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private UUID mContactId;

        @BindView(R.id.contact_name)
        TextView mContactNameTextView;
        @BindView(R.id.contact_have_telephone)
        ImageView mContactTelephone;

        public ContactsHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bindContact(ContactsModel contactsModel) {
            mContactId = contactsModel.getContactId();
            String firstName = contactsModel.getFirstName();
            String lastName = contactsModel.getLastName();
            if (firstName.equals("") && lastName.equals("")) {
                mContactNameTextView.setText(R.string.no_name);
            } else {
                String fullName = lastName + " " + firstName;
                mContactNameTextView.setText(fullName.trim());
            }
            if (contactsModel.getTelNumber().equals("")) {
                mContactTelephone.setVisibility(ImageView.GONE);
            } else {
                mContactTelephone.setVisibility(ImageView.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            mOnItemListener.onItemClick(mContactId);
        }
    }

    public interface OnItemListener {
        void onItemClick(UUID contactId);

        void onItemSwipe(UUID contactId);
    }
}
