/*
package com.mertg.worthytable;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TableAdapter extends ArrayAdapter<TableRowData> {
    public TableAdapter(Context context, int resource, ArrayList<TableRowData> data) {
        super(context, resource, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_show_result, parent, false);
        }

        // Satır verilerini alın
        TableRowData rowData = getItem(position);

        // XML içindeki TextView'lere verileri yerleştirin
        TextView gunTextView = convertView.findViewById(R.id.gunTextView);
*/
/*        TextView fiyatTextView = convertView.findViewById(R.id.fiyatTextView);
        TextView kazancTextView = convertView.findViewById(R.id.kazancTextView);*//*


        gunTextView.setText(rowData.getGunSayisi());
*/
/*
        fiyatTextView.setText(rowData.getFiyat());
        kazancTextView.setText(rowData.getKazanc());
*//*


        return convertView;
    }
}

*/
