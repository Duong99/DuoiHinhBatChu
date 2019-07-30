package com.example.duoihinhbatchu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.duoihinhbatchu.database.MyDatabase;
import com.example.duoihinhbatchu.model.Question;
import com.example.duoihinhbatchu.view.AnswerLetter;
import com.example.duoihinhbatchu.view.SuggestLetter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MainPlayGameActivity extends AppCompatActivity implements AnswerLetter.OnClickAnswer, SuggestLetter.OnClickSuggestLetter {

    private final int NUMBER_O_ANSWER_IN_LINE = 8;
    private final int NUMBER_0_SUGGEST_IN_LINE = 10;
    private final int MAX_NUMBER_O_IN_SUGGEST = 20;

    private FrameLayout frameLayout;

    private List<Integer> imageListAnswer;
    private List<String> anphabestList;
    private List<String> ramdonAnphabestList;
    private List<AnswerLetter> answerLetterList;
    private List<SuggestLetter> suggestLetterList;
    private List<String> imageListPlay;
    private List<Question> questionList;

    private MyDatabase myDatabase;

    private TextView txtMan, txtDiem;
    private ImageView imv;
    private Button btnBoQua, btnCauTiep;

    private int idMan = 1;
    private int diemPlay = 20;
    private AnswerLetter btnAnwer;

    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_play_game);

        anhXa();

        sharedPreferences = getSharedPreferences("mandiemplay", MODE_PRIVATE);

        idMan = sharedPreferences.getInt("man", idMan);
        diemPlay = sharedPreferences.getInt("diem", diemPlay);

        if(idMan == 504){
            Intent intent = new Intent(MainPlayGameActivity.this, Winner.class);
            startActivity(intent);
        }

        txtMan.setText(String.valueOf(idMan));
        txtDiem.setText(String.valueOf(diemPlay));

        questionList = myDatabase.getQuestionDB(idMan);
        createButonFrame(questionList.get(0));
    }

    private List<String> ramdomAnphabest(Question question) {
        ramdonAnphabestList = new ArrayList<>();

        String[] nameListAnphabest = getResources().getStringArray(R.array.array_alphabest_english);
        anphabestList = new ArrayList<>(Arrays.asList(nameListAnphabest));

        int position = -1;

        // Ramdom ra 20 chữ cái ngẫu nhiên trong list chữa cái anphabestList
        for (int i = 0; i < 20; i++) {
            position = new Random().nextInt(26);
            ramdonAnphabestList.add(anphabestList.get(position));
        }

        char a='a';
        int length = question.getContent().length();
        int count = 0;
        String text;
        List<Integer> numberList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            text = String.valueOf(question.getContent().charAt(i));
            count = 0;
            position = new Random().nextInt(20);

            if (i == 0) {
                ramdonAnphabestList.remove(position);
                ramdonAnphabestList.add(position, text);
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
                    ramdonAnphabestList.remove(position);
                    ramdonAnphabestList.add(position, text);
                    numberList.add(position);
                }
            }
        }

        return ramdonAnphabestList;
    }

    private void createButonFrame(Question question) {
        int idHinh = getResources().getIdentifier(imageListPlay.get(idMan - 1), "drawable", getPackageName());

        if(idMan > 44){
             idHinh = getResources().getIdentifier(imageListPlay.get(idMan - 2), "drawable", getPackageName());
        }

        imv.setBackgroundResource(idHinh);

        frameLayout = findViewById(R.id.frameLayout);
        answerLetterList = new ArrayList<>();
        createListImageButtom();
        ramdomAnphabest(questionList.get(0));

        int length = question.getContent().length();
        int btnWidhtHeight = getResources().getDimensionPixelOffset(R.dimen.width_height_answer);
        int btnMarginLeft = getResources().getDimensionPixelOffset(R.dimen.margin_left);
        int btnMarginTop = getResources().getDimensionPixelOffset(R.dimen.margin_top);
        int widthScreen = getResources().getDisplayMetrics().widthPixels;

        int marginScreenLeft = 0;
        int marginScreenTop = 0;

        // Tạo nút cho câu trả lời
        for (int i = 0; i < length; i++) {

            btnAnwer = new AnswerLetter(this, String.valueOf(question.getContent().charAt(i)), this);

            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(btnWidhtHeight, btnWidhtHeight);

            answerLetterList.add(btnAnwer);

            btnAnwer.setTextColor(Color.BLUE);
            btnAnwer.setBackgroundResource(imageListAnswer.get(2));
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
            frameLayout.addView(btnAnwer, layoutParams);
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
            btn.setText(ramdonAnphabestList.get(i));
            btn.setTextColor(Color.GREEN);
            btn.setBackgroundResource(imageListAnswer.get(3));
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

    private void createListImageButtom() {
        imageListAnswer = new ArrayList<>();
        imageListAnswer.add(R.drawable.ic_tile_false); // 0 trả lời sai
        imageListAnswer.add(R.drawable.ic_tile_true); // 1 trả lời đúng
        imageListAnswer.add(R.drawable.ic_anwser); // 2 button trên
        imageListAnswer.add(R.drawable.ic_tile_hover); // 3 button duoi
    }

    @Override
    public void onClickAnswer(SuggestLetter suggestLetter) {
        // Thay đổi backgound button khi kích vào list button câu trả lời
        for (AnswerLetter answerLetter : answerLetterList) {
            answerLetter.setBackgroundResource(imageListAnswer.get(2));
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

    private void anhXa() {
        txtDiem = (TextView) findViewById(R.id.txtDiemPlay);
        txtMan= (TextView) findViewById(R.id.txtDiemHelp);
        imv = (ImageView) findViewById(R.id.imvPlay);
        btnBoQua = (Button) findViewById(R.id.btnBoQua);
        btnCauTiep = (Button) findViewById(R.id.btnCauTiep);

        btnCauTiep.setVisibility(View.INVISIBLE);

        questionList = new ArrayList<>();
        myDatabase = new MyDatabase(this);

        imageListPlay = new ArrayList<>();

        String[] nameListPlay = getResources().getStringArray(R.array.array_image);
        imageListPlay = new ArrayList<>(Arrays.asList(nameListPlay));

        txtDiem.setText(String.valueOf(diemPlay));
        txtMan.setText(String.valueOf(idMan));
    }

    public void onClickBoQua(View view) {
        dialogBoQua();
    }

    public void onClickCauTiep(View view) {
        quaCau();
    }

    private void quaCau() {
        btnCauTiep.setVisibility(View.INVISIBLE);
        btnBoQua.setVisibility(View.VISIBLE);
        txtDiem.setEnabled(true);

        //idMan = 504;
        if(idMan == 504){
            Intent intent = new Intent(MainPlayGameActivity.this, Winner.class);
            startActivity(intent);
        }

        txtMan.setText(String.valueOf(idMan));

        if(idMan > 44){
            txtMan.setText(String.valueOf(idMan-1));
        }

        frameLayout.removeAllViews();

        luuManDiemPlay();
        questionList = myDatabase.getQuestionDB(idMan);
        createButonFrame(questionList.get(0));
    }

    public void onclickDiem(View view) {
        if(diemPlay > 0) {
            for(int i=0; i<answerLetterList.size(); i++){
                // Hiện thị chữ cái gợi ý ra
                if(answerLetterList.get(i).getText() == ""){
                    answerLetterList.get(i).setText(String.valueOf(questionList.get(0).getContent().charAt(i)));
                    answerLetterList.get(i).setTextColor(Color.BLACK);
                    answerLetterList.get(i).setEnabled(false);

                    // Tìm chưa cái gợi ý ở suggestLetterList rồi ẩn nói đi
                    for(int j=0; j<suggestLetterList.size(); j++){
                        if(suggestLetterList.get(j).getText().toString().equals(String.valueOf(questionList.get(0).getContent().charAt(i)))){
                            suggestLetterList.get(j).setVisibility(View.INVISIBLE);
                            answerLetterList.get(i).setAnswer(suggestLetterList.get(j)); // Thêm vào list câu trả lời

                            diemPlay += -5;
                            txtDiem.setText(String.valueOf(diemPlay));

                            luuManDiemPlay();
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
                idMan += 1;
                if(idMan == 44){
                    idMan += 1;
                }
                quaCau();
                luuManDiemPlay();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
    }

    private void dialogGiaiNghia(){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.giai_nghia);

        dialog.setCanceledOnTouchOutside(false);

        final TextView txtOK = (TextView) dialog.findViewById(R.id.txtOK);
        TextView txtGiaiNghia = (TextView) dialog.findViewById(R.id.txtGiaiNghia);

        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtOK.setBackgroundColor(Color.GREEN);
                dialog.cancel();
            }
        });

        txtGiaiNghia.setText(questionList.get(0).getGiaiNghia());
        dialog.show();
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
                suggestLetter.setEnabled(false);        // Không cho bấm vào button suggestLetter nữa
            }
            String anwserThey = "";

            for (int i = 0; i < answerLetterList.size(); i++) {
                anwserThey += answerLetterList.get(i).getText();
            }

            if (anwserThey.equals(questionList.get(0).getContent())) {

                /*
                for(AnswerLetter answerLetter: answerLetterList){
                    answerLetter.setVisibility(View.INVISIBLE);
                }

                for(SuggestLetter suggestLetter : suggestLetterList){
                    suggestLetter.setVisibility(View.INVISIBLE);
                }
                */
                dialogGiaiNghia();

                btnBoQua.setVisibility(View.INVISIBLE);
                btnCauTiep.setVisibility(View.VISIBLE);
                for (AnswerLetter answerTrue : answerLetterList) {
                    answerTrue.setBackgroundResource(imageListAnswer.get(1));
                }

                idMan += 1;

                if(idMan == 44){
                    idMan +=1;
                }
                diemPlay += 10;

                txtDiem.setText(String.valueOf(diemPlay));

                for (AnswerLetter answerLetter : answerLetterList) {
                    answerLetter.setEnabled(false);
                }

                txtDiem.setEnabled(false);



                luuManDiemPlay();

                //btnCauTiep.setVisibility(View.VISIBLE);

                Toast.makeText(this, "Đáp án đúng rồi :)", Toast.LENGTH_SHORT).show();
            } else {
                for (AnswerLetter answerFalse : answerLetterList) {
                    answerFalse.setBackgroundResource(imageListAnswer.get(0));
                }
                btnAnwer.setBackgroundResource(imageListAnswer.get(0));
                Toast.makeText(this, "Đáp án sai rồi :(", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void luuManDiemPlay(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("man", idMan);
        editor.putInt("diem", diemPlay);
        editor.commit();
    }
}
