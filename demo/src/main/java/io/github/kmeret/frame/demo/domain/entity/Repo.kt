package io.github.kmeret.frame.demo.domain.entity

import io.github.kmeret.frame.android.ui.RecyclerAdapter

data class Repo(
        override val id: Long,
        val fullName: String,
        val description: String,
        val language: String,
        val starsCount: Int,
        val forksCount: Int,
        val updatedAt: String
) : RecyclerAdapter.Identifiable