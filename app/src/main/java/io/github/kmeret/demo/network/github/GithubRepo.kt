package io.github.kmeret.demo.network.github

import com.google.gson.annotations.SerializedName

data class GithubRepo(
        val id: Long,
        val name: String,
        val description: String,
        @SerializedName("stargazers_count")
        val starsCount: Int,
        val owner: GithubUser
)