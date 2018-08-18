package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {


    // Tag for log messages
    public static final String LOG_TAG = NewsActivity.class.getName();

    /**
     * Constant value for the news loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    // Base URI to Guardian site to query and receive JSON Response from
    private static final String GUARDIAN_REQUEST_URL = "https://content.guardianapis.com/search?api-key=76a7c048-fbdb-4f7a-890e-37c0a493a57e";


    // Adapter for the list of news
    private NewsAdapter mAdapter;


    // TextView that is displayed when the list is empty
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);


        // Find a reference to the {@link ListView} in the layout
        ListView newsListView = (ListView) findViewById(R.id.list);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        newsListView.setEmptyView(mEmptyStateTextView);

        // Check whether the system has an Internet connection
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            // Create a new adapter that takes an empty list of news as input
            mAdapter = new NewsAdapter(this, new ArrayList<News>());

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            newsListView.setAdapter(mAdapter);


            // Set an item click listener on the ListView, which sends an intent to a web browser
            // to open a website with more information about the selected news.
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    // Find the current news that was clicked on
                    News currentNews = mAdapter.getItem(position);

                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri newsUri = Uri.parse(currentNews.getURL());

                    // Create a new intent to view the news URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {

            // Display error if system does not have an Internet connection
            mEmptyStateTextView.setText(R.string.no_connection);

            // Hide loading indicator because the data has been loaded
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

        }
    }

    // Creates a New News Loader
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        Log.v("Loader", "Creating Loader");

        // Adding base URI
        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);

        // Return final URI
        return new NewsLoader(this, baseUri.toString());
    }


    /**
     * This method runs on the main UI thread after the background work has been
     * completed. This method receives as input, the return value from the doInBackground()
     * method. First we clear out the adapter, to get rid of news data from a previous
     * query to USGS. Then we update the adapter with the new list of news,
     * which will trigger the ListView to re-populate its list items.
     */
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> newsList) {
        Log.v("Loader", "Finished Loading");

        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Clear the adapter of previous news data
        mAdapter.clear();

        // Set empty state text to display "No newsList found."
        mEmptyStateTextView.setText(R.string.no_news);


        // If there is a valid list of {@link News}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (newsList != null && !newsList.isEmpty()) {
            mAdapter.addAll(newsList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        Log.v("Loader", "Resetting Loader");

        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }


}

