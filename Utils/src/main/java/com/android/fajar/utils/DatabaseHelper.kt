package com.android.fajar.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import java.io.Serializable
import java.sql.SQLException
import java.util.HashMap

class DatabaseHelper(var1: Context?) : OrmLiteSqliteOpenHelper(
    var1,
    Helper.getConfig().databaseName,
    null as SQLiteDatabase.CursorFactory?,
    Helper.getConfig().databaseVersion
) {
    override fun onCreate(var1: SQLiteDatabase, var2: ConnectionSource) {
        val var3 = Helper.getConfig().listModelForDatabase
        val var4 = var3?.size
        for (var5 in 0 until var4!!) {
            val var6 = var3.get(var5)
            try {
                TableUtils.createTableIfNotExists(var2, var6)
            } catch (var8: SQLException) {
                var8.printStackTrace()
            }
        }
    }

    override fun onUpgrade(var1: SQLiteDatabase, var2: ConnectionSource, var3: Int, var4: Int) {
        Helper.getConfig().onUpgradeDatabase(var1, var2, var3, var4)
    }

    fun clearDatabase() {
        val var1 = Helper.getConfig().listModelForDatabase
        val var2 = var1?.size
        for (var3 in 0 until var2!!) {
            val var4 = var1[var3]
            try {
                TableUtils.clearTable(getConnectionSource(), var4)
            } catch (var6: SQLException) {
                var6.printStackTrace()
            }
        }
    }

    @Throws(SQLException::class)
    fun <T : Serializable?> getIDao(var1: Class<T>): Any? {
        Helper.checkPermission()
        return if (daoMap[var1.simpleName] != null) {
            daoMap[var1.simpleName]
        } else {
            daoMap[var1.simpleName] = getDao(var1)
            daoMap[var1.simpleName]
        }
    }

    @Throws(SQLException::class)
    fun <T : Serializable?> getSDao(var1: Class<T>): Any? {
        Helper.checkPermission()
        return if (daoStringMap[var1.simpleName] != null) {
            daoStringMap[var1.simpleName]
        } else {
            daoStringMap[var1.simpleName] = getDao(var1)
            daoStringMap[var1.simpleName]
        }
    }

    override fun close() {
        super.close()
        val var1 = Helper.getConfig().listModelForDatabase
        val var2 = var1?.size
        for (var3 in 0 until var2!!) {
            val var4 = var1[var3]
            if (daoMap[var4?.simpleName] != null) {
                daoMap[var4?.simpleName] = null as Any?
            }
        }
    }

    companion object {
        var daoMap: HashMap<Any?, Any?> = HashMap<Any?, Any?>()
        var daoStringMap: HashMap<Any?, Any?> = HashMap<Any?, Any?>()
    }
}