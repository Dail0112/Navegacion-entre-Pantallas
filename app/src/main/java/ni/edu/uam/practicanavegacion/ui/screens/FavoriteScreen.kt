package ni.edu.uam.practicanavegacion.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ni.edu.uam.practicanavegacion.R
import ni.edu.uam.practicanavegacion.BarraNavegacionFlotante
import ni.edu.uam.practicanavegacion.ColorRojoPrincipal

@Composable
fun FavoriteScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BarraNavegacionFlotante(navController) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp)) {
            Text("My Favorites", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))

            // Lista de favoritos (Ejemplo)
            val favorites = listOf("Hamburger", "Pepperoni Pizza")
            LazyColumn(verticalArrangement = Arrangement.spacedBy(15.dp)) {
                items(favorites) { item ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(if(item == "Hamburger") R.drawable.hamburguesa else R.drawable.pizza),
                                contentDescription = null,
                                modifier = Modifier.size(70.dp).clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Column(modifier = Modifier.weight(1f).padding(horizontal = 15.dp)) {
                                Text(item, fontWeight = FontWeight.Bold)
                                Text("Fast Food", color = Color.Gray, fontSize = 12.sp)
                            }
                            IconButton(onClick = {}) {
                                Icon(Icons.Default.DeleteOutline, contentDescription = null, tint = ColorRojoPrincipal)
                            }
                        }
                    }
                }
            }
        }
    }
}