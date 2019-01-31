package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Matija on 31 Jan 19.
 */

public class GoalView implements DrawableView{

    private Paint goalPaint = new Paint();
    private float startX, startY, endX, endY;
    private int color;

    public GoalView(float startX, float startY, float endX, float endY) {

        this.startX = startX;
        this.startY = startY;

        this.endX = endX;
        this.endY = endY;


        goalPaint.setColor(Color.WHITE);
        goalPaint.setAlpha(128);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(startX, startY, endX, endY, goalPaint);
    }
}
