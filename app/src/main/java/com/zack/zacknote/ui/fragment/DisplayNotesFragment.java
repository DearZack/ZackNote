package com.zack.zacknote.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.zack.bean.Note;
import com.zack.dao.NoteDao;
import com.zack.zacknote.APP;
import com.zack.zacknote.R;
import com.zack.zacknote.adapter.MyRecyclerViewAdapter;
import com.zack.zacknote.data.DealNotes;
import com.zack.zacknote.ui.activity.NoteActivity;
import com.zack.zacknote.utils.ConstantUtils;
import com.zack.zacknote.utils.PopupWindowUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayNotesFragment extends Fragment {

    private RecyclerView recyclerView;
    private DealNotes dealNotes;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;

    private List<Note> notes;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ConstantUtils.ADD_NOTE_SUCCEED:
                    Note newNote = msg.getData().getParcelable("newNote");
                    notes.add(0, newNote);
                    myRecyclerViewAdapter.notifyDataSetChanged();
                    break;
                case ConstantUtils.SET_NOTE_TO_RECYCLE_BIN:
                    notes.remove(msg.arg1);
                    myRecyclerViewAdapter.notifyItemRemoved(msg.arg1);
                    break;
                case ConstantUtils.SET_NOTE_TO_NORMAL:
                    notes.remove(msg.arg1);
                    myRecyclerViewAdapter.notifyItemRemoved(msg.arg1);
                    break;
                case ConstantUtils.DELETE_NOTE_FOREVER:
                    notes.remove(msg.arg1);
                    myRecyclerViewAdapter.notifyItemRemoved(msg.arg1);
                    break;
                default:
                    break;
            }
        }
    };
    private int whereIsModifying;


    public DisplayNotesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_notes, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_in_display_fragment);
        notes = getNotes(getArguments().getInt("type"));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this.getActivity(), notes);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myRecyclerViewAdapter.OnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                boolean isDeleted = notes.get(position).getIsDeleted();
                PopupWindowUtils popupWindowUtils = new PopupWindowUtils(DisplayNotesFragment.this);
                switch (view.getId()) {
                    case R.id.note_more:
                        if (isDeleted) {
                            popupWindowUtils.showPopupWindow(getActivity(), ConstantUtils.SHOW_NOTE_DELETED_PUPOP_WINDOW, view, notes.get(position), position);
                            Toast.makeText(getActivity(), "恢复", Toast.LENGTH_SHORT).show();
                        } else {
                            popupWindowUtils.showPopupWindow(getActivity(), ConstantUtils.SHOW_NOTE_NORMAL_PUPOP_WINDOW, view, notes.get(position), position);
                            Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        if (!isDeleted) {
                            whereIsModifying = position;
                            Intent intent = new Intent(getActivity(), NoteActivity.class);
                            intent.putExtra("note", notes.get(position));
                            intent.putExtra("type", ConstantUtils.MODIFY_NOTE);
                            startActivityForResult(intent, ConstantUtils.MODIFY_NOTE);
                        }
                        break;
                }
            }
        });
        recyclerView.setAdapter(myRecyclerViewAdapter);
        return view;
    }

    private List<Note> getNotes(int type) {
        List<Note> mNotes = new ArrayList<>();
        if (type == ConstantUtils.SHOW_NOTES) {
            mNotes = APP.noteDao.queryBuilder().where(NoteDao.Properties.IsDeleted.eq(false)).list();
            Collections.reverse(mNotes);
        } else if (type == ConstantUtils.SHOW_DELETED_NOTES) {
            mNotes = APP.noteDao.queryBuilder().where(NoteDao.Properties.IsDeleted.eq(true)).list();
            Collections.reverse(mNotes);
        }
        return mNotes;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (dealNotes == null) {
            dealNotes = new DealNotes();
        }
        switch (requestCode) {
            case ConstantUtils.MODIFY_NOTE:
                if (resultCode == ConstantUtils.MODIFY_NOTE_SUCCEED) {
                    Note note = data.getParcelableExtra("note");
                    dealNotes.modifyNote(note);
                    notes.set(whereIsModifying, note);
                    myRecyclerViewAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), "没有修改笔记", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
}
