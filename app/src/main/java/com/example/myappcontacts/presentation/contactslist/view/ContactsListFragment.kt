package com.example.myappcontacts.presentation.contactslist.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import androidx.navigation.fragment.NavHostFragment
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.myappcontacts.R
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.presentation.contacts.view.ContactsFragment
import com.example.myappcontacts.presentation.contactslist.adapters.ContactsListRecyclerAdapter
import com.example.myappcontacts.presentation.contactslist.adapters.SimpleItemTouchHelperCallback
import com.example.myappcontacts.presentation.contactslist.presenter.ContactsListPresenter
import kotlinx.android.synthetic.main.fragment_contacts_list.*
import java.util.*

class ContactsListFragment : MvpAppCompatFragment(), IContactsListView, ContactsListRecyclerAdapter.OnItemListener {

    companion object {
        val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    @InjectPresenter
    lateinit var contactsListPresenter: ContactsListPresenter

    lateinit var contactsListRecyclerAdapter: ContactsListRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_contacts_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        //RecyclerView settings
        contacts_recycler_view.layoutManager = LinearLayoutManager(activity)
        contactsListRecyclerAdapter = ContactsListRecyclerAdapter(this)
        contacts_recycler_view.adapter = contactsListRecyclerAdapter

        //ItemTouchHelper
        val touchHelper = ItemTouchHelper(SimpleItemTouchHelperCallback(contactsListRecyclerAdapter))
        touchHelper.attachToRecyclerView(contacts_recycler_view)

        contactsListPresenter.loadContactsList()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.fragment_contacts_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_new_contact -> {
                addContact(); true
            }
            R.id.show_on_map -> {
                contactsListPresenter.onShowContactsMapClicked(); true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun addContact() {
        contactsListPresenter.addContact()
    }

    override fun deleteContact(contactId: UUID) {
        contactsListPresenter.deleteContact(contactId)
    }

    override fun updateContactsList(contactsList: List<ContactsModel>) {
        contactsListRecyclerAdapter.updateList(contactsList)
    }

    override fun onItemClick(contactId: UUID) {
        contactsListPresenter.onContactItemClicked(contactId)
    }

    override fun onItemSwipe(contactId: UUID) {
        contactsListPresenter.onItemSwiped(contactId)
    }

    override fun openContact(contactId: UUID) {
        val args = Bundle()
        args.putString(ContactsFragment.ARG_CONTACT_ID, contactId.toString())
        NavHostFragment.findNavController(this).navigate(R.id.contactsFragment, args)
    }

    override fun openMap() {
        if (hasLocationPermission()) {
            NavHostFragment.findNavController(this).navigate(R.id.contactsMapFragment)
        } else {
            requestPermissions()
        }
    }

    private fun requestPermissions() {
        val LOCATION_PERMISSIONS = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        requestPermissions(LOCATION_PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
    }

    private fun hasLocationPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
        return result == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) openMap()
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
