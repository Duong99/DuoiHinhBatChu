package com.example.duoihinhbatchu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.duoihinhbatchu.R;
import com.example.duoihinhbatchu.model.History;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class HistoryAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<History> histories;

    public HistoryAdapter(Context context, ArrayList<History> histories) {
        this.context = context;
        this.histories = histories;
    }

    @Override
    public int getCount() {
        return histories.size();
    }

    @Override
    public Object getItem(int i) {
        return histories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.gridview_list_history, null);
        }
        ImageView imvHistoryVictory = view.findViewById(R.id.imvHistoryVictory);
        History history = histories.get(i);
        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(history.getImage());
        Bitmap bitmap = BitmapFactory.decodeStream(arrayInputStream);
        imvHistoryVictory.setImageBitmap(bitmap);

        return view;
    }
}
