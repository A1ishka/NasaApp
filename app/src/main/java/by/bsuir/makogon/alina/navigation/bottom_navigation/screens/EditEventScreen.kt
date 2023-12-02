package by.bsuir.makogon.alina.navigation.bottom_navigation.screens

import android.icu.util.Calendar
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import by.bsuir.makogon.alina.R
import by.bsuir.makogon.alina.domain.model.NasaEvent
import by.bsuir.makogon.alina.events.edit.EditEventState
import by.bsuir.makogon.alina.events.edit.EditEventsViewModel
import kotlinx.coroutines.Job
import org.koin.androidx.compose.getViewModel
import java.util.UUID
import kotlin.reflect.KFunction5

@Composable
fun EditEventScreen(
    eventId: UUID?,// Так как вьюмодель переживает экран то передавать данные нужно из экрана во вьюмодель при создании
    navController: NavController,
) {
    val viewModel: EditEventsViewModel = getViewModel()//viewModel<EditEventsViewModel> { EditEventsViewModel(eventId) }
    viewModel.setEventId(eventId)
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if ((state as? EditEventState.EditingEvent)?.saved == true) navController.navigateUp()
    }

    EditEventContent(
        state = state,
        onSave = viewModel::onClickSave,
        onDelete = viewModel::onClickDelete,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EditEventContent(
    state: EditEventState,
    onSave: KFunction5<String, String, String, String, String, Job>,
    onDelete: () -> Unit,
) {
    Surface(
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            when (state) {
                is EditEventState.Error -> androidx.compose.material3.Text(
                    state.e.message ?: stringResource(id = R.string.error_message)
                )

                is EditEventState.Loading -> CircularProgressIndicator()
                is EditEventState.EditingEvent -> Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.matchParentSize()
                ) {

                    var name by remember(state.name) { mutableStateOf(state?.name ?: "") }
                    var date by remember(state.date) {
                        mutableStateOf(
                            state.date/*?.toString() */?: ""
                        )
                    }
                    var type by remember(state.type) { mutableStateOf(state.type ?: "") }
                    var description by remember(state.description) {
                        mutableStateOf(
                            state.description ?: ""
                        )
                    }
                    var notes by remember(state.notes) { mutableStateOf(state.notes ?: "") }
                    androidx.compose.material3.Text(
                        text = stringResource(
                            id = R.string.edit_event_id_message,
                            state.eventId.toString(),
                        ),
                        modifier = Modifier.padding(12.dp)
                    )
                    //var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
                    val calendar = Calendar.getInstance()
                    calendar.set(1990, 0, 22) // add year, month (Jan), date
                    val datePickerState =
                        rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)

                    var showDatePicker by remember {
                        mutableStateOf(false)
                    }
                    var selectedDate by remember {
                        mutableLongStateOf(calendar.timeInMillis) // or use mutableStateOf(calendar.timeInMillis)
                    }

                    if (showDatePicker) {
                        DatePickerDialog(
                            onDismissRequest = {
                                showDatePicker = false
                            },
                            confirmButton = {
                                TextButton(onClick = {
                                    showDatePicker = false
                                    selectedDate = datePickerState.selectedDateMillis!!
                                }) {
                                    Text(text = "Confirm")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = {
                                    showDatePicker = false
                                }) {
                                    Text(text = "Cancel")
                                }
                            }
                        ) {
                            DatePicker(
                                state = datePickerState
                            )
                        }
                    }
                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .background(color = androidx.compose.material3.MaterialTheme.colorScheme.onTertiary)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        EventTextField(
                            value = name,
                            onValueChange = { newValue ->
                                name = newValue
                            },
                            label = stringResource(R.string.event_name)
                        )
                        Button(
                            onClick = {
                                showDatePicker = true
                            }
                        ) {
                            Text(text = "Pick the Date")
                        }
                        /*DatePicker(
                            state = state,
                            modifier = Modifier.padding(16.dp)
                        )*/
                        EventTextField(
                            value = date,
                            onValueChange = { newValue ->
                                date = newValue
                            },
                            label = stringResource(R.string.date),
                            onlyNumbers = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        EventTextField(
                            value = type,
                            onValueChange = { newValue ->
                                type = newValue
                            },
                            label = stringResource(R.string.type)
                        )
                        EventTextField(
                            value = description,
                            onValueChange = { newValue ->
                                description = newValue
                            },
                            label = stringResource(R.string.description)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        EventTextField(
                            value = notes,
                            onValueChange = { newValue ->
                                notes = newValue
                            },
                            label = stringResource(R.string.notes)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        /*OutlinedTextField(
                            value = name,
                            onValueChange = { name = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            singleLine = true,
                        )*/
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AnimatedVisibility(visible = state.eventId != null) {
                                FloatingActionButton(
                                    onClick = onDelete,
                                    modifier = Modifier.padding(24.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                    )
                                }
                            }
                            FloatingActionButton(
                                onClick = { onSave(name, date/*LocalDate.parse(date)*/, type, description, notes) },
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
/*
@Composable
@Preview(name = "EditCatScreen", showSystemUi = false, showBackground = true)
private fun EditCatScreenPreview() = KittenTrackerTheme {
    EditCatContent(EditCatState.EditingCat("Cat", null), {}, { })
}
*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    onConfirmClick: (NasaEvent) -> Unit,
    onCancelClick: () -> Unit,
    NasaEvent: NasaEvent?
) {
    var name by remember { mutableStateOf(NasaEvent?.name ?: "") }
    var date by remember { mutableStateOf(NasaEvent?.date?.toString() ?: "") }
    var type by remember { mutableStateOf(NasaEvent?.type ?: "") }
    var description by remember { mutableStateOf(NasaEvent?.description ?: "") }
    var notes by remember { mutableStateOf(NasaEvent?.notes ?: "") }

    //var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    val calendar = Calendar.getInstance()
    calendar.set(1990, 0, 22) // add year, month (Jan), date
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = calendar.timeInMillis)

    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var selectedDate by remember {
        mutableLongStateOf(calendar.timeInMillis) // or use mutableStateOf(calendar.timeInMillis)
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    selectedDate = datePickerState.selectedDateMillis!!
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
    Column(
        modifier = modifier
            .padding(5.dp)
            .background(color = androidx.compose.material3.MaterialTheme.colorScheme.onTertiary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = { onCancelClick() },
                modifier = Modifier.padding(end = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onTertiaryContainer
                )
            ) {
                androidx.compose.material3.Text(stringResource(R.string.cancel))
            }
            Button(
                onClick = {
                    if (name.trim().isNotEmpty()) {
                        onConfirmClick(
                            NasaEvent(
                                name = name,
                                date = date,//LocalDate.parse(selectedDate.toString()/*, formatter*/),
                                type = type,
                                description = description,
                                notes = notes
                            )
                        )
                        onCancelClick()
                    }
                },
                enabled = name.trim().isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onTertiaryContainer,
                    disabledContainerColor = androidx.compose.material3.MaterialTheme.colorScheme.tertiaryContainer
                )
            ) {
                androidx.compose.material3.Text(stringResource(R.string.confirm))
            }
        }
        EventTextField(
            value = name,
            onValueChange = { newValue ->
                name = newValue
            },
            label = stringResource(R.string.event_name)
        )
        Button(
            onClick = {
                showDatePicker = true
            }
        ) {
            Text(text = "Pick the Date")
        }
        Spacer(modifier = Modifier.height(16.dp))
        /*DatePicker(
        state = state,
        modifier = Modifier.padding(16.dp)
    )*/
        EventTextField(
            value = date,
            onValueChange = { newValue ->
                date = newValue
            },
            label = stringResource(R.string.date),
            onlyNumbers = true
        )

        Spacer(modifier = Modifier.height(16.dp))
        EventTextField(
            value = type,
            onValueChange = { newValue ->
                type = newValue
            },
            label = stringResource(R.string.type)
        )
        Spacer(modifier = Modifier.height(16.dp))
        EventTextField(
            value = description,
            onValueChange = { newValue ->
                description = newValue
            },
            label = stringResource(R.string.description)
        )
        Spacer(modifier = Modifier.height(16.dp))
        EventTextField(
            value = notes,
            onValueChange = { newValue ->
                notes = newValue
            },
            label = stringResource(R.string.notes)
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    onlyNumbers: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = { androidx.compose.material3.Text(label) },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
            focusedLabelColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
            focusedContainerColor = androidx.compose.material3.MaterialTheme.colorScheme.onTertiaryContainer,
            focusedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.background,
            unfocusedTextColor = androidx.compose.material3.MaterialTheme.colorScheme.background
        ),
        keyboardOptions = KeyboardOptions(
            keyboardType =
            if (onlyNumbers) KeyboardType.Number else KeyboardType.Text
        )

    )
    Spacer(modifier = Modifier.height(16.dp))
}

/*@Composable
fun ListFab(
    onFabClicked: () -> Unit
) {
    FloatingActionButton(onClick = onFabClicked) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add event-notes button"
        )

    }
}*/
/*
@Composable
@Preview
fun EditEventScreenPreview() {
    EditEventScreen()
}*/
