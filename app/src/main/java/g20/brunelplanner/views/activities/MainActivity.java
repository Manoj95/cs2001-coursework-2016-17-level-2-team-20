package g20.brunelplanner.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import g20.brunelplanner.R;
import g20.brunelplanner.models.Timetable;
import g20.brunelplanner.views.activities.adapters.RecyclerViewAdapter;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private Realm realm = Realm.getDefaultInstance();

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        // Start the recycler view
        setUpRecyclerView();

    }

    private void setUpRecyclerView() {
        // This is needed for some reason
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Set the adapter for the recycler view and populate with the realm data
        recyclerView.setAdapter(new RecyclerViewAdapter(this, realm.where(Timetable.class).findAllAsync()));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                return true;
            // Delete the realm db and start the login activity when
            // the user wants to logout
            case R.id.action_logout:
                realm.close();
                Realm.deleteRealm(realm.getConfiguration());
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (realm != null) {
            realm.close();
        }
        super.onDestroy();
    }
}
