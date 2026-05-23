package com.henriquesebastiao.helpos.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.henriquesebastiao.helpos.presentation.feature.clients.ClientDetailScreen
import com.henriquesebastiao.helpos.presentation.feature.clients.ClientsScreen
import com.henriquesebastiao.helpos.presentation.feature.login.LoginScreen
import com.henriquesebastiao.helpos.presentation.feature.serviceorders.ServiceOrderDetailScreen
import com.henriquesebastiao.helpos.presentation.feature.serviceorders.ServiceOrdersScreen
import com.henriquesebastiao.helpos.presentation.feature.settings.SettingsScreen

@Composable
fun HelpOsNavHost(
    navController: NavHostController,
    startDestination: Any,
    padding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable<LoginRoute> {
            LoginScreen()
        }
        composable<ClientsRoute> {
            ClientsScreen(
                padding = padding,
                onClientClick = { id -> navController.navigate(ClientDetailRoute(id)) },
            )
        }
        composable<ClientDetailRoute> { backStackEntry ->
            val route: ClientDetailRoute = backStackEntry.toRoute()
            ClientDetailScreen(
                clientId = route.clientId,
                onBack = navController::navigateUp,
            )
        }
        composable<ServiceOrdersRoute> {
            ServiceOrdersScreen(
                padding = padding,
                onServiceOrderClick = { id -> navController.navigate(ServiceOrderDetailRoute(id)) },
            )
        }
        composable<ServiceOrderDetailRoute> { backStackEntry ->
            val route: ServiceOrderDetailRoute = backStackEntry.toRoute()
            ServiceOrderDetailScreen(
                serviceOrderId = route.serviceOrderId,
                onBack = navController::navigateUp,
            )
        }
        composable<SettingsRoute> {
            SettingsScreen(onBack = navController::navigateUp)
        }
    }
}
