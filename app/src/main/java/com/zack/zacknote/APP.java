package com.zack.zacknote;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.zack.bean.Note;
import com.zack.dao.DaoMaster;
import com.zack.dao.DaoSession;
import com.zack.dao.NoteDao;

/**
 * Created by Zack Zhou on 2016/5/20.
 */
public class APP extends Application {

    public static DaoSession daoSession;
    public static NoteDao noteDao;

    @Override
    public void onCreate() {
        super.onCreate();
        initDatabase();
        if (isFirstIn()) {
            addInitNotes();
        }
    }

    private void initDatabase() {
        DaoMaster.DevOpenHelper openHelp = new DaoMaster.DevOpenHelper(this, "notes.db", null);
        SQLiteDatabase database = openHelp.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        daoSession = daoMaster.newSession();
        noteDao = daoSession.getNoteDao();
    }

    private boolean isFirstIn() {
        SharedPreferences sharedPreferences = getSharedPreferences("isFirstIn", Context.MODE_PRIVATE);
        boolean isFirstIn = sharedPreferences.getBoolean("isFirstIn", true);
        if (isFirstIn) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("isFirstIn", false);
            System.out.println("第一次");
            editor.apply();
        }
        return isFirstIn;
    }

    private void addInitNotes() {
        Note note1 = new Note();
        note1.setIsDeleted(false);
        note1.setCreateTime(System.currentTimeMillis());
        note1.setLastModifyTime(System.currentTimeMillis());
        note1.setTitle("标题");
        note1.setContent("内容");
        noteDao.insertOrReplace(note1);
    }
}
