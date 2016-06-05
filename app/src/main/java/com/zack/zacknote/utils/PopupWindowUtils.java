package com.zack.zacknote.utils;

import android.app.Fragment;
import android.content.Context;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.zack.bean.Note;
import com.zack.zacknote.R;
import com.zack.zacknote.data.DealNotes;
import com.zack.zacknote.ui.fragment.DisplayNotesFragment;

/**
 * Created by Zack Zhou on 2016/6/5.
 */
public class PopupWindowUtils {
    private DealNotes dealNotes;
    private DisplayNotesFragment displayNotesFragment;

    public PopupWindowUtils(DisplayNotesFragment displayNotesFragment) {
        this.displayNotesFragment = displayNotesFragment;
    }

    public void showPopupWindow(Context context, int type, View view, final Note note, final int position) {
        if (dealNotes == null) {
            dealNotes = new DealNotes();
        }
        switch (type) {
            case ConstantUtils.SHOW_NOTE_NORMAL_PUPOP_WINDOW:
                View normal = LayoutInflater.from(context).inflate(R.layout.popup_window_note_normal, null);
                Button recycleBin = (Button) normal.findViewById(R.id.popup_window_recycle_bin);

                final PopupWindow normalPopupWindow = new PopupWindow(normal, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                normalPopupWindow.setTouchable(true);
                normalPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
                normalPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.popup_window_normal_bg));
                normalPopupWindow.showAsDropDown(view);
                recycleBin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        note.setIsDeleted(true);
                        dealNotes.modifyNote(note);
                        Message message = new Message();
                        message.what = ConstantUtils.SET_NOTE_TO_RECYCLE_BIN;
                        message.arg1 = position;
                        displayNotesFragment.handler.sendMessage(message);
                        normalPopupWindow.dismiss();
                    }
                });
                break;
            case ConstantUtils.SHOW_NOTE_DELETED_PUPOP_WINDOW:
                View deleted = LayoutInflater.from(context).inflate(R.layout.popup_window_note_deleted, null);
                Button recovery = (Button) deleted.findViewById(R.id.popup_window_recovery);
                Button delete = (Button) deleted.findViewById(R.id.popup_window_delete);
                final PopupWindow deletedPopupWindow = new PopupWindow(deleted, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                deletedPopupWindow.setTouchable(true);
                deletedPopupWindow.setTouchInterceptor(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return false;
                    }
                });
                deletedPopupWindow.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.popup_window_normal_bg));
                deletedPopupWindow.showAsDropDown(view);
                recovery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        note.setIsDeleted(false);
                        dealNotes.modifyNote(note);
                        Message message = new Message();
                        message.what = ConstantUtils.SET_NOTE_TO_NORMAL;
                        message.arg1 = position;
                        displayNotesFragment.handler.sendMessage(message);
                        deletedPopupWindow.dismiss();
                    }
                });
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dealNotes.deleteNote(note);
                        Message message = new Message();
                        message.what = ConstantUtils.DELETE_NOTE_FOREVER;
                        message.arg1 = position;
                        displayNotesFragment.handler.sendMessage(message);
                        deletedPopupWindow.dismiss();
                    }
                });
                break;
            default:
                break;
        }
    }
}
