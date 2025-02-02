package com.example.exito

class BookRepository {

    suspend fun getBookData(): ReadingListResponse? {
        return try {
            val response = RetrofitInstance.api.getBook()
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getSearchBooks(query: String): SearchResponse? {
        return try {
            val response = RetrofitInstance.api.searchBook(query)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }


}