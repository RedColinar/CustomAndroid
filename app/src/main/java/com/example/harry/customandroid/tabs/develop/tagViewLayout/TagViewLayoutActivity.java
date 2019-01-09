package com.example.harry.customandroid.tabs.develop.tagViewLayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.Toast;

import com.example.harry.customandroid.R;
import com.example.harry.customandroid.base.BaseActivity;
import com.example.harry.customandroid.tabs.develop.tagViewLayout.widget.TagContainerLayout;
import com.example.harry.customandroid.tabs.develop.tagViewLayout.widget.TagView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * created by panqiang at 18-11-24
 */
public class TagViewLayoutActivity extends BaseActivity {

    @BindView(R.id.tag_container)
    TagContainerLayout tagContainerLayout;
    @BindView(R.id.change_edit_status)
    Button btEdit;
    @BindView(R.id.show_tag)
    Button btShowTag;
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
                Toast.makeText(TagViewLayoutActivity.this, "click position " + position, LENGTH_SHORT).show();
            }
            @Override
            public void onTagLongClick(int position, String text) {}

            @Override
            public void onTagCrossClick(int position) {
                Toast.makeText(TagViewLayoutActivity.this, "click cross position " + position, LENGTH_SHORT).show();
                tagContainerLayout.removeTag(position);
            }
        });

        tagContainerLayout.setTags(tabs);

        btEdit.setOnClickListener(v -> {
            tagContainerLayout.setEnableCross(!tagContainerLayout.isEnableCross());
            tagContainerLayout.setDragEnable(!tagContainerLayout.getDragEnable());
        });

        btShowTag.setOnClickListener(v -> {
            StringBuilder sb = new StringBuilder();
            for (String s : tagContainerLayout.getTags()) {
                sb.append(s);
            }
            Toast.makeText(this, sb.toString(), LENGTH_SHORT).show();
        });
    }
}
