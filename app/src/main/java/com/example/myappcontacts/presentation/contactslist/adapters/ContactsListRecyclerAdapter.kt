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

class ContactsListRecyclerAdapter(private val onItemListener: OnItemListener, private val contactsList: MutableList<ContactsModel> = ArrayList())
    : RecyclerView.Adapter<ContactsListRecyclerAdapter.ContactsHolder>(), ItemTouchHelperAdapter {

    companion object {
        val TAG = "TEST"
    }

    interface OnItemListener {
        fun onItemClick(contactId: UUID)

        fun onItemSwipe(contactId: UUID)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ContactsListRecyclerAdapter.ContactsHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_contacts_list, viewGroup, false)
        return ContactsHolder(view)
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
        onItemListener.onItemSwipe(contactId)
        contactsList.removeAt(position)
        notifyDataSetChanged()
    }


    inner class ContactsHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        init {
            view.setOnClickListener(this)
        }

        lateinit var contactId: UUID

        internal fun bindContact(contactsModel: ContactsModel) {
            contactId = contactsModel.contactId
            val firstName = contactsModel.firstName
            val lastName = contactsModel.lastName
            val photoStringUri = contactsModel.photoUri

            if (photoStringUri != "") {
                itemView.contact_photo.setImageURI(photoStringUri)
            } else {
                itemView.contact_photo.setActualImageResource(R.drawable.ic_contact_photo)
            }

            if (firstName == "" && lastName == "") {
                itemView.contact_name.setText(R.string.no_name)
            } else {
                val fullName = """$lastName $firstName""".trim()
                itemView.contact_name.text = fullName
            }

            if (contactsModel.telNumber == "") itemView.contact_have_telephone.visibility = ImageView.GONE
            else itemView.contact_have_telephone.visibility = ImageView.VISIBLE
        }

        override fun onClick(v: View?) {
            onItemListener.onItemClick(contactId)
        }
    }
}