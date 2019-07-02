package com.example.duoihinhbatchu.view;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.view.View;

public class SuggestLetter extends AppCompatButton {
    OnClickSuggestLetter mOnClickSuggestLetter;

    public SuggestLetter(Context context, OnClickSuggestLetter onClickSuggestLetter) {
        super(context);
        init(onClickSuggestLetter);
    }

    public SuggestLetter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SuggestLetter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public interface OnClickSuggestLetter{
        void onClickSuggest(SuggestLetter suggestLetter);
    }

    private void init(final OnClickSuggestLetter onClickSuggestLetter){
        this.mOnClickSuggestLetter = onClickSuggestLetter;
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSuggestLetter.onClickSuggest(SuggestLetter.this);
            }
        });
    }
}
