package ni.edu.uam.practicanavegacion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// --- PALETA DE COLORES (Extremedamente fiel a tu diseño) ---
val PrimaryRed = Color(0xFFB71C1C) // Rojo intenso de fondo/botones
val BackgroundRedGradient = Color(0xFFD84343) // Rojo más claro del gradiente
val SurfaceWhite = Color.White
val TextColorMain = Color.Black
val TextColorSecondary = Color.Gray

// --- MODELO DE DATOS SENCILLO ---
data class FoodItem(
    val id: String,
    val name: String,
    val description: String,
    val price: Double,
    val category: String,
    val imageResId: Int // R.drawable...
)

// Datos de prueba (Necesitas tener estas imágenes en drawable)
val sampleFoodList = listOf(
    FoodItem("burger1", "Hamburger", "Delicious double beef patty", 250.0, "Burger", android.R.drawable.ic_menu_gallery),
    FoodItem("pizza1", "Pepperoni Pizza", "Classic Italian pizza", 350.0, "Pizza", android.R.drawable.ic_menu_gallery),
    FoodItem("sandwich1", "Cheese Sandwich", "Toasted sandwich with extra cheese", 200.0, "Sandwich", android.R.drawable.ic_menu_gallery)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // Usamos un Box para el fondo degradado global
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = androidx.compose.ui.graphics.Brush.verticalGradient(
                                colors = listOf(BackgroundRedGradient, PrimaryRed)
                            )
                        )
                ) {
                    FoodAppNavigation()
                }
            }
        }
    }
}

// =========================================
// 🎯 NAVEGACIÓN (NavHost)
// =========================================
@Composable
fun FoodAppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inicio") {

        // PANTALLA 1: Inicio (Listado)
        composable("inicio") {
            MainScreen(navController = navController)
        }

        // PANTALLA 2: Detalle (Parámetro ID)
        composable("detalle/{foodId}") { backStackEntry ->
            val foodId = backStackEntry.arguments?.getString("foodId")
            val foodItem = sampleFoodList.find { it.id == foodId }

            foodItem?.let {
                DetailsScreen(foodItem = it, navController = navController)
            } ?: Text("Error: Producto no encontrado")
        }

        // PANTALLA 3: Perfil (O resumen)
        composable("perfil") {
            ProfileScreen(navController = navController)
        }
    }
}

// =========================================
// 📱 PANTALLA 1: INICIO (Adaptación fiel)
// =========================================
@Composable
fun MainScreen(navController: NavController) {
    // Contenedor de la "pantalla blanca" de la App
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp) // Simular espacio de status bar
            .background(Color.Transparent), // Fondo transparente para el Card
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // -- Header --
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar (Simulado con Círculo y Texto "U")
                // En el diseño real es una foto de usuario
                Box(modifier = Modifier.size(48.dp).clip(CircleShape).background(Color.LightGray), contentAlignment = Alignment.Center) {
                    // Si tienes una imagen de usuario, úsala:
                    // Image(painter = painterResource(id = R.drawable.user_avatar), contentDescription = "Perfil")
                    Text("U", style = MaterialTheme.typography.titleMedium, color = PrimaryRed)
                }

                // Botón de Perfil Interactivo (Navegar a P3)
                IconButton(onClick = { navController.navigate("perfil") }) {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = "Notificaciones", tint = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // -- Título --
            Text(
                text = buildAnnotatedString {
                    append("Choose\nYour Favorite ")
                    withStyle(style = SpanStyle(color = PrimaryRed, fontWeight = FontWeight.Bold)) {
                        append("Food")
                    }
                },
                fontSize = 28.sp,
                lineHeight = 36.sp,
                fontWeight = FontWeight.Light,
                color = TextColorMain
            )

            Spacer(modifier = Modifier.height(24.dp))

            // -- Barra de Búsqueda (TextField) --
            // Reemplazamos la "barra simulada" por un TextField interactivo real.
            var searchText by remember { mutableStateOf("") }
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search", color = TextColorSecondary) },
                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = PrimaryRed) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = PrimaryRed,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                shape = RoundedCornerShape(24.dp), // Redondeado como el diseño
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            // -- Pestañas de Categoría (Simuladas con botones) --
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                item { CategoryChip("All", isSelected = true) }
                item { CategoryChip("Pizza") }
                item { CategoryChip("Burger") }
                item { CategoryChip("Sandwich") }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // -- Sección Popular --
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Popular Food", style = MaterialTheme.typography.titleLarge, color = TextColorMain)
                Text(text = "See All", style = MaterialTheme.typography.bodySmall, color = PrimaryRed)
            }
            Spacer(modifier = Modifier.height(16.dp))

            // -- Listado de Productos (LazyRow) --
            // Cada Card navega al detalle (P2).
            LazyRow(contentPadding = PaddingValues(end = 16.dp)) {
                items(sampleFoodList) { foodItem ->
                    FoodListItem(foodItem = foodItem, navController = navController)
                }
            }
        }
    }
}

@Composable
fun FoodListItem(foodItem: FoodItem, navController: NavController) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(end = 16.dp)
            .clickable { navController.navigate("detalle/${foodItem.id}") },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite)
    ) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            // Imagen circular pequeña (Adaptada de tu diseño)
            Image(
                painter = painterResource(id = foodItem.imageResId),
                contentDescription = foodItem.name,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(text = foodItem.name, style = MaterialTheme.typography.titleSmall, color = TextColorMain)
            Text(text = foodItem.category, style = MaterialTheme.typography.bodySmall, color = TextColorSecondary)

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Rs. ${foodItem.price}", style = MaterialTheme.typography.titleMedium, color = PrimaryRed, fontWeight = FontWeight.Bold)

                // Botón '+' interactivo para añadir
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(PrimaryRed),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Añadir", tint = Color.White, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

@Composable
fun CategoryChip(text: String, isSelected: Boolean = false) {
    Button(
        onClick = { /* Acción de filtro */ },
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) PrimaryRed else Color.LightGray.copy(alpha = 0.2f),
            contentColor = if (isSelected) Color.White else Color.Black
        ),
        shape = RoundedCornerShape(16.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        modifier = Modifier.height(40.dp)
    ) {
        Text(text = text)
    }
}

// =========================================
// 📱 PANTALLA 2: DETALLE (Adaptación fiel)
// =========================================
@Composable
fun DetailsScreen(foodItem: FoodItem, navController: NavController) {
    // Usamos Column para estructurar los elementos verticales
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Fondo blanco de la página de detalle
            .padding(16.dp)
    ) {
        // -- Top Bar (Retroceso y Menú) --
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Botón de retroceso interactivo (Navegar atrás)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(PrimaryRed)
                    .clickable { navController.popBackStack() },
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color.White)
            }

            IconButton(onClick = { /* Menú de opciones */ }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Menú", tint = Color.Black)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -- Contenedor Circular de Imagen (Aspecto clave de tu diseño) --
        // Adaptamos la "media luna" roja de tu diseño con un Box que contenga la imagen circular.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f) // Cuadrado para contener el círculo
                .padding(32.dp)
                .clip(CircleShape) // Imagen principal circular
                .background(Color.Transparent), // El fondo del círculo es transparente aquí
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = foodItem.imageResId), // Reemplazar por imagen real grande si tienes
                contentDescription = foodItem.name,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -- Título y Precio --
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = foodItem.name, fontSize = 26.sp, fontWeight = FontWeight.Bold, color = TextColorMain)
                Text(text = foodItem.category, style = MaterialTheme.typography.bodyMedium, color = TextColorSecondary)
            }
            Text(text = "Rs. ${foodItem.price}", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = PrimaryRed)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // -- Pestañas de Detalle/Reseñas --
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { /* Acción */ },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                modifier = Modifier.weight(1f)
            ) {
                Text("Details")
            }
            OutlinedButton(
                onClick = { /* Acción */ },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
                modifier = Modifier.weight(1f),
                border = null
            ) {
                Text("Reviews")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -- Descripción (Text) --
        Text(
            text = "${foodItem.description}. This is a very delicious item cooked with fresh ingredients.",
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 24.sp,
            color = TextColorSecondary
        )
        Text(text = "See more", style = MaterialTheme.typography.labelSmall, color = PrimaryRed, modifier = Modifier.align(Alignment.End))

        Spacer(modifier = Modifier.weight(1f)) // Empujar botones finales hacia abajo

        // -- Sección Inferior (Cantidad y Botón de compra) --
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Controles de cantidad
            Row(verticalAlignment = Alignment.CenterVertically) {
                QuantityControl(icon = Icons.Default.Home) // Simulación '-'
                Spacer(modifier = Modifier.width(16.dp))
                Text("1", fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.width(16.dp))
                QuantityControl(icon = Icons.Default.Add, isPrimary = true) // '+'
            }

            // Botón principal de compra
            Button(
                onClick = { /* Ir a pagar */ },
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryRed),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.height(56.dp).width(160.dp)
            ) {
                Text("Add to Cart", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun QuantityControl(icon: ImageVector, isPrimary: Boolean = false) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(if (isPrimary) PrimaryRed else Color.Gray.copy(alpha = 0.2f)),
        contentAlignment = Alignment.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = if (isPrimary) Color.White else Color.Black, modifier = Modifier.size(20.dp))
    }
}

// =========================================
// 📱 PANTALLA 3: PERFIL (O resumen)
// =========================================
@Composable
fun ProfileScreen(navController: NavController) {
    // Usamos el mismo estilo de card blanca de P1 para mantener la coherencia
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .background(Color.Transparent),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {

            // -- Título Identificando la Pantalla (Requisito) --
            Text(text = "UserProfile", style = MaterialTheme.typography.displayMedium, color = PrimaryRed, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(24.dp))

            // -- Componente Interactivo (Bótón de retroceso) --
            Button(
                onClick = { navController.popBackStack() }, // Navegación funcional
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Back to Home")
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Contenido simulado de perfil
            UserInfoRow(label = "Username", value = "User_InterfaceVault")
            UserInfoRow(label = "Email", value = "vault@example.com")
            UserInfoRow(label = "Location", value = "Managua, Nicaragua")
        }
    }
}

@Composable
fun UserInfoRow(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelSmall, color = TextColorSecondary)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, color = TextColorMain, fontWeight = FontWeight.Bold)
        Divider(color = Color.LightGray, thickness = 1.dp)
    }
}