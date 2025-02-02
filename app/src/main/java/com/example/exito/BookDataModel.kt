package com.example.exito


// This class models the whole JSON response.
data class ReadingListResponse(
    val reading_log_entries: List<ReadingLogEntry>
)

// Each entry in the reading log.
data class ReadingLogEntry(
    val work: Work,
    val logged_edition: String,
    val logged_date: String
)

// Models the work (book) details.
data class Work(
    val title: String,
    val key: String,
    val author_keys: List<String>,
    val author_names: List<String>,
    val first_publish_year: Int,
    val lending_edition_s: String?,  // May be null
    val edition_key: List<String>,
    val cover_id: Int?,
    val cover_edition_key: String?
)


// Top-level response for search data
data class SearchResponse(
    val docs: List<Doc>
)

// Represents each search result
data class Doc(
    val author_key: List<String>,
    val author_name: List<String>,
    val cover_edition_key: String,
    val cover_i: Int,
    val edition_count: Int,
    val first_publish_year: Int,
    val has_fulltext: Boolean,
    val key: String,
    val language: List<String>,
    val public_scan_b: Boolean,
    val title: String
)
