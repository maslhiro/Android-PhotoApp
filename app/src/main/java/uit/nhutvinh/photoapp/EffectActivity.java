package uit.nhutvinh.photoapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
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
import android.view.MotionEvent;
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

    RotatePicture rotatePicture;
    TakePicture takePicture;
    Uri imageUri;


    // Test Draw Image
 //   private BitmapDrawable originalBitmapDrawable ;
//    private Bitmap originalBitmap ;
//    private int originalImageWith ;
//    private int originalImageHeight ;
//    private Bitmap.Config originalImageConfig ;


   // float downx = 0, downy = 0, upx = 0, upy = 0;

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

                    return true;
                }else if(item.getItemId()==R.id.drawPic)
                {
                    drawPicture();
                    return true;
                }else if(item.getItemId()==R.id.rotatePic)
                {

                    currRotateDegree += 90;
                    rotatePicture(currRotateDegree);
                    return true;
                }

                return false;
            }
        });

    }

    private void addConTrols() {
        imgPic = (EffectView) findViewById(R.id.imgPic);
        imgGrid = (ImageView) findViewById(R.id.imgGrid);

        rotatePicture = new RotatePicture(imgPic);
        takePicture = new TakePicture(imgPic);


        // kiem tra co gui uri anh tu mainactivity
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

                    rotatePicture.initCanvas();

                    // Test Draw
//                    if(imgPic.getDrawable()!=null)
//                    {
//                        originalBitmapDrawable = (BitmapDrawable) imgPic.getDrawable();
//                        originalBitmap = originalBitmapDrawable.getBitmap();
//                        originalImageHeight = originalBitmap.getHeight();
//                        originalImageWith = originalBitmap.getWidth();
//                        originalImageConfig = originalBitmap.getConfig();
//                    }
                }
        }

    }

    // lay anh tu bo suu tap
    public void takePicture() {
        imgPic.setEnableDraw(false);
        imgPic.setEnableZoomDrag(true);

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    public void rotatePicture(float rotateDegree) {
        imgPic.setEnableDraw(false);
        imgPic.setEnableZoomDrag(true);
        rotatePicture.rotateImage(rotateDegree);

    }

    public  void drawPicture(){
        imgPic.setEnableDraw(true);
        imgPic.setEnableZoomDrag(false);
//        Bitmap bitmap = Bitmap.createBitmap(originalImageWith, originalImageHeight, originalImageConfig);
//
//        final Canvas canvas = new Canvas(bitmap);
//
//        final Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        downx = 0; downy = 0; upx = 0; upy = 0;
//
//        imgPic.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        downx = event.getX();
//                        downy = event.getY();
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        upx = event.getX();
//                        upy = event.getY();
//                        canvas.drawLine(downx, downy, upx, upy, paint);
//
//                        // ve lai img pic lien tuc
//                        imgPic.invalidate();
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                        break;
//                    default:
//                        break;
//                }
//
//                return true;
//
//
//            }
//
//        });
    }


}
