package com.example.harry.customandroid.tabs.develop.timeline;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.harry.customandroid.R;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater inflater;
    private List<Trace> traceList = new ArrayList<>();
    private static final int TYPE_TOP = 0x0000;
    private static final int TYPE_NORMAL= 0x0001;

    TimelineAdapter(Context context, List<Trace> traceList) {
        inflater = LayoutInflater.from(context);
        this.traceList = traceList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(inflater.inflate(R.layout.recyclerview_item_timeline, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder itemHolder = (ViewHolder) holder;
        if (getItemViewType(position) == TYPE_TOP) {
            // 第一行头的竖线不显示
            itemHolder.tvTopLine.setVisibility(View.INVISIBLE);
            // 字体颜色加深
            itemHolder.tvAcceptTime.setTextColor(0xff555555);
            itemHolder.tvAcceptStation.setTextColor(0xff555555);
        } else if (getItemViewType(position) == TYPE_NORMAL) {
            itemHolder.tvTopLine.setVisibility(View.VISIBLE);
            itemHolder.tvAcceptTime.setTextColor(0xff999999);
            itemHolder.tvAcceptStation.setTextColor(0xff999999);
        }

        itemHolder.tvDot.setBackgroundResource(R.drawable.timeline_dot);
        itemHolder.bindHolder(traceList.get(position));
    }

    @Override
    public int getItemCount() {
        return traceList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_NORMAL;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAcceptTime, tvAcceptStation;
        private TextView tvTopLine, tvDot;
        ViewHolder(View itemView) {
            super(itemView);
            tvAcceptTime = itemView.findViewById(R.id.tvAcceptTime);
            tvAcceptStation = itemView.findViewById(R.id.tvAcceptStation);
            tvTopLine = itemView.findViewById(R.id.tvTopLine);
            tvDot = itemView.findViewById(R.id.tvDot);
        }

        void bindHolder(Trace trace) {
            tvAcceptTime.setText(trace.getAcceptTime());
            tvAcceptStation.setText(trace.getAcceptStation());
        }
    }
}

