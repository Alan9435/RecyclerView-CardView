package com.example.recycleviewcardview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton floatbtn;
    private ArrayList<ExampleItem> exampleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        floatbtn = findViewById(R.id.floatbtn);
        floatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exampleList.clear();
                getData();
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();    //通知adapter數據變更
            }
        });

        getData();


        mRecyclerView = findViewById(R.id.recycleview);
        mRecyclerView.setHasFixedSize(true);                //確定item不會影響RecyclerView的寬高時可以設置
        mRecyclerView.setItemViewCacheSize(100);  //設定緩存的item數
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ExampleAdapter(exampleList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        recyclerViewAction(mRecyclerView,exampleList,mAdapter);
    }

    private void getData() {
        for (int i=0;i<=30;i++){
            exampleList.add(new ExampleItem(R.drawable.ic_baseline_audiotrack_24, String.valueOf(i), String.valueOf(i)));
            exampleList.add(new ExampleItem(R.drawable.ic_baseline_directions_boat_24, String.valueOf(i), String.valueOf(i)));
            exampleList.add(new ExampleItem(R.drawable.ic_android_black_24dp, String.valueOf(i), String.valueOf(i)));
        }
    }

    private void recyclerViewAction(RecyclerView recyclerView, final ArrayList<ExampleItem> choose, final RecyclerView.Adapter myAdapter) {
        ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            //管理操作
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(0
                        , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            //上下拖曳
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            //左右滑動
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                    case ItemTouchHelper.RIGHT:
                        choose.remove(position);
                        myAdapter.notifyItemRemoved(position);
                        break;
                }
            }
        });
        helper.attachToRecyclerView(recyclerView);
    }


}