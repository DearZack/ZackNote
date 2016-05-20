package com.zack.zacknote;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.zack.dao.DaoMaster;
import com.zack.dao.DaoSession;
import com.zack.dao.NotesDao;

/**
 * Created by Zack Zhou on 2016/5/20.
 */
public class APP extends Application {

    private DaoSession daoSession;
    private NotesDao notesDao;

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
    }

    private void initDatabase() {
        DaoMaster.DevOpenHelper openHelp = new DaoMaster.DevOpenHelper(this, "notes.db", null);
        SQLiteDatabase database = openHelp.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        notesDao = daoSession.getNotesDao();
    }
}
