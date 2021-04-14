package com.honhai.foxconn.fxccalendar.colormanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;

import java.util.ArrayList;
import java.util.List;

public class ColorManagerActivity extends AppCompatActivity {
    private ArrayList<ColorItem> colorItemList = new ArrayList<>();
    private int[] color;
    private String[] colorLabel;
    private ColorRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_manager);
        initColorItem();
        RecyclerView colorList = findViewById(R.id.lv);
        adapter = new ColorRecyclerViewAdapter(this, colorItemList, colorList);
        colorList.setAdapter(adapter);
        colorList.setLayoutManager(new LinearLayoutManager(this));
        colorList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_backup);
        Drawable drawable = toolbar.getNavigationIcon();
        drawable.setTint(Data.getInstance().getThemeColor());
        drawable.setAlpha(255);
        toolbar.setNavigationIcon(drawable);
        TextView title = findViewById(R.id.toolbar_title);
        title.setTextColor(Data.getInstance().getThemeColor());
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        Drawable drawable = menu.getItem(0).getIcon();
        drawable.setAlpha(255);
        drawable.setTint(Data.getInstance().getThemeColor());
        menu.getItem(0).setIcon(drawable);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.confirm) {
            Toast.makeText(this, R.string.saveConfirm, Toast.LENGTH_SHORT).show();
            adapter.updateSharedPreferences();
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initColorItem() {
        color = getResources().getIntArray(R.array.eventColorArray);
        colorLabel = getResources().getStringArray(R.array.stringColor);
        for (int i = 0; i < color.length; i++) {
            ColorItem colorItem = new ColorItem(colorLabel[i], color[i]);
            colorItemList.add(colorItem);
        }
    }
}