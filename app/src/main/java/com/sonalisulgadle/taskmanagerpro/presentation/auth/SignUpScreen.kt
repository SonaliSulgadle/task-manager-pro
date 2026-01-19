package com.sonalisulgadle.taskmanagerpro.presentation.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sonalisulgadle.taskmanagerpro.R
import com.sonalisulgadle.taskmanagerpro.presentation.components.TMOutlinedTextField
import com.sonalisulgadle.taskmanagerpro.ui.theme.TaskManagerProTheme

@Composable
fun SignUpScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onSignUpSuccess: () -> Unit,
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    val state by viewModel.state.collectAsState()


    LaunchedEffect(state) {
        if (state is LoginState.Success) {
            onSignUpSuccess()
            viewModel.resetState()
        }
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
                text = stringResource(R.string.sign_up),
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
                        stringResource(R.string.password_placeholder),
                        modifier = Modifier.padding(4.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                singleLine = true,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = confirmPassword,
                placeholder = {
                    Text(
                        stringResource(R.string.confirm_password_placeholder),
                        modifier = Modifier.padding(4.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                singleLine = true,
                onValueChange = { confirmPassword = it },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(34.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                onClick = {
                    viewModel.onSignUpWithEmail(email, password, confirmPassword)
                },
                enabled = state !is LoginState.Loading
            ) {
                if (state is LoginState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text = stringResource(R.string.sign_up),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            if (state is LoginState.Error) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = (state as LoginState.Error).message ?: "",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@PreviewLightDark
fun PreviewSignUpScreen() {
    TaskManagerProTheme {
        SignUpScreen(onSignUpSuccess = {})
    }
}