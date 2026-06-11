package com.example.noteplusadmin.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.Visibility
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteplusadmin.NotePlusAdminApp
import com.example.noteplusadmin.navigation.Screen
import com.example.noteplusadmin.ui.theme.ErrorRed
import com.example.noteplusadmin.ui.theme.GreenDark
import com.example.noteplusadmin.ui.theme.GreenPrimary
import com.example.noteplusadmin.ui.theme.TextSecondary
import com.example.noteplusadmin.ui.theme.White
import com.example.noteplusadmin.viewmodel.LoginState
import com.example.noteplusadmin.viewmodel.LoginViewModel

@Composable
fun LoginConstraintScreen(navController: NavController) {
    val app = LocalContext.current.applicationContext as NotePlusAdminApp
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory(app.database))
    val estado by viewModel.state.collectAsState()

    var usuario          by remember { mutableStateOf("") }
    var contrasena       by remember { mutableStateOf("") }
    var contrasenaVisible by remember { mutableStateOf(false) }

    LaunchedEffect(estado) {
        if (estado is LoginState.Success) {
            navController.navigate(Screen.Dashboard.route) {
                popUpTo(Screen.LoginConstraint.route) { inclusive = true }
            }
            viewModel.resetState()
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(horizontal = 32.dp)
    ) {
        val (iconRef, titleRef, subtitleRef, userRef, passRef,
             errorRef, btnRef, hintRef, backRef) = createRefs()

        // Barrier dinámico: se mueve hacia abajo cuando aparece el error
        val errorBarrier = createBottomBarrier(passRef, errorRef)

        // ── Ícono — anclado parte superior central ──────────────────────────
        Icon(
            imageVector = Icons.Default.School,
            contentDescription = null,
            tint = GreenDark,
            modifier = Modifier
                .size(80.dp)
                .constrainAs(iconRef) {
                    top.linkTo(parent.top, margin = 64.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        // ── Título — debajo del ícono ────────────────────────────────────────
        Text(
            text = "NotePlus Admin",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = GreenDark,
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(iconRef.bottom, margin = 8.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // ── Subtítulo — debajo del título ────────────────────────────────────
        Text(
            text = "Sistema de Gestión Educativa",
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
            modifier = Modifier.constrainAs(subtitleRef) {
                top.linkTo(titleRef.bottom, margin = 4.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // ── Campo usuario — debajo del subtítulo con margen amplio ───────────
        OutlinedTextField(
            value = usuario,
            onValueChange = {
                usuario = it
                if (estado is LoginState.Error) viewModel.resetState()
            },
            label = { Text("Usuario") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            singleLine = true,
            colors = coloresConstraint(),
            modifier = Modifier.constrainAs(userRef) {
                top.linkTo(subtitleRef.bottom, margin = 40.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        // ── Campo contraseña — debajo del campo usuario ──────────────────────
        OutlinedTextField(
            value = contrasena,
            onValueChange = {
                contrasena = it
                if (estado is LoginState.Error) viewModel.resetState()
            },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            singleLine = true,
            visualTransformation = if (contrasenaVisible) VisualTransformation.None
                                   else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                    Icon(
                        imageVector = if (contrasenaVisible) Icons.Default.VisibilityOff
                                      else Icons.Default.Visibility,
                        contentDescription = if (contrasenaVisible) "Ocultar" else "Mostrar"
                    )
                }
            },
            colors = coloresConstraint(),
            modifier = Modifier.constrainAs(passRef) {
                top.linkTo(userRef.bottom, margin = 12.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )

        // ── Error — Gone cuando no hay error; el barrier lo ignora ───────────
        Text(
            text = "Usuario o contraseña incorrectos",
            color = ErrorRed,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.constrainAs(errorRef) {
                top.linkTo(passRef.bottom, margin = 6.dp)
                start.linkTo(parent.start)
                // Gone = sin tamaño → el barrier queda en passRef.bottom
                visibility = if (estado is LoginState.Error) Visibility.Visible else Visibility.Gone
            }
        )

        // ── Botón — anclado al barrier (sube/baja según estado del error) ────
        Button(
            onClick = { viewModel.login(usuario, contrasena) },
            enabled = estado !is LoginState.Loading,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
            modifier = Modifier
                .height(52.dp)
                .constrainAs(btnRef) {
                    top.linkTo(errorBarrier, margin = 16.dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
        ) {
            if (estado is LoginState.Loading) {
                CircularProgressIndicator(
                    color = White,
                    modifier = Modifier.size(22.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(
                    text = "Iniciar Sesión",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        // ── Texto de credenciales — anclado en la parte inferior ─────────────
        Text(
            text = "Usuario: admin  |  Clave: admin2026#",
            style = MaterialTheme.typography.bodySmall,
            color = TextSecondary,
            modifier = Modifier.constrainAs(hintRef) {
                bottom.linkTo(backRef.top, margin = 4.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        // ── Botón volver — anclado en la parte inferior ───────────────────────
        TextButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier.constrainAs(backRef) {
                bottom.linkTo(parent.bottom, margin = 20.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        ) {
            Text(
                text = "← Volver al login estándar",
                color = GreenPrimary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun coloresConstraint() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor       = GreenPrimary,
    focusedLabelColor        = GreenPrimary,
    focusedLeadingIconColor  = GreenPrimary,
    focusedTrailingIconColor = GreenPrimary,
    cursorColor              = GreenPrimary
)
