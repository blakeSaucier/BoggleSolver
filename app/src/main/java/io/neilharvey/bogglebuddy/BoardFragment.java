package io.neilharvey.bogglebuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

public class BoardFragment extends Fragment {

    public static final int SIZE = 4;
    private char[][] board = new char[SIZE][SIZE];

    public BoardFragment() {
    }

    public char[][] getBoard() {
        return board;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    public boolean isValid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!Character.isLetter(board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public void clear() {
        board = new char[SIZE][SIZE];
        drawBoard();
    }

    public void setLetter(int x, int y, char letter) {
        board[x][y] = letter;
        drawBoard();
    }

    public void highlightWord(Word word) {

        clearHighlight();
        GridLayout grid = getGridLayout();
        for (Point point : word.getPath()) {
            int position = (4 * point.getCol()) + point.getRow();
            TextView text = (TextView) grid.getChildAt(position);
            int color = ContextCompat.getColor(getContext(), R.color.primary_light);
            text.setBackgroundColor(color);
        }
    }

    private GridLayout getGridLayout() {
        return (GridLayout) this.getActivity().findViewById(R.id.board_grid);
    }

    public void clearHighlight() {

        GridLayout grid = getGridLayout();
        for (int position = 0; position < SIZE * SIZE; position++) {
            TextView text = (TextView) grid.getChildAt(position);
            int color = ContextCompat.getColor(getContext(), R.color.icons);
            text.setBackgroundColor(color);
        }
    }

    private void drawBoard() {

        GridLayout grid = getGridLayout();

        for (int i = 0; i < SIZE * SIZE; i++) {
            TextView text = (TextView) grid.getChildAt(i);
            int x = i % 4;
            int y = i / 4;
            setBoardCharacter(text, x, y);
        }
    }

    private void setBoardCharacter(TextView text, int x, int y) {
        char character = Character.toUpperCase(board[x][y]);

        if (character == 'Q') {
            text.setText(new char[]{character, 'u'}, 0, 2);
        } else {
            text.setText(new char[]{character}, 0, 1);
        }
    }
}
