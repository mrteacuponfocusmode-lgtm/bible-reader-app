package com.example.biblereader

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.io.FileOutputStream

data class BookInfo(
    val bookOrder: Int,
    val name: String,
    val testament: String,
    val chapterCount: Int
)

data class VerseLine(val verse: Int, val text: String)

class DbHelper(private val context: Context) {

    private val dbName = "kjv_reading.db"
    private var database: SQLiteDatabase? = null

    fun open(): SQLiteDatabase {
        database?.let { return it }
        val dbFile = context.getDatabasePath(dbName)
        if (!dbFile.exists()) {
            dbFile.parentFile?.mkdirs()
            context.assets.open(dbName).use { input ->
                FileOutputStream(dbFile).use { output -> input.copyTo(output) }
            }
        }
        val db = SQLiteDatabase.openDatabase(dbFile.path, null, SQLiteDatabase.OPEN_READONLY)
        database = db
        return db
    }

    fun getBooks(): List<BookInfo> {
        val db = open()
        val results = mutableListOf<BookInfo>()
        val cursor = db.rawQuery(
            "SELECT book_order, book_name, testament, chapter_count FROM books ORDER BY book_order",
            null
        )
        cursor.use {
            while (it.moveToNext()) {
                results.add(BookInfo(it.getInt(0), it.getString(1), it.getString(2), it.getInt(3)))
            }
        }
        return results
    }

    fun getBook(bookOrder: Int): BookInfo? {
        val db = open()
        val cursor = db.rawQuery(
            "SELECT book_order, book_name, testament, chapter_count FROM books WHERE book_order = ?",
            arrayOf(bookOrder.toString())
        )
        cursor.use {
            if (it.moveToFirst()) {
                return BookInfo(it.getInt(0), it.getString(1), it.getString(2), it.getInt(3))
            }
        }
        return null
    }

    fun getChapterVerses(bookOrder: Int, chapter: Int): List<VerseLine> {
        val db = open()
        val results = mutableListOf<VerseLine>()
        val cursor = db.rawQuery(
            "SELECT verse, text FROM verses WHERE book_order = ? AND chapter = ? ORDER BY verse",
            arrayOf(bookOrder.toString(), chapter.toString())
        )
        cursor.use {
            while (it.moveToNext()) {
                results.add(VerseLine(it.getInt(0), it.getString(1)))
            }
        }
        return results
    }

    fun close() {
        database?.close()
        database = null
    }
}
