package com.starwars.app.feature_planet_list.shared.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.starwars.app.core_database.shared.di.coreDatabaseModule
import com.starwars.app.core_network.shared.di.networkModule
import com.starwars.app.coredatabase.shared.PlanetEntity
import com.starwars.app.feature_planet_list.shared.di.featurePlanetListModule
import com.starwars.app.feature_planet_list.shared.di.testHttpEngineModule
import com.starwars.app.feature_planet_list.shared.di.testSqliteDriverModule
import kotlinx.coroutines.test.runTest
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue


@OptIn(ExperimentalPagingApi::class)
class PlanetListRemoteMediatorTest : KoinTest {

    private val planetListRemoteMediator: PlanetListRemoteMediator by inject<PlanetListRemoteMediator>()

    @BeforeTest
    fun setup() {
        startKoin {
            modules(
                coreDatabaseModule,
                testSqliteDriverModule,
                networkModule,
                featurePlanetListModule
            )
        }
    }

    @AfterTest
    fun cleanup() {
        stopKoin()
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val pagingState = PagingState<Int, PlanetEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = planetListRemoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertFalse { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
    }

    @Test
    fun appendLoadSuccessAndEndOfPaginationWhenNoMoreData() = runTest {
        val module = testHttpEngineModule(isSuccess = true)
        loadKoinModules(module)
        val pagingState = PagingState<Int, PlanetEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = planetListRemoteMediator.load(LoadType.APPEND, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Success }
        assertTrue { (result as RemoteMediator.MediatorResult.Success).endOfPaginationReached }
        unloadKoinModules(module)
    }

    @Test
    fun refreshLoadReturnsErrorResultWhenErrorOccurs() = runTest {
        val module = testHttpEngineModule(isSuccess = false)
        loadKoinModules(module)
        val pagingState = PagingState<Int, PlanetEntity>(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
        val result = planetListRemoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue { result is RemoteMediator.MediatorResult.Error }
        unloadKoinModules(module)
    }
}