package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;
public class MainActivity extends AppCompatActivity {
    public XMLParser xmlParser;
    public ArrayList<InfoRSS> links;
    public static int positionid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView usersList = findViewById(R.id.usersList);
        TextView contentView = findViewById(R.id.contentView);

        contentView.setText("Загрузка...");
        new Thread(new Runnable() {
            public void run() {
                try{
                    String content = download("https://people.onliner.by/feed");
                    usersList.post(new Runnable() {
                        public void run() {
                            XMLParser parser = new XMLParser();
                            if(parser.parse(content))
                            {
                                ArrayAdapter<InfoRSS> adapter = new ArrayAdapter(getBaseContext(),
                                        android.R.layout.simple_list_item_1, parser.getUsers());
                                usersList.setAdapter(adapter);

                                contentView.setText("Загруженно объектов: " + adapter.getCount());
                            }
                        }
                    });
                }
                catch (IOException ex){
                    contentView.post(new Runnable() {
                        public void run() {
                            contentView.setText("Ошибка: " + ex.getMessage());
                        }
                    });
                }
            }
        }).start();

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                positionid = position;
                Intent intent = new Intent(MainActivity.this, News.class);
                startActivity(intent);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101){
            if (resultCode == Activity.RESULT_OK){


               // xmlParser.notifyDataSetChanged();
            }
        }

    }
    private String download(String urlPath) throws IOException{
        StringBuilder xmlResult = new StringBuilder();
        BufferedReader reader = null;
        InputStream stream = null;
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(urlPath);
            connection = (HttpsURLConnection) url.openConnection();
            stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line=reader.readLine()) != null) {
                xmlResult.append(line);
            }
            return xmlResult.toString();
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (stream != null) {
                stream.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}