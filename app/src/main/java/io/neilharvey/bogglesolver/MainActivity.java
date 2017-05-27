package io.neilharvey.bogglesolver;

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

public class MainActivity extends AppCompatActivity implements FloatingActionButton.OnClickListener, TextWatcher, EditText.OnEditorActionListener, ExpandableListView.OnChildClickListener {

    private static final String TAG = "MainActivity";
    private static final int MIN_WORD_LENGTH = 3;
    private WordFinder wordFinder;
    private Board board;
    private EditText editText;
    private ExpandableListView wordListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        board = (Board) findViewById(R.id.board);
        editText = (EditText) findViewById(R.id.editText);
        editText.addTextChangedListener(this);
        editText.setOnEditorActionListener(this);
        wordListView = (ExpandableListView) findViewById(R.id.expandableListView);
        wordListView.setOnChildClickListener(this);
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

        char[][] letters = board.getLetters();
        Set<Word> words = wordFinder.findWords(letters);
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

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        WordListAdapter adapter = (WordListAdapter) parent.getExpandableListAdapter();
        Word word = (Word)adapter.getChild(groupPosition, childPosition);
        board.highlightWord(word);
        return true;
    }
}
