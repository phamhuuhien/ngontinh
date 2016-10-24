package com.hstudio.ngontinh.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hstudio.ngontinh.MainActivity;
import com.hstudio.ngontinh.R;
import com.hstudio.ngontinh.object.Type;

import java.util.List;

/**
 * Created by phhien on 6/6/2016.
 */
public class TypeAdapter extends RecyclerView.Adapter<TypeAdapter.MyViewHolder> {

    private MainActivity mMainActivity;
    private List<Type> typeList;

    public TypeAdapter(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public String link;

        public MyViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
        }

        @Override
        public void onClick(View view) {
            Log.d("TAG", "onClick " + getPosition() + " " + link);
            mMainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMainActivity.onTypeClick(typeList.get(getPosition()));
                }
            });
        }
    }

    public List<Type> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<Type> typeList) {
        this.typeList = typeList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.type, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.title.setText(typeList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return typeList.size();
    }
}
