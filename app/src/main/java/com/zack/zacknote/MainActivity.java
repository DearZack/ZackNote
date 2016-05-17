package com.zack.zacknote;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zack.zacknote.adapter.MyRecyclerViewAdapter;
import com.zack.zacknote.bean.Note;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private FloatingActionButton noteFab;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private MenuItem menuItem;

    private List<Note> notes;

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
        toolbar.setTitle("扎克笔记");
        menuItem = navigationView.getMenu().getItem(0);//设置默认亮的菜单
        menuItem.setChecked(true);
        setSupportActionBar(toolbar);
        setNavigationViewItemClickListener();
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    private void initData() {
        if (notes == null) {
            notes = new ArrayList<>();
        }
        for (int i = 0; i < 20; i++) {
            Note note = new Note();
            note.setTitle("title: " + i);
            note.setContent("content: " + i);
            note.setLastModifyTime("time: " + i);
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
                        Toast.makeText(MainActivity.this, "笔记", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.deleted_in_menu:
                        Toast.makeText(MainActivity.this, "回收站", Toast.LENGTH_SHORT).show();
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
}
