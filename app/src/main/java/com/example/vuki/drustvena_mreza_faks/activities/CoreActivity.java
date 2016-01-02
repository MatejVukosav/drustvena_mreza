package com.example.vuki.drustvena_mreza_faks.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
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
import com.example.vuki.drustvena_mreza_faks.adapters.HomeFragmentAdapter;
import com.example.vuki.drustvena_mreza_faks.adapters.SearchAdapter;
import com.example.vuki.drustvena_mreza_faks.helpers.InternetConnection;
import com.example.vuki.drustvena_mreza_faks.helpers.NotesHelpers;
import com.example.vuki.drustvena_mreza_faks.helpers.SendEmailHelper;
import com.example.vuki.drustvena_mreza_faks.network.ApiManager;
import com.search.material.library.MaterialSearchView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class CoreActivity extends AppCompatActivity {


    @Bind(R.id.core_viewpager)
    ViewPager viewPager;

    @Bind(R.id.core_drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.core_viewpager_tabs)
    TabLayout tabLayout;

    @Bind(R.id.search_view)
    MaterialSearchView searchView;

   /* @Bind(R.id.core_navigation_header_profile_picture)
    CircleImageView mHeaderProfileCircleImage;

    @Bind(R.id.core_navigation_header_username)
    TextView mHaderUsername;
*/

    @Bind(R.id.core_navigation_view)
    NavigationView navigationView;


    private int TAB_SEARCH;
    private int TAB_HOME;
    private int TAB_USER_WALL;
    private int TAB_INBOX;

    Toolbar toolbar;
    String[] tabText;
    HomeFragmentAdapter pagerAdapter;
    int numOfTabs;
    Context context;

    String bugMessage = "";
    String messageTitle = "";
    String receiver = "vuki146@gmail.com";

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


    }


    private void init() {
        context = this;

        numOfTabs = getResources().getInteger(R.integer.tabs_number);

        TAB_HOME = getResources().getInteger(R.integer.tabs_number_1_home);
        TAB_INBOX = getResources().getInteger(R.integer.tabs_number_2_inbox);
        TAB_SEARCH = getResources().getInteger(R.integer.tabs_number_3_search);
        TAB_USER_WALL = getResources().getInteger(R.integer.tabs_number_4_user_wall);

        tabText = getResources().getStringArray(R.array.tab_texts);

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

        setHeaderItems();

    }

    /**
     * Set items in user header
     */
    private void setHeaderItems() {
     /*   TextView mHaderUsername = (TextView) navigationView.findViewById(R.id.core_navigation_header_username);
        CircleImageView mHeaderProfileCircleImage = (CircleImageView) navigationView.findViewById(R.id.core_navigation_header_profile_picture);*/

        //set navigation view user name
        String username = "";
        if (ApiManager.getInstance().getUser() != null) {
            username = ApiManager.getInstance().getUser().getUsername();

            View headerLayout = navigationView.getHeaderView(0);
            TextView user = (TextView) headerLayout.findViewById(R.id.core_navigation_header_username);
            user.setText(username);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(final MenuItem item) {
                if (item.isChecked()) {
                    item.setChecked(false);
                }
                //drawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    /*case R.id.menu_navigation_edit_profile:
                        Toast.makeText(getApplicationContext(), "edit profile Selected", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(CoreActivity.this, EditProfileActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.menu_navigation_settings:
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent settings = new Intent(CoreActivity.this, SettingsActivity.class);
                        startActivity(settings);
                        break;
                    case R.id.menu_navigation_invite_friends:
                        Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent inviteFriends = new Intent(CoreActivity.this, InviteFriendsActivity.class);
                        startActivity(inviteFriends);
                        break;*/
                    case R.id.menu_navigation_ask_helpe:
                       /* Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent askForHelp = new Intent(CoreActivity.this, AskForHelpActivity.class);
                        startActivity(askForHelp)*/
                        ;

                        bugMessage = "";
                        messageTitle = "Ask_for_help_Bubbles";
                        SendEmailHelper.sendEmail(context, receiver, messageTitle, bugMessage);

                        break;
                    case R.id.menu_navigation_report_bug:
                      /*  Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                        Intent reportBug = new Intent(CoreActivity.this, ReportBugActivity.class);
                        startActivity(reportBug);*/
                        bugMessage = "";
                        messageTitle = "Report_bug_Bubbles";
                        SendEmailHelper.sendEmail(context, receiver, messageTitle, bugMessage);

                        break;
                    case R.id.menu_navigation_log_out:
                        final String title = item.getTitle().toString();
                        /*Intent logOut = new Intent(CoreActivity.this, LoginActivity.class);
                        NotesHelpers.toastMessage(getApplicationContext(), title);
                        startActivity(logOut);
                        finish();*/

                        Call<Void> logout = ApiManager.getInstance().getService().postSignOut();
                        logout.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Response<Void> response, Retrofit retrofit) {
                                if (response.isSuccess()) {
                                    Intent logOut = new Intent(CoreActivity.this, LoginActivity.class);
                                    NotesHelpers.toastMessage(getApplicationContext(), title);
                                    startActivity(logOut);
                                    finish();
                                }
                            }

                            @Override
                            public void onFailure(Throwable t) {
                                NotesHelpers.toastMessage(getApplicationContext(), getResources().getString(R.string.error_something_is_wrong));
                            }
                        });
                        break;

                    case R.id.menu_navigation_log_out2:
                        String title1 = item.getTitle().toString();
                        Intent logOut = new Intent(CoreActivity.this, LoginActivity.class);
                        NotesHelpers.toastMessage(getApplicationContext(), title1);
                        startActivity(logOut);
                        finish();

                        break;
                }
                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // AdapterHelpers.setImage(this, AdapterHelpers.profilePic, mHeaderProfileCircleImage);
        //mHaderUsername.setText(AdapterHelpers.userName);


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
        CharSequence tabCharSeq[] = tabText;
        pagerAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), tabCharSeq, tabsCount);
        initTabLayout(pagerAdapter);
    }

    private void initTabLayout(PagerAdapter adapter) {
        viewPager.setAdapter(adapter);

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0, size = tabLayout.getTabCount(); i < size; i++) {
            TabLayout.Tab singleTab = tabLayout.getTabAt(i);


            if (singleTab != null) {
                //initial first item is selected
                LinearLayout customTab = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.core_tab_custom, null);
                if (i == TAB_HOME) {
                    setTabIcon(customTab, R.mipmap.ic_home_black_24dp);
                } else if (i == TAB_INBOX) {
                    setTabIcon(customTab, R.mipmap.ic_message_black_24dp);
                } else if (i == TAB_SEARCH) {
                    setTabIcon(customTab, R.mipmap.ic_person_add_black_24dp);
                } else if (i == TAB_USER_WALL) {
                    setTabIcon(customTab, R.mipmap.ic_person_black_24dp);
                } else {
                    setTabIcon(customTab, R.mipmap.ic_error_black_24dp);
                }
                singleTab.setCustomView(customTab);
            }
        }


        TabLayout.OnTabSelectedListener listener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //TODO set selected tab icon
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getCustomView() != null) {

                    View customTab = tab.getCustomView();
                    int i = tab.getPosition();

                    if (i == TAB_HOME) {
                        setTabIcon(customTab, R.mipmap.ic_home_black_24dp);
                    } else if (i == TAB_INBOX) {
                        setTabIcon(customTab, R.mipmap.ic_message_black_24dp);
                    } else if (i == TAB_SEARCH) {
                        setTabIcon(customTab, R.mipmap.ic_person_add_black_24dp);
                    } else if (i == TAB_USER_WALL) {
                        setTabIcon(customTab, R.mipmap.ic_person_black_24dp);
                    } else {
                        setTabIcon(customTab, R.mipmap.ic_error_black_24dp);
                    }
                }


            }


            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getCustomView() != null) {

                    View customTab = tab.getCustomView();
                    int i = tab.getPosition();

                    if (i == TAB_HOME) {
                        setTabIcon(customTab, R.mipmap.ic_home_black_24dp);
                    } else if (i == TAB_INBOX) {
                        setTabIcon(customTab, R.mipmap.ic_message_black_24dp);
                    } else if (i == TAB_SEARCH) {
                        setTabIcon(customTab, R.mipmap.ic_person_add_black_24dp);
                    } else if (i == TAB_USER_WALL) {
                        setTabIcon(customTab, R.mipmap.ic_person_black_24dp);
                    } else {
                        setTabIcon(customTab, R.mipmap.ic_error_black_24dp);
                    }
                }
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
                String itemContext = parent.getItemAtPosition(position).toString();
                NotesHelpers.toastMessage(getApplicationContext(), itemContext);

                Intent intent = new Intent(CoreActivity.this, UserWallFromFriend.class);
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
