package com.example.harry.customandroid.activities.widgets.expand;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.harry.customandroid.R;

import java.util.List;

/**
 * CustomAndroid
 * Created by Harry on 2018/4/6.
 */

public class ExpandAdapter extends RecyclerView.Adapter<ExpandAdapter.LegendsViewHolder> {

    private List<LegendItem> list;
    private Context context;

    public ExpandAdapter(List<LegendItem> list) {
        this.list = list;
    }

    @Override
    public LegendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_legend, parent, false);
        return new LegendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LegendsViewHolder holder, int position) {
        LegendItem item = list.get(position);
        holder.percentageTextView.setText(item.getPercentage());
        holder.colorImageView.setBackgroundColor(Color.parseColor(item.getColor()));
        holder.nameTextView.setText(item.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class LegendsViewHolder extends RecyclerView.ViewHolder {
        TextView percentageTextView;
        ImageView colorImageView;
        TextView nameTextView;

        LegendsViewHolder(View itemView) {
            super(itemView);

            percentageTextView = itemView.findViewById(R.id.percentage_textView);
            colorImageView = itemView.findViewById(R.id.color_imageView);
            nameTextView = itemView.findViewById(R.id.name_textView);
        }
    }

    public static class LegendItem {
        String percentage;
        String color;
        String name;

        public LegendItem(String percentage, String color, String name) {
            this.percentage = percentage;
            this.color = color;
            this.name = name;
        }

        public String getPercentage() {
            return percentage;
        }

        public String getColor() {
            return color;
        }

        public String getName() {
            return name;
        }
    }
}
