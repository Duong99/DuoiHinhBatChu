package com.example.duoihinhbatchu.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duoihinhbatchu.PlayMusic;
import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.RandomAlphabetQuestion;
import com.example.duoihinhbatchu.Screenshot;
import com.example.duoihinhbatchu.ShowDialogExplain;
import com.example.duoihinhbatchu.database.MyDatabase;
import com.example.duoihinhbatchu.model.Question;
import com.example.duoihinhbatchu.view.AnswerLetter;
import com.example.duoihinhbatchu.view.SuggestLetter;

import java.util.ArrayList;
import java.util.List;

public class MainPlayGameActivity extends AppCompatActivity implements AnswerLetter.OnClickAnswer,
        SuggestLetter.OnClickSuggestLetter, View.OnClickListener {

    private final int NUMBER_O_ANSWER_IN_LINE = 8;
    private final int NUMBER_0_SUGGEST_IN_LINE = 10;
    private final int MAX_NUMBER_O_IN_SUGGEST = 20;

    private FrameLayout frameLayout;

    private List<String> twentyAlphabet;
    private List<AnswerLetter> answerLetterList;
    private List<SuggestLetter> suggestLetterList;
    private Question question;

    private MyDatabase myDatabase;
    private boolean music = false;

    private TextView txtMan, txtDiem;
    private ImageView imv;
    private Button btnBoQua, btnCauTiep, btnScreenshot, btnExplain;

    private int idMan = -1, man = 1;
    private int diemPlay = 20;
    private AnswerLetter btnAnswer;
    private RandomAlphabetQuestion raq;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_play_game);

        init();

        raq = new RandomAlphabetQuestion(this);
        sharedPreferences = getSharedPreferences("MuiscManDiem", MODE_PRIVATE);

        man = sharedPreferences.getInt("man", man);
        idMan = sharedPreferences.getInt("idman", idMan);
        diemPlay = sharedPreferences.getInt("diem", diemPlay);
        music = sharedPreferences.getBoolean("music", false);

        txtMan.setText(String.valueOf(man));
        txtDiem.setText(String.valueOf(diemPlay));

        if (idMan == -1){
            randomQuesion();
        }else {
            question = myDatabase.getQuestionDB(idMan);
        }

        createButtonFrame(question);
    }

    private void createButtonFrame(Question question) {
        int idHinh = getResources().getIdentifier(question.getContent(), "drawable",
                getPackageName());

        imv.setBackgroundResource(idHinh);

        frameLayout = findViewById(R.id.frameLayout);
        answerLetterList = new ArrayList<>();
        twentyAlphabet = new ArrayList<>();
        twentyAlphabet = new RandomAlphabetQuestion(this)
                .returnTwentyAlphabet(question, this);

        int length = question.getContent().length(); // Độ dài đáp án
        int btnWidhtHeight = getResources().getDimensionPixelOffset(R.dimen.width_height_answer);
        int btnMarginLeft = getResources().getDimensionPixelOffset(R.dimen.margin_left);
        int btnMarginTop = getResources().getDimensionPixelOffset(R.dimen.margin_top);
        int widthScreen = getResources().getDisplayMetrics().widthPixels;

        int marginScreenLeft = 0;
        int marginScreenTop = 0;

        // Tạo nút cho câu trả lời
        for (int i = 0; i < length; i++) {
            btnAnswer = new AnswerLetter(this, String.valueOf(question.getContent().charAt(i)), this);

            FrameLayout.LayoutParams layoutParams = new
                    FrameLayout.LayoutParams(btnWidhtHeight, btnWidhtHeight);

            answerLetterList.add(btnAnswer);

            btnAnswer.setTextColor(Color.BLUE);
            btnAnswer.setBackgroundResource(R.drawable.ic_anwser);
            if (i < 8) {
                int lengthText = length > NUMBER_O_ANSWER_IN_LINE ? NUMBER_O_ANSWER_IN_LINE : length;

                marginScreenLeft = (widthScreen - (btnWidhtHeight * lengthText + btnMarginLeft * (length - 1))) / 2
                        + (btnMarginLeft + btnWidhtHeight) * i;

                marginScreenTop = btnMarginTop;
            } else {
                int lenthText = length - NUMBER_O_ANSWER_IN_LINE;

                marginScreenLeft = (widthScreen - (lenthText * btnWidhtHeight + btnMarginLeft * (lenthText - 1))) / 2
                        + (btnMarginLeft + btnWidhtHeight) * (i - 8);

                marginScreenTop = btnMarginTop + btnWidhtHeight + btnMarginTop;
            }

            layoutParams.setMargins(marginScreenLeft, marginScreenTop, 0, 0);
            frameLayout.addView(btnAnswer, layoutParams);
        }

        for(AnswerLetter answerLetter : answerLetterList){
            answerLetter.setVisibility(View.VISIBLE);
        }

        // Tạo nút gợi ý đáp án trả lời
        suggestLetterList = new ArrayList<>();
        int btnWidthHeightSuggest = getResources().getDimensionPixelOffset(R.dimen.width_height_suggest);
        for (int i = 0; i < MAX_NUMBER_O_IN_SUGGEST; i++) {
            SuggestLetter btn = new SuggestLetter(this, this);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(btnWidhtHeight, btnWidhtHeight);
            suggestLetterList.add(btn);
            btn.setText(twentyAlphabet.get(i));

            btn.setTextColor(Color.GREEN);
            btn.setBackgroundResource(R.drawable.ic_tile_hover);
            if (i < 10) {
                marginScreenLeft = (widthScreen - ((NUMBER_0_SUGGEST_IN_LINE * btnMarginLeft - 1) +
                        (btnWidthHeightSuggest * NUMBER_0_SUGGEST_IN_LINE))) / 2 +
                        (btnMarginLeft + btnWidthHeightSuggest) * i;

                marginScreenTop = btnMarginTop * 3 + btnWidhtHeight * 3 + 10;
            } else {
                marginScreenLeft = (widthScreen - ((NUMBER_0_SUGGEST_IN_LINE * btnMarginLeft - 1) +
                        (btnWidthHeightSuggest * NUMBER_0_SUGGEST_IN_LINE))) / 2 +
                        (btnMarginLeft + btnWidthHeightSuggest) * (i - 10);

                marginScreenTop = btnMarginTop * 4 + btnWidhtHeight * 3 + btnWidthHeightSuggest ;
            }

            layoutParams.setMargins(marginScreenLeft, marginScreenTop, 0, 0);
            frameLayout.addView(btn, layoutParams);
        }

        for(SuggestLetter suggestLetter : suggestLetterList){
            suggestLetter.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClickAnswer(SuggestLetter suggestLetter) {
        // Thay đổi backgound button khi kích vào list button câu trả lời
        for (AnswerLetter answerLetter : answerLetterList) {
            answerLetter.setBackgroundResource(R.drawable.ic_anwser);
        }

        for (SuggestLetter suggestLetter1: suggestLetterList){
            suggestLetter1.setEnabled(true);
        }
        suggestLetter.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClickSuggest(SuggestLetter suggestLetter) {
        suggestLetter.setVisibility(View.INVISIBLE);

        for (AnswerLetter answerLetter : answerLetterList) {
            if (!answerLetter.isFill()) {
                answerLetter.setAnswer(suggestLetter);
                break;
            }
        }
        checkTrueFalseResult();
    }

    private void init() {
        txtDiem =  findViewById(R.id.txtDiemPlay);
        txtMan=  findViewById(R.id.txtDiemHelp);
        imv =  findViewById(R.id.imvPlay);

        btnBoQua =  findViewById(R.id.btnBoQua);
        btnCauTiep =  findViewById(R.id.btnCauTiep);
        btnExplain =  findViewById(R.id.btnExplain);
        btnScreenshot =  findViewById(R.id.btnScreenshot);

        btnCauTiep.setOnClickListener(this);
        btnBoQua.setOnClickListener(this);
        btnExplain.setOnClickListener(this);
        btnScreenshot.setOnClickListener(this);

        showOffBtn(View.INVISIBLE);

        myDatabase = new MyDatabase(this);

        txtDiem.setText(String.valueOf(diemPlay));
        txtMan.setText(String.valueOf(idMan));
    }

    private void showOffBtn(int visibility){
        btnCauTiep.setVisibility(visibility);
        btnScreenshot.setVisibility(visibility);
        btnExplain.setVisibility(visibility);
    }

    private void quaCau(Question question) {
        frameLayout.removeAllViews();
        showOffBtn(View.INVISIBLE);
        btnBoQua.setVisibility(View.VISIBLE);
        txtDiem.setEnabled(true);
        txtMan.setText(String.valueOf(man));
        if (question == null){
            Intent intent = new Intent(MainPlayGameActivity.this, Winner.class);
            startActivity(intent);
        }else {
            createButtonFrame(question);
        }
    }

    public void onclickDiem(View view) {
        if(diemPlay > 0) {
            for(int i=0; i<answerLetterList.size(); i++){
                // Hiện thị chữ cái gợi ý ra
                if(answerLetterList.get(i).getText() == ""){
                    answerLetterList.get(i).setText(String.valueOf(question.getContent().charAt(i)));
                    answerLetterList.get(i).setTextColor(Color.BLACK);
                    answerLetterList.get(i).setEnabled(false);

                    // Tìm chưa cái gợi ý ở suggestLetterList rồi ẩn nói đi
                    for(int j=0; j<suggestLetterList.size(); j++){
                        if(suggestLetterList.get(j).getText().toString().equals(String.valueOf(question.getContent().charAt(i)))){
                            suggestLetterList.get(j).setVisibility(View.INVISIBLE);
                            answerLetterList.get(i).setAnswer(suggestLetterList.get(j)); // Thêm vào list câu trả lời

                            diemPlay += -5;
                            txtDiem.setText(String.valueOf(diemPlay));
                            break;
                        }
                    }
                    break;
                }
            }
        }
        checkTrueFalseResult();
    }

    private void dialogBoQua(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Xác Nhận");
        builder.setMessage("Sẽ hiện quảng cáo nếu bạn bỏ qua");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                man += 1;
                randomQuesion();
                quaCau(question);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });

        builder.show();
    }

    private void checkTrueFalseResult(){
        int count = 0;
        //Đếm xem đã điền hết câu trả lời chưa
        for (AnswerLetter answerLetter : answerLetterList){
            if (answerLetter.isFill()) {
                count++;
            }else{
                break;
            }
        }

        // Nếu điền hết rồi mới kiểm tra xem đúng hay sai
        if(count == answerLetterList.size()){

            for (SuggestLetter suggestLetter: suggestLetterList){
                suggestLetter.setEnabled(false);  // Không cho bấm vào button suggestLetter nữa
            }

            String answerThey = "";

            for (int i = 0; i < answerLetterList.size(); i++) {
                answerThey += answerLetterList.get(i).getText();
            }

            if (answerThey.equals(question.getContent())) {
                btnBoQua.setVisibility(View.INVISIBLE);
                showOffBtn(View.VISIBLE);
                if (music){
                    new PlayMusic(this, R.raw.dung);
                }

                for (AnswerLetter answerTrue : answerLetterList) {
                    answerTrue.setBackgroundResource(R.drawable.ic_tile_true);
                }

                Toast.makeText(this, "Đáp án đúng rồi :)", Toast.LENGTH_SHORT).show();
                diemPlay += 10;
                txtDiem.setText(String.valueOf(diemPlay));
                txtDiem.setEnabled(false);
                man ++;

                for (AnswerLetter answerLetter : answerLetterList) {
                    answerLetter.setEnabled(false);
                }

            } else {
                for (AnswerLetter answerFalse : answerLetterList) {
                    answerFalse.setBackgroundResource(R.drawable.ic_tile_false);
                }

                //btnAnswer.setBackgroundResource(R.drawable.ic_tile_false);
                if (music){
                    new PlayMusic(this, R.raw.sai);
                }
                Toast.makeText(this, "Đáp án sai rồi :(", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveLevel(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idman", question.getId());
        editor.putInt("diem", diemPlay);
        editor.putInt("man", man);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        if (btnCauTiep.getVisibility() == View.VISIBLE){
            randomQuesion();
        }
        saveLevel();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (btnCauTiep.getVisibility() == View.VISIBLE){
            randomQuesion();
        }
        saveLevel();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBoQua:
                dialogBoQua();
                break;
            case R.id.btnCauTiep:
                randomQuesion();
                quaCau(question);
                break;
            case R.id.btnScreenshot:
                showOffBtn(View.INVISIBLE);
                final Screenshot screen = new Screenshot(this);
                final View rootView = getWindow().getDecorView().findViewById(android.R.id.content);

                screen.getScreenShot(rootView);
                final Dialog dialog = new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_screenshot);

                ImageView imvScreen = dialog.findViewById(R.id.imvScreenshot);
                Button btnCancel = dialog.findViewById(R.id.btnCancelScreenshot);
                Button btnSave = dialog.findViewById(R.id.btnSaveScreenshot);

                final Bitmap bitmap = screen.getScreenShot(rootView);
                imvScreen.setImageBitmap(bitmap);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        screen.addScreenShotInDB(String.valueOf(question.getId()), bitmap, rootView);
                        Toast.makeText(MainPlayGameActivity.this, "Save Successfully",
                                Toast.LENGTH_SHORT).show();
                        showOffBtn(View.VISIBLE);
                        btnScreenshot.setVisibility(View.INVISIBLE);
                        dialog.cancel();
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        showOffBtn(View.VISIBLE);
                        dialog.cancel();
                    }
                });

                dialog.show();
                break;
            case R.id.btnExplain:
                new ShowDialogExplain(this, question);
                break;
        }
    }

    private void randomQuesion(){
        question = raq.returnQuestion();
    }
}