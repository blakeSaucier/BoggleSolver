package io.neilharvey.bogglebuddy;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements FloatingActionButton.OnClickListener, TextWatcher, EditText.OnEditorActionListener {

    private static final String TAG = "MainActivity";
    private static final int MIN_WORD_LENGTH = 3;
    private WordFinder wordFinder;
    private BoardFragment board;
    private EditText editText;
    private ExpandableListView wordListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = (BoardFragment) getSupportFragmentManager().findFragmentById(R.id.board);
        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(this);
        editText.setOnEditorActionListener(this);
        wordListView = (ExpandableListView) findViewById(R.id.expandableListView);
        wordFinder = createWordFinder();
    }

    private WordFinder createWordFinder() {

        TrieNode vocabulary = null;
        try {
            vocabulary = TrieBuilder.buildTrie(getApplicationContext());
        } catch (IOException ex) {
            Log.e(TAG, ex.toString());
            makeToast("Failed to load vocabulary : " + ex.getMessage());
        }

        return new WordFinder(vocabulary, MIN_WORD_LENGTH);
    }

    private void makeToast(CharSequence text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private void solveBoard() {
        if (!board.isValid()) {
            return;
        }

        //editText.setVisibility(View.GONE);
        Set<Word> words = wordFinder.findWords(board.getBoard());
        showWords(words);
    }


    private void showWords(Set<Word> words) {
        WordListAdapter listAdapter = WordListAdapter.create(this.getApplicationContext(), words);
        wordListView.setAdapter(listAdapter);
        wordListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        board.clear();
        editText.setText("");
        //editText.setVisibility(View.VISIBLE);
        editText.requestFocus();
        wordListView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String value = s.toString().toLowerCase();
        board.clear();
        for (int i = 0; i < s.length(); i++) {
            board.setLetter(i % 4, i / 4, value.charAt(i));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        Log.d(TAG, "onEditorAction:actionId=" + actionId);
        if(event != null) {
            Log.d(TAG, "onEditorAction:keyEvent=" + event.getKeyCode());
        }

        if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            hideKeyboard(v);
            solveBoard();
            return true;
        }

        return false;

    }

    private void hideKeyboard(TextView v) {
        InputMethodManager in = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
