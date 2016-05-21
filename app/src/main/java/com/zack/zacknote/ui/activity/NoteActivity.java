package com.zack.zacknote.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.zack.zacknote.R;

public class NoteActivity extends BaseActivity {

    public static final int CREATE_NOTE = 1;
    public static final int MODIFY_NOTE = 2;
    private Toolbar toolbar;
    private MaterialEditText editTextTitle, editTextContent;
    private Intent intent;
    private Bundle bundle;
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        intent = getIntent();
        type = intent.getIntExtra("type", CREATE_NOTE);
        switch (type) {
            case CREATE_NOTE:
                createNote();
                break;
            case MODIFY_NOTE:
                modifyNote();
                break;
            default:
                break;
        }
        initViews();
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.tool_bar_in_note);
        editTextTitle = (MaterialEditText) findViewById(R.id.note_title_in_note);
        editTextContent = (MaterialEditText) findViewById(R.id.note_content_in_note);

        toolbar.setTitle("新的笔记");
        setSupportActionBar(toolbar);
        editTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editTextContent.getText().toString().isEmpty() && !editTextTitle.getText().toString().isEmpty()) {
                    toolbar.getMenu().getItem(0).setVisible(true);
                } else {
                    toolbar.getMenu().getItem(0).setVisible(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editTextContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!editTextContent.getText().toString().isEmpty() && !editTextTitle.getText().toString().isEmpty()) {
                    toolbar.getMenu().getItem(0).setVisible(true);
                } else {
                    toolbar.getMenu().getItem(0).setVisible(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.commit_in_menu:
                        Toast.makeText(NoteActivity.this, "提交", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commit, menu);
        toolbar.getMenu().getItem(0).setVisible(false);
        return true;
    }

    private void createNote() {

    }

    private void modifyNote() {

    }
}
