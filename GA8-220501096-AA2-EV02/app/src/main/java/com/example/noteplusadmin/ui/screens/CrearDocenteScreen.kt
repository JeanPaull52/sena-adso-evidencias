package com.example.noteplusadmin.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteplusadmin.NotePlusAdminApp
import com.example.noteplusadmin.navigation.Screen
import com.example.noteplusadmin.ui.theme.ErrorRed
import com.example.noteplusadmin.ui.theme.GreenDark
import com.example.noteplusadmin.ui.theme.GreenPrimary
import com.example.noteplusadmin.ui.theme.White
import com.example.noteplusadmin.viewmodel.DocenteEstado
import com.example.noteplusadmin.viewmodel.DocenteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearDocenteScreen(
    navController: NavController,
    idPersona: Int
) {
    val app = LocalContext.current.applicationContext as NotePlusAdminApp
    val viewModel: DocenteViewModel = viewModel(factory = DocenteViewModel.Factory(app.database))

    val estado by viewModel.estado.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var fechaIngreso  by remember { mutableStateOf("") }
    var especialidad  by remember { mutableStateOf("") }
    var estadoLaboral by remember { mutableStateOf("") }

    LaunchedEffect(estado) {
        when (val s = estado) {
            is DocenteEstado.Exito -> {
                snackbarHostState.showSnackbar("Docente registrado exitosamente")
                viewModel.resetEstado()
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.Dashboard.route) { inclusive = false }
                }
            }
            is DocenteEstado.Error -> {
                snackbarHostState.showSnackbar("Error: ${s.mensaje}")
                viewModel.resetEstado()
            }
            else -> Unit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Datos del Docente", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor          = GreenDark,
                    titleContentColor       = White,
                    navigationIconContentColor = White
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                Snackbar(
                    snackbarData   = data,
                    containerColor = GreenPrimary,
                    contentColor   = White
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Información laboral",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = GreenDark
            )

            OutlinedTextField(
                value = fechaIngreso,
                onValueChange = { fechaIngreso = it },
                label = { Text("Fecha de ingreso (dd/MM/yyyy)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = campoColores()
            )

            OutlinedTextField(
                value = especialidad,
                onValueChange = { especialidad = it },
                label = { Text("Especialidad") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = campoColores()
            )

            var expandidoEstado by remember { mutableStateOf(false) }
            ExposedDropdownMenuBox(
                expanded = expandidoEstado,
                onExpandedChange = { expandidoEstado = !expandidoEstado },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = estadoLaboral,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Estado laboral") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoEstado) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    colors = campoColores()
                )
                ExposedDropdownMenu(
                    expanded = expandidoEstado,
                    onDismissRequest = { expandidoEstado = false }
                ) {
                    listOf("Activo", "Inactivo", "Suspendido").forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                estadoLaboral = opcion
                                expandidoEstado = false
                            }
                        )
                    }
                }
            }

            // Mensaje de error en pantalla (complementa el Snackbar)
            if (estado is DocenteEstado.Error) {
                Text(
                    text = (estado as DocenteEstado.Error).mensaje,
                    color = ErrorRed,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(8.dp))

            Button(
                onClick = {
                    viewModel.guardar(
                        idPersona    = idPersona,
                        fechaIngreso = fechaIngreso,
                        especialidad = especialidad,
                        estadoLaboral = estadoLaboral
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = estado !is DocenteEstado.Cargando,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                if (estado is DocenteEstado.Cargando) {
                    CircularProgressIndicator(color = White, modifier = Modifier.padding(4.dp))
                } else {
                    Text(
                        text = "Registrar Docente",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun campoColores() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = GreenPrimary,
    focusedLabelColor  = GreenPrimary,
    cursorColor        = GreenPrimary
)
