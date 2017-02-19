package io.neilharvey.bogglebuddy;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.List;

public class Board extends GridLayout {

    private final String TAG = "Board";
    private final int SIZE = 4;
    private final float MARGIN = 5;

    private char[][] letters = new char[SIZE][SIZE];
    private List<Point> wordPath;
    private Path arrowPath;
    private Paint highlightPaint;
    private Paint gridPaint;
    private Paint textPaint;
    private float width;
    private float height;

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.board, this);
        setWillNotDraw(false);

        int highlightColor = ContextCompat.getColor(getContext(), R.color.primary);
        highlightPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        highlightPaint.setStyle(Paint.Style.STROKE);
        highlightPaint.setStrokeWidth(5);
        highlightPaint.setColor(highlightColor);

        int squareColor = ContextCompat.getColor(getContext(), R.color.icons);
        gridPaint = new Paint();
        gridPaint.setColor(squareColor);

        float textSize = new TextView(this.getContext()).getTextSize();
        int textColor = ContextCompat.getColor(getContext(), R.color.secondary_text);
        textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(textSize);
        textPaint.setColor(textColor);
    }

    public char[][] getLetters() {
        return letters;
    }

    public void setLetter(int x, int y, char letter) {
        letters[x][y] = letter;
        clearHighlight();
        invalidate();
    }

    public boolean isValid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!Character.isLetter(letters[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public void clear() {
        letters = new char[SIZE][SIZE];
        clearHighlight();
        invalidate();
    }

    public void highlightWord(Word word) {
        wordPath = word.getPath();
        calculateArrowPath();
        invalidate();
    }

    private void calculateArrowPath() {
        if (wordPath == null) {
            arrowPath = null;
        } else {
            arrowPath = new Path();
            Point startPoint = wordPath.get(0);
            float startX = getHorizontalCenter(startPoint);
            float startY = getVerticalCenter(startPoint);
            arrowPath.moveTo(startX, startY);
            for (int i = 1; i < wordPath.size(); i++) {
                Point p = wordPath.get(i);
                float x = getHorizontalCenter(p);
                float y = getVerticalCenter(p);
                arrowPath.lineTo(x, y);
                arrowPath.moveTo(x, y);
            }
        }
    }

    private float getHorizontalCenter(Point point) {
        return (width * (1 + (2 * point.getCol()))) / (2 * SIZE);
    }

    private float getVerticalCenter(Point point) {
        return (height * (1 + (2 * point.getRow()))) / (2 * SIZE);
    }

    private float getTop(Point point) {
        return ((height * point.getRow()) / SIZE) + MARGIN;
    }

    private float getLeft(Point point) {
        return ((width * point.getCol()) / SIZE) + MARGIN;
    }

    private float getBottom(Point point) {
        return ((height * (1 + point.getRow())) / SIZE) - MARGIN;
    }

    private float getRight(Point point) {
        return ((width * (1 + point.getCol())) / SIZE) - MARGIN;
    }

    public void clearHighlight() {
        this.wordPath = null;
        this.arrowPath = null;
        invalidate();
    }

    private void setBoardCharacter(TextView text, int x, int y) {
        char character = Character.toUpperCase(letters[x][y]);

        if (character == 'Q') {
            text.setText(new char[]{character, 'u'}, 0, 2);
        } else {
            text.setText(new char[]{character}, 0, 1);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
        drawHighlightedWord(canvas);
        drawLetters(canvas);
    }

    private void drawHighlightedWord(Canvas canvas) {
        if (arrowPath != null) {
            canvas.drawPath(arrowPath, highlightPaint);
        }
    }

    private void drawGrid(Canvas canvas) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Point p = new Point(row, col);
                canvas.drawRect(
                        getLeft(p),
                        getTop(p),
                        getRight(p),
                        getBottom(p),
                        gridPaint);
            }
        }
    }

    private void drawLetters(Canvas canvas) {
        Rect bounds = new Rect();
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Point p = new Point(row, col);
                String letter = String.valueOf(letters[col][row]).toUpperCase();
                textPaint.getTextBounds(letter, 0, 1, bounds);
                canvas.drawText(
                        letter,
                        getHorizontalCenter(p) - (bounds.width() / 2),
                        getVerticalCenter(p) + (bounds.height() / 2),
                        textPaint);
            }
        }
    }
}