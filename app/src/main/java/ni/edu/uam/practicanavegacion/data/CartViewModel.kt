package ni.edu.uam.practicanavegacion.data

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

// Definimos qué es un producto
data class Producto(
    val nombre: String,
    val precio: String,
    val imagen: Int,
    var cantidad: Int = 1
)

class CartViewModel : ViewModel() {
    // Esta es la lista "viva" que todas las pantallas observarán
    private val _itemsCarrito = mutableStateListOf<Producto>()
    val itemsCarrito: List<Producto> get() = _itemsCarrito

    fun agregarAlCarrito(producto: Producto) {
        // Si ya existe, solo aumentamos la cantidad
        val existente = _itemsCarrito.find { it.nombre == producto.nombre }
        if (existente != null) {
            existente.cantidad++
        } else {
            _itemsCarrito.add(producto)
        }
    }

    fun calcularTotal(): Double {
        return _itemsCarrito.sumOf { it.precio.toDouble() * it.cantidad }
    }
}