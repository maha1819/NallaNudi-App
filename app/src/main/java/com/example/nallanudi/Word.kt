package com.example.nallanudi

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_table")
data class Word(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val english_term: String,
    val kannada_term: String,
    val definition: String,
    val simple_example: String, // New Column
    val subject: String,
    var is_favorite: Int = 0    // 0 = No, 1 = Yes (My List)
)