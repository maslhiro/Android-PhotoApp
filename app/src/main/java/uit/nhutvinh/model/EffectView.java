package uit.nhutvinh.model;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import static uit.nhutvinh.photoapp.R.id.imgPic;


/**
 * Created by Vin Vin on 19/12/2017.
 */

public class EffectView extends android.support.v7.widget.AppCompatImageView {
    // Debug Logcat
    private static final String TAG = "Effect View";

    private float FLAG = 0 ;

    private Bitmap imgBitmap = null;
    private static Matrix matrix = new Matrix();
    private static Matrix savedMatrix = new Matrix();

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private static  final  float MAX_ZOOM = 5.0f;
    private static  final  float MIN_ZOOM =0.5f;

    private int mode = NONE;

    private PointF start = new PointF();
    private PointF mid = new PointF();
    private float oldDist = 1f;

    private float dx; // postTranslate X distance
    private float dy; // postTranslate Y distance
    private float[] matrixValues = new float[9];
    float matrixX = 0; // X coordinate of matrix inside the ImageView
    float matrixY = 0; // Y coordinate of matrix inside the ImageView
    float width = 0; // width of drawable
    float height = 0; // height of drawable

    int viewWidth = 0;
    int viewHeight =0;

    public EffectView(Context context) {
        super(context);
        init();
    }

    public EffectView(Context context, AttributeSet attrs) {

        super(context, attrs);
        init();
    }

    public Bitmap getImgBitmap() {
         return imgBitmap;
    }

    public void setImgBitmap(Bitmap imgBitmap) {

            this.imgBitmap = imgBitmap;
            setImageBitmap(this.imgBitmap);

            //  chinh lai image center view
            Drawable image = getDrawable();
            RectF rectfView = new RectF(0, 0, this.getWidth(), this.getHeight());
            RectF rectfImage = new RectF(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            matrix.setRectToRect(rectfImage, rectfView, Matrix.ScaleToFit.CENTER);
            setImageMatrix(matrix);

    }
    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(imgBitmap==null) return false;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            // khi cham vao man hinh
            case MotionEvent.ACTION_DOWN:
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                mode = DRAG;
                break;
            //Khi cham vao 1 diem
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                if (oldDist >10f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:

                mode = NONE;

                break;

            // ngon tay di chuyen tren man hinh
            case MotionEvent.ACTION_MOVE:
                if (mode == DRAG) {

//                    matrix.set(savedMatrix);
//                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
//                    limitDrag(matrix);


//                    matrix.set(savedMatrix);
//
//                    matrix.getValues(matrixValues);
//                    matrixX = matrixValues[2];
//                    matrixY = matrixValues[5];
//                    width = matrixValues[0] * getDrawable().getIntrinsicWidth();
//                    height = matrixValues[4] * getDrawable().getIntrinsicHeight();
//
//                    dx = event.getX() - start.x;
//                    dy = event.getY() - start.y;
//
//                    //if image will go outside left bound
//                    if (matrixX + dx < 0) {
//
//                        dx = -matrixX;
//                    }
//                    //if image will go outside right bound
//                    if (matrixX + dx + width < getWidth()) {
//                        dx = getWidth() - matrixX - width;
//                    }
//                    //if image will go oustside top bound
//                    if (matrixY + dy > 0) {
//                        dy = -matrixY;
//                    }
//                    //if image will go outside bottom bound
//                    if (matrixY + dy + height < getHeight()) {
//                        dy = getHeight() - matrixY - height;
//                    }
//                    matrix.postTranslate(dx, dy);

                } else if (mode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f ) {
                        float scale = newDist / oldDist;
//                        matrix.set(savedMatrix);
//                       matrix.postScale(scale, scale, mid.x, mid.y);

                        float[] values = new float[9];
                        matrix.getValues(values);
                        float currentScale = values[Matrix.MSCALE_X];

                        if(scale * currentScale >=   MAX_ZOOM)
                            scale = MAX_ZOOM / currentScale;
                        else if (scale * currentScale <= MIN_ZOOM)
                            scale = MIN_ZOOM / currentScale;
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;



        }
       //limitZoom(matrix);

        this.setImageMatrix(matrix);
        return true; // indicate event was handled

    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return  (float)Math.sqrt(x * x + y * y);
    }

    private void limitDrag(Matrix m) {
        float[] values = new float[9];
        m.getValues(values);
        float transX = values[Matrix.MTRANS_X];
        float transY = values[Matrix.MTRANS_Y];
        float scaleX = values[Matrix.MSCALE_X];
        float scaleY = values[Matrix.MSCALE_Y];

        Rect bounds = this.getDrawable().getBounds();


        int width = bounds.right - bounds.left;
        int height = bounds.bottom - bounds.top;

        float minX = (-width + 20) * scaleX;
        float minY = (-height + 20) * scaleY;

        if(transX > (getWidth() - 20)) {
            transX = getWidth() - 20;
        } else if(transX < minX) {
            transX = minX;
        }

        if(transY > (getHeight() - 80)) {
            transY = getHeight() - 80;
        } else if(transY < minY) {
            transY = minY;
        }

        values[Matrix.MTRANS_X] = transX;
        values[Matrix.MTRANS_Y] = transY;
        m.setValues(values);

    }

    private void init(){
        viewWidth = getResources().getDisplayMetrics().widthPixels;
        viewHeight = getResources().getDisplayMetrics().heightPixels;

        Log.d(TAG,"viewWidth : " + viewWidth);
        Log.d(TAG,"viewHeight : " + viewHeight);


    }



}
