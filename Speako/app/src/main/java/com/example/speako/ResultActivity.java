package com.example.speako;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.TextView;

import com.example.speako.databinding.ActivityResultBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;

    TextView textsample;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        description_webscrape dw = new description_webscrape();
        dw.execute();
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



    private class description_webscrape extends AsyncTask<Void,Void,Void> {


        protected void  onPreExecute(){
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

//            org.jsoup.nodes.Document document=null;
//
//            document= (Document) Jsoup.connect("https://stackoverflow.com");
//
//            org.jsoup.select.Elements elements=document.getElementsByClass("s-item__title");
//            String title=elements.text();
//            textsample.setText(title);
            return null;
        }

        protected void onPostExecute(Void avoid){

        }

    }

}

