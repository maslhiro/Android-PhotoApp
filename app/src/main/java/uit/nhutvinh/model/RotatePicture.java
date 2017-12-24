package uit.nhutvinh.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

/**
 * Created by Vin Vin on 11/12/2017.
 */

public class RotatePicture {
    private EffectView imgPic;
    private Matrix matrix;
    private Bitmap bitmap = null;

    private Paint paint ;
    private Canvas canvas;

    public void rotatePicture(){
        if(bitmap!=null){
            Bitmap.Config config = bitmap.getConfig();

            Bitmap rotateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), config);
//
//            Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
//            Bitmap mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
//            canvas = new Canvas(mutableBitmap);
            canvas = new Canvas(rotateBitmap);


            matrix = new Matrix();
            paint = new Paint();
            matrix.setRotate(90,bitmap.getWidth()/2,bitmap.getHeight()/2);
            canvas.drawBitmap(bitmap,matrix,paint);
            imgPic.setImgBitmap(bitmap);
        }

    }
    public RotatePicture() {
    }

    public RotatePicture(EffectView imgPic) {
        if (imgPic.getImgBitmap() != null) {
            this.imgPic = imgPic;
            bitmap = imgPic.getImgBitmap();


        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public EffectView getImgPic() {

        return imgPic;
    }

    public void setImgPic(EffectView imgPic) {
        this.imgPic = imgPic;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }


}
