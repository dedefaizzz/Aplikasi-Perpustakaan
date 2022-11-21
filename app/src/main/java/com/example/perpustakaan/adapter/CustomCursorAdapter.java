package com.example.perpustakaan.adapter;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.perpustakaan.R;

public class CustomCursorAdapter extends CursorAdapter {
    private LayoutInflater ly;
    private SparseBooleanArray mSelectedItem;

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public CustomCursorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        ly = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSelectedItem = new SparseBooleanArray();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = ly.inflate(R.layout.row_data,viewGroup, false);
        MyHolder holder = new MyHolder();
        holder.listID = (TextView) v.findViewById(R.id.listID);
        holder.listJudul = (TextView) v.findViewById(R.id.listJudul);
        holder.listNama = (TextView) v.findViewById(R.id.listNama);
        holder.listPinjam = (TextView) v.findViewById(R.id.listTglPinjam);
        holder.listStatus = (TextView) v.findViewById(R.id.listStatus);

        v.setTag(holder);
        return v;
    }

    @SuppressLint("Range")
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        MyHolder holder = (MyHolder) view.getTag();
        holder.listID.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_id)));
        holder.listJudul.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_judul)));
        holder.listNama.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_nama)));
        holder.listPinjam.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_pinjam))
                + " - " + cursor.getString(cursor.getColumnIndex(DBHelper.row_kembali)));
        holder.listStatus.setText(cursor.getString(cursor.getColumnIndex(DBHelper.row_status)));

    }

    private class MyHolder {
        public TextView listID;
        public TextView listJudul;
        public TextView listNama;
        public TextView listPinjam;
        public TextView listStatus;
    }
}
