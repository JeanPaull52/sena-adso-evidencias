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
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.noteplusadmin.NotePlusAdminApp
import com.example.noteplusadmin.data.entities.Persona
import com.example.noteplusadmin.navigation.Screen
import com.example.noteplusadmin.ui.theme.ErrorRed
import com.example.noteplusadmin.ui.theme.GreenDark
import com.example.noteplusadmin.ui.theme.GreenPrimary
import com.example.noteplusadmin.ui.theme.White
import com.example.noteplusadmin.viewmodel.PersonaEstado
import com.example.noteplusadmin.viewmodel.PersonaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearPersonaScreen(navController: NavController) {
    val app = LocalContext.current.applicationContext as NotePlusAdminApp
    val viewModel: PersonaViewModel = viewModel(factory = PersonaViewModel.Factory(app.database))

    val estado        by viewModel.estado.collectAsState()
    val nacionalidades by viewModel.nacionalidades.collectAsState()

    // Campos del formulario
    var tipoDocumento      by remember { mutableStateOf("") }
    var numeroDocumento    by remember { mutableStateOf("") }
    var primerNombre       by remember { mutableStateOf("") }
    var segundoNombre      by remember { mutableStateOf("") }
    var primerApellido     by remember { mutableStateOf("") }
    var segundoApellido    by remember { mutableStateOf("") }
    var fechaNacimiento    by remember { mutableStateOf("") }
    var telefonoFijo       by remember { mutableStateOf("") }
    var celular            by remember { mutableStateOf("") }
    var correo             by remember { mutableStateOf("") }
    var direccion          by remember { mutableStateOf("") }
    var idNacionalidad     by remember { mutableIntStateOf(0) }
    var nombreNacionalidad by remember { mutableStateOf("") }
    var estadoPersona      by remember { mutableStateOf("") }

    LaunchedEffect(estado) {
        if (estado is PersonaEstado.Exito) {
            navController.navigate(Screen.CrearDocente.withArgs((estado as PersonaEstado.Exito).idPersona))
            viewModel.resetEstado()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Datos Personales", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Información del documento",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = GreenDark
            )

            CampoDropdown(
                valor = tipoDocumento,
                onValorChange = { tipoDocumento = it },
                etiqueta = "Tipo de documento",
                opciones = listOf("CC", "CE", "Pasaporte", "TI")
            )

            CampoTexto(
                valor = numeroDocumento,
                onValorChange = { numeroDocumento = it },
                etiqueta = "Número de documento",
                teclado = KeyboardType.Number
            )

            Text(
                text = "Nombres y apellidos",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = GreenDark
            )

            CampoTexto(primerNombre, { primerNombre = it }, "Primer nombre")
            CampoTexto(segundoNombre, { segundoNombre = it }, "Segundo nombre (opcional)", requerido = false)
            CampoTexto(primerApellido, { primerApellido = it }, "Primer apellido")
            CampoTexto(segundoApellido, { segundoApellido = it }, "Segundo apellido (opcional)", requerido = false)

            Text(
                text = "Datos de contacto",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = GreenDark
            )

            CampoTexto(
                valor = fechaNacimiento,
                onValorChange = { fechaNacimiento = it },
                etiqueta = "Fecha de nacimiento (dd/MM/yyyy)"
            )
            CampoTexto(
                valor = celular,
                onValorChange = { celular = it },
                etiqueta = "Celular",
                teclado = KeyboardType.Phone
            )
            CampoTexto(
                valor = telefonoFijo,
                onValorChange = { telefonoFijo = it },
                etiqueta = "Teléfono fijo (opcional)",
                requerido = false,
                teclado = KeyboardType.Phone
            )
            CampoTexto(
                valor = correo,
                onValorChange = { correo = it },
                etiqueta = "Correo electrónico",
                teclado = KeyboardType.Email
            )
            CampoTexto(direccion, { direccion = it }, "Dirección de residencia")

            Text(
                text = "Datos adicionales",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
                color = GreenDark
            )

            // Dropdown nacionalidad desde BD
            CampoDropdown(
                valor = nombreNacionalidad,
                onValorChange = { nombre ->
                    nombreNacionalidad = nombre
                    idNacionalidad = nacionalidades.firstOrNull { it.nombreNacionalidad == nombre }?.idNacionalidad ?: 0
                },
                etiqueta = "Nacionalidad",
                opciones = nacionalidades.map { it.nombreNacionalidad }
            )

            CampoDropdown(
                valor = estadoPersona,
                onValorChange = { estadoPersona = it },
                etiqueta = "Estado",
                opciones = listOf("Activo", "Inactivo")
            )

            // Mensaje de error
            if (estado is PersonaEstado.Error) {
                Text(
                    text = (estado as PersonaEstado.Error).mensaje,
                    color = ErrorRed,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(Modifier.height(4.dp))

            Button(
                onClick = {
                    viewModel.guardar(
                        Persona(
                            idNacionalidad     = idNacionalidad,
                            tipoDocumento      = tipoDocumento,
                            numeroDocumento    = numeroDocumento,
                            primerNombre       = primerNombre,
                            segundoNombre      = segundoNombre.ifBlank { null },
                            primerApellido     = primerApellido,
                            segundoApellido    = segundoApellido.ifBlank { null },
                            fechaNacimiento    = fechaNacimiento,
                            telefonoFijo       = telefonoFijo.ifBlank { null },
                            celular            = celular,
                            correoElectronico  = correo,
                            direccionResidencia = direccion,
                            estado             = estadoPersona
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                enabled = estado !is PersonaEstado.Cargando,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
            ) {
                if (estado is PersonaEstado.Cargando) {
                    CircularProgressIndicator(color = White, modifier = Modifier.padding(4.dp))
                } else {
                    Text(
                        text = "Continuar",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ─── Componentes reutilizables ─────────────────────────────────────────────

@Composable
private fun CampoTexto(
    valor: String,
    onValorChange: (String) -> Unit,
    etiqueta: String,
    requerido: Boolean = true,
    teclado: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = valor,
        onValueChange = onValorChange,
        label = { Text(if (requerido) etiqueta else "$etiqueta (opcional)") },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = teclado),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GreenPrimary,
            focusedLabelColor  = GreenPrimary,
            cursorColor        = GreenPrimary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CampoDropdown(
    valor: String,
    onValorChange: (String) -> Unit,
    etiqueta: String,
    opciones: List<String>
) {
    var expandido by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expandido,
        onExpandedChange = { expandido = !expandido },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = valor,
            onValueChange = {},
            readOnly = true,
            label = { Text(etiqueta) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandido) },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenPrimary,
                focusedLabelColor  = GreenPrimary
            )
        )
        ExposedDropdownMenu(
            expanded = expandido,
            onDismissRequest = { expandido = false }
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    text = { Text(opcion) },
                    onClick = {
                        onValorChange(opcion)
                        expandido = false
                    }
                )
            }
        }
    }
}
