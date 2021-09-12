package com.example.nytimes;

import static com.example.nytimes.controller.API.getMostPopular;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.nytimes.adapters.ArticlesAdapter;
import com.example.nytimes.model.Article;
import com.example.nytimes.model.Media;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //declare objects
    private ArticlesAdapter adapter;
    private ProgressDialog loading;
    public List<Article> articles = new ArrayList<>();
    public List<Article> filtered = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup recycler view and loading dialog
        RecyclerView rv = findViewById(R.id.articles);
        loading = new ProgressDialog(this);
        loading.setTitle("Getting articles");
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ArticlesAdapter(this, filtered);
        rv.setAdapter(adapter);
        loadData(1);
    }

    public void loadData(int period){

        if(period == 1) {
            loading.setMessage("Updating articles from today.");
        }else if(period == 7) {
            loading.setMessage("Updating articles from this week.");
        }else{
            loading.setMessage("Updating articles from this month.");
        }

        //show loading dialog
        loading.show();

        //make a call to new york times api endpoint
        getMostPopular(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                runOnUiThread(() -> {
                    //dismiss loading dialog
                    if(loading.isShowing()){
                        loading.dismiss();
                    }
                    //toast error message
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                });
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Response response) {
                if(response.isSuccessful()) {
                    try {

                        //get endpoint response
                        JSONObject main = new JSONObject(response.body().string());
                        JSONArray results = main.getJSONArray("results");
                        filtered.clear();
                        articles.clear();

                        for(int x = 0; x < results.length(); x++){

                            JSONObject single = results.getJSONObject(x);
                            List<Media> media = new ArrayList<>();

                            JSONArray attached = single.getJSONArray("media");

                            for(int y = 0;  y < attached.length(); y++){

                                JSONObject metadata = attached.getJSONObject(y).getJSONArray("media-metadata").getJSONObject(0);

                                media.add(new Media(attached.getJSONObject(y).getString("type"),
                                        attached.getJSONObject(y).getString("caption"),
                                        attached.getJSONObject(y).getString("copyright"),
                                        Uri.parse(metadata.getString("url"))));
                            }

                            //add article to filtered list and all articles list
                            filtered.add(new Article(single.getString("url"), single.getString("title"), single.getString("abstract"), single.getString("byline"), single.getString("published_date"), media));
                            articles.add(new Article(single.getString("url"), single.getString("title"), single.getString("abstract"), single.getString("byline"), single.getString("published_date"), media));

                        }

                        runOnUiThread(() -> {
                            if(loading.isShowing()){
                                loading.dismiss();
                            }
                            adapter.notifyDataSetChanged();
                        });

                    }catch(Exception e){

                        runOnUiThread(() -> {
                            //dismiss loading dialog
                            if(loading.isShowing()){
                                loading.dismiss();
                            }
                            //toast error message
                            Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        });

                    }
                }
            }
        }, period);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.filter, menu);

        SearchView search = (SearchView) menu.findItem(R.id.search).getActionView();
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //clear filtered list
                filtered.clear();

                //loop through all articles and check if title matches search query - ignoring case
                for(Article article : articles){
                    if(article.getTitle().toLowerCase().contains(newText.toLowerCase())){
                        //add to filtered list
                        filtered.add(article);
                    }
                }
                //notify adapter to refresh recycler view
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.today:
                // request articles for 1 day period
                loadData(1);
                break;
            case R.id.week:
                // request articles for 7 day period
                loadData(7);
                break;
            case R.id.month:
                // request articles for 30 day period
                loadData(30);
                break;
        }
        return true;
    }
}