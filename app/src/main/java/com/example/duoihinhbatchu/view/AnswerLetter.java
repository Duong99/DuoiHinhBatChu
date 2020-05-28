package com.example.duoihinhbatchu.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class AnswerLetter extends AppCompatButton {
    private OnClickAnswer mOnClickAnswer;
    private SuggestLetter mSuggestLetter;

    public AnswerLetter(Context context,  OnClickAnswer onClickAnswer) {
        super(context);
        init(onClickAnswer);
    }

    public interface OnClickAnswer{
        void onClickAnswer(SuggestLetter suggestLetter);
    }

    private void init( final OnClickAnswer onClickAnswer){
        this.mOnClickAnswer = onClickAnswer;

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AnswerLetter.this.getText() == ""){

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
