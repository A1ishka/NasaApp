package by.bsuir.makogon.alina.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import by.bsuir.makogon.alina.navigation.bottom_navigation.screens.MainScreen
import by.bsuir.makogon.alina.ui.theme.NasaTheme
import org.koin.core.component.KoinComponent

/*@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerComponent() {
    val state = rememberPagerState { 10 }
    val animationScope = rememberCoroutineScope()
    Column {
        HorizontalPager(
            modifier = Modifier.weight(0.7f),
            state = state
        ) { page ->
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .background(Color.Blue)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(text = page.toString(), fontSize = 32.sp)
            }
        }

        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxWidth(), contentAlignment = Alignment.Center
        ) {

        }
    }

}*/

class MainActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var darkTheme by remember { mutableStateOf(false)}
            NasaTheme(darkTheme = darkTheme) {
                MainScreen(darkTheme = darkTheme,
                    onThemeUpdated = { darkTheme = !darkTheme }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    var darkTheme by remember { mutableStateOf(false)}
    NasaTheme(darkTheme = darkTheme) {
        MainScreen(
            darkTheme = darkTheme,
            onThemeUpdated = { darkTheme = !darkTheme }
        )
    }
}