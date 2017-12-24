package uit.nhutvinh.photoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import uit.nhutvinh.model.EffectView;
import uit.nhutvinh.model.RotatePicture;
import uit.nhutvinh.model.TakePicture;

/**
 * Created by Vin Vin on 06/12/2017.
 */

public class EffectActivity extends AppCompatActivity{
    private static final int SELECT_PHOTO = 100;
    boolean enabledGrid = true;
    private float currRotateDegree = 0;

    EffectView imgPic;
    ImageView imgGrid;

    BottomNavigationView bottomNavigationView;

  //  TouchImageView touchImageView;
    RotatePicture rotatePicture;
    TakePicture takePicture;
    Uri imageUri;


    private BitmapDrawable originalBitmapDrawable ;
    private Bitmap originalBitmap ;
    private int originalImageWith ;
    private int originalImageHeight ;
    private Bitmap.Config originalImageConfig ;


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
              //      Toast.makeText(EffectActivity.this, "Vừa nhấn add nhé :l", Toast.LENGTH_SHORT).show();
                    return true;
                }else  if(item.getItemId()==R.id.cropPic)
                {
                //    Toast.makeText(EffectActivity.this, "Vừa nhấn crop nhé :l", Toast.LENGTH_SHORT).show();
                    return true;
                }else if(item.getItemId()==R.id.gridPic)
                {
                    enabledGrid = !enabledGrid;
                    if(enabledGrid){
                        imgGrid.setVisibility(View.VISIBLE);
                        item.setIcon(R.drawable.ic_grid_off);
                    }
                    else{
                        imgGrid.setVisibility(View.INVISIBLE);
                        item.setIcon(R.drawable.ic_grid_on);
                    }

                    Log.d("Enable Grid", String.valueOf(enabledGrid));

                    return true;
                }else if(item.getItemId()==R.id.drawPic)
                {
              //      Toast.makeText(EffectActivity.this, "Vừa nhấn draw nhé :l", Toast.LENGTH_SHORT).show();
                    return true;
                }else if(item.getItemId()==R.id.rotatePic)
                {

                    currRotateDegree += 90;

                   rotateImage(currRotateDegree);
                        return true;
                }

                return false;
            }
        });

    }

    private void addConTrols() {
        imgPic = (EffectView) findViewById(R.id.imgPic);
        imgGrid = (ImageView) findViewById(R.id.imgGrid);

        takePicture = new TakePicture(imgPic);
        rotatePicture = new RotatePicture(imgPic);

//        if(imgPic.getDrawable()!=null)
//        {
//            originalBitmapDrawable = (BitmapDrawable) imgPic.getDrawable();
//            originalBitmap = originalBitmapDrawable.getBitmap();
//            originalImageHeight = originalBitmap.getHeight();
//            originalImageWith = originalBitmap.getWidth();
//            originalImageConfig = originalBitmap.getConfig();
//        }


        // kiem tra co gui uri tu mainactivity
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

                    if(imgPic.getDrawable()!=null)
                    {
                        originalBitmapDrawable = (BitmapDrawable) imgPic.getDrawable();
                        originalBitmap = originalBitmapDrawable.getBitmap();
                        originalImageHeight = originalBitmap.getHeight();
                        originalImageWith = originalBitmap.getWidth();
                        originalImageConfig = originalBitmap.getConfig();
                    }
                }
        }

    }

    // lay anh tu bo suu tap
    public void takePicture() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

//    public  void rotatePicture(){
//        rotatePicture.setBitmap(imgPic.getImgBitmap());
//        rotatePicture.rotatePicture();
//     //   imgPic.setImgBitmap(rotatePicture.getBitmap());
//    }

    private void rotateImage(float rotateDegree)
    {
//        final ImageView imageViewOriginal = (ImageView)findViewById(R.id.imageViewOriginal);
//        BitmapDrawable originalBitmapDrawable = (BitmapDrawable) imageViewOriginal.getDrawable();
//        final Bitmap originalBitmap = originalBitmapDrawable.getBitmap();
//        final int originalImageWith = originalBitmap.getWidth();
//        final int originalImageHeight = originalBitmap.getHeight();
//        final Config originalImageConfig = originalBitmap.getConfig();

        // Create a bitmap which has same width and height value of original bitmap.
        Bitmap rotateBitmap = Bitmap.createBitmap(originalImageWith, originalImageHeight, originalImageConfig);

        Canvas rotateCanvas = new Canvas(rotateBitmap);

        Matrix rotateMatrix = new Matrix();

        // Rotate around the center point of the original image.
        rotateMatrix.setRotate(rotateDegree, originalBitmap.getWidth()/2, originalBitmap.getHeight()/2);

        Paint paint = new Paint();
        rotateCanvas.drawBitmap(originalBitmap, rotateMatrix, paint);
        imgPic.setImgBitmap(rotateBitmap);
    }



}
