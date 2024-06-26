package com.starwars.app.feature_planet_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.starwars.app.feature_planet_detail.shared.ui.PlanetDetailScreenContract.State
import com.starwars.app.feature_planet_detail.shared.ui.PlanetDetailScreenContract.Event
import com.starwars.app.feature_planet_detail.shared.ui.PlanetDetailViewModel
import org.koin.androidx.compose.koinViewModel

data class PlanetDetailScreen(
    private val uid: String
) : Screen {
    @Composable
    override fun Content() {
        val viewModel: PlanetDetailViewModel = koinViewModel()

        LaunchedEffect(Unit) {
            viewModel.setEvent(Event.FetchPlanetDetail(uid))
        }

        val state by viewModel.uiState.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.92f)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                when (val value = state) {
                    State.Loading -> {
                        CircularProgressIndicator()
                    }

                    is State.Success -> {
                        val properties = value.planetDetail?.properties

                        Text(
                            text = stringResource(
                                id = R.string.planet_name,
                                properties?.name.orEmpty()
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Cursive,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                        )

                        Text(
                            text = stringResource(
                                id = R.string.climate,
                                properties?.climate.orEmpty()
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Cursive,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                        )

                        Text(
                            text = stringResource(
                                id = R.string.diameter,
                                properties?.diameter.orEmpty()
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Cursive,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                        )

                        Text(
                            text = stringResource(
                                id = R.string.gravity,
                                properties?.gravity.orEmpty()
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Cursive,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                        )

                        Text(
                            text = stringResource(
                                id = R.string.orbital_period,
                                properties?.orbitalPeriod.orEmpty()
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Cursive,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                        )

                        Text(
                            text = stringResource(
                                id = R.string.population,
                                properties?.population.orEmpty()
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Cursive,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                        )

                        Text(
                            text = stringResource(
                                id = R.string.rotation_period,
                                properties?.rotationPeriod.orEmpty()
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Cursive,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                        )

                        Text(
                            text = stringResource(
                                id = R.string.surface_water,
                                properties?.surfaceWater.orEmpty()
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Cursive,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                        )

                        Text(
                            text = stringResource(
                                id = R.string.terrain,
                                properties?.terrain.orEmpty()
                            ),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            fontFamily = FontFamily.Cursive,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    horizontal = 16.dp,
                                    vertical = 16.dp
                                )
                        )
                    }
                }
            }
        }
    }
}