package ni.edu.uam.practicanavegacion.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
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

@Composable
fun ProfileScreen(navController: NavController) {
    Scaffold(
        bottomBar = { BarraNavegacionFlotante(navController) }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(150.dp).background(Color(0xFFB71C1C)))

            // Foto de perfil circular
            Image(
                painter = painterResource(id = R.drawable.usuario),
                contentDescription = null,
                modifier = Modifier.size(120.dp).offset(y = (-60).dp).clip(CircleShape).background(Color.White).padding(5.dp).clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Text("User Name", fontSize = 24.sp, fontWeight = FontWeight.Bold, modifier = Modifier.offset(y = (-40).dp))
            Text("user.email@uam.edu.ni", color = Color.Gray, modifier = Modifier.offset(y = (-40).dp))

            // Botones de ajustes
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(horizontal = 30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray.copy(0.3f))
            ) {
                Icon(Icons.Default.Settings, contentDescription = null, tint = Color.Black)
                Spacer(Modifier.width(10.dp))
                Text("Account Settings", color = Color.Black)
            }
        }
    }
}