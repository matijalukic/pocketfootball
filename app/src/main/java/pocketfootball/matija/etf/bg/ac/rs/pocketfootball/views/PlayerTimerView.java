package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class PlayerTimerView implements DrawableView {
    private Paint timerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float w, h, screenWidth;

    public PlayerTimerView(int color, float w, float h) {
        this.w = w;
        this.h = h;
        this.screenWidth = screenWidth;
        timerPaint.setAlpha(128);
        timerPaint.setColor(color);
    }

    public void setColor(int color) {
        timerPaint.setColor(color);
    }

    public void setH(float h) {
        this.h = h;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(0, 0, w, h, timerPaint);
    }
}
