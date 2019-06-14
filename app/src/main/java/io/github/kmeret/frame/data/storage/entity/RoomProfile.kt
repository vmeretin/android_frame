package io.github.kmeret.frame.data.storage.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.kmeret.frame.data.storage.base.Identifiable
import io.github.kmeret.frame.domain.model.Profile

@Entity
data class RoomProfile(
        @PrimaryKey
        @ColumnInfo(index = true)
        override val id: Long,
        var login: String,
        var avatarUrl: String,
        var name: String,
        var bio: String,
        var company: String,
        var location: String
) : Identifiable {
    fun map() = Profile(id, login, avatarUrl, name, bio, company, location)
}