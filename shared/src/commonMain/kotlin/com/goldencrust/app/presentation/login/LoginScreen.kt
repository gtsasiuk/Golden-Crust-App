package com.goldencrust.app.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.goldencrust.app.domain.auth.validate.PasswordError
import com.goldencrust.app.domain.auth.validate.UsernameError
import com.goldencrust.app.presentation.ui.AppTopBar
import com.goldencrust.app.presentation.ui.GoldenCrustLogo
import com.goldencrust.app.presentation.ui.ObserveEvents
import goldencrustapp.shared.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

// ---------- Stateful ----------
@Composable
fun LoginScreen(
    isDark: Boolean,
    isUk: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: LoginViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveEvents(viewModel.events) { event ->
        when (event) {
            LoginEvent.NavigateToHome -> onNavigateToHome()
        }
    }

    LoginContent(state, viewModel::onAction, isDark, isUk, onToggleTheme, onToggleLanguage)
}

// ---------- Stateless ----------
@Composable
private fun LoginContent(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    isDark: Boolean,
    isUk: Boolean,
    onToggleTheme: () -> Unit,
    onToggleLanguage: () -> Unit,
) {
    val focusManager = LocalFocusManager.current

    Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
        Box(Modifier.safeDrawingPadding(), contentAlignment = Alignment.TopCenter) {
            Column(
                modifier = Modifier.widthIn(max = 480.dp).fillMaxSize().padding(24.dp),
            ) {
                AppTopBar(isDark, isUk, onToggleTheme, onToggleLanguage, showLogo = false)

                Column(
                    modifier = Modifier.weight(1f).fillMaxWidth().verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.height(32.dp))
                    GoldenCrustLogo(markHeight = 48.dp)
                    Spacer(Modifier.height(32.dp))

                    Text(
                        stringResource(Res.string.login_title),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        stringResource(Res.string.login_subtitle),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(Modifier.height(24.dp))

                    // Логін: клавіатура "Далі" -> фокус на пароль
                    LoginField(
                        label = stringResource(Res.string.login_username_label),
                        hint = stringResource(Res.string.login_username_hint),
                        value = state.username,
                        onValueChange = { onAction(LoginAction.UsernameChanged(it)) },
                        leadingIcon = Res.drawable.ic_person,
                        error = state.usernameError?.toMessage(),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        ),
                    )
                    Spacer(Modifier.height(16.dp))

                    // Пароль: клавіатура "Готово" -> та сама дія, що й кнопка "Увійти"
                    LoginField(
                        label = stringResource(Res.string.login_password_label),
                        hint = stringResource(Res.string.login_password_hint),
                        value = state.password,
                        onValueChange = { onAction(LoginAction.PasswordChanged(it)) },
                        leadingIcon = Res.drawable.ic_lock,
                        error = state.passwordError?.toMessage(),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password,
                            imeAction = ImeAction.Done,
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                focusManager.clearFocus()
                                onAction(LoginAction.SignIn)
                            }
                        ),
                        visualTransformation =
                            if (state.isPasswordVisible) VisualTransformation.None
                            else PasswordVisualTransformation(),
                        trailing = {
                            IconButton(onClick = { onAction(LoginAction.TogglePasswordVisibility) }) {
                                Icon(
                                    painter = painterResource(
                                        if (state.isPasswordVisible) Res.drawable.ic_eye_off else Res.drawable.ic_eye
                                    ),
                                    contentDescription = stringResource(
                                        if (state.isPasswordVisible) Res.string.login_hide_password
                                        else Res.string.login_show_password
                                    ),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(20.dp),
                                )
                            }
                        },
                    )

                    TextButton(
                        onClick = { onAction(LoginAction.ForgotPasswordClicked) },
                        modifier = Modifier.align(Alignment.End),
                    ) {
                        Text(
                            stringResource(Res.string.login_forgot),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    }
                    Spacer(Modifier.height(8.dp))

                    Button(
                        onClick = {
                            focusManager.clearFocus()
                            onAction(LoginAction.SignIn)
                        },
                        modifier = Modifier.fillMaxWidth().height(52.dp),
                    ) {
                        Text(stringResource(Res.string.login_sign_in), style = MaterialTheme.typography.labelLarge)
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        stringResource(Res.string.login_no_account),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    TextButton(onClick = { onAction(LoginAction.RegisterClicked) }) {
                        Text(
                            stringResource(Res.string.login_register),
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LoginField(
    label: String,
    hint: String,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: DrawableResource,
    error: String?,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailing: (@Composable () -> Unit)? = null,
) {
    Column(Modifier.fillMaxWidth()) {
        // Підпис окремим Text над полем, як на дизайні (не label усередині)
        Text(
            label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(Modifier.height(6.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true, // обов'язково для Next/Done
            shape = MaterialTheme.shapes.medium,
            placeholder = { Text(hint) },
            leadingIcon = {
                Icon(
                    painterResource(leadingIcon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp),
                )
            },
            trailingIcon = trailing,
            isError = error != null,
            supportingText = error?.let { { Text(it) } },
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                errorContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                errorBorderColor = MaterialTheme.colorScheme.tertiary,
                errorSupportingTextColor = MaterialTheme.colorScheme.tertiary,
            ),
        )
    }
}

@Composable
private fun UsernameError.toMessage(): String = stringResource(
    when (this) {
        UsernameError.Empty -> Res.string.error_username_empty
        UsernameError.TooShort -> Res.string.error_username_short
    }
)

@Composable
private fun PasswordError.toMessage(): String = stringResource(
    when (this) {
        PasswordError.Empty -> Res.string.error_password_empty
        PasswordError.TooShort -> Res.string.error_password_short
    }
)