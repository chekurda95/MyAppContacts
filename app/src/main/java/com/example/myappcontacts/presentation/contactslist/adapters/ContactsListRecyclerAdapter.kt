package com.example.myappcontacts.presentation.contactslist.adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.myappcontacts.R
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import kotlinx.android.synthetic.main.item_contacts_list.view.*
import java.util.*

internal const val TAG = "TEST"

class ContactsListRecyclerAdapter(private val contactsList: MutableList<ContactsModel> = ArrayList())
    : RecyclerView.Adapter<ContactsListRecyclerAdapter.ContactsHolder>(),
        ItemTouchHelperAdapter {

    var onItemClick: ((contactId: UUID) -> Unit)? = null
    var onItemSwipe: ((contactId: UUID) -> Unit)? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ContactsListRecyclerAdapter.ContactsHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_contacts_list, viewGroup, false)
        return ContactsHolder(view, onItemClick!!)
    }

    override fun onBindViewHolder(contactsHolder: ContactsHolder, i: Int) {
        contactsHolder.bindContact(contactsList[i])
    }

    override fun getItemCount() = contactsList.size

    fun updateList(contactsList: List<ContactsModel>) {
        this.contactsList.clear()
        this.contactsList.addAll(contactsList)
        notifyDataSetChanged()
        Log.i(TAG, " notifyDataSetChanged() адаптера сработал")
    }

    override fun onItemDismiss(position: Int) {
        val contactId = contactsList[position].contactId
        onItemSwipe!!(contactId)
        contactsList.removeAt(position)
        notifyDataSetChanged()
    }


    inner class ContactsHolder(view: View, private val onItemClick: (contactId: UUID) -> Unit)
        : RecyclerView.ViewHolder(view){

        internal fun bindContact(contactsModel: ContactsModel) {
            with(contactsModel){
                itemView.setOnClickListener { onItemClick(contactId) }

                if (photoUri.isBlank()) {
                    itemView.contact_photo.setActualImageResource(R.drawable.ic_contact_photo)
                } else {
                    itemView.contact_photo.setImageURI(photoUri)
                }

                if (firstName.isBlank() && lastName.isBlank()) {
                    itemView.contact_name.setText(R.string.no_name)
                } else {
                    val fullName = "$lastName $firstName".trim()
                    itemView.contact_name.text = fullName
                }

                itemView.contact_have_telephone.visibility =
                if (contactsModel.telNumber.isBlank()) ImageView.GONE
                else ImageView.VISIBLE
            }
        }
    }
}