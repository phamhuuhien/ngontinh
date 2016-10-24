package com.hstudio.doctruyen.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hstudio.doctruyen.R;
import com.hstudio.doctruyen.StoryActivity;
import com.hstudio.doctruyen.object.Story;
import com.hstudio.doctruyen.utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by phhien on 6/8/2016.
 */
public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.MyViewHolder> {

    private List<Story> mStoryList;
    private Context mContext;

    public StoryAdapter(List<Story> storyList, Context context) {
        mStoryList = storyList;
        mContext = context;
    }

    public List<Story> getStoryList() {
        return mStoryList;
    }

    public void setStoryList(List<Story> storyList) {
        this.mStoryList = storyList;
    }

    public void addStoryList(List<Story> stories) {
        this.mStoryList.addAll(stories);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.truyen, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(mStoryList.get(position).getTitle());
        if(!TextUtils.isEmpty(mStoryList.get(position).getImage())) {
            Picasso.with(mContext).load(mStoryList.get(position).getImage()).transform(new CircleTransform()).into(holder.image);
        }
        holder.link = mStoryList.get(position).getUrl();
        holder.author.setText(mStoryList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return mStoryList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public ImageView image;
        public TextView author;
        public String link;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            image = (ImageView) view.findViewById(R.id.image);
            author = (TextView) view.findViewById(R.id.author);
        }

        @Override
        public void onClick(View view) {
            Log.d("TAG", "onClick " + getPosition() + " " + link);
            Intent intent = new Intent(mContext, StoryActivity.class);
            intent.putExtra("LINK", link);
            mContext.startActivity(intent);
        }
    }
}
