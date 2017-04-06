package com.bauandhornick.warlocksvsdemons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Thomas on 3/24/2017.
 */

public class BattleFieldView extends View {

    int currentWidth;
    int currentHeight;

    Paint paint;
    Bitmap bitmapTemp;
    Bitmap bitMap[][];
    Bitmap backgroundBitmap;
    Bitmap towerBitmap;
    Context context;

    List <Character> alliesAndEnemies;

    public BattleFieldView(Context context) {
        super(context);
        setUp(null);
        this.context = context;
    }

    public BattleFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setUp(attrs);
    }

    public BattleFieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        setUp(attrs);
    }

    public void setUp(AttributeSet attrs){

        paint = new Paint();
        paint.setColor(0xff659d32);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);


        DisplayMetrics dm = getResources().getDisplayMetrics();

        currentWidth=dm.widthPixels;
        currentHeight= dm.heightPixels;


        //float widthPixel= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,currentWidth,dm);
        //float heightPixel= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,currentHeight,dm);

        bitmapTemp = BitmapFactory.decodeResource(getResources(),R.drawable.dg_classm32);
        bitmapTemp = Bitmap.createScaledBitmap(bitmapTemp,800,1100,false);

        bitMap = new Bitmap[11][8];

        for(int i=0;i<11;i++) {
            for(int j=0;j<8;j++)
                bitMap[i][j] = Bitmap.createBitmap(bitmapTemp, 100*j, 100*i, 100, 1000 / 11);
        }

        bitmapTemp = BitmapFactory.decodeResource(getResources(),R.drawable.path2);
        backgroundBitmap = Bitmap.createScaledBitmap(bitmapTemp,currentWidth,currentHeight,false);

        bitmapTemp = BitmapFactory.decodeResource(getResources(),R.drawable.torremagica5);
        towerBitmap = Bitmap.createScaledBitmap(bitmapTemp, 250, 250, false);

        alliesAndEnemies = new ArrayList<>();

        Random rand = new Random();
        for(int i=0;i<100;i++){
            alliesAndEnemies.add(new Ally(rand.nextInt(currentWidth),rand.nextInt(currentHeight-220)+200,100,100,10,0,bitMap[rand.nextInt(10)][rand.nextInt(7)],"fire","ice"
            ,0,0,0,0,0,100,"no"));
        }
        bitmapTemp=null;
        //Canvas canvas = new Canvas(bitmap);

        //drawable.setBounds(0,0,canvas.getWidth(),canvas.getHeight());

        //drawable.draw(canvas);

        //drawable = getResources().getDrawable(R.drawable.dg_effects32);
        //bitmap2 = Bitmap.createBitmap(currentWidth,currentHeight, Bitmap.Config.RGB_565);

  //        Canvas canvas2 = new Canvas(bitmap2);

  //      drawable.draw(canvas2);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //canvas.drawPaint(paint);

        canvas.drawBitmap(backgroundBitmap,0,0,paint);

        //for(int i=0;i<11;i++) {
            //for (int j = 0; j < 8; j++)
              // canvas.drawBitmap(bitMap[i][j],j*100,i*100,paint);

        //}
        Matrix m = new Matrix();


        //m.setRotate(80,50,50);
        m.setTranslate(500,500);
        //m.setScale(-1,1,50,50);

        if(bitmapTemp!=null) {
            canvas.drawBitmap(bitmapTemp, m, paint);
            bitmapTemp=Bitmap.createScaledBitmap(bitmapTemp,500,500,false);
        }

        //canvas.drawBitmap(towerBitmap,m, paint);

        for(Character characters:alliesAndEnemies){
            m.setTranslate(characters.getPos_x(),characters.getPos_y());
            canvas.drawBitmap(characters.getAppearance(),m, paint);
        }

//        canvas.drawBitmap(bitmap2,0,0,paint);

    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
     //   currentWidth = w;
     //   currentHeight = h;
    }
    private class animateEnemies extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            for(Character character:alliesAndEnemies){

             character.animate();
            }
            return null;
        }
    }
}
