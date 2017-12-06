package uit.nhutvinh.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Vin Vin on 25/10/2017.
 */

public class TakePicture {
    private ImageView imgHinh;

    public TakePicture(ImageView imgHinh) {
        this.imgHinh = imgHinh;
    }

    public TakePicture() {
    }

    public ImageView getImgHinh() {
        return imgHinh;
    }

    public void setImgHinh(ImageView imgHinh) {
        this.imgHinh = imgHinh;
    }

    // Lấy file ảnh và scale ảnh phù hợp
    public void decodeUri(Context context, Uri uri) {
        ParcelFileDescriptor parcelFD = null;
        try {
            // Get Bitmap
            parcelFD = context.getContentResolver().openFileDescriptor(uri, "r");
            assert parcelFD != null;
            FileDescriptor imageSource = parcelFD.getFileDescriptor();

            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(imageSource, null, o);

            // the new size we want to scale to
            final int REQUIRED_SIZE = 2048;

            // Find the correct scale value. It should be the power of 2.
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE) {
                    break;
                }
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(imageSource, null, o2);

            imgHinh.setImageBitmap(bitmap);

        } catch (FileNotFoundException e) {
            Toast.makeText(context, "File ảnh ko khả dụng !, Vui lòng thủ lại", Toast.LENGTH_LONG).show();
        } finally {
            if (parcelFD != null)
                try {
                    parcelFD.close();
                } catch (IOException e) {
                    Toast.makeText(context, "File ảnh ko khả dụng !, Vui lòng thủ lại", Toast.LENGTH_LONG).show();
                    // ignored
                }
        }
    }
}
