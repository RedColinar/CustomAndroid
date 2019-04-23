package com.example.harry.customandroid.tabs.widgets.expand;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expandview.ExpandView;
import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;
import com.example.harry.customandroid.tabs.widgets.expand.ExpandAdapter.LegendItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ExpandActivity
 * Created by Harry on 2018/4/5.
 * ExpandView 可用于折叠展开复杂布局
 *
 */

public class ExpandActivity extends BaseActivity {

    private static final List<LegendItem> legends = new ArrayList<>();
    static {
        legends.add(new LegendItem("100%", "#FF2E2E", "RED"));
        legends.add(new LegendItem("75%", "#2EFF2E", "GREEN"));
        legends.add(new LegendItem("50%", "#2E2EFF", "BLUE"));
    }

    @BindView(R.id.expand_text)
    ExpandView expandTextView;
    @BindView(R.id.expand_recycler_view)
    ExpandView expandRecyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.activity_expand;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);
        textView.setText(R.string.expand_text);
        expandTextView.setSourceView(textView);

        RecyclerView recyclerView = createLinearRecyclerView(new ExpandAdapter(legends));
        expandRecyclerView.setSourceView(recyclerView);
    }

    private RecyclerView createLinearRecyclerView(RecyclerView.Adapter adapter) {
        RecyclerView recyclerView = new RecyclerView(this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setBackgroundColor(getColor(R.color.white));
        recyclerView.setNestedScrollingEnabled(false);

        return recyclerView;
    }

    @Override
    public int getTitleId() {
        return R.string.expand_title;
    }
}
