package uit.nhutvinh.photoapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import uit.nhutvinh.model.AbsRuntimePermission;


public class MainActivity extends AbsRuntimePermission {

    private static final int REQUEST_PERMISSION = 10;
    ImageButton btnCamera,btnEffect,btnInfo,btnSetting,btnShare;
    TextView txtCamera,txtEffect,txtInfo,txtSetting,txtShare;
    Typeface font;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();
        addControls();
        addEvents();

    }

    private void addEvents() {

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(v);
            }
        });

        btnEffect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               doOpenEffectActivity();
            }
        });

    }

    private void addControls() {
        btnCamera = (ImageButton) findViewById(R.id.btnCamera);
        btnEffect = (ImageButton) findViewById(R.id.btnEffect);
        btnSetting = (ImageButton) findViewById(R.id.btnSetting);
        btnInfo = (ImageButton) findViewById(R.id.btnInfo);
        btnShare = (ImageButton) findViewById(R.id.btnShare);

        txtCamera = (TextView) findViewById(R.id.txtCamera);
        txtEffect = (TextView) findViewById(R.id.txtEffect);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        txtSetting = (TextView) findViewById(R.id.txtSetting);
        txtShare = (TextView) findViewById(R.id.txtShare);

        font = Typeface.createFromAsset(this.getAssets(),"fonts/amita-regular.ttf");
        txtCamera.setTypeface(font);
        txtShare.setTypeface(font);
        txtSetting.setTypeface(font);
        txtInfo.setTypeface(font);
        txtEffect.setTypeface(font);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {
        Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(MainActivity.this,EffectActivity.class);
                intent.setData(imageUri);
                startActivity(intent);

            }
        }
    }

    private void initPermission() {
        requestAppPermissions(new String[]{
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                R.string.request_permission,REQUEST_PERMISSION);

    }

    public void takePicture(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = Uri.fromFile(getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(intent, 100);
    }

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraDemo");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                Log.d("CameraDemo", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    private void doOpenEffectActivity() {
        Intent intent=new Intent(MainActivity.this, EffectActivity.class);
        startActivity(intent);
    }


}
