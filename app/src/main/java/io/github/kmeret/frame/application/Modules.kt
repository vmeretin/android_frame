package io.github.kmeret.frame.application

import androidx.room.Room
import io.github.kmeret.frame.BuildConfig
import io.github.kmeret.frame.data.network.login.GithubAuthApi
import io.github.kmeret.frame.data.network.open.GithubOpenApi
import io.github.kmeret.frame.data.network.open.GithubOpenApiFactory
import io.github.kmeret.frame.data.network.user.GithubUserApiFactory
import io.github.kmeret.frame.data.repos.AuthDataRepo
import io.github.kmeret.frame.data.repos.UserDataRepo
import io.github.kmeret.frame.data.storage.Database
import io.github.kmeret.frame.domain.cases.UserInteractor
import io.github.kmeret.frame.domain.repos.AuthRepo
import io.github.kmeret.frame.domain.repos.UserRepo
import io.github.kmeret.frame.infrastructure.application.permission.PermissionProvider
import io.github.kmeret.frame.presentation.followers.FollowersViewModel
import io.github.kmeret.frame.presentation.following.FollowingViewModel
import io.github.kmeret.frame.presentation.main.MainViewModel
import io.github.kmeret.frame.presentation.profile.ProfileViewModel
import io.github.kmeret.frame.presentation.repos.ReposViewModel
import io.github.kmeret.frame.presentation.stars.StarsViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

object Modules {
    fun getList() = listOf(
        appModule,
        networkModule,
        dataModule,
        storageModule,
        navigationModule,
        domainModule,
        viewModelModule
    )

    private val appModule = module {
        single { PermissionProvider(androidApplication()) }
    }

    private val networkModule = module {
        single { GithubOpenApiFactory() }
        single { get<GithubOpenApiFactory>().create(GithubOpenApi::class.java) }
        single { get<GithubOpenApiFactory>().create(GithubAuthApi::class.java) }

        single { GithubUserApiFactory(get()) }
        single { get<GithubUserApiFactory>().create(GithubAuthApi::class.java) }
    }

    private val dataModule = module {
        single<AuthRepo> { AuthDataRepo(get()) }
        single<UserRepo> { UserDataRepo(get()) }
    }

    private val storageModule = module {
        single {
            Room.databaseBuilder(
                androidApplication(),
                Database::class.java,
                BuildConfig.APPLICATION_ID
            ).allowMainThreadQueries().build()
        }
        single { get<Database>().repoDao() }
        single { get<Database>().profileDao() }
        single { get<Database>().topicDao() }
    }

    private val navigationModule = module {
        single { Cicerone.create() }
        single { get<Cicerone<Router>>().router }
        single { get<Cicerone<Router>>().navigatorHolder }
    }

    private val domainModule = module {
        single { UserInteractor(get()) }
    }

    private val viewModelModule = module {
        viewModel { MainViewModel(get()) }
        viewModel { ProfileViewModel(get()) }
        viewModel { ReposViewModel(get()) }
        viewModel { StarsViewModel(get()) }
        viewModel { FollowersViewModel(get()) }
        viewModel { FollowingViewModel(get()) }
    }
}
