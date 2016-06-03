package com.walmart.gtulla.nyts.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.walmart.gtulla.nyts.model.Article;
import com.walmart.gtulla.nyts.ArticleArrayAdapter;
import com.walmart.gtulla.nyts.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {
    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    List<Article> articles;
    ArticleArrayAdapter adapter;
    int  status_code =50;
    String beginDate = "";
    String endDate="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setup();
    }

    public void setup() {
        etQuery = (EditText)findViewById(R.id.etQuery);
        gvResults = (GridView)findViewById(R.id.gvResults);
        btnSearch = (Button)findViewById(R.id.btnSearch);
        articles = new ArrayList<>();
        adapter = new ArticleArrayAdapter(this, articles);
        gvResults.setAdapter(adapter);
        //hook listener for item click
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                Article article = articles.get(position);
                //i.putExtra("article", Parcels.wrap(article));
                i.putExtra("article", article);
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SearchOptionsActivity.class);
            startActivityForResult(i, status_code);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == status_code) {
            beginDate = data.getExtras().get("fromDate").toString();
            endDate = data.getExtras().get("toDate").toString();

        }
    }

    public void searchByQuery(View view) {
        String query = etQuery.getText().toString();
        //Toast.makeText(this, "Enter Search for "+query,Toast.LENGTH_SHORT).show();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams reqParams = new RequestParams();
        reqParams.put("api-key", "9541123aee094fa1a3d3da9cfe54d21e");
        reqParams.put("page", 0);
        reqParams.put("q", query);
        if(beginDate != null && ! beginDate.isEmpty() &&
                endDate != null && ! endDate.isEmpty()) {
            reqParams.put("begin_date", beginDate);
            reqParams.put("end_date", endDate);
        }

        client.get(url, reqParams, new JsonHttpResponseHandler() {
            /*
            @Override
            public void onStart() {
            }*/

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonArray = null;
                try {
                  articleJsonArray = response.getJSONObject("response").getJSONArray("docs");
                  adapter.addAll(Article.getArticles(articleJsonArray));
                  Log.d("DEBUG", articleJsonArray.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(SearchActivity.this, "FAIL", Toast.LENGTH_LONG).show();
            }
        });

    }
}
