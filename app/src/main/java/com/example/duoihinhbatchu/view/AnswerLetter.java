package com.example.duoihinhbatchu.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

public class AnswerLetter extends AppCompatButton {
    private OnClickAnswer mOnClickAnswer;
    private String mLerter;
    private SuggestLetter mSuggestLetter;

    public AnswerLetter(Context context, String letter, OnClickAnswer onClickAnswer) {
        super(context);
        init(letter, onClickAnswer);

    }

    public AnswerLetter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnswerLetter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnClickAnswer{
        void onClickAnswer(SuggestLetter suggestLetter);
    }

    private void init(final String letter, final OnClickAnswer onClickAnswer){
        mOnClickAnswer = onClickAnswer;
        mLerter = letter;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if( AnswerLetter.this.getText() == ""){

                }else{
                    AnswerLetter.this.setText("");
                    onClickAnswer.onClickAnswer(mSuggestLetter);
                    mSuggestLetter = null;
                }

            }
        });
    }

    public boolean isFill(){
        return mSuggestLetter != null;
    }

    public void setAnswer(SuggestLetter suggestLetter){
        mSuggestLetter = suggestLetter;
        setText(suggestLetter.getText());
    }
}
