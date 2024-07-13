package cedricmeyer.kmp.di

import cedricmeyer.kmp.sqldelight.SharedDatabase
import cedricmeyer.kmp.api.RemoteDataImp
import cedricmeyer.kmp.api.model.mapper.ApiTaskMapper
import cedricmeyer.kmp.domain.IRepository
import cedricmeyer.kmp.domain.interactors.GetTasksUseCase
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import cedricmeyer.kmp.domain.interactors.AddTaskUseCase
import cedricmeyer.kmp.domain.interactors.GetTaskUseCase
import cedricmeyer.kmp.domain.interactors.GetTasksFavoritesUseCase
import cedricmeyer.kmp.domain.interactors.IsTaskFavoriteUseCase
import cedricmeyer.kmp.domain.interactors.SwitchTaskFavoriteUseCase
import cedricmeyer.kmp.presentation.view.features.task_add.AddTaskViewModel
import cedricmeyer.kmp.presentation.view.features.task_details.TaskDetailViewModel
import cedricmeyer.kmp.presentation.view.features.tasks.TasksViewModel
import cedricmeyer.kmp.presentation.view.features.tasks_favorites.TasksFavoritesViewModel
import cedricmeyer.kmp.repository.ICacheData
import cedricmeyer.kmp.repository.IRemoteData
import cedricmeyer.kmp.repository.RepositoryImp
import cedricmeyer.kmp.sqldelight.CacheDataImp

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        appDeclaration()
        modules(
            viewModelModule,
            useCasesModule,
            repositoryModule,
            ktorModule,
            sqlDelightModule,
            mapperModule,
            dispatcherModule,
            platformModule()
        )
    }
val viewModelModule = module {
    factory { TasksViewModel(get()) }
    factory { TasksFavoritesViewModel(get()) }
    factory { params -> TaskDetailViewModel(get(),get(),get(),params.get()) }
    factory { AddTaskViewModel(get()) }
}
val useCasesModule: Module = module {
    factory { GetTasksUseCase(get(), get()) }
    factory { GetTaskUseCase(get(), get()) }
    factory { GetTasksFavoritesUseCase(get(), get()) }
    factory { IsTaskFavoriteUseCase(get(), get()) }
    factory { SwitchTaskFavoriteUseCase(get(), get()) }
    factory { AddTaskUseCase(get(), get()) }
}

val repositoryModule = module {
    single<IRepository> { RepositoryImp(get(), get()) }
    single<ICacheData> { CacheDataImp(get()) }
    single<IRemoteData> { RemoteDataImp(get(), get(), get()) }
}

val mapperModule = module {
    factory { ApiTaskMapper() }
}

val ktorModule = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    single { "http://192.168.1.80:3000" }
}
val sqlDelightModule = module {
    single { SharedDatabase(get()) }
}

val dispatcherModule = module {
    factory { Dispatchers.Default }
}

fun initKoin() = initKoin {}