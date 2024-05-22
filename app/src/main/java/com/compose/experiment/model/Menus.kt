package com.compose.experiment.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

enum class Menus (
    val menuImage : String,
    val text : String)
{
    BestSellers(menuImage = "https://queen.jollibee.com.ph/2023/05/8pc.-Chickenjoy-Bucket-w-Jolly-Spaghetti-Family-Pan-min.png",
        text = "Bestsellers"),
    NewProducts(menuImage = "https://queen.jollibee.com.ph/2023/07/JBPH-P-SEOUL-THUMBNAIL_SOLO_SPICY_750X750px_FA-min.png",
        text = "New Products"),
    FamilyMeals(menuImage = "https://queen.jollibee.com.ph/2022/01/Chickenjoy-Bucket-w-RIce-Jolly-Spaghetti-Drinks.png",
        text = "Family Meals"),
    Breakfast(menuImage = "https://queen.jollibee.com.ph/2022/01/Longganisa-Solo.png",
        text = "Breakfast"),
    Chickenjoy(menuImage = "https://queen.jollibee.com.ph/2023/05/UPDATED-BUCKET-DARKER-CRISPY-JUICY-min-915x1024.png",
        text = "Chickenjoy"),
    Burgers(menuImage = "https://queen.jollibee.com.ph/2022/01/Yumburger-Solo.png",
        text = "Burgers"),
    JollySpaghetti(menuImage = "https://queen.jollibee.com.ph/2022/01/Jolly-Spaghetti-Family-Pan-1.png",
        text = "Jolly Spaghetti"),
    BurgerSteak(menuImage = "https://queen.jollibee.com.ph/2022/01/1-pc-Burger-Steak-Solo.png",
        text = "Burger Steak"),
    SuperMeals(menuImage = "https://queen.jollibee.com.ph/2022/05/1-pc-Chickenjoy-w-Burger-Steak-Half-Jolly-Spaghetti-Super-Meal.webp",
        text = "Super Meals"),
    FriesAndSides(menuImage = "https://queen.jollibee.com.ph/2022/05/Jolly-Crispy-Fries-Regular.webp",
        text = "Fries & Sides"),
    Desserts(menuImage = "https://queen.jollibee.com.ph/2022/01/Peach-Mango-Pie.png",
        text = "Desserts"),
    Beverages(menuImage = "https://queen.jollibee.com.ph/2022/01/Coke-Zero-Regular.png",
        text = "Beverages"),



}