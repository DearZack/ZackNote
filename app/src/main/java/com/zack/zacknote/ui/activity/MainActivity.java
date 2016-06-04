package com.zack.zacknote.ui.activity;

import android.content.Intent;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.zack.bean.Note;
import com.zack.zacknote.R;
import com.zack.zacknote.data.DealNotes;
import com.zack.zacknote.ui.fragment.DisplayNotesFragment;
import com.zack.zacknote.utils.ConstantUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private FloatingActionButton noteFab;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private MenuItem menuItem;
    private Fragment currentFragment;
    private FragmentManager fragmentManager;
    private Intent intent;
    private String nowTag;
    private boolean isOpen;
    private long lastBackPressedTime;
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
        noteFab = (FloatingActionButton) findViewById(R.id.note_fab);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_in_main);
        navigationView = (NavigationView) findViewById(R.id.navigation_in_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar_in_main);
        menuItem = navigationView.getMenu().getItem(0);//设置默认亮的菜单
        menuItem.setChecked(true);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.menu_note);
        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, toolbar, R.string.app_name, R.string.app_name) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                toolbar.setTitle(R.string.app_name_in_chinese);
                isOpen = true;
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                toolbar.setTitle(nowTag);
                isOpen = false;
            }
        };
        actionBarDrawerToggle.syncState();
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        navigationView.setItemTextColor(getResources().getColorStateList(R.color.button_text));
        navigationView.setItemIconTintList(getResources().getColorStateList(R.color.button_text));
        fragmentManager = getSupportFragmentManager();
        initDefaultFragment();
    }

    private void initData() {
        nowTag = getResources().getString(R.string.menu_note);
        if (notes == null) {
            notes = new ArrayList<>();
        }
        dealNotes = new DealNotes();
    }

    private void setOnListener() {
        noteFab.setOnClickListener(this);
        setNavigationViewItemClickListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.note_fab:
                Toast.makeText(this, "写笔记", Toast.LENGTH_SHORT).show();
                intent = new Intent(MainActivity.this, NoteActivity.class);
                intent.putExtra("type", ConstantUtils.CREATE_NOTE);
                startActivityForResult(intent, ConstantUtils.CREATE_NOTE);
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
                Bundle bundle = new Bundle();
                switch (item.getItemId()) {
                    case R.id.notes_in_menu:
                        nowTag = getResources().getString(R.string.menu_note);
                        toolbar.setTitle(R.string.menu_note);
                        bundle.putInt("type", ConstantUtils.SHOW_NOTES);
                        currentFragment = new DisplayNotesFragment();
                        currentFragment.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.frame_layout_in_main, currentFragment).commit();
                        noteFab.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, R.string.menu_note, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.deleted_in_menu:
                        nowTag = getResources().getString(R.string.menu_deleted_note);
                        toolbar.setTitle(R.string.menu_deleted_note);
                        bundle.putInt("type", ConstantUtils.SHOW_DELETED_NOTES);
                        currentFragment = new DisplayNotesFragment();
                        currentFragment.setArguments(bundle);
                        fragmentManager.beginTransaction().replace(R.id.frame_layout_in_main, currentFragment).commit();
                        noteFab.setVisibility(View.GONE);
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.notes, menu);
//        return true;
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ConstantUtils.CREATE_NOTE:
                if (resultCode == ConstantUtils.CREATE_NOTE_SUCCEED) {
                    Note note = data.getParcelableExtra("note");
                    dealNotes.addNote(note);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("newNote", note);
                    System.out.println(note.getTitle());
                    Message message = new Message();
                    message.what = ConstantUtils.ADD_NOTE_SUCCEED;
                    message.setData(bundle);
                    ((DisplayNotesFragment) currentFragment).handler.sendMessage(message);
                } else {
                    Toast.makeText(this, "没有创建笔记", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    private void initDefaultFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("type", ConstantUtils.SHOW_NOTES);
        currentFragment = new DisplayNotesFragment();
        currentFragment.setArguments(bundle);
        fragmentManager.beginTransaction().add(R.id.frame_layout_in_main, currentFragment).commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isOpen) {
                drawerLayout.closeDrawers();
                return true;
            } else {
                long interval = System.currentTimeMillis() - lastBackPressedTime;
                lastBackPressedTime = System.currentTimeMillis();
                if (interval >= 2000) {
                    Toast.makeText(this, R.string.click_again_to_exit, Toast.LENGTH_SHORT).show();
                    return true;
                }
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
