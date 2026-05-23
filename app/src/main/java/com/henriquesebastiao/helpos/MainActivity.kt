package com.henriquesebastiao.helpos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.henriquesebastiao.helpos.core.ui.theme.HelpOsTheme
import com.henriquesebastiao.helpos.domain.model.SessionState
import com.henriquesebastiao.helpos.presentation.navigation.ClientsRoute
import com.henriquesebastiao.helpos.presentation.navigation.HelpOsNavHost
import com.henriquesebastiao.helpos.presentation.navigation.LoginRoute
import com.henriquesebastiao.helpos.presentation.navigation.SettingsRoute
import com.henriquesebastiao.helpos.presentation.navigation.TopLevelDestination
import com.henriquesebastiao.helpos.presentation.navigation.isInHierarchy
import com.henriquesebastiao.helpos.presentation.navigation.route
import com.henriquesebastiao.helpos.session.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sessionViewModel: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splash = installSplashScreen()
        splash.setKeepOnScreenCondition { sessionViewModel.sessionState.value is SessionState.Loading }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            HelpOsTheme {
                val session by sessionViewModel.sessionState.collectAsStateWithLifecycle()
                if (session !is SessionState.Loading) {
                    HelpOsAppShell(session)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HelpOsAppShell(session: SessionState) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val showShell = session is SessionState.LoggedIn &&
        TopLevelDestination.entries.any { currentDestination.isInHierarchy(it) }

    val startDestination: Any = when (session) {
        is SessionState.LoggedIn -> ClientsRoute
        else -> LoginRoute
    }

    LaunchedEffect(session) {
        if (session is SessionState.LoggedOut &&
            currentDestination != null &&
            currentDestination?.hasRoute(LoginRoute::class) != true
        ) {
            navController.navigate(LoginRoute) {
                popUpTo(navController.graph.id) { inclusive = true }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (showShell) {
                TopAppBar(
                    title = { Text(stringResource(R.string.app_name)) },
                    actions = {
                        IconButton(onClick = { navController.navigate(SettingsRoute) }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = stringResource(R.string.cd_settings),
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                    ),
                )
            }
        },
        bottomBar = {
            if (showShell) {
                NavigationBar {
                    TopLevelDestination.entries.forEach { destination ->
                        val selected = currentDestination.isInHierarchy(destination)
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(destination.route()) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = null,
                                )
                            },
                            label = { Text(stringResource(destination.labelRes)) },
                        )
                    }
                }
            }
        },
    ) { padding ->
        HelpOsNavHost(
            navController = navController,
            startDestination = startDestination,
            padding = padding,
        )
    }
}
