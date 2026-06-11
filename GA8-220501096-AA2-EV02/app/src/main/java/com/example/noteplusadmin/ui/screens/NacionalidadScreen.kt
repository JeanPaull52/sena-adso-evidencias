package com.example.noteplusadmin.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteplusadmin.NotePlusAdminApp
import com.example.noteplusadmin.ui.theme.ErrorRed
import com.example.noteplusadmin.ui.theme.GreenDark
import com.example.noteplusadmin.ui.theme.GreenLight
import com.example.noteplusadmin.ui.theme.GreenPrimary
import com.example.noteplusadmin.ui.theme.TextPrimary
import com.example.noteplusadmin.ui.theme.TextSecondary
import com.example.noteplusadmin.ui.theme.White
import com.example.noteplusadmin.viewmodel.NacionalidadGuardarEstado
import com.example.noteplusadmin.viewmodel.NacionalidadViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NacionalidadScreen(navController: NavController) {
    val app = LocalContext.current.applicationContext as NotePlusAdminApp
    val viewModel: NacionalidadViewModel = viewModel(factory = NacionalidadViewModel.Factory(app.database))

    val nacionalidades by viewModel.nacionalidades.collectAsState()
    val guardarEstado  by viewModel.guardarEstado.collectAsState()

    var nombreInput by remember { mutableStateOf("") }

    // Auto-limpiar el mensaje de éxito tras 3 segundos
    LaunchedEffect(guardarEstado) {
        if (guardarEstado is NacionalidadGuardarEstado.Exito) {
            kotlinx.coroutines.delay(3_000)
            viewModel.resetEstado()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nacionalidades", fontWeight = FontWeight.Bold) },
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
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            // Sección de alta
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = GreenLight),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Agregar nacionalidad",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = GreenDark
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = nombreInput,
                            onValueChange = {
                                nombreInput = it
                                if (guardarEstado !is NacionalidadGuardarEstado.Idle) viewModel.resetEstado()
                            },
                            label = { Text("Nombre") },
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            colors = androidx.compose.material3.OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = GreenPrimary,
                                focusedLabelColor  = GreenPrimary,
                                cursorColor        = GreenPrimary
                            )
                        )
                        Button(
                            onClick = {
                                viewModel.insertar(nombreInput)
                                if (nombreInput.isNotBlank()) nombreInput = ""
                            },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Agregar")
                        }
                    }

                    // Mensaje de feedback
                    when (val s = guardarEstado) {
                        is NacionalidadGuardarEstado.Exito ->
                            Text(
                                text = "Nacionalidad agregada correctamente",
                                color = GreenPrimary,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        is NacionalidadGuardarEstado.Error ->
                            Text(
                                text = s.mensaje,
                                color = ErrorRed,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        else -> Unit
                    }
                }
            }

            Spacer(Modifier.height(20.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Registros (${nacionalidades.size})",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = TextPrimary
                )
            }

            Spacer(Modifier.height(8.dp))

            if (nacionalidades.isEmpty()) {
                Text(
                    text = "No hay nacionalidades registradas",
                    color = TextSecondary,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            } else {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    LazyColumn {
                        items(items = nacionalidades, key = { it.idNacionalidad }) { nac ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 14.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Flag,
                                    contentDescription = null,
                                    tint = GreenPrimary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = nac.nombreNacionalidad,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = TextPrimary
                                )
                            }
                            HorizontalDivider(color = com.example.noteplusadmin.ui.theme.Divider)
                        }
                    }
                }
            }
        }
    }
}
