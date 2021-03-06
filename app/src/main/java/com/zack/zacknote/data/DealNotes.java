package com.zack.zacknote.data;

import com.zack.bean.Note;
import com.zack.zacknote.APP;

/**
 * Created by Zack Zhou on 2016/5/23.
 */
public class DealNotes {
    private Note mNote;

    public void addNote(Note note) {
        APP.noteDao.insertOrReplace(note);
    }

    public void modifyNote(Note note) {
        APP.noteDao.update(note);
    }

    public void deleteNote(Note note) {
        APP.noteDao.delete(note);
    }
}
