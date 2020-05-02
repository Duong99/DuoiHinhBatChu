package com.example.duoihinhbatchu;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.example.duoihinhbatchu.model.Question;

public class ShowDialogExplain {
    public ShowDialogExplain(Context context, Question question){
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.giai_nghia);

        dialog.setCanceledOnTouchOutside(false);

        final TextView txtOK = dialog.findViewById(R.id.txtOK);
        TextView txtGiaiNghia = dialog.findViewById(R.id.txtGiaiNghia);

        txtOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtOK.setBackgroundColor(Color.GREEN);
                dialog.cancel();
            }
        });

        txtGiaiNghia.setText(question.getGiaiNghia());
        dialog.show();
    }
}
