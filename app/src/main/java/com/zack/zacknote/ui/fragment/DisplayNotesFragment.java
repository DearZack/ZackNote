package com.zack.zacknote.ui.fragment;

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
import com.zack.zacknote.APP;
import com.zack.zacknote.R;
import com.zack.zacknote.adapter.MyRecyclerViewAdapter;

import java.util.Collections;
import java.util.List;

public class DisplayNotesFragment extends Fragment {

    private RecyclerView recyclerView;

    private List<Note> notes;

    public DisplayNotesFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_display_notes, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_in_display_fragment);
        notes = getNotes();
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(this.getActivity(), notes);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myRecyclerViewAdapter.OnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.note_more:
                        System.out.println("more");
                        break;
                    default:
                        System.out.println("other");
                        break;
                }
            }
        });
        recyclerView.setAdapter(myRecyclerViewAdapter);
        return view;
    }

    private List<Note> getNotes() {
        List<Note> mNotes;
        mNotes = APP.noteDao.queryBuilder().list();
        Collections.reverse(mNotes);
        return mNotes;
    }
}
