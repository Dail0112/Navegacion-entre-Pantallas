package ni.edu.uam.practicanavegacion.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ni.edu.uam.practicanavegacion.BarraNavegacionFlotante
import ni.edu.uam.practicanavegacion.ColorRojoPrincipal
import ni.edu.uam.practicanavegacion.data.CartViewModel

@Composable
fun CartScreen(navController: NavController, cartViewModel: CartViewModel) {
    val items = cartViewModel.itemsCarrito

    Scaffold(
        bottomBar = { BarraNavegacionFlotante(navController) }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp)) {
            Text("My Cart", fontSize = 28.sp, fontWeight = FontWeight.Bold)

            if (items.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Your cart is empty", color = Color.Gray)
                }
            } else {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    items(items) { producto ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(painterResource(producto.imagen), null, Modifier.size(60.dp).clip(CircleShape))
                            Column(Modifier.padding(start = 12.dp).weight(1f)) {
                                Text(producto.nombre, fontWeight = FontWeight.Bold)
                                Text("Quantity: ${producto.cantidad}", fontSize = 12.sp)
                            }
                            Text("C$ ${producto.precio}", fontWeight = FontWeight.Bold, color = ColorRojoPrincipal)
                        }
                    }
                }

                // Botón de Checkout con total real
                Button(
                    onClick = { /* Checkout */ },
                    modifier = Modifier.fillMaxWidth().height(60.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = ColorRojoPrincipal),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text("Pay C$ ${cartViewModel.calcularTotal()}", fontSize = 18.sp)
                }
            }
        }
    }
}