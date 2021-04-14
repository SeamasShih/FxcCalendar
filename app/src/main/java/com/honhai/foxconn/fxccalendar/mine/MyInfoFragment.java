package com.honhai.foxconn.fxccalendar.mine;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.addevent.DeleteMessagePopupWindow;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.User;
import com.honhai.foxconn.fxccalendar.data.interfaces.RefreshData;
import com.honhai.foxconn.fxccalendar.login.Register;
import com.honhai.foxconn.fxccalendar.main.MainActivity;

import java.lang.reflect.Field;

public class MyInfoFragment extends Fragment implements RefreshData {
    private View workId, password, name, englishName, logout;
    private EditText edtWorkId, edtPassword, edtName, edtEnglishName;
    private View icLogout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_mine_layout, container, false);
        findViews(layout);
        setOnClickListener();
        Log.d("Seamas","MyInfoFragment : onCreateView");
        return layout;
    }

    @Override
    public void onDestroyView() {
        Log.d("Seamas","MyInfoFragment : onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        setThemeColor();
        setMineInfo(Data.getUserInfo());
        Log.d("Seamas","MyInfoFragment : onResume");
        super.onResume();
    }

    private void setThemeColor() {
        icLogout.getBackground().setTint(Data.getInstance().getThemeColor());
        setCursorColor(edtWorkId, Data.getInstance().getThemeColor());
        setCursorColor(edtPassword, Data.getInstance().getThemeColor());
        setCursorColor(edtName, Data.getInstance().getThemeColor());
        setCursorColor(edtEnglishName, Data.getInstance().getThemeColor());
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

    private void setOnClickListener() {
        edtWorkId.setFocusable(false);
        edtWorkId.setClickable(false);
        edtWorkId.cancelLongPress();
        edtWorkId.setLongClickable(false);
        edtPassword.setFocusable(false);
        edtPassword.setClickable(false);
        edtPassword.cancelLongPress();
        edtPassword.setLongClickable(false);
        edtName.setFocusable(false);
        edtName.setClickable(false);
        edtName.cancelLongPress();
        edtName.setLongClickable(false);
        edtEnglishName.setFocusable(false);
        edtEnglishName.setClickable(false);
        edtEnglishName.cancelLongPress();
        edtPassword.setLongClickable(false);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutPopupWindow();
            }
        });
    }

    private void showLogoutPopupWindow(){
        View layout = getLayoutInflater().inflate(R.layout.popup_window_delete_event_message, null);
        Window window = ((MainActivity)getContext()).getWindow();
        final DeleteMessagePopupWindow popupWindow = new DeleteMessagePopupWindow((MainActivity)getContext(), layout, (int) (window.getDecorView().getWidth() * .8f), (int) (window.getDecorView().getHeight() * .2f));
        popupWindow.showAtLocation(window.getDecorView(), Gravity.CENTER, 0, 0);
        TextView message = layout.findViewById(R.id.deleteWarningMessage);
        TextView cancel = layout.findViewById(R.id.deleteCancelBtn);
        TextView confirm = layout.findViewById(R.id.deleteConfirmBtn);
        message.setText(R.string.popupWindowLogoutMessage);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getContext(), Register.class);
                getContext().startActivity(intent);
                ((MainActivity) getContext()).finish();
            }
        });
    }

    private void setMineInfo(User userInfo) {
        edtWorkId.setText(userInfo.getWorkId());
        edtPassword.setText(userInfo.getPassword());
        edtName.setText(userInfo.getName());
        edtEnglishName.setText(userInfo.getEnglishName());
    }

    private void findViews(View layout) {
        workId = layout.findViewById(R.id.work_id);
        edtWorkId = layout.findViewById(R.id.work_id_info);

        password = layout.findViewById(R.id.password);
        edtPassword = layout.findViewById(R.id.password_info);

        name = layout.findViewById(R.id.name);
        edtName = layout.findViewById(R.id.name_info);

        englishName = layout.findViewById(R.id.english_name);
        edtEnglishName = layout.findViewById(R.id.english_name_info);

        icLogout = layout.findViewById(R.id.logout_icon);
        logout = layout.findViewById(R.id.logout);
    }

    @Override
    public void refresh() {
        setMineInfo(Data.getUserInfo());
    }
}
