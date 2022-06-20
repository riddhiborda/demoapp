package com.example.mydemoapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.mydemoapp.util.PaginationScrollListener;

public class RecyclerviewPagination extends AppCompatActivity {
    RecyclerView rcvList;
    private boolean isLoading = false;
    private int currentPage = 1;
    private boolean isLastPage = false;
    MyAdapter adapter;
    LinearLayoutManager manager;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview_pagination);
        initList();
    }

    private void initList() {
        rcvList = findViewById(R.id.rcvList);
        progressBar = findViewById(R.id.progressBar);

        adapter = new MyAdapter();
        manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcvList.setLayoutManager(manager);
        rcvList.setAdapter(adapter);
        rcvList.addOnScrollListener(new PaginationScrollListener(manager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();

            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }

    private void loadNextPage() {
        progressBar.setVisibility(View.VISIBLE);
        Handler h = new Handler(Looper.getMainLooper());
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                isLoading = false;

                adapter.updateData(10);
                if(adapter.getSize() == 30)
                {
                    isLastPage=true;
                }
                Log.e("TAG", "loadNextPage: " + currentPage);
            }
        }, 2000);

    }
}