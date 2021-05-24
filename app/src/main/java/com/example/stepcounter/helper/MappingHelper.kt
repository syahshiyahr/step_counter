package com.example.stepcounter.helper

import android.database.Cursor
import com.example.stepcounter.db.DatabaseContract
import com.example.stepcounter.entity.History

object MappingHelper {
    fun mapCursorToArrayList(historyCursor: Cursor?): ArrayList<History> {
        val historyList = ArrayList<History>()
        historyCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns._ID))
                val target = getInt(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TARGET))
                val date = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.DATE))
                val time = getString(getColumnIndexOrThrow(DatabaseContract.NoteColumns.TIME))
                historyList.add(History(id, target, date, time))
            }
        }
        return historyList
    }
}