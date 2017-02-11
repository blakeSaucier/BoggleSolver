package io.neilharvey.bogglebuddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

    private void drawBoard() {
        setBoardCharacter(R.id.a1, 0, 0);
        setBoardCharacter(R.id.b1, 1, 0);
        setBoardCharacter(R.id.c1, 2, 0);
        setBoardCharacter(R.id.d1, 3, 0);
        setBoardCharacter(R.id.a2, 0, 1);
        setBoardCharacter(R.id.b2, 1, 1);
        setBoardCharacter(R.id.c2, 2, 1);
        setBoardCharacter(R.id.d2, 3, 1);
        setBoardCharacter(R.id.a3, 0, 2);
        setBoardCharacter(R.id.b3, 1, 2);
        setBoardCharacter(R.id.c3, 2, 2);
        setBoardCharacter(R.id.d3, 3, 2);
        setBoardCharacter(R.id.a4, 0, 3);
        setBoardCharacter(R.id.b4, 1, 3);
        setBoardCharacter(R.id.c4, 2, 3);
        setBoardCharacter(R.id.d4, 3, 3);
    }

    private void setBoardCharacter(int id, int x, int y) {
        TextView text = (TextView) this.getActivity().findViewById(id);
        char character = Character.toUpperCase(board[x][y]);
        text.setText(new char[]{character}, 0, 1);
    }
}
