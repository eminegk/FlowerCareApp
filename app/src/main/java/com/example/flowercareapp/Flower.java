package com.example.flowercareapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "flower_table")
public class Flower {

    @PrimaryKey(autoGenerate = true)
    private int id; // Çiçek ID'si (otomatik oluşturulur)

    private String flowerName;        // Çiçek adı

    private String wateringInfo; // Sulama bilgisi
    private long lastWatered;   // Çiçeğin en son sulandığı zaman
    private String selectedFlowerType;
    // Constructor
    public Flower(String flowerName, String wateringInfo, long lastWatered, String selectedFlowerType) {
        this.flowerName = flowerName;

        this.wateringInfo = wateringInfo;
        this.lastWatered = lastWatered;
        this.selectedFlowerType= selectedFlowerType;
    }

    // Getter ve Setter Metotları
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String Flowername) {
        this.flowerName = flowerName;
    }


    public String getSelectedFlowerType() {
        return selectedFlowerType;
    }

    public void setSelectedFlowerType(String selectedFlowerType) {
        this.selectedFlowerType = selectedFlowerType;
    }

    public String getWateringInfo() {
        return wateringInfo;
    }

    public void setWateringInfo(String wateringInfo) {
        this.wateringInfo = wateringInfo;
    }

    public long getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(long lastWatered) {
        this.lastWatered = lastWatered;
    }
}
