package com.hstudio.ngontinh.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hstudio.ngontinh.ChapActivity;
import com.hstudio.ngontinh.R;
import com.hstudio.ngontinh.object.ChapItem;

import java.util.List;

/**
 * Created by phhien on 6/16/2016.
 */
public class ChapAdapter extends RecyclerView.Adapter<ChapAdapter.MyViewHolder> {

    private List<ChapItem> chaps;
    private Context mContext;

    public ChapAdapter(Context context, List<ChapItem> chaps) {
        mContext = context;
        this.chaps = chaps;
    }

    public List<ChapItem> getChaps() {
        return chaps;
    }

    public void addChaps(List<ChapItem> chaps) {
        this.chaps.addAll(chaps);
    }

    @Override
    public ChapAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chap_layout, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ChapAdapter.MyViewHolder holder, int position) {
        holder.title.setText(chaps.get(position).getName());
        holder.id = chaps.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return chaps.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public int id;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public void onClick(View view) {
            Log.d("TAG", "onClick " + getPosition() + "link=" + id);
            Intent intent = new Intent(mContext, ChapActivity.class);
            intent.putExtra("LINK", id);
            mContext.startActivity(intent);
        }
    }
}
