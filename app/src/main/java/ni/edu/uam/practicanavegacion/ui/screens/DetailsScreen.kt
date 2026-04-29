package ni.edu.uam.practicanavegacion.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import ni.edu.uam.practicanavegacion.ColorRojoPrincipal
import ni.edu.uam.practicanavegacion.data.CartViewModel

@Composable
fun DetailsScreen(
    navController: NavController,
    nombre: String,
    precio: String,
    imagenRes: Int,
    cartViewModel: CartViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Top Bar personalizada
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Atrás")
            }
            Text("Details", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorito", tint = Color.Black)
        }

        // Imagen del producto
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imagenRes),
                contentDescription = nombre,
                modifier = Modifier.size(220.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        // Información del producto
        Column(modifier = Modifier.padding(24.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = nombre, fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, contentDescription = null, tint = Color(0xFFFFB300))
                    Text(" 4.5", fontWeight = FontWeight.Bold)
                }
            }

            Text("Fast Food", color = Color.Gray, modifier = Modifier.padding(vertical = 8.dp))

            Text(
                text = "Enjoy our delicious $nombre, made with fresh ingredients and the best secret sauce of the house. A unique experience in every bite.",
                color = Color.Gray,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            // Footer con precio y botón de compra
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("Price", color = Color.Gray)
                    Text("C$ $precio", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = ColorRojoPrincipal)
                }
                Button(
                    onClick = { /* Comprar */ },
                    colors = ButtonDefaults.buttonColors(containerColor = ColorRojoPrincipal),
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.height(55.dp).width(150.dp)
                ) {
                    Text("Add to Cart", color = Color.White, fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}