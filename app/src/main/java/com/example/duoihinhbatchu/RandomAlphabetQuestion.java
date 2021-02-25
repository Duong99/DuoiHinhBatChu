package com.example.duoihinhbatchu;

import android.content.Context;

import com.example.duoihinhbatchu.database.MyDatabase;
import com.example.duoihinhbatchu.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomAlphabetQuestion {
    private Context mContext;

    public RandomAlphabetQuestion(Context context) {
        this.mContext = context;
    }

    public List<String> returnTwentyAlphabet(Question question) {
        List<String> twentyAlphabet = new ArrayList<>();
        String[] nameListAlphabet = mContext.getResources().getStringArray(R.array.array_alphabet_english);
        List<String> twentySixAlphabet = new ArrayList<>(Arrays.asList(nameListAlphabet));
        int position;
        // Ramdom ra 20 chữ cái ngẫu nhiên trong list chữa cái anphabestList
        for (int i = 0; i < 20; i++) {
            position = new Random().nextInt(26);
            twentyAlphabet.add(twentySixAlphabet.get(position));
        }
        int length = question.getContent().length();
        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            String text = String.valueOf(question.getContent().charAt(i));
            position = new Random().nextInt(20);
            if (numberList.contains(position)) {
                i--;
            } else {
                twentyAlphabet.remove(position);
                twentyAlphabet.add(position, text);
                numberList.add(position);
            }
        }
        return twentyAlphabet;
    }

    public Question returnQuestion() {
        MyDatabase db = new MyDatabase(mContext);
        ArrayList<Question> questions = new ArrayList<>();
        questions = db.getQuestionOk(0);

        if (questions.size() == 0) {
            return null;
        }

        Random r = new Random();
        int position = r.nextInt(questions.size());
        db.updateQuestion(questions.get(position));
        return questions.get(position);
    }
}
