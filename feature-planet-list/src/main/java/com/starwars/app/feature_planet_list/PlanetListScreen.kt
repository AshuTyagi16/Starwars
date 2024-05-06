package com.starwars.app.feature_planet_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import cafe.adriel.voyager.core.registry.ScreenRegistry
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.starwars.app.core_navigation.SharedScreen
import com.starwars.app.feature_planet_list.shared.ui.PlanetListViewModel
import org.koin.androidx.compose.koinViewModel

data object PlanetListScreen : Screen {

    private fun readResolve(): Any = PlanetListScreen

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel: PlanetListViewModel = koinViewModel()
        val snackbarHostState = remember { SnackbarHostState() }
        val pagingResult = viewModel.pager.collectAsLazyPagingItems()

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.Black.copy(alpha = 0.92f))
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(
                        count = pagingResult.itemCount,
                        key = pagingResult.itemKey { it.uid },
                    ) {
                        val planet = pagingResult[it]
                        if (planet != null) {
                            val text = remember { "${planet.uid} - ${planet.name}" }
                            Text(
                                text = text,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 38.sp,
                                fontFamily = FontFamily.Cursive,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 16.dp,
                                        vertical = 16.dp
                                    )
                                    .clickable {
                                        navigator.push(
                                            ScreenRegistry.get(
                                                SharedScreen.PlanetDetail(
                                                    planet.uid
                                                )
                                            )
                                        )
                                    }
                            )

                            HorizontalDivider()
                        }
                    }

                    when (val state = pagingResult.loadState.refresh) {
                        is LoadState.Error -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillParentMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = state.error.message.orEmpty(),
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        modifier = Modifier
                                            .fillParentMaxWidth()
                                            .padding(vertical = 12.dp)
                                    )
                                    Button(
                                        modifier = Modifier,
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                        onClick = {
                                            pagingResult.retry()
                                        }
                                    ) {
                                        Text(
                                            text = "Retry",
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }

                        is LoadState.Loading -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillParentMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }

                        else -> {}
                    }

                    when (val state = pagingResult.loadState.append) {
                        is LoadState.Error -> {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = state.error.message.orEmpty(),
                                        fontSize = 12.sp,
                                        textAlign = TextAlign.Center,
                                        color = Color.White,
                                        modifier = Modifier
                                            .fillParentMaxWidth()
                                            .padding(vertical = 12.dp)
                                    )
                                    Button(
                                        modifier = Modifier,
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                        onClick = {
                                            pagingResult.retry()
                                        }
                                    ) {
                                        Text(
                                            text = "Retry",
                                            color = Color.Black
                                        )
                                    }
                                }
                            }
                        }

                        is LoadState.Loading -> { // Pagination Loading UI
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {

                                    Text(
                                        text = "Loading",
                                        color = Color.White
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    CircularProgressIndicator(color = Color.White)
                                }
                            }
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}