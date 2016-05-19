package com.zack.zacknote.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zack.zacknote.R;

public class NoteActivity extends BaseActivity {

    private Toolbar toolbar;
    private MaterialEditText editTextTitle, editTextContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        initViews();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar_in_note);
        editTextTitle = (MaterialEditText) findViewById(R.id.note_title_in_note);
        editTextContent = (MaterialEditText) findViewById(R.id.note_content_in_note);

        toolbar.setTitle("新的笔记");
        setSupportActionBar(toolbar);
        toolbar.getMenu().getItem(0).setVisible(false);
//        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_36dp);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commit, menu);
        return true;
    }
}
