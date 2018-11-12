package io.github.kmeret.frame.demo.storage.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.kmeret.frame.demo.storage.base.CrudDao
import io.github.kmeret.frame.demo.storage.entity.Repo

@Dao
abstract class RepoDao : CrudDao<Repo>() {

    @Query("select * from repo where id = :id")
    abstract override fun getById(id: Long): Repo?

}