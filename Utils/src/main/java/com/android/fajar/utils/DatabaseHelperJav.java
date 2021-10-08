package com.android.fajar.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelperJav extends OrmLiteSqliteOpenHelper {
    public static Map<String, Dao> daoMap = new HashMap();
    public static Map<String, Dao> daoStringMap = new HashMap();

    public DatabaseHelperJav(Context var1) {
        super(var1, Helper.getConfig().getDatabaseName(), (SQLiteDatabase.CursorFactory)null, Helper.getConfig().getDatabaseVersion());
    }

    public void onCreate(SQLiteDatabase var1, ConnectionSource var2) {
        Class[] var3 = Helper.getConfig().getListModelForDatabase();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Class var6 = var3[var5];

            try {
                TableUtils.createTableIfNotExists(var2, var6);
            } catch (SQLException var8) {
                var8.printStackTrace();
            }
        }

    }

    public void onUpgrade(SQLiteDatabase var1, ConnectionSource var2, int var3, int var4) {
        Helper.getConfig().onUpgradeDatabase(var1, var2, var3, var4);
    }

    public final void clearDatabase() {
        Class[] var1 = Helper.getConfig().getListModelForDatabase();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Class var4 = var1[var3];

            try {
                TableUtils.clearTable(this.getConnectionSource(), var4);
            } catch (SQLException var6) {
                var6.printStackTrace();
            }
        }

    }

    public <T extends Serializable> Dao<T, Integer> getIDao(Class<T> var1) throws SQLException {
        HelperJav.checkPermission();
        if (daoMap.get(var1.getSimpleName()) != null) {
            return (Dao)daoMap.get(var1.getSimpleName());
        } else {
            daoMap.put(var1.getSimpleName(), this.getDao(var1));
            return (Dao)daoMap.get(var1.getSimpleName());
        }
    }

    public <T extends Serializable> Dao<T, Integer> getIDaoCreateOrUpdate(Class<T> var1) throws SQLException {
        HelperJav.checkPermission();
        if (daoMap.get(var1.getSimpleName()) != null) {
            return (Dao)daoMap.get(var1.getSimpleName()).createOrUpdate(var1);
        } else {
            daoMap.put(var1.getSimpleName(), this.getDao(var1));
            return (Dao)daoMap.get(var1.getSimpleName()).createOrUpdate(var1);
        }
    }

    public <T extends Serializable> Dao<T, String> getSDao(Class<T> var1) throws SQLException {
        HelperJav.checkPermission();
        if (daoStringMap.get(var1.getSimpleName()) != null) {
            return (Dao)daoStringMap.get(var1.getSimpleName());
        } else {
            daoStringMap.put(var1.getSimpleName(), this.getDao(var1));
            return (Dao)daoStringMap.get(var1.getSimpleName());
        }
    }

    public <T extends Serializable> Dao<T, String> getSDaoCreateOrUpdate(Class<T> var1) throws SQLException {
        HelperJav.checkPermission();
        if (daoStringMap.get(var1.getSimpleName()) != null) {
            return (Dao)daoStringMap.get(var1.getSimpleName()).createOrUpdate(var1);
        } else {
            daoStringMap.put(var1.getSimpleName(), this.getDao(var1));
            return (Dao)daoStringMap.get(var1.getSimpleName()).createOrUpdate(var1);
        }
    }

    public void close() {
        super.close();
        Class[] var1 = HelperJav.getConfig().getListModelForDatabase();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Class var4 = var1[var3];
            if (daoMap.get(var4.getSimpleName()) != null) {
                daoMap.put(var4.getSimpleName(), (Dao) null);
            }
        }

    }
}
