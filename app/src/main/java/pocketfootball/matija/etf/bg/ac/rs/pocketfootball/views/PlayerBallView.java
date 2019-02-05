package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.R;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.DrawableViewGenerator;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.persist.Match;

/**
 * Created by Matija on 29 Jan 19.
 */

public class PlayerBallView implements DrawableView {
    public static final int OPACITY_HIGHLIGHTED = 255;
    public static final int OPACITY_DIMMED = 200;
    protected float diameter, x, y;
    private float imgStartX, imgStartY;
    private float width;
    private Rect bitmapDestination;
    protected Paint ballPaint;
    private Paint imagePaint;

    private Bitmap teamImage;

    public void setTeamImage(Bitmap teamImage) {
        this.teamImage = teamImage;
    }

    private void calculateImagePosition(){
        if(bitmapDestination == null) bitmapDestination = new Rect();

        // side of the square
        width = (float)Math.sqrt(2) * diameter;
        imgStartX = x - width / 2;
        imgStartY = y - width / 2;

        bitmapDestination.set((int)imgStartX, (int)imgStartY, (int)(imgStartX + width), (int)(imgStartY + width));
    }

    public PlayerBallView(float x, float y, float diameter, int color) {
        this.diameter = diameter;
        this.x = x;
        this.y = y;

        calculateImagePosition();

        ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        imagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ballPaint.setColor(color);
        ballPaint.setAlpha(OPACITY_DIMMED);
    }

    public void setDiameter(float diameter) {
        this.diameter = diameter;
    }

    public void setOpacity(int alpha){
        ballPaint.setAlpha(alpha);
    }

    public void setX(float x) {
        this.x = x;

        calculateImagePosition();
    }

    public void setY(float y) {
        this.y = y;

        calculateImagePosition();
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, diameter, ballPaint);

        if(teamImage != null){
            canvas.drawBitmap(teamImage, null, bitmapDestination, imagePaint);
        }
    }
}
