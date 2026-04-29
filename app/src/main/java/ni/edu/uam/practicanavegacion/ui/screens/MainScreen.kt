package ni.edu.uam.practicanavegacion.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ni.edu.uam.practicanavegacion.R
import ni.edu.uam.practicanavegacion.data.CartViewModel
import ni.edu.uam.practicanavegacion.data.Producto
import ni.edu.uam.practicanavegacion.BarraNavegacionFlotante
import ni.edu.uam.practicanavegacion.ColorGrisFondo
import ni.edu.uam.practicanavegacion.ColorRojoPrincipal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController, cartViewModel: CartViewModel) {
    Scaffold(
        bottomBar = { BarraNavegacionFlotante(navController) },
        containerColor = ColorRojoPrincipal
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp)
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                // Header: Usuario y Notificaciones
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Image(
                        painter = painterResource(id = R.drawable.usuario),
                        contentDescription = null,
                        modifier = Modifier.size(45.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Icon(Icons.Default.NotificationsNone, null)
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Título dinámico
                Text(
                    text = buildAnnotatedString {
                        append("Choose\nYour Favorite ")
                        withStyle(style = SpanStyle(color = ColorRojoPrincipal, fontWeight = FontWeight.Bold)) {
                            append("Food")
                        }
                    },
                    fontSize = 28.sp, lineHeight = 34.sp
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Buscador y Filtro
                Row(verticalAlignment = Alignment.CenterVertically) {
                    var search by remember { mutableStateOf("") }
                    TextField(
                        value = search,
                        onValueChange = { search = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Search") },
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = ColorRojoPrincipal) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = ColorGrisFondo,
                            unfocusedContainerColor = ColorGrisFondo,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(15.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .background(ColorRojoPrincipal, RoundedCornerShape(15.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Tune, null, tint = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Categorías
                val cats = listOf("All", "Pizza", "Burger", "Sandwich")
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(cats) { cat ->
                        FilterChip(
                            selected = cat == "All",
                            onClick = {},
                            label = { Text(cat) },
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(25.dp))

                // Sección Popular Food
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Popular Food", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("See All", color = ColorRojoPrincipal)
                }

                Spacer(modifier = Modifier.height(15.dp))

                // Lista de Tarjetas (Pasando el cartViewModel)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                    FoodCard(
                        name = "Hamburger",
                        price = "250.0",
                        img = R.drawable.hamburguesa,
                        navController = navController,
                        cartViewModel = cartViewModel
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    FoodCard(
                        name = "Pepperoni Pizza",
                        price = "350.0",
                        img = R.drawable.pizza,
                        navController = navController,
                        cartViewModel = cartViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun FoodCard(
    name: String,
    price: String,
    img: Int,
    navController: NavController,
    cartViewModel: CartViewModel // Agregado para poder añadir al carrito
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .padding(bottom = 8.dp)
            .clickable {
                val encodedName = Uri.encode(name)
                val encodedPrice = Uri.encode(price)
                navController.navigate("detalle/$encodedName/$encodedPrice/$img")
            },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = img),
                contentDescription = name,
                modifier = Modifier
                    .size(95.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                maxLines = 1
            )

            Text(
                text = "Fast Food",
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "C$ $price",
                    color = ColorRojoPrincipal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                // Botón "+" con funcionalidad real
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .background(ColorRojoPrincipal, CircleShape)
                        .clickable {
                            // Acción: Agregar el producto al ViewModel
                            cartViewModel.agregarAlCarrito(Producto(name, price, img))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Agregar",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

