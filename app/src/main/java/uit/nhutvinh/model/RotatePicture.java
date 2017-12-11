package uit.nhutvinh.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

/**
 * Created by Vin Vin on 11/12/2017.
 */

public class RotatePicture {
    private ImageView imgPic;
    private Matrix matrix;
    private Bitmap bitmap;

    public void rotatePicture(){

        matrix.setRotate(90,400,400);
        imgPic.setImageMatrix(matrix);
    }
    public RotatePicture() {
    }

    public RotatePicture(ImageView imgPic) {
        this.imgPic = imgPic;

    }

    public ImageView getImgPic() {

        return imgPic;
    }

    public void setImgPic(ImageView imgPic) {
        this.imgPic = imgPic;
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
    }
}
