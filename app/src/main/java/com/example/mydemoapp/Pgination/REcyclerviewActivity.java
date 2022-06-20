package com.example.mydemoapp.Pgination;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mydemoapp.R;
import com.google.gson.Gson;

public class REcyclerviewActivity extends AppCompatActivity {

    RecyclerView rcvList;
    boolean isLastPage;
    boolean isLoading;
    int page=1;
    Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);

        rcvList=findViewById(R.id.rcvList);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.item, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.setting:
                delete();
                Toast.makeText(this, "setting", Toast.LENGTH_SHORT).show();
            break;



        }
        return super.onOptionsItemSelected(item);
    }

    private void delete() {
    }

    private void initView() {
         adapter=new Adapter();
        LinearLayoutManager manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rcvList.setLayoutManager(manager);
        rcvList.setAdapter(adapter);


        rcvList.addOnScrollListener(new PaginationScrollListener(manager) {
            @Override
            protected void loadMoreItems() {
                page=page+1;
                loadMore();
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

    private void loadMore() {
        if(adapter.getsize() != 30) {
            adapter.updateData();
        }
        else
        {
            Toast.makeText(this, "no data ", Toast.LENGTH_SHORT).show();
        }
    }

}