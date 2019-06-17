package edgar.yodgorbek.yangiliklar.sportactivities

import android.app.SearchManager
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import edgar.yodgorbek.yangiliklar.R

class MainActivity: AppCompatActivity() {
    internal lateinit var mContext: Context
    // Default active navigation menu
    internal var mActiveMenu = 0
    internal var searchQuery = ""
    private var mDrawer: DrawerLayout? = null
    private var toolbar: Toolbar? = null
    private var nvDrawer: NavigationView? = null
    // Action bar search widget
    private var drawerToggle: ActionBarDrawerToggle? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Set a Toolbar to replace the ActionBar.
        toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        // Find our drawer view
        mDrawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawerToggle = setupDrawerToggle()
        // Tie DrawerLayout events to the ActionBarToggle
        mDrawer!!.addDrawerListener(drawerToggle!!)
        nvDrawer = findViewById(R.id.nvView) as NavigationView
        // Inflate the header view at runtime
        val headerLayout = nvDrawer!!.inflateHeaderView(R.layout.nav_header)
        // We can now look up items within the header if needed
        val ivHeaderPhoto = headerLayout.findViewById((R.id.header_image)) as ImageView
        ivHeaderPhoto.setImageResource(R.mipmap.ic_launcher_round)
        setupDrawerContent(nvDrawer!!)
        val fragmentManager = getSupportFragmentManager()
        fragmentManager.beginTransaction().replace(R.id.flContent, BBCSportFragment(), "" + MENU_FIRST).commit()
    }
    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.getItemId()) {
            android.R.id.home -> {
                mDrawer!!.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onCreateOptionsMenu(menu: Menu):Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        // Getting search action from action bar and setting up search view
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.getActionView() as SearchView
        // Setup searchView
        setupSearchView(searchView)
        Log.e(TAG, "crash")
        return true
    }
    fun setupSearchView(searchView:SearchView) {
        val searchManager = this.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        if (searchManager != null)
        {
            val info = searchManager.getSearchableInfo(getComponentName())
            searchView.setSearchableInfo(info)
        }
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText:String):Boolean {
                return true
            }
            override fun onQueryTextSubmit(newText:String):Boolean {
                searchQuery = newText
                val currFragment = getSupportFragmentManager().findFragmentByTag("" + mActiveMenu)
                // Load search data on respective fragment
                if (mActiveMenu == MENU_FIRST)
                {
                    if (currFragment is BBCSportFragment)
                    {
                        (currFragment as BBCSportFragment).doFilter(newText)
                    }
                }
                if (mActiveMenu == MENU_SECOND)
                // First
                {
                    if (currFragment is TalkSportsFragment)
                    {
                        (currFragment as TalkSportsFragment).doFilter(newText)
                    }
                }
                if (mActiveMenu == MENU_THIRD)
                {
                    if (currFragment is FoxSportsFragment)
                    {
                        (currFragment as FoxSportsFragment).doFilter(newText)
                    }
                }
                if (mActiveMenu == MENU_FOURTH)
                {
                    if (currFragment is FootballItaliaFragment)
                    {
                        (currFragment as FootballItaliaFragment).doFilter(newText)
                    }
                }
                else if (mActiveMenu == MENU_FIFTH)
                {
                    if (currFragment is ESPNFragment)
                    {
                        (currFragment as ESPNFragment).doFilter(newText)
                    }
                }
                searchView.clearFocus()
                return false
            }
        })
        // Handling focus change of search view
        searchView.setOnQueryTextFocusChangeListener(object: View.OnFocusChangeListener {
            override fun onFocusChange(v: View, hasFocus:Boolean) {
                // Focus changed after pressing back key or pressing done in keyboard
                if (!hasFocus)
                {
                    searchQuery = ""
                }
            }
        })
    }
    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener(
                { menuItem->
                    selectDrawerItem(menuItem)
                    true })
    }
    fun selectDrawerItem(menuItem:MenuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        // Fragment fragment = null;
        var fragmentClass: Fragment? = null
        when (menuItem.getItemId()) {
            R.id.bbcsports_fragment -> {
                mActiveMenu = 0
                fragmentClass = BBCSportFragment()
            }
            R.id.talksports_fragment -> {
                mActiveMenu = 1
                fragmentClass = TalkSportsFragment()
            }
            R.id.foxsports_fragment -> {
                mActiveMenu = 2
                fragmentClass = FoxSportsFragment()
            }
            R.id.footballitalia_fragment -> {
                mActiveMenu = 3
                fragmentClass = FootballItaliaFragment()
            }
            R.id.espn_fragment -> {
                mActiveMenu = 4
                fragmentClass = ESPNFragment()
            }
            else -> mActiveMenu = -1
        }
        // try {
        // fragment = (Fragment) fragmentClass.newInstance();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // Insert the fragment by replacing any existing fragment
        if (fragmentClass != null)
        {
            val fragmentManager = getSupportFragmentManager()
            fragmentManager.beginTransaction().replace(R.id.flContent, fragmentClass, "" + mActiveMenu).commit()
        }
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true)
        // Set action bar title
        setTitle(menuItem.getTitle())
        // Close the navigation drawer
        mDrawer!!.closeDrawers()
    }
    private fun setupDrawerToggle():ActionBarDrawerToggle {
        // NOTE: Make sure you pass in a valid toolbar reference. ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.drawer_open, R.string.drawer_close)
    }
    protected final override fun onPostCreate(savedInstanceState:Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle!!.syncState()
    }
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Pass any configuration change to the drawer toggles
        drawerToggle!!.onConfigurationChanged(newConfig)
    }
    companion object {
        // TAGS
        val MENU_FIRST = 0
        val MENU_SECOND = 1
        val MENU_THIRD = 2
        val MENU_FOURTH = 3
        val MENU_FIFTH = 4
        val TAG = "crash"
    }
}