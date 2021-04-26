package com.example.recycleviewcardview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private   ArrayList<ExampleItem> mExampleList;
    MainActivity mainActivity;
    public static class ExampleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;
        //item點擊用
        private View view;
        public ExampleViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.image);
            mTextView1 = itemView.findViewById(R.id.textview1);
            mTextView2 = itemView.findViewById(R.id.textview2);
            view = itemView;
        }

    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleItemArrayList){
        mExampleList = exampleItemArrayList;
    }
    //連接layout
    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ExampleViewHolder exampleViewHolder = new ExampleViewHolder(v);
        return exampleViewHolder;
    }
    //元件的控制
    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        Picasso.with(holder.mImageView.getContext()).load(currentItem.getmImageResource()).fit().centerInside().into(holder.mImageView);
//        holder.mImageView.setImageResource(currentItem.getmImageResource());
        holder.mTextView1.setText("ID: " + currentItem.getmId());
        holder.mTextView2.setText("Likes: " + currentItem.getmLikes());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //do something
                Intent intent = new Intent(MainActivity.getMainActivityContext(),detail_activity.class);
                intent.putExtra("img",currentItem.getmImageResource())
                        .putExtra("id",currentItem.getmId())
                        .putExtra("likes",currentItem.getmLikes());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.getMainActivityContext().startActivity(intent);

            }
        });
    }
    //顯示的數量
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
