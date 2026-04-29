package ni.edu.uam.practicanavegacion

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

val ColorRojoPrincipal = Color(0xFFB71C1C)
val ColorGrisFondo = Color(0xFFF5F5F5)

@Composable
fun BarraNavegacionFlotante(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 25.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            modifier = Modifier
                .width(300.dp)
                .height(65.dp)
                .clip(RoundedCornerShape(35.dp))
                .background(ColorRojoPrincipal)
                .padding(horizontal = 25.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Home, null, modifier = Modifier.clickable { navController.navigate("inicio") })
            Icon(Icons.Default.FavoriteBorder, null, modifier = Modifier.clickable { navController.navigate("favoritos") })
            Icon(Icons.Default.ShoppingCart, null, modifier = Modifier.clickable { navController.navigate("carrito") })
            Icon(Icons.Default.Person, null, modifier = Modifier.clickable { navController.navigate("perfil") })
        }
    }
}