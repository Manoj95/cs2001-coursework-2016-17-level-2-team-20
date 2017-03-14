package g20.brunelplanner.views.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import g20.brunelplanner.R;
import g20.brunelplanner.controllers.databases.RealmController;
import g20.brunelplanner.views.activities.fragments.AboutFragment;
import g20.brunelplanner.views.activities.fragments.BuildingsFragment;
import g20.brunelplanner.views.activities.fragments.CustomEventsFragment;
import g20.brunelplanner.views.activities.fragments.HelpFragment;
import g20.brunelplanner.views.activities.fragments.ModulesFragment;
import g20.brunelplanner.views.activities.fragments.NotesFragment;
import g20.brunelplanner.views.activities.fragments.SettingsFragment;
import g20.brunelplanner.views.activities.fragments.TimetableFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    protected RealmController realmController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        // Adds animation to hamburger icon
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            setDefault();
        }

        //This changes the student id to the actual student ID.
        setStudentId();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_logout:
                realmController = RealmController.getInstance();
                // Delete the realm db and start the login activity when
                // the user wants to logout
                realmController.deleteRealm();
                startActivity(new Intent(this, LoginActivity.class));
                // Might not be needed
                finish();
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;

        switch(item.getItemId()) {
            case R.id.nav_timetable:
                fragmentClass = TimetableFragment.class;
                break;
            case R.id.nav_modules:
                fragmentClass = ModulesFragment.class;
                break;
            case R.id.nav_buildings:
                fragmentClass = BuildingsFragment.class;
                break;
            case R.id.nav_custom_events:
                fragmentClass = CustomEventsFragment.class;
                break;
            case R.id.nav_notes:
                fragmentClass = NotesFragment.class;
                break;
            case R.id.nav_about:
                fragmentClass = AboutFragment.class;
                break;
            case R.id.nav_settings:
                fragmentClass = SettingsFragment.class;
                break;
            case R.id.nav_help:
                fragmentClass = HelpFragment.class;
                break;
            default:
                fragmentClass = TimetableFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        item.setChecked(true);
        // Set action bar title
        setTitle(item.getTitle());
        // Close the navigation drawer
        drawerLayout.closeDrawers();
        return true;
    }

    private void setDefault() {
        Fragment fragment = null;
        Class fragmentClass;
        fragmentClass = TimetableFragment.class;

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        navigationView.getMenu().findItem(R.id.nav_timetable).setChecked(true);
        // Set action bar title
        setTitle(navigationView.getMenu().findItem(R.id.nav_timetable).getTitle());
    }

    private void setStudentId() {
        View headerView = navigationView.getHeaderView(0);

        TextView studentId = (TextView) headerView.findViewById(R.id.nav_student_id);
        SharedPreferences sharedPreferences = PreferenceManager.
                getDefaultSharedPreferences(this);

        studentId.setText(sharedPreferences.getString("studentId", "Planner"));
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
