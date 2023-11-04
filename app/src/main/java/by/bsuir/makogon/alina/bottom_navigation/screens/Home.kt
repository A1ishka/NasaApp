package by.bsuir.makogon.alina.bottom_navigation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import by.bsuir.makogon.alina.HomeState
import by.bsuir.makogon.alina.HomeState.DisplayingEvents
import by.bsuir.makogon.alina.HomeViewModel
import by.bsuir.makogon.alina.R
import by.bsuir.makogon.alina.bottom_navigation.Route
import by.bsuir.makogon.alina.domain.model.NasaEvent
import by.bsuir.makogon.alina.ui.theme.NasaTheme
import java.util.UUID

@Composable
fun HomeScreen(controller: NavController) {
    val viewModel = viewModel<HomeViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeScreenContent(
        state = state,
        onEdit = { controller.navigate(route = Route.EditEventScreen.route + "/{${it}}") },
        onRemove = viewModel::onClickRemoveEvent // ссылка на функцию
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    state: HomeState,
    onRemove: (UUID) -> Unit,
    onEdit: (eventId: UUID?) -> Unit,
) {
    Scaffold(
        modifier = Modifier,
        topBar = {
            CenterAlignedTopAppBar(title = { Text(stringResource(R.string.home_title)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { onEdit(null) }) {
                Icon(
                    modifier = Modifier.padding(bottom = 200.dp),
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center
        ) {
            when (state) {
                is DisplayingEvents -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                ) {
                    items(state.events, key = { it.eventId }) { item ->
                        EventItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            event = item,
                            onRemove = { onRemove(item.eventId) },
                            onClick = { onEdit(item.eventId) },
                        )
                    }
                }

                is HomeState.Error -> Text(
                    state.e.message ?: stringResource(id = R.string.error_message)
                )

                is HomeState.Loading -> CircularProgressIndicator()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EventItem(
    event: NasaEvent,
    onRemove: () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.requiredWidthIn(max = 400.dp),
        onClick = onClick,
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(9f)) {
                Text(
                    text = event.name,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp)
                )
                Text(
                    text = event.description,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(12.dp)
                )
                /*Text(
                    text = event.date,
                    style = MaterialTheme.typography.titleMedium,
                )*/
            }
            Icon(
                Icons.Default.Delete,
                contentDescription = null,
                modifier = Modifier.clickable(onClick = onRemove)
            )
        }
    }
}


@Preview
@Composable
private fun HomePreview() = NasaTheme {
    Scaffold {
        Box(modifier = Modifier.padding(it)) {
            HomeScreenContent(state = HomeState.Loading, onRemove = {}, onEdit = {})
        }
    }
}