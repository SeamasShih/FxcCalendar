package com.honhai.foxconn.fxccalendar.elsemember.photo;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.honhai.foxconn.fxccalendar.R;
import com.honhai.foxconn.fxccalendar.data.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PhotoSelectActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView title;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private ScaleGestureDetector detector;
    private PhotoAdapter adapter;
    private String groupObjectId;
    private SharedPreferences sharedPreferences;

    public static String INTENT_GROUP_OBJECT_ID = "groupObjectId";

    public final String SHARED_PREFERENCE_NAME = "mSharePreference";
    public final String SHARED_PREFERENCE_SPAN_COUNT = "mSpanCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_select);

        Intent intent = getIntent();
        groupObjectId = intent.getStringExtra(INTENT_GROUP_OBJECT_ID);
        sharedPreferences = getSharedPreferences(SHARED_PREFERENCE_NAME, MODE_PRIVATE);

        findViews();
        setToolbar();
        setAdapter();
        setRecyclerView();
        setGestureListener();
    }

    private void setToolbar() {
        toolbar.inflateMenu(R.menu.menu_toolbar);
        toolbar.getNavigationIcon().setAlpha(255);
        toolbar.getMenu().getItem(0).getIcon().setAlpha(255);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (Data.getInstance().isNetworkAvailable(PhotoSelectActivity.this)) {
                    PhotoData photoData = adapter.getChosenData();
                    if (photoData == null)
                        Toast.makeText(PhotoSelectActivity.this, getString(R.string.neverSelectPhoto), Toast.LENGTH_SHORT).show();
                    else {
                        savePhotoToInternalStorage(photoData);
                        String path = Environment.getExternalStorageDirectory() + "/" + "FxcCalendar" + "/" + groupObjectId + ".jpg";
                        Data.getInstance().uploadGroupsIcon(groupObjectId, path);
                        finish();
                    }
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

    private void savePhotoToInternalStorage(PhotoData photoData) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoData.getUrl(), options);
        int bw = options.outWidth;
        int bh = options.outHeight;
        int minL = Math.min(bw, bh);
        options.inSampleSize = minL / 100;
        options.inJustDecodeBounds = false;
        Bitmap bitmap = BitmapFactory.decodeFile(photoData.getUrl(), options);

        Matrix matrix = new Matrix();
        matrix.postRotate(photoData.getRotation());
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        String path = getFilesDir().getPath();
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String filename = groupObjectId + ".jpg";
        File file = new File(appDir, filename);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.parse(path);
            mediaScanIntent.setData(contentUri);
            sendBroadcast(mediaScanIntent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        values.put(MediaStore.Images.Media.DATA, file.getPath());
//        values.put(MediaStore.Images.Media.DISPLAY_NAME, groupObjectId + ".jpg");
//        ContentResolver cr = this.getContentResolver();
//        cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void setGestureListener() {
        detector = new ScaleGestureDetector(this, new ScaleGestureDetector.SimpleOnScaleGestureListener() {

            boolean canScale = true;

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float factor = detector.getScaleFactor();
                if (canScale && factor != 1) {
                    if (factor < 1 && layoutManager.getSpanCount() != 6) {
                        layoutManager.setSpanCount(layoutManager.getSpanCount() + 1);
                        adapter.notifyItemChanged(0); // It can perform an animation but I don't know why...
                        sharedPreferences.edit().putInt(SHARED_PREFERENCE_SPAN_COUNT, layoutManager.getSpanCount()).apply();
                    } else if (factor > 1 && layoutManager.getSpanCount() != 1) {
                        layoutManager.setSpanCount(layoutManager.getSpanCount() - 1);
                        adapter.notifyItemChanged(0); // It can perform an animation but I don't know why...
                        sharedPreferences.edit().putInt(SHARED_PREFERENCE_SPAN_COUNT, layoutManager.getSpanCount()).apply();
                    }
                    canScale = false;
                }
                return super.onScale(detector);
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {
                canScale = true;
                super.onScaleEnd(detector);
            }
        });

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getPointerCount() == 2) {
                    detector.onTouchEvent(event);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setToolbarColor();
    }

    private void setToolbarColor() {
        title.setTextColor(Data.getInstance().getThemeColor());

        toolbar.getMenu().getItem(0).getIcon().setTint(Data.getInstance().getThemeColor());
        toolbar.getNavigationIcon().setTint(Data.getInstance().getThemeColor());
    }

    private void setAdapter() {
        ArrayList<PhotoData> data = getData();
        adapter = new PhotoAdapter(this, recyclerView, data);
    }

    private ArrayList<PhotoData> getData() {
        ArrayList<PhotoData> data = new ArrayList<>();

        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projections = {
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.ORIENTATION
        };
        String order = MediaStore.Images.Media.DATE_MODIFIED + " DESC";

        try {
            Cursor imageCursor = getContentResolver().query(
                    uri,
                    projections,
                    null,
                    null,
                    order
            );
            if (imageCursor != null) {
                while (imageCursor.moveToNext()) {
                    PhotoData photoData = new PhotoData();
                    photoData.setUrl(imageCursor.getString(0));
                    photoData.setRotation(imageCursor.getInt(1));
                    data.add(photoData);
                }
                imageCursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;
    }

    private void setRecyclerView() {
        int mSpanCount = sharedPreferences.getInt(SHARED_PREFERENCE_SPAN_COUNT, 3);

        layoutManager = new GridLayoutManager(this, mSpanCount, OrientationHelper.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new PhotoDecoration(this, LinearLayout.VERTICAL));
        recyclerView.addItemDecoration(new PhotoDecoration(this, LinearLayout.HORIZONTAL));
    }

    private void findViews() {
        toolbar = findViewById(R.id.topBar);
        title = findViewById(R.id.toolbarTitle);
        recyclerView = findViewById(R.id.recycler);
    }
}
