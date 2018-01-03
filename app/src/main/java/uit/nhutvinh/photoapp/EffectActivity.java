package uit.nhutvinh.photoapp;

import android.content.Intent;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import uit.nhutvinh.model.EffectView;
import uit.nhutvinh.model.RotatePicture;
import uit.nhutvinh.model.SavePicture;
import uit.nhutvinh.model.TakePicture;

/**
 * Created by Vin Vin on 06/12/2017.
 */

public class EffectActivity extends AppCompatActivity {
    private static final int SELECT_PHOTO = 100;

    boolean enabledGrid = true;

    private float currRotateDegree = 0;

    EffectView imgPic;
    ImageView imgGrid;

    BottomNavigationView bottomNavigationView;
    ImageButton btnSave,btnCancel;

    RotatePicture rotatePicture;
    TakePicture takePicture;
    SavePicture savePicture;

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
                    btnCancel.setBackgroundResource(R.drawable.ic_cancel);
                    btnSave.setBackgroundResource(R.drawable.ic_done);

                    enableSavePicture(true);

                    takePicture();

                }else  if(item.getItemId()==R.id.cropPic)
                {
                    btnCancel.setBackgroundResource(R.drawable.ic_cancel);
                    btnSave.setBackgroundResource(R.drawable.ic_done);

                    enableSavePicture(true);

                    imgPic.setEnableDraw(false);
                    imgPic.setEnableZoomDrag(true);


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
                }else if(item.getItemId()==R.id.drawPic) {
                    enableSavePicture(false);
                    btnSave.setBackgroundResource(R.drawable.ic_redo);
                    btnCancel.setBackgroundResource(R.drawable.ic_undo);


                    drawPicture();
                    //rotatePicture.setOriginalBitmap(imgPic.getOriginalBitmap());
                    return true;
                }else if(item.getItemId()==R.id.rotatePic)
                {
                    enableSavePicture(true);
                    btnCancel.setBackgroundResource(R.drawable.ic_cancel);
                    btnSave.setBackgroundResource(R.drawable.ic_done);


                    currRotateDegree = 90;
                    imgPic.setOriginalBitmap(rotatePicture(currRotateDegree));
                    return true;
                }


                return false;
            }
        });


    }



    private void addConTrols() {
        imgPic = (EffectView) findViewById(R.id.imgPic);
        imgGrid = (ImageView) findViewById(R.id.imgGrid);
        btnCancel = (ImageButton) findViewById(R.id.btnCancel);
        btnSave = (ImageButton) findViewById(R.id.btnSave);

        btnSave.setBackgroundResource(R.drawable.ic_done);
        btnCancel.setBackgroundResource(R.drawable.ic_cancel);

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

    public Bitmap rotatePicture(float rotateDegree) {

        imgPic.setEnableDraw(false);
        imgPic.setEnableZoomDrag(true);

        if(imgPic.getDrawable()!=null) {
            rotatePicture = new RotatePicture(imgPic);
            return rotatePicture.rotatePicture(rotateDegree);

        }

        return null;
    }

    public  void drawPicture(){
            imgPic.setEnableDraw(true);
            imgPic.setEnableZoomDrag(false);
    }

    public void savePicture(){
        if(imgPic.getDrawable()!=null){
            savePicture = new SavePicture(imgPic);
            if(savePicture.savePicture()){
                Toast.makeText(this,"Lưu Thành Công",Toast.LENGTH_SHORT).show();
            }
            else  Toast.makeText(this,"Lưu Thất Bại",Toast.LENGTH_SHORT).show();

        }

    }


    void enableSavePicture(boolean bool){
        if(bool){
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgPic.setOriginalBitmap(null);
                    addConTrols();
                }
            });


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    savePicture();
                }
            });
        }
        else {
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgPic.onClickUndo();
                }
            });


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imgPic.onClickRedo();
                }
            });
        }


    }
}
