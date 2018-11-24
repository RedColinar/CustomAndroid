package com.example.harry.customandroid.tabs.develop.tagViewLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;
import com.example.harry.customandroid.tabs.develop.tagViewLayout.widget.TagContainerLayout;
import com.example.harry.customandroid.tabs.develop.tagViewLayout.widget.TagView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * created by panqiang at 18-11-24
 */
public class TagViewLayoutActivity extends BaseActivity {

    @BindView(R.id.tag_container)
    TagContainerLayout tagContainerLayout;

    @Override
    public int getLayoutId() {
        return R.layout.activity_tag_view_layout;
    }

    @Override
    public int getTitleId() {
        return R.string.tag_view_title;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        List<String> tabs = new ArrayList<>();
        tabs.add("Java");
        tabs.add("C++");
        tabs.add("Python");
        tabs.add("Swift");
        tabs.add("PHP");
        tabs.add("JavaScript");

        tagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                Toast.makeText(TagViewLayoutActivity.this, "click position " + position, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onTagLongClick(int position, String text) {}

            @Override
            public void onTagCrossClick(int position) {}
        });

        tagContainerLayout.setTags(tabs);
    }
}
