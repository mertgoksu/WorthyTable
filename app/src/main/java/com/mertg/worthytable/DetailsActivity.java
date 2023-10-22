package com.mertg.worthytable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mertg.worthytable.databinding.ActivityDetailsBinding;
import com.mertg.worthytable.databinding.ActivityMainBinding;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.darkThemeColor)));
        binding.calculateButton.setBackgroundColor(ContextCompat.getColor(this,R.color.darkThemeColor));

        database = this.openOrCreateDatabase("Companies", MODE_PRIVATE,null);

    }



    public void calculate(View view){
        String hisseAdi = binding.hisseAdiText.getText().toString();
        String lotBasinaFiyat = binding.lotBasinaFiyatText.getText().toString();
        String alinanLotSayisi = binding.alinanLotSayisiText.getText().toString();

        //Kullanıcının hisse adını kontrol et
        if (hisseAdi.isEmpty() || lotBasinaFiyat.isEmpty() || alinanLotSayisi.isEmpty()) {
            //Buralar boşsa kullanıcıya hata mesajı göster
            Toast.makeText(this, "Bu kısımları boş bırakamazsınız.", Toast.LENGTH_SHORT).show();
        } else {
            try {
                //Veritabanına ekleme işlemi yap
                database.execSQL("CREATE TABLE IF NOT EXISTS companies(id INTEGER PRIMARY KEY, hisseadi VARCHAR, lotfiyati VARCHAR, lotsayisi VARCHAR)");

                String sqlString = "INSERT INTO companies(hisseadi, lotfiyati, lotsayisi) VALUES(?, ?, ?)";

                SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
                sqLiteStatement.bindString(1, hisseAdi);
                sqLiteStatement.bindString(2, lotBasinaFiyat);
                sqLiteStatement.bindString(3, alinanLotSayisi);
                sqLiteStatement.execute();

                //İşlem başarılıysa MainActivity'ye dön
                finish();
                Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                startActivity(intent);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
