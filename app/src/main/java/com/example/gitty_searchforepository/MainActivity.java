package com.example.gitty_searchforepository;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    EditText search;
    TextView urldisplay, errormessage;
    TextView results;
    ProgressBar progressBar;

    public class GitHubQueryTask extends AsyncTask<URL,Void, String>
    {
        @Override
        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(URL... urls)
        {
            URL searchurl = urls[0];
            String res= null;
            try
            {
                res =  NetworkUtils.getResponseFromHttpUrl(searchurl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String s)
        {
            progressBar.setVisibility(View.INVISIBLE);
            if(s!=null&& !s.equals(""))
            {
                showresult();
                results.setText(s);
            }

            else
                showerrormessage();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int selecteditem = item.getItemId();
        if(selecteditem==R.id.action_search)
        {
            makeGitHubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void makeGitHubSearchQuery()
    {
        String githubquery= search.getText().toString();
        URL githubqueryURL = NetworkUtils.buildUrl(githubquery);
        urldisplay.setText(githubqueryURL.toString());
        GitHubQueryTask ob = new GitHubQueryTask();
        ob.execute(githubqueryURL);

    }

    private void showerrormessage()
    {
        errormessage.setVisibility(View.VISIBLE);
        results.setVisibility(View.INVISIBLE);
    }
    private void showresult()
    {
        errormessage.setVisibility(View.INVISIBLE);
        results.setVisibility(View.VISIBLE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = findViewById(R.id.et_search_box);
        urldisplay= findViewById(R.id.tv_url_display);
        results= findViewById(R.id.cv_tv_result);
        errormessage = findViewById(R.id.error_message);
        progressBar =findViewById(R.id.progressBar);

    }
}