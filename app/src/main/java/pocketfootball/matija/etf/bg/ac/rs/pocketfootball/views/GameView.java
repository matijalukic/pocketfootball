package pocketfootball.matija.etf.bg.ac.rs.pocketfootball.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.DrawableViewGenerator;
import pocketfootball.matija.etf.bg.ac.rs.pocketfootball.logic.GameLogic;

public class GameView extends View  {

    private GameLogic gameLogic;
    private Paint testPaint;
    private Paint textPaint;

    private String dimensionsText;


    private void createGame(){
        gameLogic = new GameLogic(getWidth(), getHeight());

        dimensionsText = String.format("%d x %d", gameLogic.getW(), gameLogic.getH());
        Log.e("dbg", dimensionsText );
    }

    public GameView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        testPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        testPaint.setColor(Color.BLUE);

        textPaint = new Paint(Paint.LINEAR_TEXT_FLAG);
        textPaint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        createGame();
    }


    private long currentTime = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // first time
        if(currentTime == 0)
            currentTime = System.currentTimeMillis();

        // for each Games DrawableViewGenerator calls it
        for(DrawableViewGenerator drawableViewGenerator: gameLogic.getDrawableGeneratorElements()){
            // call a DrawableView from generator
            drawableViewGenerator.getDrawable().draw(canvas);
        }

        // update game logic
        long nowTime = System.currentTimeMillis();
        gameLogic.update(( (float)(nowTime - currentTime) ) / 1000);
        currentTime = nowTime;

        invalidate();
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }


}
