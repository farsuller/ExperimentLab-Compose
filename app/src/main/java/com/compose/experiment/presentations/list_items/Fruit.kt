package com.compose.experiment.presentations.list_items

import androidx.compose.ui.graphics.Color

data class Fruit(
    val color: Color,
    val name: String,
    val description: String,
    val category: FruitCategory,
    val isSelected: Boolean = false
)

val SAMPLE_FRUIT = listOf(
    Fruit(Color(0xFFB22222), "Strawberry", "Sweet and juicy", FruitCategory.BERRY),
    Fruit(Color(0xFFFFD700), "Pineapple", "Tropical and sweet", FruitCategory.TROPICAL),
    Fruit(Color(0xFF66BB6A), "Lime", "Tart and zesty", FruitCategory.CITRUS),
    Fruit(Color(0xFF1E90FF), "Blueberry", "Sweet and juicy", FruitCategory.BERRY),
    Fruit(Color(0xFFFFA500), "Mango", "Tropical and sweet", FruitCategory.TROPICAL),
    Fruit(Color(0xFFFFA07A), "Orange", "Citrusy and sweet", FruitCategory.CITRUS),
    Fruit(Color(0xFF8BC34A), "Kiwi", "Sweet and tart", FruitCategory.BERRY),
    Fruit(Color(0xFF8A2BE2), "Plum", "Sweet and juicy", FruitCategory.DRUPES),
    Fruit(Color(0xFF00CED1), "Coconut", "Creamy and tropical", FruitCategory.TROPICAL),
    Fruit(Color(0xFFFF69B4), "Dragonfruit", "Exotic and sweet", FruitCategory.TROPICAL),

    Fruit(Color(0xFFFFEB3B), "Banana", "Creamy and sweet", FruitCategory.CITRUS),
    Fruit(Color(0xFFFFC0CB), "Peach", "Fuzzy and sweet", FruitCategory.DRUPES),
    Fruit(Color(0xFF4CAF50), "Green Apple", "Crisp and tart", FruitCategory.CITRUS),
    Fruit(Color(0xFFFFC107), "Watermelon", "Juicy and refreshing", FruitCategory.BERRY),
    Fruit(Color(0xFF8A2BE2), "Blackberry", "Rich and sweet", FruitCategory.BERRY),
    Fruit(Color(0xFF00BFFF), "Blue Raspberry", "Sweet and tangy", FruitCategory.BERRY),

    Fruit(Color(0xFFFFA500), "Tangerine", "Citrusy and sweet", FruitCategory.CITRUS),
    Fruit(Color(0xFFFFE4C4), "Nectarine", "Sweet and juicy", FruitCategory.DRUPES),
    Fruit(Color(0xFFF0FFF0), "Honeydew", "Sweet and mild", FruitCategory.MELON),
    Fruit(Color(0xFFF5F5DC), "Casaba Melon", "Mild and sweet", FruitCategory.MELON),
    Fruit(Color(0xFFDC143C), "Cranberry", "Tart and refreshing", FruitCategory.BERRY),

    Fruit(Color(0xFFCD5C5C), "Starfruit", "Tart and sweet", FruitCategory.TROPICAL),
    Fruit(Color(0xFFFFE4B5), "Maracuja", "Tropical and tangy", FruitCategory.TROPICAL),
    Fruit(Color(0xFF8B0000), "Blackcurrant", "Rich and tart", FruitCategory.BERRY),
    Fruit(Color(0xFFFFDAB9), "Lychee", "Sweet and floral", FruitCategory.TROPICAL),
    Fruit(Color(0xFF4CAF50), "Avocado", "Creamy and nutty", FruitCategory.BERRY),

    Fruit(Color(0xFFFFC0CB), "Raspberry", "Sweet and tart", FruitCategory.BERRY),
    Fruit(Color(0xFFD8BFD8), "Olive", "Savory and rich", FruitCategory.DRUPES),
    Fruit(Color(0xFF8A2BE2), "Blue Plum", "Sweet and juicy", FruitCategory.DRUPES),
    Fruit(Color(0xFFFF6347), "Tomato", "Juicy and savory", FruitCategory.CITRUS),
    Fruit(Color(0xFFDABF20), "Figs", "Sweet and chewy", FruitCategory.DRUPES)
)
