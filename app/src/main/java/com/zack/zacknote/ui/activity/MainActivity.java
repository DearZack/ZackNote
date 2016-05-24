package com.zack.zacknote.ui.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zack.bean.Note;
import com.zack.zacknote.R;
import com.zack.zacknote.adapter.MyRecyclerViewAdapter;
import com.zack.zacknote.data.DealNotes;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static final int CREATE_NOTE = 1;
    public static final int MODIFY_NOTE = 2;
    public static final int CREATE_NOTE_SUCCEED = 3;
    public static final int MODIFY_NOTE_SUCCEED = 4;
    private RecyclerView recyclerView;
    private FloatingActionButton noteFab;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private MenuItem menuItem;
    private Intent intent;
    private String nowTag;
    private List<Note> notes;
    private DealNotes dealNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initViews();
        setOnListener();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.note_recycler_view);
        MyRecyclerViewAdapter myRecyclerViewAdapter = new MyRecyclerViewAdapter(MainActivity.this, notes);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        myRecyclerViewAdapter.OnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (view.getId()) {
                    case R.id.note_more:
                        Toast.makeText(MainActivity.this, "more" + position, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "other" + position, Toast.LENGTH_SHORT).show();
                }
            }
        });
        recyclerView.setAdapter(myRecyclerViewAdapter);
        noteFab = (FloatingActionButton) findViewById(R.id.note_fab);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_in_main);
        navigationView = (NavigationView) findViewById(R.id.navigation_in_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar_in_main);
        toolbar.setTitle(R.string.menu_note);
        menuItem = navigationView.getMenu().getItem(0);//设置默认亮的菜单
        menuItem.setChecked(true);
        setSupportActionBar(toolbar);
        setNavigationViewItemClickListener();
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle(R.string.app_name_in_chinese);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toolbar.setTitle(nowTag);
            }
        };
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        navigationView.setItemTextColor(getResources().getColorStateList(R.color.button_text));
        navigationView.setItemIconTintList(getResources().getColorStateList(R.color.button_text));

    }

    private void initData() {
        nowTag = getResources().getString(R.string.menu_note);
        if (notes == null) {
            notes = new ArrayList<>();
        }
        dealNotes = new DealNotes();
        for (int i = 0; i < 20; i++) {
            Note note = new Note();
            note.setTitle("title: " + i);
            note.setContent("content: " + i);
            note.setLastModifyTime((long) i);
            if (i == 2) {
                note.setTitle("title: title: title: title: " + i);
                note.setContent("content:content:content:content:content: " + i);
            }
            notes.add(note);
        }
    }

    private void setOnListener() {
        noteFab.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_fab:
                Toast.makeText(this, "写笔记", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("type", CREATE_NOTE);
                startActivityForResult(intent, CREATE_NOTE);
                break;
        }
    }

    private void setNavigationViewItemClickListener() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                if (menuItem != null) {
                    menuItem.setChecked(false);
                }
                switch (item.getItemId()) {
                    case R.id.notes_in_menu:
                        nowTag = getResources().getString(R.string.menu_note);
                        toolbar.setTitle(R.string.menu_note);
                        Toast.makeText(MainActivity.this, R.string.menu_note, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.deleted_in_menu:
                        nowTag = getResources().getString(R.string.menu_deleted_note);
                        toolbar.setTitle(R.string.menu_deleted_note);
                        Toast.makeText(MainActivity.this, R.string.menu_deleted_note, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.about_in_menu:
                        nowTag = getResources().getString(R.string.menu_about);
                        toolbar.setTitle(R.string.menu_about);
                        Toast.makeText(MainActivity.this, R.string.menu_about, Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                item.setChecked(true);
                drawerLayout.closeDrawer(Gravity.LEFT);
                menuItem = item;
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notes, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CREATE_NOTE:
                if (requestCode == CREATE_NOTE_SUCCEED) {
                    Note note = data.getParcelableExtra("note");
                    dealNotes.addNote(note);
                    System.out.println(note);
                } else {
                    Toast.makeText(this, "没有输入笔记", Toast.LENGTH_SHORT).show();
                }
                break;
            case MODIFY_NOTE:
                break;
            default:
                break;
        }
    }
}
