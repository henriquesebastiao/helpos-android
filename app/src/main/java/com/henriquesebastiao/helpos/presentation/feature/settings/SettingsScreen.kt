package com.henriquesebastiao.helpos.presentation.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.henriquesebastiao.helpos.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val savedMessage = stringResource(R.string.settings_saved)

    LaunchedEffect(state.saved) {
        if (state.saved) snackbarHostState.showSnackbar(savedMessage)
    }

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = stringResource(R.string.settings_title)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_back),
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.save() }) {
                        Icon(
                            imageVector = Icons.Filled.Save,
                            contentDescription = stringResource(R.string.cd_save),
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        SettingsContent(
            state = state,
            padding = padding,
            onBackendUrlChange = viewModel::onBackendUrlChange,
            onHelpOsApiKeyChange = viewModel::onHelpOsApiKeyChange,
            onClaudeApiKeyChange = viewModel::onClaudeApiKeyChange,
            onToggleHelpOsKey = viewModel::toggleHelpOsKeyVisibility,
            onToggleClaudeKey = viewModel::toggleClaudeKeyVisibility,
        )
    }
}

@Composable
private fun SettingsContent(
    state: SettingsUiState,
    padding: PaddingValues,
    onBackendUrlChange: (String) -> Unit,
    onHelpOsApiKeyChange: (String) -> Unit,
    onClaudeApiKeyChange: (String) -> Unit,
    onToggleHelpOsKey: () -> Unit,
    onToggleClaudeKey: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        OutlinedTextField(
            value = state.backendUrl,
            onValueChange = onBackendUrlChange,
            label = { Text(stringResource(R.string.settings_backend_url)) },
            placeholder = { Text("http://172.16.0.10:8000/") },
            singleLine = true,
            isError = state.backendUrlError != null,
            supportingText = {
                state.backendUrlError?.let { Text(it) }
                    ?: Text(stringResource(R.string.settings_backend_url_hint))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
            modifier = Modifier.fillMaxWidth(),
        )

        KeyField(
            value = state.helpOsApiKey,
            onValueChange = onHelpOsApiKeyChange,
            label = stringResource(R.string.settings_helpos_api_key),
            visible = state.showHelpOsKey,
            onToggleVisibility = onToggleHelpOsKey,
            hint = stringResource(R.string.settings_helpos_api_key_hint),
        )

        KeyField(
            value = state.claudeApiKey,
            onValueChange = onClaudeApiKeyChange,
            label = stringResource(R.string.settings_claude_api_key),
            visible = state.showClaudeKey,
            onToggleVisibility = onToggleClaudeKey,
            hint = stringResource(R.string.settings_claude_api_key_hint),
        )
    }
}

@Composable
private fun KeyField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    visible: Boolean,
    onToggleVisibility: () -> Unit,
    hint: String,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        visualTransformation = if (visible) VisualTransformation.None else PasswordVisualTransformation(),
        supportingText = { Text(hint) },
        trailingIcon = {
            IconButton(onClick = onToggleVisibility) {
                Icon(
                    imageVector = if (visible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = if (visible) {
                        stringResource(R.string.cd_hide_key)
                    } else {
                        stringResource(R.string.cd_show_key)
                    },
                )
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        modifier = Modifier.fillMaxWidth(),
    )
}
