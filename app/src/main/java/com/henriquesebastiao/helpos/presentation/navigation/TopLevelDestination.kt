package com.henriquesebastiao.helpos.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.filled.People
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.henriquesebastiao.helpos.R

enum class TopLevelDestination(
    val labelRes: Int,
    val icon: ImageVector,
) {
    Clients(R.string.nav_clients, Icons.Filled.People),
    ServiceOrders(R.string.nav_service_orders, Icons.AutoMirrored.Filled.Assignment),
}

fun NavDestination?.isInHierarchy(destination: TopLevelDestination): Boolean = when (destination) {
    TopLevelDestination.Clients -> this?.hasRoute(ClientsRoute::class) == true
    TopLevelDestination.ServiceOrders -> this?.hasRoute(ServiceOrdersRoute::class) == true
}

fun TopLevelDestination.route(): Any = when (this) {
    TopLevelDestination.Clients -> ClientsRoute
    TopLevelDestination.ServiceOrders -> ServiceOrdersRoute
}
