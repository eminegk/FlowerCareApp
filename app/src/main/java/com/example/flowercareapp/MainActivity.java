package com.example.flowercareapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FlowerAdapter adapter;
    private FlowerDao flowerDao;
    private List<Flower> flowerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // RecyclerView'i bağlama
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Veritabanına erişim
        AppDatabase db = AppDatabase.getInstance(this);
        flowerDao = db.flowerDao();

        // Çiçek listesini yükleme
        loadFlowers();

        // Yeni Çiçek Ekle Butonu
        Button addFlowerButton = findViewById(R.id.add_flower_button);
        addFlowerButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddFlowerActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Ekran yeniden açıldığında çiçek listesini güncelle
        loadFlowers();
    }

    // Çiçekleri Veritabanından Yükleme
    private void loadFlowers() {
        new Thread(() -> {
            flowerList = flowerDao.getAllFlowers(); // Tüm çiçekleri getir
            runOnUiThread(() -> {
                adapter = new FlowerAdapter(flowerList, flowerDao);
                recyclerView.setAdapter(adapter);
            });
        }).start();
    }
}
