package by.bsuir.makogon.alina.navigation_drawer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import by.bsuir.makogon.alina.R

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer() {
    val items = listOf(
        DrawerItem(
            Icons.Default.Favorite,
            "title"
        ),
        DrawerItem(
            Icons.Default.Add,
            "title2"
        ),
        DrawerItem(
            Icons.Default.AccountBox,
            "title3"
        ),
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val selectedItem = remember{
        mutableStateOf(items[0])
    }
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Image(
                    painter = painterResource(id = R.drawable.round_item_img_4),
                    contentDescription = "Header",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    contentScale = ContentScale.FillWidth
                )
                Spacer(modifier = Modifier.height(15.dp))
                items.forEach{ item ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(5.dp),
                        label = {
                            Text(text = item.title)
                                
                        }, 
                        selected = selectedItem.value == item, 
                        icon = {
                          Icon(
                              imageVector = item.imageVector,
                              contentDescription = item.title
                          )  
                        },
                        onClick = {
                            scope.launch { 
                                selectedItem.value == item
                                drawerState.close()
                            }
                            
                        }
                    )
                }
            }
        },
        content = {
        //scaffle, например
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                Button(onClick = { 
                    scope.launch { 
                        drawerState.open()
                    }
                }) {
                    Text(text = "meow")
                }
            }
        }
    )
}

data class DrawerItem(
    val imageVector: ImageVector,
    val title: String
)*/


@Composable
fun DrawerMenu() {
    Box(modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(
            id = R.drawable.star),
            contentDescription = "Main Bg image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Column(modifier = Modifier.fillMaxSize()) {
            Header()
            Body()
        }
    }
}

@Composable
fun Header(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(170.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.Cyan)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                painter = painterResource(id = R.drawable.round_item_img_1),
                contentDescription = "Header image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Text(
                text = "-menu-",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(10.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

@Composable
fun Body(){
    val list = stringArrayResource(id = R.array.drawer_list)
    LazyColumn(modifier = Modifier.fillMaxSize()){
        itemsIndexed(list){ index, title ->
            Card(
                Modifier
                    .fillMaxWidth()
                    .padding(3.dp),
            ) {
                Text(
                    text = title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                        .padding(10.dp)
                        .wrapContentWidth(),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}