package com.example.flowercareapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FlowerAdapter extends RecyclerView.Adapter<FlowerAdapter.FlowerViewHolder> {

    private final List<Flower> flowerList; // Çiçeklerin listesi
    private final FlowerDao flowerDao;     // DAO üzerinden veritabanı erişimi

    // Constructor
    public FlowerAdapter(List<Flower> flowerList, FlowerDao flowerDao) {
        this.flowerList = flowerList;
        this.flowerDao = flowerDao;
    }

    @NonNull
    @Override
    public FlowerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.flower_item, parent, false); // flower_item.xml layout dosyasını bağlar
        return new FlowerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlowerViewHolder holder, int position) {
        Flower flower = flowerList.get(position);

        // Çiçek bilgilerini göster
        holder.flowerName.setText("İsim: " + flower.getFlowerName());
        holder.flowerType.setText("Tür: " + flower.getSelectedFlowerType());
        holder.wateringInfo.setText("Sulama: " + flower.getWateringInfo());

        // Silme butonu için tıklama olayı
        holder.deleteButton.setOnClickListener(view -> {
            new Thread(() -> {
                flowerDao.deleteFlower(flower);  // Veritabanından çiçeği sil
                flowerList.remove(position);    // Listeden çiçeği kaldır
                notifyItemRemoved(position);   // RecyclerView'i güncelle
            }).start();
        });
    }

    @Override
    public int getItemCount() {
        return flowerList.size();
    }

    // FlowerViewHolder sınıfı
    static class FlowerViewHolder extends RecyclerView.ViewHolder {
        TextView flowerName, flowerType, wateringInfo;
        Button deleteButton;

        public FlowerViewHolder(@NonNull View itemView) {
            super(itemView);

            // View'leri bağla
            flowerName = itemView.findViewById(R.id.flower_name_text);
            flowerType = itemView.findViewById(R.id.flower_type_text);
            wateringInfo = itemView.findViewById(R.id.flower_watering_info);
            deleteButton = itemView.findViewById(R.id.delete_button);
        }
    }
}
