package edgar.yodgorbek.sportnews;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import edgar.yodgorbek.sportnews.sportactivities.BBCSportFragment;
import edgar.yodgorbek.sportnews.sportactivities.ESPNFragment;
import edgar.yodgorbek.sportnews.sportactivities.FootballItaliaFragment;
import edgar.yodgorbek.sportnews.sportactivities.FoxSportsFragment;
import edgar.yodgorbek.sportnews.sportactivities.TalkSportsFragment;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    Context mContext;

    // Default active navigation menu
    int mActiveMenu;

    // TAGS
    public static final int MENU_FIRST = 0;
    public static final int MENU_SECOND = 1;
    public static final int MENU_THIRD = 2;
    public static final int MENU_FOURTH = 3;
    public static final int MENU_FIFTH = 3;


    // Action bar search widget
    SearchView searchView;
    String searchQuery = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = setupDrawerToggle();

        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer.addDrawerListener(drawerToggle);

        nvDrawer = (NavigationView) findViewById(R.id.nvView);

// Inflate the header view at runtime
        View headerLayout = nvDrawer.inflateHeaderView(R.layout.nav_header);
// We can now look up items within the header if needed
        ImageView ivHeaderPhoto = (ImageView) headerLayout.findViewById((R.id.header_image));
        ivHeaderPhoto.setImageResource(R.drawable.ic_sportnews);
        setupDrawerContent(nvDrawer);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new BBCSportFragment()).commit();
        fragmentManager.beginTransaction().replace(R.id.flContent, new FoxSportsFragment()).commit();
        fragmentManager.beginTransaction().replace(R.id.flContent, new TalkSportsFragment()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // Getting search action from action bar and setting up search view
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searchItem.getActionView();

        // Setup searchView
        setupSearchView(searchItem);

        return true;
    }

    private void setupSearchView(MenuItem searchItem)
    {
        SearchManager searchManager = (SearchManager) this.getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null)
        {
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            searchView.setSearchableInfo(info);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                searchQuery = newText;

                // Load search data on respective fragment
                if(mActiveMenu == MENU_FIRST)   // First
                {
                    BBCSportFragment.doFilter(newText);
                }

                if(mActiveMenu == MENU_SECOND)   // First
                {
                    BBCSportFragment.doFilter(newText);
                }

                if(mActiveMenu == MENU_THIRD)   // First
                {
                    BBCSportFragment.doFilter(newText);
                }

                if(mActiveMenu == MENU_FOURTH)   // First
                {
                    BBCSportFragment.doFilter(newText);
                }
                else if(mActiveMenu == MENU_FIFTH) // Second
                {
                    ESPNFragment.doFilter(newText);
                }
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                //searchView.clearFocus();
                return false;
            }
        });

        // Handling focus change of search view
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                // Focus changed after pressing back key or pressing done in keyboard
                if (!hasFocus) {
                    searchQuery = "";
                }
            }
        });
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;
        switch (menuItem.getItemId()) {
            case R.id.bbcsports_fragment:
                fragmentClass = BBCSportFragment.class;
                break;
            case R.id.talksports_fragment:
                fragmentClass = TalkSportsFragment.class;
                break;
            case R.id.foxsports_fragment:
                fragmentClass = FoxSportsFragment.class;
                break;

            case R.id.footballitalia_fragment:
                fragmentClass = FootballItaliaFragment.class;
                break;

            case R.id.espn_fragment:
                fragmentClass = ESPNFragment.class;
                break;

            default:

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }


}
