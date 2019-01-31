package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Matija on 31 Jan 19.
 */

public class GoalPostView implements DrawableView {
    private float startX, startY;
    private float endX, endY;
    private Paint goalPostPaint = new Paint();


    public GoalPostView(float startX, float startY, float endX, float endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;

        goalPostPaint.setColor(Color.WHITE);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(startX, startY, endX, endY, goalPostPaint);
    }
}
