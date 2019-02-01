package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Matija on 01 Feb 19.
 */

public class TimerView implements DrawableView {

    private float seconds;
    private Paint textPaint = new Paint(Paint.SUBPIXEL_TEXT_FLAG);


    public TimerView(float newTime) {
        seconds = newTime;
        textPaint.setColor(Color.WHITE);
        textPaint.setAlpha(128);
        textPaint.setTextSize(48f);
    }

    public void updates(float newTime){
        seconds = newTime;
    }

    private Rect r = new Rect();

    @Override
    public void draw(Canvas canvas) {
        String resultText = String.format("%.2f", seconds);
        canvas.save();

        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.getTextBounds(resultText, 0, resultText.length(), r);
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        float x = cWidth - 50;
        canvas.rotate(-90, x, y);
        canvas.drawText(resultText, x, y, textPaint);

        canvas.restore();

    }
}
