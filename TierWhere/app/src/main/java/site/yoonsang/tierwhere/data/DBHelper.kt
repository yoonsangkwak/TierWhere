package site.yoonsang.tierwhere.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

const val DB_NAME = "TierWhere"
const val DB_VERSION = 1
const val TABLE_NAME = "HistorySummoner"
const val COL_ID = "id"
const val COL_NAME = "name"
const val COL_TIER = "tier"
const val COL_RANK = "rank"
const val COL_PROFILE_ICON = "img"

class DBHelper(
    context: Context,
    name: String,
    version: Int
): SQLiteOpenHelper(context, name, null, version) {

    override fun onCreate(db: SQLiteDatabase?) {
        val create = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
                COL_ID + " ID, " +
                COL_NAME + " NAME, " +
                COL_TIER + " TIER, " +
                COL_RANK + " RANK, " +
                COL_PROFILE_ICON + " img " + ")"
        db?.execSQL(create)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun insertHistorySummoner(historySummoner: HistorySummoner) {
        val wd = writableDatabase
        val values = ContentValues()
        values.put(COL_ID, historySummoner.summonerId)
        values.put(COL_NAME, historySummoner.name)
        values.put(COL_TIER, historySummoner.tier)
        values.put(COL_RANK, historySummoner.rank)
        values.put(COL_PROFILE_ICON, historySummoner.profileIcon)
        wd.insert(TABLE_NAME, null, values)
        wd.close()
    }

    fun selectHistorySummoner(): ArrayList<HistorySummoner> {
        val list = arrayListOf<HistorySummoner>()
        val select = "SELECT * FROM $TABLE_NAME"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, null)

        while (cursor.moveToNext()) {
            val summonerId = cursor.getString(cursor.getColumnIndex(COL_ID))
            val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            val tier = cursor.getString(cursor.getColumnIndex(COL_TIER))
            val rank = cursor.getString(cursor.getColumnIndex(COL_RANK))
            val profileIcon = cursor.getInt(cursor.getColumnIndex(COL_PROFILE_ICON))
            val historySummoner = HistorySummoner(summonerId, name, tier, rank, profileIcon)
            list.add(historySummoner)
        }
        cursor.close()
        rd.close()
        return list
    }

    fun updateHistorySummoner(historySummoner: HistorySummoner) {
        val wd = writableDatabase
        val values = ContentValues()
        values.put(COL_ID, historySummoner.summonerId)
        values.put(COL_NAME, historySummoner.name)
        values.put(COL_TIER, historySummoner.tier)
        values.put(COL_RANK, historySummoner.rank)
        values.put(COL_PROFILE_ICON, historySummoner.profileIcon)
        wd.update(TABLE_NAME, values, "$COL_ID = ?", arrayOf(historySummoner.summonerId))
        wd.close()
    }

    fun deleteHistorySummoner(historySummoner: HistorySummoner) {
        val wd = writableDatabase
        wd.delete(TABLE_NAME, "$COL_ID = ?", arrayOf(historySummoner.summonerId))
        wd.close()
    }

    fun searchHistorySummoner(id: String): HistorySummoner? {
        val select = "SELECT * FROM $TABLE_NAME"
        val rd = readableDatabase
        val cursor = rd.rawQuery(select, null)
        var historySummoner: HistorySummoner? = null

        while (cursor.moveToNext()) {
            val summonerId = cursor.getString(cursor.getColumnIndex(COL_ID))
            val name = cursor.getString(cursor.getColumnIndex(COL_NAME))
            val tier = cursor.getString(cursor.getColumnIndex(COL_TIER))
            val rank = cursor.getString(cursor.getColumnIndex(COL_RANK))
            val profileIcon = cursor.getInt(cursor.getColumnIndex(COL_PROFILE_ICON))
            if (summonerId == id) {
                historySummoner = HistorySummoner(summonerId, name, tier, rank, profileIcon)
                break
            }
        }
        cursor.close()
        rd.close()
        return historySummoner
    }
}