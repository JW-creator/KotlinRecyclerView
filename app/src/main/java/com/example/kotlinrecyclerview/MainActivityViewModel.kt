package com.example.kotlinrecyclerview

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "MainActivityViewModel"
class MainActivityViewModel : ViewModel() {
    private val contactsLiveData: MutableLiveData<MutableList<Contact>>
    private val isRefreshingLiveData: MutableLiveData<Boolean>
    init {
        Log.i(TAG,"init")
        contactsLiveData = MutableLiveData()
        contactsLiveData.value = createContacts()
        isRefreshingLiveData = MutableLiveData()
        isRefreshingLiveData.value = false
    }

    fun getContacts(): LiveData<MutableList<Contact>> {
        return contactsLiveData
    }

    fun getIsRefreshing(): LiveData<Boolean> {
        return isRefreshingLiveData
    }

    fun fetchNewContact() {
        Log.i(TAG, "fetchNewContact")
        isRefreshingLiveData.value = true
        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val contacts = contactsLiveData.value
            contacts?.add(0, Contact("Julius Caeser", 52))
            contactsLiveData.value = contacts
            isRefreshingLiveData.value = false
            }, 500)
    }

    private fun createContacts(): MutableList<Contact> {
        Log.i(TAG, "createContacts")
        val contacts = mutableListOf<Contact>()
        for (i in 1..150) contacts.add(Contact("Person #$i", i))
        return contacts
    }
}