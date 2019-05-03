package com.example.myappcontacts.presentation.contacts.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.myappcontacts.R
import com.example.myappcontacts.data.dao.contacts.db.ContactsModel
import com.example.myappcontacts.presentation.contacts.presenter.ContactsPresenter
import kotlinx.android.synthetic.main.fragment_contact.*
import java.util.*

const val ARG_CONTACT_ID = "contactId"
const val REQUEST_CODE_PHOTO = 100

class ContactsFragment : MvpAppCompatFragment(), IContactsView {

    @InjectPresenter
    lateinit var contactsPresenter: ContactsPresenter
    lateinit var contactId: UUID

    private var editSaveButton: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments!!.containsKey(ARG_CONTACT_ID)) {
            contactId = UUID.fromString(arguments?.getString(ARG_CONTACT_ID))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        tel_number_edit_text.addTextChangedListener(PhoneNumberFormattingTextWatcher())
        contact_photo.setOnClickListener { contactsPresenter.onPhotoImageClicked() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_contacts_menu, menu)

        editSaveButton = menu.findItem(R.id.edit_save_changes)

        contactsPresenter.loadContact(contactId)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
            when (item.itemId) {
                R.id.edit_save_changes -> {
                    contactsPresenter.onEditSaveButtonClicked()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun updateUI(contactsModel: ContactsModel, edit: Boolean) {
        updateMenuUI(edit)
        visibilityEdit(edit)
        if (edit) {
            editContact(contactsModel)
        } else {
            showContact(contactsModel)
        }
        if (contactsModel.photoUri.isNotBlank()) {
            contact_photo.setImageURI(contactsModel.photoUri)
        }
    }

    private fun visibilityEdit(edit: Boolean) {
        val editViews = arrayOf(contacts_edit_texts, tel_number_edit_text, address_edit_text)
        val textViews = arrayOf(contacts_text_views, tel_number_text_view, address_text_view)
        val editViewVisibility = if (edit) View.VISIBLE else View.GONE
        val textViewVisibility = if (edit) View.GONE else View.VISIBLE
        editViews.forEach { it.visibility = editViewVisibility }
        textViews.forEach { it.visibility = textViewVisibility }
    }

    private fun showContact(contactsModel: ContactsModel) {
        with(contactsModel) {
            name_text_view.text =
                    if (firstName.isNotBlank() || lastName.isNotBlank()) {
                        getString(R.string.contact_name, firstName, lastName).trim()
                    } else {
                        getString(R.string.no_name)
                    }

            if (companyName.isNotBlank()) {
                company_text_view.visibility = View.VISIBLE
                company_text_view.text = contactsModel.companyName
            } else {
                company_text_view.visibility = View.GONE
            }

            if (telNumber.isNotBlank()) {
                tel_number_text_view.text = contactsModel.telNumber
                tel_number_text_view.setTextColor(Color.BLACK)
            } else {
                tel_number_text_view.text = getString(R.string.no_telephone)
                tel_number_text_view.setTextColor(Color.RED)
            }

            address_text_view.text =
                    if (address.isNotBlank()) address
                    else getString(R.string.no_address)

        }
    }

    private fun editContact(contactsModel: ContactsModel) {
        with(contactsModel) {
            first_name_edit_text.setText(firstName)
            last_name_edit_text.setText(lastName)
            company_name_edit_text.setText(companyName)
            tel_number_edit_text.setText(telNumber)
            address_edit_text.setText(address)
        }
    }

    private fun updateMenuUI(edit: Boolean) {
        if (edit) {
            editSaveButton?.setTitle(R.string.save_contact)
            editSaveButton?.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_save_changes)
        } else {
            editSaveButton?.setTitle(R.string.edit_contact)
            editSaveButton?.icon = ContextCompat.getDrawable(context!!, R.drawable.ic_edit_contact)
        }
    }

    override fun updateContact() {
        if (tel_number_edit_text.text.toString().isBlank()) {
            tel_number_edit_text.setHintTextColor(Color.RED)
        } else {
                contactsPresenter.updateContact(saveContactsModel())
            }
        }

    private fun saveContactsModel(): ContactsModel {
        with(ContactsModel(contactId)) {
            firstName = first_name_edit_text.text.toString()
            lastName = last_name_edit_text.text.toString()
            companyName = company_name_edit_text.text.toString()
            telNumber = tel_number_edit_text.text.toString()
            address = address_edit_text.text.toString()

            contactsPresenter.saveContactsModel(this)
            return this
        }
    }

    override fun updatePhoto() {
        startActivityForResult(Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                REQUEST_CODE_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PHOTO) {
            val uri = data?.data ?: return
            contact_photo.setImageURI(uri.toString())
            contactsPresenter.photoUriLoaded(uri.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        saveContactsModel()
    }
}
