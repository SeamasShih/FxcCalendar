package com.honhai.foxconn.fxccalendar.login;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;
import com.honhai.foxconn.fxccalendar.data.User;

import java.util.ArrayList;


public class ModifyActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText edtPassword, edtName, edtEnglishName;
    private int pervColor;
    private int currColor;
    private TextView title;
    private Button new_pwd_clear;
    private Button pwd_eye;
    private Button check_clear;
    private Button check_eye;
    private Button chinese_clear;
    private Button english_clear;
    private boolean isOpen = false;
    private EditText checkPassword;
    private String workId;
    private String objectId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_personal_information);
        findViews();
        setToolbar();

        setModifyInfo(Data.getUserInfo());//数据库获取密码
        pervColor = Data.getInstance().getThemeColor();
        currColor = pervColor;

        EditextWatcher();

        //给清除按钮和密码眼睛按钮设置点击事件
        new_pwd_clear.setOnClickListener(this);
        pwd_eye.setOnClickListener(this);
        check_clear.setOnClickListener(this);
        check_eye.setOnClickListener(this);
        chinese_clear.setOnClickListener(this);
        english_clear.setOnClickListener(this);
    }

    private void setToolbar() {
        toolbar.inflateMenu(R.menu.menu_toolbar);
        Drawable drawable = toolbar.getMenu().getItem(0).getIcon();
        drawable.setAlpha(255);
        drawable.setTint(Data.getInstance().getThemeColor());

        toolbar.setNavigationIcon(R.drawable.ic_backup);
        drawable = toolbar.getNavigationIcon();
        drawable.setTint(Data.getInstance().getThemeColor());
        drawable.setAlpha(255);

        title.setTextColor(Data.getInstance().getThemeColor());
        title.setText(getString(R.string.settingsManageGroupPopupWindowTitle));
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (isUserPwdValid() && Data.getInstance().isNetworkAvailable(ModifyActivity.this)) {
                    workId = Data.getUserInfo().getWorkId();
                    objectId = Data.getUserInfo().getObjectId();

                    Data.getInstance().updateUserForCloud(workId, edtPassword.getText().toString(), edtName.getText().toString(), edtEnglishName.getText().toString(), objectId);
                    finish();
                }
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //获取密码名字
    private void setModifyInfo(User userInfo) {
        edtPassword.setText(userInfo.getPassword());
        edtName.setText(userInfo.getName());
        edtEnglishName.setText(userInfo.getEnglishName());
    }

    private boolean isUserPwdValid() {
        if (checkPassword.getText().toString().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!edtPassword.getText().toString().equals(checkPassword.getText().toString())) {
            Toast.makeText(this, getString(R.string.Psaaword_same), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void EditextWatcher() {
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {    // 获得文本框中的用户密码
                String pwd = edtPassword.getText().toString().trim();
                if ("".equals(pwd)) {    // 用户名为空,设置按钮不可见
                    new_pwd_clear.setVisibility(View.INVISIBLE);
                } else {             // 用户名不为空，设置按钮可见
                    new_pwd_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        checkPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {    // 获得文本框中的用户确认密码
                String pwd = checkPassword.getText().toString().trim();
                if ("".equals(pwd)) {    // 用户名为空,设置按钮不可见
                    check_clear.setVisibility(View.INVISIBLE);
                } else {             // 用户名不为空，设置按钮可见
                    check_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {    // 获得文本框中的中文名字
                String pwd = edtName.getText().toString().trim();
                if ("".equals(pwd)) {    // 用户名为空,设置按钮不可见
                    chinese_clear.setVisibility(View.INVISIBLE);
                } else {             // 用户名不为空，设置按钮可见
                    chinese_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtEnglishName.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {    // 获得文本框中的英文名字
                String pwd = edtEnglishName.getText().toString().trim();
                if ("".equals(pwd)) {    // 用户名为空,设置按钮不可见
                    english_clear.setVisibility(View.INVISIBLE);
                } else {             // 用户名不为空，设置按钮可见
                    english_clear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }


    // 设置密码不可见与不可见
    private void changePwdOpenOrClose(boolean flag) {
        if (flag) {
            pwd_eye.setBackgroundResource(R.drawable.ic_visibility_black_24dp);
            edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            pwd_eye.setBackgroundResource(R.drawable.ic_visibility_off_black_24dp);
            edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void checkPwdOpenOrClose(boolean flag) {
        if (flag) {
            check_eye.setBackground(getDrawable(R.drawable.ic_visibility_black_24dp));
            checkPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        } else {
            check_eye.setBackground(getDrawable(R.drawable.ic_visibility_off_black_24dp));
            checkPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }

    private void findViews() {
        toolbar = findViewById(R.id.toolbar);
        title = findViewById(R.id.toolbar_title);

        edtPassword = findViewById(R.id.et_newPassword);
        checkPassword = findViewById(R.id.et_checkPassword);
        edtName = findViewById(R.id.et_chineseName);
        edtEnglishName = findViewById(R.id.et_englishName);
        //找到关心的Edittext里面的清除按钮和密码眼睛按钮
        new_pwd_clear = findViewById(R.id.new_pwd_clear);
        pwd_eye = findViewById(R.id.bt_pwd_eye);
        check_clear = findViewById(R.id.check_pwd_clear);
        check_eye = findViewById(R.id.check_pwd_eye);
        chinese_clear = findViewById(R.id.chinese_name_clear);
        english_clear = findViewById(R.id.english_name_clear);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.new_pwd_clear:
                edtPassword.setText("");
                break;
            case R.id.bt_pwd_eye:
                if (isOpen) { // 密码可见与不可见的切换
                    isOpen = false;
                } else {
                    isOpen = true;
                }      // 默认isOpen是false,密码不可见
                changePwdOpenOrClose(isOpen);
                break;
            case R.id.check_pwd_clear:
                checkPassword.setText("");
                break;
            case R.id.check_pwd_eye:
                if (isOpen) { // 密码可见与不可见的切换
                    isOpen = false;
                } else {
                    isOpen = true;
                }      // 默认isOpen是false,密码不可见
                checkPwdOpenOrClose(isOpen);
                break;
            case R.id.chinese_name_clear:
                edtName.setText("");
                break;
            case R.id.english_name_clear:
                edtEnglishName.setText("");
                break;
            default:
        }
    }
}

