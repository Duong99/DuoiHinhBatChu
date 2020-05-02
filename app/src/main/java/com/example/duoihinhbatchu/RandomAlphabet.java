package com.example.duoihinhbatchu;

import android.content.Context;

import com.example.duoihinhbatchu.model.Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class RandomAlphabet {
    public RandomAlphabet(){}

    public List<String> returnTwentyAlphabet(Question question, Context context){
        List<String> twentyAlphabet = new ArrayList<>();

        String[] nameListAlphabet = context.getResources().getStringArray(R.array.array_alphabet_english);
        List<String> twentyFourAlphabet = new ArrayList<>(Arrays.asList(nameListAlphabet));

        int position = -1;

        // Ramdom ra 20 chữ cái ngẫu nhiên trong list chữa cái anphabestList
        for (int i = 0; i < 20; i++) {
            position = new Random().nextInt(26);
            twentyAlphabet.add(twentyFourAlphabet.get(position));
        }

        int length = question.getContent().length();
        int count = 0;
        String text;
        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            text = String.valueOf(question.getContent().charAt(i));
            count = 0;
            position = new Random().nextInt(20);

            if (i == 0) {
                twentyAlphabet.remove(position);
                twentyAlphabet.add(position, text);
                numberList.add(position);
            } else {
                for (int j = 0; j < numberList.size(); j++) {
                    if (position != numberList.get(j)) {
                        count++;
                    } else {
                        count = 0;
                        i--;
                        break;
                    }
                }
                if (count == numberList.size()) {
                    twentyAlphabet.remove(position);
                    twentyAlphabet.add(position, text);
                    numberList.add(position);
                }
            }
        }
        return twentyAlphabet;
    }
}
