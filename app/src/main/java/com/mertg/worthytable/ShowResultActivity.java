package com.mertg.worthytable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mertg.worthytable.databinding.ActivityDetailsBinding;
import com.mertg.worthytable.databinding.ActivityShowResultBinding;

import java.util.ArrayList;
import java.util.Objects;

public class ShowResultActivity extends AppCompatActivity {
    private ActivityShowResultBinding binding;
    SQLiteDatabase database;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShowResultBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.darkThemeColor)));
        binding.button.setBackgroundColor(ContextCompat.getColor(this,R.color.darkThemeColor));

        database = this.openOrCreateDatabase("Companies", MODE_PRIVATE, null);

        //Verileri adapter'den çekiyorum
        Intent intent = getIntent();
        int hisseId = intent.getIntExtra("companyId", 0);

        Cursor cursor = database.rawQuery("SELECT * FROM companies WHERE id = ?", new String[]{String.valueOf(hisseId)});

        if (cursor.moveToFirst()) {
            int hisseAdiIndex = cursor.getColumnIndex("hisseadi");
            int alinanLotSayisiIndex = cursor.getColumnIndex("lotsayisi");
            int lotBasinaFiyatIndex = cursor.getColumnIndex("lotfiyati");

            String hisseAdiFinal = cursor.getString(hisseAdiIndex);
            String alinanLotSayisiFinal = cursor.getString(alinanLotSayisiIndex);
            String lotBasinaFiyatFinal = cursor.getString(lotBasinaFiyatIndex);

            //Sermaye virgül sonrası 2 basamak yazdırma kodu
            float sermayeFloat = sermayeCalc(alinanLotSayisiFinal, lotBasinaFiyatFinal);
            String sermayeStr = String.format("%.2f",sermayeFloat);


            //Verileri ekrana yazdırdım
            binding.showCompanyText.setText(hisseAdiFinal);
            binding.showLotMiktariText.setText(alinanLotSayisiFinal + " Lot");
            binding.showLotBasinaFiyatText.setText("Değer: " + lotBasinaFiyatFinal);
            binding.showSermayeText.setText("Sermaye: " + sermayeStr);
            afterWorth(alinanLotSayisiFinal, lotBasinaFiyatFinal);
        }

        cursor.close();
    }


    public void deleteCompany(View view) {
        //Şu an gösterilen hisse adını al
        String hisseAdi = binding.showCompanyText.getText().toString();

        //Veritabanından bu hisseyi sil
        database.execSQL("DELETE FROM companies WHERE hisseadi = ?", new String[]{hisseAdi});

        //Kullanıcıyı bir önceki ekrana döndür
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



    //Geçici çözüm, şu anlık sermaye işlemlerini burada yapacağım.
    public float sermayeCalc(String lotSayisi, String lotFiyati) {
        float lotSayisiFloat = Float.parseFloat(lotSayisi);
        float lotFiyatiFloat = Float.parseFloat(lotFiyati);

        return lotFiyatiFloat * lotSayisiFloat;
    }

    //Textlerin içini doldurup tabloyu oluşturma fonksiyonu
    public void afterWorth(String alinanLotSayisiFinal, String lotBasinaFiyatFinal) {

        float sermayeAfterWorth = sermayeCalc(alinanLotSayisiFinal, lotBasinaFiyatFinal);
        float sermayeFirst = sermayeAfterWorth;

        for (int x = 1; x < 11; x++) {
            float sermayeBeforeWorth = sermayeAfterWorth;
            sermayeAfterWorth = (sermayeAfterWorth * 110) / 100; //Sermayeyi %10 artır
            float karAfterWorth = sermayeAfterWorth - sermayeFirst;
            String zort = "textView" + x; //Her döngüde farklı bir string oluştur
            int textViewID = getResources().getIdentifier(zort, "id", getPackageName());

            //String.format("%.2f", sermayeAfterWorth) direk kar ile toplanan değeri veriyor

            float degerLast1 = sermayeAfterWorth / Float.parseFloat(alinanLotSayisiFinal);
            String degerLast = String.format("%.2f",degerLast1);
            //TextView'dan sermayeyi ayarla
            TextView textView = findViewById(textViewID);
            String showResult = x + ". Gün        " + "Değer: " + degerLast + "        Kâr: " + String.format("%.2f", karAfterWorth);

            textView.setText(showResult);
        }
    }
}