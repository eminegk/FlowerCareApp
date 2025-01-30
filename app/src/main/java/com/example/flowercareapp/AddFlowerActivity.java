package com.example.flowercareapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFlowerActivity extends AppCompatActivity {

    private EditText flowerNameEditText;
    private Spinner flowerTypeSpinner;
    private TextView wateringInfoTextView;
    private Button saveButton;
    private FlowerDao flowerDao;

    private String selectedFlowerType;
    private String wateringInfo;
    private Map<String, String> wateringInfoMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flower);

        // Veritabanına erişim
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "flower_database")
                .allowMainThreadQueries()
                .build();
        flowerDao = db.flowerDao();

        // Bileşenleri tanımla
        flowerNameEditText = findViewById(R.id.flower_name);
        flowerTypeSpinner = findViewById(R.id.flower_type_spinner);
        wateringInfoTextView = findViewById(R.id.watering_info);
        saveButton = findViewById(R.id.save_button);

        // Spinner için Çiçek Türleri ve Sulama Bilgileri
        List<String> flowerTypes = Arrays.asList("Orkide", "Gül", "Papatya", "Lale", "Kaktüs", "Menekşe", "Sukulent", "Karanfil", "Zambak", "Aloe Vera");
        wateringInfoMap = new HashMap<>();
        wateringInfoMap.put("Orkide", "Haftada bir kez sulayın.");
        wateringInfoMap.put("Gül", "Haftada iki kez sulayın.");
        wateringInfoMap.put("Papatya", "Her gün sulayın.");
        wateringInfoMap.put("Lale", "Haftada bir kez sulayın.");
        wateringInfoMap.put("Kaktüs", "Ayda bir kez sulayın.");
        wateringInfoMap.put("Menekşe", "Haftada bir kez sulayın.");
        wateringInfoMap.put("Sukulent", "İki haftada bir kez sulayın.");
        wateringInfoMap.put("Karanfil", "Haftada iki kez sulayın.");
        wateringInfoMap.put("Zambak", "Haftada bir kez sulayın.");
        wateringInfoMap.put("Aloe Vera", "İki haftada bir kez sulayın.");

        // Spinner için çiçek türleri alt alta gör Adapter Tanımlama
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, flowerTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        flowerTypeSpinner.setAdapter(adapter);

        // Spinner Seçim Olayı
        flowerTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFlowerType = flowerTypes.get(position);
                wateringInfo = wateringInfoMap.get(selectedFlowerType);
                wateringInfoTextView.setText(wateringInfo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedFlowerType = null;
                wateringInfo = null;
                wateringInfoTextView.setText("");
            }
        });

        // Kaydet Butonu
        saveButton.setOnClickListener(v -> {
            String flowerName = flowerNameEditText.getText().toString().trim();
            long lastWatered = System.currentTimeMillis();

            if (flowerName.isEmpty() || selectedFlowerType == null || wateringInfo == null) {
                Toast.makeText(AddFlowerActivity.this, "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show();
                return;
            }

            // Yeni Çiçek Veritabanına Ekle
            Flower flower = new Flower(flowerName, wateringInfo, lastWatered, selectedFlowerType);
            flowerDao.insertFlower(flower);

            Toast.makeText(AddFlowerActivity.this, "Çiçek başarıyla eklendi", Toast.LENGTH_SHORT).show();

            // Aktiviteyi Kapat
            finish();
        });
    }
}
