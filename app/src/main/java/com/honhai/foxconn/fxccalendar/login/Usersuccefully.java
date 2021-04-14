package com.honhai.foxconn.fxccalendar.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import static android.view.View.OnClickListener;
import com.honhai.foxconn.fxccalendar.R;
public class Usersuccefully extends AppCompatActivity {


    private Button bt_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usersuccefully_ui);

        bt_close = (Button) findViewById(R.id.close_button);

        bt_close.setOnClickListener(new MyClickListener());

    }

    private class MyClickListener implements OnClickListener {
        public void onClick(View v) {
            Intent intent = new Intent(Usersuccefully.this, Register.class);
            startActivity(intent);
            finish();
        }
    }
}