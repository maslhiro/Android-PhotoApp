package uit.nhutvinh.photoapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import uit.nhutvinh.model.TakePicture;

/**
 * Created by Vin Vin on 06/12/2017.
 */

public class EffectActivity extends AppCompatActivity{
    private static final int SELECT_PHOTO = 100;
    ImageView imgPic;
    TakePicture takePicture;
    Uri imageUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.effect_activity);

        addConTrols();
        addEvents();


    }

    private void addEvents() {


    }

    private void addConTrols() {
        imgPic = (ImageView) findViewById(R.id.imgPic);
        takePicture = new TakePicture(imgPic);
        if (getIntent().getData() != null) {
            imageUri = getIntent().getData();
            takePicture.decodeUri(this, imageUri);
        }
    }

    public void takePicture(View view) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
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





}
