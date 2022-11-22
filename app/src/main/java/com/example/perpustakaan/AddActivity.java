package com.example.perpustakaan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.perpustakaan.adapter.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddActivity extends AppCompatActivity {
    DBHelper dbHelper;
    TextView TvStatus;
    Button BtnProses;
    EditText TxID, TxNama, TxJudul, TxtglPinjam, TxtglKembali, TxStatus;
    long id;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbHelper = new DBHelper(this);
        id = getIntent().getLongExtra(DBHelper.row_id, 0);

        TxID = (EditText) findViewById(R.id.txID);
        TxJudul = (EditText) findViewById(R.id.txJudul);
        TxNama = (EditText) findViewById(R.id.txNamaAnggota);
        TxtglPinjam = (EditText) findViewById(R.id.txPinjam);
        TxtglKembali = (EditText) findViewById(R.id.txPengembalian);
        TxStatus = (EditText) findViewById(R.id.txStatus);

        TvStatus = (TextView) findViewById(R.id.TVStatus);
        BtnProses = (Button) findViewById(R.id.btnProses);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        getData();
        TxtglKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        BtnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prosesKembali();
            }
        });

        ActionBar menu = getSupportActionBar();
        menu.setDisplayShowHomeEnabled(true);
        menu.setDisplayHomeAsUpEnabled(true);
    }

    // Logika ketika Alert muncul
    private void prosesKembali() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setMessage("Proses ke Pengembalian Buku?");
        builder.setCancelable(true);
        builder.setPositiveButton("Proses", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                String idpinjam = TxID.getText().toString().trim();
                String kembali = "Dikembalikan";

                ContentValues values = new ContentValues();

                values.put(DBHelper.row_status, kembali);
                dbHelper.updateData(values, id);
                Toast.makeText(AddActivity.this, "Proses Pengembalian Berhasil", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Akan muncul Alert ketika pengembalian buku berhasil
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year,month,dayOfMonth);
                TxtglKembali.setText(dateFormatter.format(newDate.getTime()));
            }
        },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getData() {
        Calendar cl = Calendar.getInstance();
        SimpleDateFormat sdfl = new SimpleDateFormat("dd-MM-yy", Locale.US);
        String txtglPinjam = sdfl.format(cl.getTime());
        TxtglPinjam.setText(txtglPinjam);

        Cursor cur = dbHelper.oneData(id);
        if (cur.moveToFirst()){
            @SuppressLint("Range") String idpinjam = cur.getString(cur.getColumnIndex(DBHelper.row_id));
            @SuppressLint("Range") String nama = cur.getString(cur.getColumnIndex(DBHelper.row_nama));
            @SuppressLint("Range") String judul = cur.getString(cur.getColumnIndex(DBHelper.row_judul));
            @SuppressLint("Range") String pinjam = cur.getString(cur.getColumnIndex(DBHelper.row_pinjam));
            @SuppressLint("Range") String kembali = cur.getString(cur.getColumnIndex(DBHelper.row_kembali));
            @SuppressLint("Range") String status = cur.getString(cur.getColumnIndex(DBHelper.row_status));

            TxID.setText(idpinjam);
            TxNama.setText(nama);
            TxJudul.setText(judul);
            TxtglPinjam.setText(pinjam);
            TxtglKembali.setText(kembali);
            TxStatus.setText(status);

            if (TxJudul.equals("")){
                TvStatus.setVisibility(View.GONE);
                TxStatus.setVisibility(View.GONE);
                BtnProses.setVisibility(View.GONE);
            } else {
                TvStatus.setVisibility(View.VISIBLE);
                TxStatus.setVisibility(View.VISIBLE);
                BtnProses.setVisibility(View.VISIBLE);
            }

            if (status.equals("Dipinjam")){
                BtnProses.setVisibility(View.VISIBLE);
            } else {
                BtnProses.setVisibility(View.GONE);
                TxNama.setEnabled(false);
                TxJudul.setEnabled(false);
                TxtglKembali.setEnabled(false);
                TxStatus.setEnabled(false);
            }
        }
    }
}