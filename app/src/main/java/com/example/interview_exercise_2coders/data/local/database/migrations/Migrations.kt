package com.example.interview_exercise_2coders.data.local.database.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS cached_movies (
                id INTEGER PRIMARY KEY NOT NULL,
                title TEXT NOT NULL,
                overview TEXT,
                posterUrl TEXT,
                backdropUrl TEXT,
                voteAverage REAL,
                releaseDate TEXT
            )
        """.trimIndent()
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS remote_keys (
                movieId INTEGER PRIMARY KEY NOT NULL,
                prevPage INTEGER,
                nextPage INTEGER
            )
            """.trimIndent()
        )
    }
}
