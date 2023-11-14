package by.bsuir.makogon.alina.navigation.bottom_navigation.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.bsuir.makogon.alina.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    titleContentColor = androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer,
                    containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer
                ),
                title = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                    ) {
                        androidx.compose.material3.Text(
                            text = stringResource(R.string.about_app),
                            modifier = Modifier.align(Alignment.TopCenter),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    }
                }
            )
        },
        bottomBar = {

        }
    ) {
        AboutAppContent(
            modifier = Modifier.padding(it).verticalScroll(state = rememberScrollState()),
            appDescription = listOf(
                R.string.about_app1,
                R.string.about_app2,
                R.string.about_app3
            )
        )
    }
}

@Composable
fun AboutAppContent(modifier: Modifier = Modifier, appDescription: List<Int>) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Poster()
            AppDescription(
                appDescription = appDescription
            )
        }
    }
}


@Composable
fun Poster(modifier: Modifier = Modifier) {
    Box(modifier, contentAlignment = Alignment.TopCenter) {
        Image(
            painter = painterResource(id = R.drawable.img),
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .padding(20.dp)
                .height(300.dp)
                .clip(shape = RoundedCornerShape(120.dp)),
            alignment = Alignment.TopCenter
        )
    }
}

@Composable
fun AppDescription(modifier: Modifier = Modifier, appDescription: List<Int>) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = modifier
            .background(color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer)
    ) {
        androidx.compose.material3.Text(
            text = stringResource(R.string.app_name),
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(top = 20.dp)
        )
        androidx.compose.material3.Text(
            text = stringResource(R.string.app_slogan),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
        )
        for (content in appDescription) {
            DescriptionCard(text = content)
        }
        RoundElementsRow(modifier = modifier.padding(top = 10.dp))
    }
}


@Composable
fun DescriptionCard(
    @DrawableRes
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = androidx.compose.material3.MaterialTheme.shapes.medium,
        modifier = modifier.padding(horizontal = 35.dp, vertical = 15.dp),
        color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(horizontal = 25.dp)) {
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(70.dp).padding(horizontal = 10.dp)
            )
            androidx.compose.material3.Text(
                text = stringResource(id = text),
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 15.dp)
            )
        }
    }
}

@Composable
fun RoundElementsRow(
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),

        ) {
        items(appData) { item ->
            RoundElements(drawable = item.drawable, text = item.text)
        }
    }
}


@Composable
fun RoundElements(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(280.dp)
                .clip(CircleShape)
        )
        androidx.compose.material3.Text(
            text = stringResource(text),
            modifier = Modifier.paddingFromBaseline(top = 40.dp, bottom = 90.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
        )
    }
}

private val appData = listOf(
    R.drawable.round_item_img_1 to R.string.round_item_text1,
    R.drawable.round_item_img_2 to R.string.round_item_text2,
    R.drawable.round_item_img_3 to R.string.round_item_text3,
    R.drawable.round_item_img_4 to R.string.round_item_text4,
    R.drawable.round_item_img_5 to R.string.round_item_text5,
    R.drawable.round_item_img_6 to R.string.round_item_text6,
    R.drawable.round_item_img_7 to R.string.round_item_text7,
).map { DrawableStringPair(it.first, it.second) }


private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)

@Composable
@Preview
fun ProfileScreenPreview() {
    AboutAppScreen()
}