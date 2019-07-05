package top.technopedia.myapplicationkatalogfilm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import top.technopedia.myapplicationkatalogfilm.Fragment.FavouriteFragment;
import top.technopedia.myapplicationkatalogfilm.Fragment.HomeFragment;
import top.technopedia.myapplicationkatalogfilm.Fragment.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, currentFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigationopen, R.string.navigationclose);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        drawer =  findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            if (item.getItemId() == R.id.action_settings) {
                Intent intent = new Intent(this, SettingActivity.class);
                startActivity(intent);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Bundle bundle = new Bundle();

        Fragment fragment = null;

        String title = "";

        if (id == R.id.nav_home) {

            title = getString(R.string.home);
            fragment = new HomeFragment();

        } else if (id == R.id.nav_search) {

            title = getString(R.string.search);
            fragment = new SearchFragment();
            fragment.setArguments(bundle);

        } else if (id == R.id.nav_fav) {

            title = getString(R.string.fav);
            fragment = new FavouriteFragment();
            fragment.setArguments(bundle);

        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        }

        if (getSupportActionBar() != null)

            getSupportActionBar().setTitle(title);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
