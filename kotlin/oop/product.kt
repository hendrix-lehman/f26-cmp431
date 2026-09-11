data class Product(
    val id: String,
    val name: String,
    val price: Double,
    val category: String
)

class Inventory {
    private val products = mutableListOf<Product>()
    
    fun addProduct(product: Product) {
        products.add(product)
    }
    
    fun filterByCategory(category: String): List<Product> {
        return products.filter { it.category.equals(category, ignoreCase = true) }
    }
    
    fun getProductNames(): List<String> {
        return products.map { it.name }
    }
    
    fun getAveragePrice(): Double {
        if (products.isEmpty()) return 0.0
        return products.map { it.price }.average()
    }
    
    fun displayInventory() {
        products.forEach { println("ID: ${it.id} | Name: ${it.name} | Price: $${it.price} | Category: ${it.category}") }
    }
}

fun main() {
    val inv = Inventory()
    inv.addProduct(Product("1", "Pixel Phone", 799.0, "Electronics"))
    inv.addProduct(Product("2", "Running Shoes", 120.0, "Footwear"))
    inv.addProduct(Product("3", "Tablet", 499.0, "Electronics"))
    
    println("=== Full Inventory ===")
    inv.displayInventory()
    
    println("=== Electronics Only ===")
    inv.filterByCategory("Electronics").forEach { println(it.name) }
    
    println("=== Average Price ===")
    println(String.format("$%.2f", inv.getAveragePrice()))
}
