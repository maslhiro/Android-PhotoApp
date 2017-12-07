package uit.nhutvinh.photoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import uit.nhutvinh.model.TakePicture;

/**
 * Created by Vin Vin on 06/12/2017.
 */

public class EffectActivity extends AppCompatActivity{
    private static final int SELECT_PHOTO = 100;

    ImageView imgPic;
    BottomNavigationView bottomNavigationView;

    TakePicture takePicture;
    Uri imageUri;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.effect_activity);

        addConTrols();
        addEvents();


    }

    private void addEvents() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.addPic)
                {
                    takePicture();
                    Toast.makeText(EffectActivity.this, "Vừa nhấn add nhé :l", Toast.LENGTH_SHORT).show();
                    return true;
                }else  if(item.getItemId()==R.id.cropPic)
                {
                    Toast.makeText(EffectActivity.this, "Vừa nhấn crop nhé :l", Toast.LENGTH_SHORT).show();
                    return true;
                }else if(item.getItemId()==R.id.zoomPic)
                {
                    Toast.makeText(EffectActivity.this, "Vừa nhấn zoom nhé :l", Toast.LENGTH_SHORT).show();
                    return true;
                }else if(item.getItemId()==R.id.drawPic)
                {
                    Toast.makeText(EffectActivity.this, "Vừa nhấn draw nhé :l", Toast.LENGTH_SHORT).show();
                    return true;
                }else if(item.getItemId()==R.id.rotatePic)
                {
                    Toast.makeText(EffectActivity.this, "Vừa nhấn rotate nhé :l", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });

    }

    private void addConTrols() {
        imgPic = (ImageView) findViewById(R.id.imgPic);
        takePicture = new TakePicture(imgPic);
        if (getIntent().getData() != null) {
            imageUri = getIntent().getData();
            takePicture.decodeUri(this, imageUri);
        }
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navBot);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK && null != imageReturnedIntent) {

                    takePicture.decodeUri(this,imageReturnedIntent.getData());
                }
        }

    }

    // lay anh tu bo suu tap
    public void takePicture() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }




}
