package com.workera.apps.starwars;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<SWCharacter>>
{

    /** View that is displayed when the list is empty */
    private TextView mEmptyStateTextView;
    private Button mEmptyStateButtonView;

    /** Adapter for the list of characters */
    private SWAdapter adapter;

    /**
     * Constant value for the  loader ID.
     */
    private static final int SW_LOADER_ID = 1;

    private static final String TAG = MainActivity.class.getSimpleName(); ;

    /** URL for fetching the people data from the API */
    private static final String SWAPI_REQUEST_URL =
            "https://swapi.co/api/people";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mEmptyStateButtonView = (Button) findViewById(R.id.empty_button_view);

        // Adding an action to the button

        mEmptyStateButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Button Clicked");
                checkInternetConnection();
            }
        });


        // Create a new adapter that takes the list of star war characters as input
        adapter = new SWAdapter(this, new ArrayList<SWCharacter>());

        ListView list = (ListView) findViewById(R.id.starwar_list);
        list.setEmptyView(mEmptyStateTextView);
        list.setAdapter(adapter);


        // Setting an item click listener on the ListView to open the details for each character
         list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current star war character that was clicked on
                SWCharacter currentcharacter = adapter.getItem(position);
                Log.d(TAG, new StringBuilder().append("onItemClick: ").append(currentcharacter.getMass()).toString());

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("character", currentcharacter);
                startActivity(intent);
            }
        });

         checkInternetConnection();
    }

    /**
     * Function to check the internect Connectivity
     */

    public void checkInternetConnection(){

        // Get a reference to the ConnectivityManager to check state of network connectivity
              ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If there is a network connection, fetch data and make button invisible
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager to interact with loader.
            mEmptyStateButtonView.setVisibility(View.INVISIBLE);
            LoaderManager loaderManager = getLoaderManager();

            loaderManager.initLoader(SW_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateButtonView.setVisibility(View.VISIBLE);

            mEmptyStateTextView.setText(R.string.no_internet);

        }


}
    @Override
    public Loader<List<SWCharacter>> onCreateLoader(int i, Bundle bundle) {

        // Create a loader for the given URL
        return new SWLoader(this, SWAPI_REQUEST_URL);
    }
    @Override
    public void onLoadFinished(Loader<List<SWCharacter>> loader, List<SWCharacter> swCharacters) {
        // Hide loading indicator as the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);


        // Set empty state text to display when the list is empty
        mEmptyStateTextView.setText(R.string.no_data);


        // If there is a list of {@link SWCharacter}
        // Add them to the adapter


        if (swCharacters != null && !swCharacters.isEmpty()) {
            adapter.addAll(swCharacters);

        }
    }

    @Override
    public void onLoaderReset(Loader<List<SWCharacter>> loader) {
        // Loader reset, so clearing out our existing data.
        adapter.clear();
    }
}
