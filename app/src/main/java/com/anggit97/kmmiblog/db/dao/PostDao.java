package com.anggit97.kmmiblog.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.anggit97.kmmiblog.db.entity.PostEntity;

import java.util.List;

/**
 * Created by Anggit Prayogo on 03,September,2021
 * GitHub : https://github.com/anggit97
 */
@Dao
public interface PostDao {

    @Query("SELECT * FROM posts")
    List<PostEntity> getFavoritePost();

    @Insert
    void addToFavorite(PostEntity postEntity);

    @Delete
    void removeFromFavorite(PostEntity postEntity);

    @Query("SELECT COUNT(id) FROM posts WHERE id=:postId")
    int checkIfPostIsFavorite(int postId);
}
