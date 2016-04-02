package nl.intratuin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    SearchView svIntratuin;
    ListView lSearch;
    // Progress Dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        svIntratuin = (SearchView) findViewById(R.id.svIntratuin);
        lSearch = (ListView) findViewById(R.id.lSearch);

        svIntratuin.setOnQueryTextListener(this);
        svIntratuin.setOnCloseListener(this);

        new JsonSearchTask().execute();

    }

    public boolean onQueryTextChange(String newText) {

        return false;
    }

    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    public boolean onClose() {

        return false;
    }

    private class JsonSearchTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SearchActivity.this);
            pDialog.setMessage("@string/loading");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }
}