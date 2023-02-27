package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.util.ArrayList;

public class News extends AppCompatActivity {
    public ArrayList<InfoRSS> infoRSSES;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        WebView webView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // по позиции получаем выбранный элемент
        String selectedItem = XMLParser.links.get(MainActivity.positionid);

        webView.loadUrl(selectedItem);
        this.setTitle("WebView");
    }

}