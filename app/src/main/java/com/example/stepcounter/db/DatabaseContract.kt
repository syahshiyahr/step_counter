package com.example.stepcounter.db

import android.net.Uri
import android.provider.BaseColumns

internal class DatabaseContract {
    internal class NoteColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "history"
            const val _ID = "_id"
            const val TARGET = "target"
            const val DATE = "date"
            const val TIME = "time"
        }
    }
}