package com.example.nallanudi

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<Word>)

    @Query("SELECT * FROM words_table")
    fun getAllWords(): Flow<List<Word>>

    @Query("SELECT * FROM words_table WHERE subject = :sub")
    fun getWordsBySubject(sub: String): Flow<List<Word>>

    @Query("SELECT * FROM words_table WHERE is_favorite = 1")
    fun getFavorites(): Flow<List<Word>>

    @Query("UPDATE words_table SET is_favorite = :status WHERE id = :wordId")
    suspend fun updateFavorite(wordId: Int, status: Int)

    @Query("SELECT * FROM words_table WHERE english_term LIKE :query || '%'")
    fun searchWords(query: String): Flow<List<Word>>

    @Query("SELECT COUNT(*) FROM words_table")
    suspend fun getCount(): Int
}