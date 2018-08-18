package com.example.android.newsapp;

// Populates the List with News Articles

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;


public class NewsAdapter extends ArrayAdapter<com.example.android.newsapp.News> {
    /**
     * Create a new {@link NewsAdapter} object
     *
     * @param context     is the current context (i.e. Activity) that the adapater is being cread in respect of
     * @param newsList is the list of (@link News)s to be displayed.
     */
    public NewsAdapter(Activity context, List<News> newsList) {
        //  Here, we initialize the Array Adapter's internal storage for the context and the list.
        //  the second argument is used when the ArrayAdapter is populating a single TextView
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, newsList);

    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position    The AdapterView position that is requesting a view
     * @param convertView The recycled view to populate
     *                    (search online for android view recycling for more info)
     * @param parent      The parent ViewGroup that is used for inflation
     * @return The View for the position in the AdapterView
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_view, parent, false);
        }

        // Get the {@link News} object located at this position in the list
        com.example.android.newsapp.News currentNews = getItem(position);

        //  Find the TextView in the list_view.xml layout with the ID newsTitleTextView
        TextView newsTitleTextView = (TextView) listItemView.findViewById(R.id.newsTitleTextView);

        //  Find the TextView in the list_view.xml layout with the ID sectionTextView
        TextView sectionTextView = (TextView) listItemView.findViewById(R.id.sectionTextView);

        //  Get the News Title from the current News object and set to variable initialTitleString
        String initialTitleString = currentNews.getNewsTitle();
        String titleString;

        // Split the initialTitleString into substring without the un-necessary data
        if (initialTitleString.contains("-")) {
            titleString = initialTitleString.substring(0, initialTitleString.indexOf("-"));
        } else {
            titleString = initialTitleString;
        }


        //  Get the Section Title from the current News object and set to variable sectionString
        String sectionString = currentNews.getSection();

        // Set this text on the new ListView
        sectionTextView.setText(sectionString);

        newsTitleTextView.setText(titleString);



        //  Find the TextView in the list_view.xml layout with the ID newsDateTextView
        TextView newsDateTextView = (TextView) listItemView.findViewById(R.id.newsDateTextView);


        //  Get the News Date from the current News object and set to variable initialDateString
        String initialDateString = currentNews.getNewsDate();
        String dateString;

        // Split the initialDateString into substring without the un-necessary data
        if (initialDateString.contains("T")) {
            dateString = initialDateString.substring(0, initialDateString.indexOf("T"));
        } else {
            dateString = initialDateString;
        }

        //  Get the News Date from the current News object and break apart to specific required formats
        newsDateTextView.setText(dateString);

        // Return the whole list item layout (containing 3 TextViews) so that it can be displayed
        return listItemView;

    }}