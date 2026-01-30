package com.sonalisulgadle.taskmanagerpro.presentation.auth

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest
import com.google.android.gms.auth.api.identity.Identity
import com.sonalisulgadle.taskmanagerpro.R
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMButton
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMOutlinedButton
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMOutlinedTextField
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme
import timber.log.Timber

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit,
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()


    LaunchedEffect(state) {
        if (state is LoginState.Success) onLoginSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.login_title),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(40.dp))
            TMOutlinedTextField(
                value = email,
                placeholder = stringResource(R.string.email_placeholder),
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                placeholder = {
                    Text(
                        text = stringResource(R.string.password_placeholder),
                        modifier = Modifier.padding(4.dp),
                        fontFamily = FontFamily.Monospace
                    )
                },
                singleLine = true,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(24.dp))
            TMButton(
                title = stringResource(R.string.sign_in),
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    viewModel.onLoginWithEmail(email, password)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            GoogleSignInButton(
                webClientId = stringResource(R.string.default_web_client_id),
            ) { token ->
                viewModel.onSignInWithGoogleClick(token)
            }
            Spacer(modifier = Modifier.height(20.dp))

            TextButton(
                content = {
                    Text(
                        text = stringResource(R.string.sign_up_with_email),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Center
                        )
                    )
                },
                onClick = {
                    onSignUpClick()
                }
            )

            if (state is LoginState.Loading) {
                CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
            }

            if (state is LoginState.Error) {
                Text(
                    text = (state as LoginState.Error).message ?: "Error",
                    color = Color.Red,
                    fontFamily = FontFamily.Monospace
                )
            }
        }
    }
}

@Composable
fun GoogleSignInButton(
    webClientId: String,
    onTokenReceived: (String) -> Unit
) {
    val context = LocalContext.current

    val signInClient = remember { Identity.getSignInClient(context) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            try {
                val credential = signInClient.getSignInCredentialFromIntent(result.data)
                val token = credential.googleIdToken
                if (token != null) onTokenReceived(token)
            } catch (e: Exception) {
                Timber.tag("Google Auth")
                    .e("GoogleSignInButton: Error Retrieving token ${e.message}")
            }
        }

    }
    TMOutlinedButton(
        title = stringResource(R.string.sign_in_with_google),
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            val request = GetSignInIntentRequest.builder()
                .setServerClientId(webClientId)

                .build()

            signInClient.getSignInIntent(request)
                .addOnSuccessListener { result ->
                    launcher.launch(
                        IntentSenderRequest.Builder(result.intentSender).build()
                    )
                }
                .addOnFailureListener { exception ->
                    Timber.tag("Google Auth")
                        .e("GoogleSignInButton: Cannot begin Sign-in ${exception.message}")
                    exception.printStackTrace()
                }
        }
    )
}

@Composable
@PreviewLightDark
fun PreviewLoginScreen() {
    TaskManagerProTheme {
        LoginScreen(onLoginSuccess = {}, onSignUpClick = {})
    }
}