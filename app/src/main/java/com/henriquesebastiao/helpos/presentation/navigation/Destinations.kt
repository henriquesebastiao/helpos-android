package com.henriquesebastiao.helpos.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data object ClientsRoute

@Serializable
data class ClientDetailRoute(val clientId: String)

@Serializable
data object ServiceOrdersRoute

@Serializable
data class ServiceOrderDetailRoute(val serviceOrderId: String)

@Serializable
data object SettingsRoute
