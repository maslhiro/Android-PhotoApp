package uit.nhutvinh.model;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import java.io.File;

import java.io.FileOutputStream;



/**
 * Created by Vin Vin on 03/01/2018.
 */

public class SavePicture {
    private EffectView effectView;
    private BitmapDrawable bitmapDrawable;

    public SavePicture() {
        setBitmapDrawable(null);
    }

    public SavePicture(BitmapDrawable bitmapDrawable) {
        this.bitmapDrawable = bitmapDrawable;
    }

    public SavePicture(EffectView effectView) {
        this.effectView = effectView;
        setBitmapDrawable(effectView.getDrawableBitmap());
    }

    public EffectView getEffectView() {
        return effectView;
    }

    public void setEffectView(EffectView effectView) {
        this.effectView = effectView;
    }

    public BitmapDrawable getBitmapDrawable() {
        return bitmapDrawable;
    }

    public void setBitmapDrawable(BitmapDrawable bitmapDrawable) {
        this.bitmapDrawable = bitmapDrawable;
    }

    public boolean savePicture() {
        Bitmap bitmap = bitmapDrawable.getBitmap();
       // File sdCardDirectory = Environment.getExternalStorageDirectory().getAbsoluteFile();

        String sdCardDirectory = Environment.getExternalStorageDirectory().getAbsoluteFile() + "/PhotoApp/";
       // File dir = new File(sdCardDirectory.getAbsolutePath() + "/DCIM/PhotoApp/");
       // dir.mkdirs();
        File dir = new File(sdCardDirectory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = String.format("%d.jpeg", System.currentTimeMillis());

        File image = new File(sdCardDirectory+fileName);

        boolean success = false;

        FileOutputStream outStream;
        try {

            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);

            outStream.flush();
            outStream.close();
            success = true;

        } catch (Exception  e) {
            e.printStackTrace();
        }

        return  success;


    }
}

