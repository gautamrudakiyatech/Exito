package com.example.exito

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var bookViewModel: BookViewModel

    // Adapters
    private lateinit var bookAdapter: BookAdapter       // For default data
    private lateinit var searchAdapter: SearchAdapter   // For search data

    // UI
    private lateinit var recyclerViewDefault: RecyclerView
    private lateinit var recyclerViewSearch: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var textError: TextView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Handle edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize references
        progressBar = findViewById(R.id.progressBar)
        textError = findViewById(R.id.textError)
        searchView = findViewById(R.id.search)
        recyclerViewDefault = findViewById(R.id.recyclerViewDefault)
        recyclerViewSearch = findViewById(R.id.recyclerViewSearch)

        // Setup adapters
        bookAdapter = BookAdapter(emptyList())
        searchAdapter = SearchAdapter(emptyList())

        // Setup RecyclerViews
        recyclerViewDefault.layoutManager = LinearLayoutManager(this)
        recyclerViewDefault.adapter = bookAdapter

        recyclerViewSearch.layoutManager = LinearLayoutManager(this)
        recyclerViewSearch.adapter = searchAdapter

        // Initially show default data, hide the search list
        recyclerViewDefault.visibility = View.VISIBLE
        recyclerViewSearch.visibility = View.GONE

        // Initialize ViewModel
        bookViewModel = ViewModelProvider(this)[BookViewModel::class.java]

        // Observe default data
        bookViewModel.readingLogEntries.observe(this) { entries ->
            bookAdapter.setData(entries)
            // If we want, we can hide or show "no items" message here
        }

        // Observe search data
        bookViewModel.searchDocs.observe(this) { docs ->
            searchAdapter.setData(docs)
            // We only show search results if the user typed something,
            // but we can handle UI toggling below in the search listener
        }

        // Observe isLoading
        bookViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observe error messages
        bookViewModel.errorMessage.observe(this) { errorMsg ->
            if (!errorMsg.isNullOrEmpty()) {
                textError.visibility = View.VISIBLE
                textError.text = errorMsg
            } else {
                textError.visibility = View.GONE
            }
        }

        // Handle SearchView input
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                handleSearch(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                handleSearch(newText)
                return false
            }
        })

        // Finally, load default data
        bookViewModel.fetchDefaultData()
    }

    /**
     * Toggle the RecyclerView visibility based on query,
     * and call the ViewModel's onSearchQueryChanged(...) for debouncing + search.
     */
    private fun handleSearch(query: String) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) {
            // If user cleared search, show default data
            recyclerViewDefault.visibility = View.VISIBLE
            recyclerViewSearch.visibility = View.GONE
        } else {
            // If user typed something, show search results
            recyclerViewDefault.visibility = View.GONE
            recyclerViewSearch.visibility = View.VISIBLE
        }

        // Pass the query to the ViewModel for debounce & search logic
        bookViewModel.onSearchQueryChanged(trimmed)
    }
}


