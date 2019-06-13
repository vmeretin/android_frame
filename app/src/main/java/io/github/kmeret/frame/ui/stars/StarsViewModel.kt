package io.github.kmeret.frame.ui.stars

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import io.github.kmeret.frame.domain.cases.NetworkLiveUseCase
import io.github.kmeret.frame.domain.entity.Repo
import io.github.kmeret.frame.github.GithubConfig
import io.github.kmeret.frame.github.GithubService
import io.github.kmeret.frame.github.model.GithubRepo

class StarsViewModel(private val githubService: GithubService) : ViewModel() {

    private val useCase = NetworkLiveUseCase<List<GithubRepo>>()

    val repoList: LiveData<List<Repo>> = Transformations.map(useCase.data) { githubRepoList -> githubRepoList.map { it.map() } }
    val loading: LiveData<Boolean> = useCase.loading
    val empty: LiveData<Boolean> = useCase.empty
    val error: LiveData<Exception> = useCase.error

    init {
        requestStarredList()
    }

    fun requestStarredList() {
        useCase.execute(githubService.getStarredRepoList(GithubConfig.userName))
    }

}