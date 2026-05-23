package com.henriquesebastiao.helpos.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data object ClientsRoute

@Serializable
data class ClientDetailRoute(val clientId: Long)

@Serializable
data object ServiceOrdersRoute

@Serializable
data class ServiceOrderDetailRoute(val serviceOrderId: Long)

@Serializable
data object SettingsRoute
