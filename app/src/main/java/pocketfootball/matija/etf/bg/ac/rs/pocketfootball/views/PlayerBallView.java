package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.DrawableViewGenerator;

/**
 * Created by Matija on 29 Jan 19.
 */

public class PlayerBallView implements DrawableView {
    public static final int OPACITY_HIGHLIGHTED = 255;
    public static final int OPACITY_DIMMED = 200;
    protected float diameter, x, y;
    protected Paint ballPaint;

    public PlayerBallView(float x, float y, float diameter, int color) {
        this.diameter = diameter;
        this.x = x;
        this.y = y;

        ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
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
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(x, y, diameter, ballPaint);
    }
}
