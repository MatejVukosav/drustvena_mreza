package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vuki.drustvena_mreza_faks.R;
import com.example.vuki.drustvena_mreza_faks.adapters.CoreFragmentAdapter;
import com.example.vuki.drustvena_mreza_faks.adapters.SearchAdapter;
import com.example.vuki.drustvena_mreza_faks.helpers.InternetConnection;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.search.material.library.MaterialSearchView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CoreActivity extends AppCompatActivity {

    CoreFragmentAdapter pagerAdapter;
    int numOfTabs;
    CharSequence Titles[] = {"Home", "Inbox", "Search Users", "User wall"};

    @Bind(R.id.core_viewpager)
    ViewPager viewPager;

    @Bind(R.id.core_drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.core_viewpager_tabs)
    TabLayout tabLayout;

    @Bind(R.id.search_view)
    MaterialSearchView searchView;

    private int TAB_SEARCH;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);
        ButterKnife.bind(this);

        InternetConnection internetConnection = new InternetConnection(this);
        internetConnection.setInternetConnectionCallback(new InternetConnection.OnCheckInternetConnection() {
            @Override
            public void OnInternetCheck(boolean hasInternetConnection) {
                if (hasInternetConnection) {
                    init();
                } else {
                    finish();
                }
            }
        });
        internetConnection.checkInternetConnection();

    /*    if(AppStatus.getInstance(this).isOnline()){
            init();
        }else{
            finish();
        }*/

        TAB_SEARCH = getResources().getInteger(R.integer.tabs_number_3_search);
    }


    private void init() {
        numOfTabs = getResources().getInteger(R.integer.tabs_number);

        toolbar = ButterKnife.findById(this, R.id.core_viewpager_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ViewPager viewPager = ButterKnife.findById(this, R.id.core_viewpager);
        if (viewPager != null) {
            setupViewPager();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager() {
        setupViewPager(numOfTabs);
    }

    private void setupViewPager(int tabsCount) {
        pagerAdapter = new CoreFragmentAdapter(getSupportFragmentManager(), Titles, tabsCount);
        initTabLayout(pagerAdapter);
    }

    private void initTabLayout(PagerAdapter adapter) {
        viewPager.setAdapter(adapter);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.setupWithViewPager(viewPager);

        String[] tabText = getResources().getStringArray(R.array.tab_texts);
        for (int i = 0, size = tabLayout.getTabCount(); i < size; i++) {
            TabLayout.Tab singleTab = tabLayout.getTabAt(i);

            if (singleTab != null) {
                //initial first item is selected

                LinearLayout customTab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.core_tab_custom, null);
                if (i == 0) {
                    //set icon
                    setTabIcon(customTab, R.drawable.ic_tab_white);
                    //set text
                    setTabText(customTab, tabText[i], R.color.white);
                } else {
                    //set icon
                    setTabIcon(customTab, R.drawable.ic_tab_black);
                    //set text
                    setTabText(customTab, tabText[i], R.color.black);
                }
                singleTab.setCustomView(customTab);
            }
        }


        TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getCustomView() != null) {
                    //set icon
                    setTabIcon(tab.getCustomView(), R.drawable.ic_tab_white);
                    setTabText(tab.getCustomView(), tab.getText().toString(), R.color.white);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                setTabIcon(tab.getCustomView(), R.drawable.ic_tab_black);
                setTabText(tab.getCustomView(), tab.getText().toString(), R.color.black);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        };

        tabLayout.setOnTabSelectedListener(listener);

        setMaterialSearchView();

    }

    private void setMaterialSearchView() {
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               return false;
            }
        });


        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                toolbar.setVisibility(View.GONE);
            }

            @Override
            public void onSearchViewClosed() {
                toolbar.setVisibility(View.VISIBLE);
            }
        });

        searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO search is applied for query input- open user wall with that mark
                String itemContext=parent.getItemAtPosition(position).toString();
                NotesHelpers.toastMessage(getApplicationContext(),itemContext);

                Intent intent=new Intent(CoreActivity.this,UserWall.class);
                startActivity(intent);
            }
        });


        SearchAdapter adapter = new SearchAdapter(this);
        searchView.setAdapter(adapter);
    }

    private void setTabIcon(View tab, int picture) {
        ImageView imageView = ButterKnife.findById(tab, R.id.core_tab_icon);
        imageView.setImageDrawable(ContextCompat.getDrawable(CoreActivity.this, picture));
    }

    private void setTabText(View tab, String text, int color) {
        TextView textView = ButterKnife.findById(tab, R.id.core_tab_text);
        textView.setTextColor(color);
        textView.setText(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }


    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
