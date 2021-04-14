package com.honhai.foxconn.fxccalendar.colormanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static com.honhai.foxconn.fxccalendar.colormanager.ColorAdapter.SP_COLOR_LABEL;

public class ColorRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<ColorItem> list;
    private Context context;
    private SharedPreferences mSharedPreferences;
    private RecyclerView recyclerView;
    private int choose = -1;
    private String[] strings;

    ColorRecyclerViewAdapter(Context context, ArrayList<ColorItem> list, RecyclerView recyclerView) {
        this.list = list;
        this.context = context;
        this.recyclerView = recyclerView;
        strings = new String[list.size()];
        mSharedPreferences = context.getSharedPreferences(SP_COLOR_LABEL, Context.MODE_PRIVATE);
        for (int i = 0 ; i < list.size() ; i++){
            strings[i] = mSharedPreferences.getString(list.get(i).getColorName(), "");
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.color_item_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        ColorItem colorItem = list.get(i);
        ColorImageView colorImage = holder.colorImage;
        EditText colorName = holder.colorName;
        final ImageView clear = holder.clear;

        colorImage.setColor(colorItem.getColorImageId());

        colorName.setHint(colorItem.getColorName());
        colorName.setText(strings[i]);
        colorName.setSelection(colorName.getText().toString().length());
        setCursorColor(colorName, colorItem.getColorImageId());

        Drawable drawable = context.getDrawable(R.drawable.ic_clear).mutate();
        drawable.setTint(colorItem.getColorImageId());
        drawable.setAlpha(255);
        clear.setImageDrawable(drawable);
        if (i == choose) {
            clear.setVisibility(View.VISIBLE);
            colorName.requestFocus();
        }else
            clear.setVisibility(View.INVISIBLE);

        colorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.equals("") || s.length() > 0) {
                    clear.setVisibility(View.VISIBLE);
                } else {
                    clear.setVisibility(View.INVISIBLE);
                }

                strings[i] = s.toString();

            }


            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setCursorColor(EditText view, @ColorInt int color) {
        try {
            // Get the cursor resource id
            Field field = TextView.class.getDeclaredField("mCursorDrawableRes");
            field.setAccessible(true);
            int drawableResId = field.getInt(view);

            // Get the editor
            field = TextView.class.getDeclaredField("mEditor");
            field.setAccessible(true);
            Object editor = field.get(view);

            // Get the drawable and set a color filter
            Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableResId);
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);
            Drawable[] drawables = {drawable, drawable};

            // Set the drawables
            field = editor.getClass().getDeclaredField("mCursorDrawable");
            field.setAccessible(true);
            field.set(editor, drawables);
        } catch (Exception ignored) {
        }
    }

    void updateSharedPreferences(){
        for (int i = 0 ; i < list.size() ; i++){
            mSharedPreferences.edit().putString(list.get(i).getColorName(),strings[i]).apply();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        ColorImageView colorImage;
        EditText colorName;
        ImageView clear;

        @SuppressLint("ClickableViewAccessibility")
        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            colorImage = itemView.findViewById(R.id.color_img);
            colorName = itemView.findViewById(R.id.color_name);
            clear = itemView.findViewById(R.id.clear);

            colorName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v , MotionEvent e) {
                    if (e.getActionMasked() == MotionEvent.ACTION_UP) {
                        choose = getAdapterPosition();
                        notifyDataSetChanged();
                    }
                    return false;
                }
            });

            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorName.setText("");
                }
            });
        }
    }
}
