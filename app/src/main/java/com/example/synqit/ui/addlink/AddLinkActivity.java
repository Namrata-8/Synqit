package com.example.synqit.ui.addlink;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.synqit.R;
import com.example.synqit.adapters.InfluencersAdapter;
import com.example.synqit.adapters.MostPopularLinkAdapter;
import com.example.synqit.adapters.SwipeCardAdapter;

public class AddLinkActivity extends AppCompatActivity {

    private RecyclerView recyclerView, rv, rv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link);

        recyclerView = findViewById(R.id.recyclerView);
        rv = findViewById(R.id.rv);
        rv2 = findViewById(R.id.rv2);
        recyclerView.setAdapter(new MostPopularLinkAdapter(AddLinkActivity.this));
        rv.setAdapter(new SwipeCardAdapter(AddLinkActivity.this));
        rv2.setAdapter(new InfluencersAdapter(AddLinkActivity.this));
    }
}