package com.example.gitty_searchforepository;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

class NetworkUtils
{
    public static String getResponseFromHttpUrl(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection)(url.openConnection());
        try {
            InputStream in = urlConnection.getInputStream();
            Scanner scan = new Scanner(in);
            scan.useDelimiter("\\A");
            boolean hasInput = scan.hasNext();
            if (hasInput) {
                return scan.next();
            } else
                return null;
        }
             finally{
                 urlConnection.disconnect();

            }
    }

    public static URL buildUrl(String githubquery)
    {
        final String GITHUB_BASE_URL = "https://api.github.com/search/repositories";
        final String PARAM_QUERY = "q";
        final String PARAM_SORT = "sort";

        Uri builtUri= Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY,githubquery)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }
}

