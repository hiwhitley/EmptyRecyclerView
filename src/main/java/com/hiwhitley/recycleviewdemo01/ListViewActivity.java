package com.hiwhitley.recycleviewdemo01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

/**
 * Created by hiwhitley on 2016/4/16.
 */
public class ListViewActivity extends AppCompatActivity {

    private View mEmptyView;
    private Button mAddButton;
    private Button mRemoveButton;

    private EmptyRecyclerView mRecyclerView;
    private MyRecycleViewAdapter mMyRecycleViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        findViews();
        setListener();
    }

    private void findViews() {
        mRecyclerView = (EmptyRecyclerView) findViewById(R.id.rv_ls);
        mEmptyView = findViewById(R.id.id_empty_view);
        mAddButton = (Button) findViewById(R.id.btn_add);
        mRemoveButton = (Button) findViewById(R.id.btn_delete);
    }

    private void setListener() {
        mMyRecycleViewAdapter = new MyRecycleViewAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mMyRecycleViewAdapter);
        mRecyclerView.setEmptyView(mEmptyView);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyRecycleViewAdapter.addData();
            }
        });

        mRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyRecycleViewAdapter.deleteData();
            }
        });
    }
}
