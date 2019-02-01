package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by Matija on 01 Feb 19.
 */

public class ScoreView implements DrawableView {
    private int redScore = 0, blueScore = 0;

    private Paint textPaint = new Paint(Paint.SUBPIXEL_TEXT_FLAG);

    public ScoreView() {
        textPaint.setColor(Color.WHITE);
        textPaint.setAlpha(128);
        textPaint.setTextSize(48f);
    }

    public void setScores(int red, int blue){
        redScore = red;
        blueScore = blue;
    }

    private Rect r = new Rect();

    @Override
    public void draw(Canvas canvas) {
        String resultText = redScore + ":" + blueScore;
        canvas.save();

        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.getTextBounds(resultText, 0, resultText.length(), r);
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        float x = 50;
        canvas.rotate(-90, x, y);
        canvas.drawText(resultText, x, y, textPaint);

        canvas.restore();




    }
}
