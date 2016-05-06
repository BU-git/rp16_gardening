package nl.intratuin.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import nl.intratuin.LoginActivity;
import nl.intratuin.R;
import nl.intratuin.SearchActivity;
import nl.intratuin.manager.AuthManager;


public class ToolBarActivity extends AppCompatActivity {
    protected static String profileItem = "Profile";
    Toolbar toolBar;
    MenuItem item;
    ImageView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(null);
        initToolBar();
    }

    private void initToolBar() {
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        toolbarTitle = (ImageView) findViewById(R.id.toolbarTitle);
        toolbarTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ToolBarActivity.this, SearchActivity.class));
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        item = menu.findItem(R.id.profile);
        item.setTitle(profileItem);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.profile):
                Toast.makeText(ToolBarActivity.this, "Profile Page", Toast.LENGTH_LONG).show();
                break;
            case (R.id.logout): {
                ToolBarActivity.this.getSharedPreferences(AuthManager.PREF_FILENAME, Context.MODE_PRIVATE)
                        .edit()
                        .clear()
                        .commit();
                Intent loginIntent = new Intent(ToolBarActivity.this, LoginActivity.class);
                loginIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(loginIntent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}