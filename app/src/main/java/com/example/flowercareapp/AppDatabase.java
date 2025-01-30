package com.example.flowercareapp;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// flower sınıfını tablo olarak kullandım
@Database(entities = {Flower.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    // Veritabanı örneği
    private static AppDatabase instance;

    // DAO erişimi için abstract metot
    public abstract FlowerDao flowerDao();


    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "flower_database")
                    .fallbackToDestructiveMigration() // Eski sürüm uyumsuzluklarında veriyi temizler
                    .build();
        }
        return instance;
    }
}
