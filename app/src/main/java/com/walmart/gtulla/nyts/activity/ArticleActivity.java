package com.walmart.gtulla.nyts.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.walmart.gtulla.nyts.model.Article;
import com.walmart.gtulla.nyts.R;

public class ArticleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        //Article article = (Article) Parcels.unwrap(getIntent().getParcelableExtra("article"));
        Article article = (Article) getIntent().getParcelableExtra("article");
        WebView webView = (WebView)findViewById(R.id.wvArticle);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl(article.getWebUrl());
    }
}
