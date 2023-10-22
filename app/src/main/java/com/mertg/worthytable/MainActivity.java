package com.mertg.worthytable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mertg.worthytable.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    ArrayList<Company> companyArrayList;

    CompanyAdapter companyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.darkThemeColor)));

        companyArrayList = new ArrayList<>();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        companyAdapter = new CompanyAdapter(companyArrayList);
        binding.recyclerView.setAdapter(companyAdapter);
        getData();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Çıkmak için uygulamayı komple kapatmalısın, böyle beceremedim", Toast.LENGTH_SHORT).show();
    }

    private void getData() {
        try {
            SQLiteDatabase sqLiteDatabase = this.openOrCreateDatabase("Companies",MODE_PRIVATE,null);

            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM Companies",null);
            int hisseadiIndex = cursor.getColumnIndex("hisseadi");
            int idIndex = cursor.getColumnIndex("id");

            while(cursor.moveToNext()) {
                String hisseadiData = cursor.getString(hisseadiIndex);
                int idData = cursor.getInt(idIndex);
                Company company = new Company(hisseadiData,idData);

                companyArrayList.add(company);
            }

            companyAdapter.notifyDataSetChanged();

            cursor.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_company,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add_company){
            Intent intent = new Intent(this,DetailsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}