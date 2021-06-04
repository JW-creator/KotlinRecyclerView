package com.example.kotlinrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "onCreate")

        val contacts = mutableListOf<Contact>()
        val contactAdapter = ContactAdapter(this, contacts)
        rvContacts.adapter = contactAdapter
        rvContacts.layoutManager = LinearLayoutManager(this)

        val model: MainActivityViewModel by viewModels()
        model.getContacts().observe(this, Observer{ contactsSnapshot ->
            Log.i(TAG, "receive contacts from view model")
            contacts.clear()
            contacts.addAll(contactsSnapshot)
            contactAdapter.notifyDataSetChanged()
        })

        model.getIsRefreshing().observe(this, Observer { isRefreshing ->
            swipeContainer.isRefreshing = isRefreshing
        })

        swipeContainer.setOnRefreshListener {
            model.fetchNewContact()
        }
    }

}