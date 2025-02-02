package com.example.exito

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    private val repository = BookRepository()

    // LiveData for default data
    private val _readingLogEntries = MutableLiveData<List<ReadingLogEntry>>()
    val readingLogEntries: LiveData<List<ReadingLogEntry>> get() = _readingLogEntries

    // LiveData for search results
    private val _searchDocs = MutableLiveData<List<Doc>>()
    val searchDocs: LiveData<List<Doc>> get() = _searchDocs

    // LiveData for loading
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // LiveData for error messages
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    // A Job reference to manage cancelling previous search requests
    private var searchJob: Job? = null

    /**
     * Fetch default data (reading_log_entries)
     */
    fun fetchDefaultData() {
        _isLoading.value = true
        viewModelScope.launch {
            val response = repository.getBookData()
            _isLoading.value = false
            if (response?.reading_log_entries != null) {
                _readingLogEntries.value = response.reading_log_entries
                _errorMessage.value = null
            } else {
                _readingLogEntries.value = emptyList()
                _errorMessage.value = "Failed to load default data"
            }
        }
    }

    /**
     * Called every time the user changes the text in the SearchView
     * We'll handle debounce & cancel previous searches here.
     */
    fun onSearchQueryChanged(query: String) {
        // If query is blank, just load default data and clear search results
        if (query.isBlank()) {
            // Show default data again
            _searchDocs.value = emptyList()    // clear old search results
            _errorMessage.value = null         // clear any search error
            fetchDefaultData()                // reload default
            return
        }

        // If user typed something, cancel any ongoing search job
        searchJob?.cancel()

        // Start a new job for the new query
        searchJob = viewModelScope.launch {
            _isLoading.value = true
            // Debounce delay
            delay(500)

            // Clear old search results so user doesn't see stale data
            _searchDocs.value = emptyList()

            // Perform the actual search
            val response = repository.getSearchBooks(query)
            _isLoading.value = false

            if (response?.docs != null && response.docs.isNotEmpty()) {
                _searchDocs.value = response.docs
                _errorMessage.value = null
            } else {
                // If empty or error, show message
                _searchDocs.value = emptyList()
                _errorMessage.value = "No results found for \"$query\""
            }
        }
    }
}