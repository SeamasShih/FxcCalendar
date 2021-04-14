package com.honhai.foxconn.fxccalendar.colormanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;

import java.lang.reflect.Field;
import java.util.List;

public class ColorAdapter extends ArrayAdapter {
    private int resourceId;
    private SharedPreferences mSharedPreferences;
    public static String SP_COLOR_LABEL = "colorLabel";
    private int index = -1;

    public ColorAdapter(Context context, int editTextResourceId, List<ColorItem> objects) {
        super(context, editTextResourceId, objects);
        resourceId = editTextResourceId;
        mSharedPreferences = getContext().getSharedPreferences(SP_COLOR_LABEL, Context.MODE_PRIVATE);
    }


    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final ColorItem colorItem = (ColorItem) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ColorImageView colorImage = view.findViewById(R.id.color_img);
        final EditText colorName = view.findViewById(R.id.color_name);
        colorImage.setColor(colorItem.getColorImageId());
        colorName.setHint(colorItem.getColorName());
        final ImageView clear = view.findViewById(R.id.clear);
        Drawable drawable = getContext().getDrawable(R.drawable.ic_clear);
        drawable.setTint(colorItem.getColorImageId());
        drawable.setAlpha(255);
        clear.setImageDrawable(drawable);
        setCursorColor(colorName,colorItem.getColorImageId());
        colorName.setText(mSharedPreferences.getString(colorItem.getColorName(), ""));

        colorName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    index = position;
                    if (((EditText)v).getText().length() > 0)
                        clear.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });


        colorName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    clear.setVisibility(View.VISIBLE);
                } else {
                    clear.setVisibility(View.INVISIBLE);
                }

                mSharedPreferences.edit().putString(colorItem.getColorName(), s.toString()).apply();

            }


            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        colorName.clearFocus();
        if (index != -1 && index == position) {
            colorName.requestFocus();
        }

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorName.setText("");
            }
        });
        return view;

    }

    public void setCursorColor(EditText view, @ColorInt int color) {
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
}



