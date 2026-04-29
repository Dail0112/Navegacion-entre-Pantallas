package ni.edu.uam.practicanavegacion


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.*
import androidx.navigation.NavType
import androidx.navigation.navArgument
import ni.edu.uam.practicanavegacion.ui.screens.*
import androidx.lifecycle.viewmodel.compose.viewModel
import ni.edu.uam.practicanavegacion.data.CartViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                // Creamos el ViewModel aquí para que sea el mismo en toda la app
                val cartViewModel: CartViewModel = viewModel()

                NavHost(navController = navController, startDestination = "inicio") {
                    composable("inicio") { MainScreen(navController, cartViewModel) }
                    composable("favoritos") { FavoriteScreen(navController) }
                    composable("carrito") { CartScreen(navController, cartViewModel) }
                    composable("perfil") { ProfileScreen(navController) }

                    composable(
                        "detalle/{nombre}/{precio}/{imagen}",
                        arguments = listOf(
                            navArgument("nombre") { type = NavType.StringType },
                            navArgument("precio") { type = NavType.StringType },
                            navArgument("imagen") { type = NavType.IntType }
                        )
                    ) { backStackEntry ->
                        val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
                        val precio = backStackEntry.arguments?.getString("precio") ?: ""
                        val imagen = backStackEntry.arguments?.getInt("imagen") ?: 0
                        DetailsScreen(navController, nombre, precio, imagen, cartViewModel)
                    }
                }
            }
        }
    }
}