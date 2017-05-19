package ziedkhmiri.customclockmath;

/**
 * Created by zied216 on 28/02/2017.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.util.Calendar;


public class Clock extends View {


    @ColorInt
    private static final int DIAL_BG = 0xFFF0F0F0;

    @ColorInt
    private static final int RING_BG = 0xFFF8F8F8;

    @ColorInt
    private static final int TEXT_COLOR = 0xFF141414;

    @ColorInt
    private static final int HOUR_MINUTE_COLOR = 0xFF5B5B5B;

    @ColorInt
    private static final int SECOND_COLOR = 0xFFB55050;

    private static final int MIN_SIZE = 200;

    private static final int HOUR_MINUTE_WIDTH = 30;

    private static final int SECOND_WIDTH = 8;


    private static final int DEGREE = 6;

    private int hour, minute, second;


    private int ringPaintWidth = 10;

    private int mSize;

    private Paint outCirclePaint;

    private Paint outRingPaint;

    private Paint timeTextPaint;

    private Paint hourPaint, minutePaint, secondPaint;

    private Paint inCirclePaint, inRedCirclePaint;


    private boolean isFirst = true;

    private Calendar calendar;

    public Clock(Context context) {
        super(context);
        initPaint();
    }

    public Clock(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public Clock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }


    private void initPaint() {

        calendar = Calendar.getInstance();

        outCirclePaint = new Paint();
        outCirclePaint.setColor(DIAL_BG);
        outCirclePaint.setAntiAlias(true);

        outRingPaint = new Paint();
        outRingPaint.setColor(RING_BG);
        outRingPaint.setStrokeWidth(dp2px(ringPaintWidth));
        outRingPaint.setStyle(Paint.Style.STROKE);
        outRingPaint.setAntiAlias(true);

        outRingPaint.setShadowLayer(4, 2, 2, 0x80000000);

        timeTextPaint = new Paint();
        timeTextPaint.setAntiAlias(true);
        timeTextPaint.setColor(TEXT_COLOR);

        hourPaint = new Paint();
        hourPaint.setAntiAlias(true);
        hourPaint.setColor(HOUR_MINUTE_COLOR);
        hourPaint.setStrokeWidth(HOUR_MINUTE_WIDTH);

        hourPaint.setStrokeCap(Paint.Cap.ROUND);

        hourPaint.setShadowLayer(4, 0, 0, 0x80000000);

        minutePaint = new Paint();
        minutePaint.setAntiAlias(true);
        minutePaint.setColor(HOUR_MINUTE_COLOR);
        minutePaint.setStrokeWidth(HOUR_MINUTE_WIDTH);

        minutePaint.setStrokeCap(Paint.Cap.ROUND);

        minutePaint.setShadowLayer(4, 0, 0, 0x80000000);

        secondPaint = new Paint();
        secondPaint.setAntiAlias(true);
        secondPaint.setColor(SECOND_COLOR);
        secondPaint.setStrokeWidth(SECOND_WIDTH);

        secondPaint.setStrokeCap(Paint.Cap.ROUND);

        secondPaint.setShadowLayer(4, 3, 0, 0x80000000);

        inCirclePaint = new Paint();
        inCirclePaint.setAntiAlias(true);
        inCirclePaint.setColor(HOUR_MINUTE_COLOR);

        inCirclePaint.setShadowLayer(5, 0, 0, 0x80000000);

        inRedCirclePaint = new Paint();
        inRedCirclePaint.setAntiAlias(true);
        inRedCirclePaint.setColor(SECOND_COLOR);

        inRedCirclePaint.setShadowLayer(5, 0, 0, 0x80000000);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        mSize = dp2px(MIN_SIZE);
        setMeasuredDimension(mSize, mSize);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isFirst) {
            calendar = Calendar.getInstance();
        }
        getTime();

        canvas.translate(mSize / 2, mSize / 2);

        drawHour(canvas);

        drawMinute(canvas);

        drawInCircle(canvas);
        if (isFirst) {
            drawSecond(canvas, second * DEGREE + 1);
        } else {
            drawSecond(canvas, second * DEGREE);
        }

        drawInRedCircle(canvas);

        if (isFirst) {

            postInvalidateDelayed(1000);
        }
    }

    private void getTime() {
        hour = calendar.get(Calendar.HOUR);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        System.out.println(hour + ":" + minute + ":" + second);
    }



    private void drawHour(Canvas canvas) {
        int length = mSize / 4;
        canvas.save();

        float degree = hour * 5 * DEGREE + minute / 2f;
        canvas.rotate(degree, 0, 0);
        canvas.drawLine(0, 0, 0, -length, hourPaint);
        canvas.restore();
    }


    private void drawMinute(Canvas canvas) {
        int length = mSize / 3;
        canvas.save();
        float degree = minute * DEGREE + second / 10f;
        canvas.rotate(degree, 0, 0);
        canvas.drawLine(0, 0, 0, -length, minutePaint);
        canvas.restore();
    }


    private void drawInCircle(Canvas canvas) {
        int radius = mSize / 20;
        canvas.save();
        canvas.drawCircle(0, 0, radius, inCirclePaint);
        canvas.restore();
    }


    private void drawInRedCircle(Canvas canvas) {
        int radius = mSize / 40;
        canvas.save();
        canvas.drawCircle(0, 0, radius, inRedCirclePaint);
        canvas.restore();
    }


    private void drawSecond(Canvas canvas, float degrees) {
        int length = mSize / 2;
        canvas.save();
        canvas.rotate(degrees);
        canvas.drawLine(0, length / 5, 0, -length * 4 / 5, secondPaint);
        canvas.restore();
        isFirst = !isFirst;
        if (!isFirst) {
            invalidate();
        }
    }


    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }


}