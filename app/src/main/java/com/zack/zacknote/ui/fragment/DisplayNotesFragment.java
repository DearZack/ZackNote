package com.zack.zacknote.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zack.bean.Note;
import com.zack.dao.NoteDao;
import com.zack.zacknote.APP;
import com.zack.zacknote.R;
import com.zack.zacknote.adapter.MyRecyclerViewAdapter;
import com.zack.zacknote.data.DealNotes;
import com.zack.zacknote.ui.activity.NoteActivity;
import com.zack.zacknote.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DisplayNotesFragment extends Fragment {

    private RecyclerView recyclerView;
    private DealNotes dealNotes;

    private List<Note> notes;

    public DisplayNotesFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_notes, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_in_display_fragment);
        notes = getNotes(getArguments().getInt("type"));
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(this.getActivity(), notes);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myRecyclerViewAdapter.OnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                boolean isDeleted = notes.get(position).getIsDeleted();
                switch (view.getId()) {
                    case R.id.note_more:
                        if (isDeleted) {
                            Toast.makeText(getActivity(), "恢复", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "删除", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        if (!isDeleted) {
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
            case ConstantUtils.CREATE_NOTE:
                if (resultCode == ConstantUtils.CREATE_NOTE_SUCCEED) {
                    Note note = data.getParcelableExtra("note");
                    dealNotes.addNote(note);
                } else {
                    Toast.makeText(getActivity(), "没有创建笔记", Toast.LENGTH_SHORT).show();
                }
                break;
            case ConstantUtils.MODIFY_NOTE:
                if (resultCode == ConstantUtils.MODIFY_NOTE_SUCCEED) {
                    Note note = data.getParcelableExtra("note");
                    if (note != null)
                        System.out.println(note.getId());
                    dealNotes.modifyNote(note);
                } else {
                    Toast.makeText(getActivity(), "没有修改笔记", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    public interface OnAddNoteListener {
        void addNote(Note note);
    }
}
