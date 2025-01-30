package com.example.flowercareapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FlowerDao {

    @Insert
    void insertFlower(Flower flower);

    @Query("SELECT * FROM flower_table")
    List<Flower> getAllFlowers();

    @Delete
    void deleteFlower(Flower flower);
}
