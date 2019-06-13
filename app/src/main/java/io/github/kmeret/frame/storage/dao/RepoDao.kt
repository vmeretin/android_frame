package io.github.kmeret.frame.storage.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.kmeret.frame.storage.base.CrudDao
import io.github.kmeret.frame.storage.entity.RoomRepo

@Dao
abstract class RepoDao : CrudDao<RoomRepo>() {

    @Query("select * from RoomRepo where id = :id")
    abstract override fun getById(id: Long): RoomRepo?

}