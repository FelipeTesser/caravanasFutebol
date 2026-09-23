package com.example.caravanasfutebol.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.caravanasfutebol.model.Caravana
import com.example.caravanasfutebol.ui.theme.*
import com.example.caravanasfutebol.viewmodel.AuthViewModel
import com.example.caravanasfutebol.viewmodel.CaravanaViewModel

@Composable
fun AppContent(
    authViewModel: AuthViewModel,
    caravanaViewModel: CaravanaViewModel
) {
    val isLoggedIn by authViewModel.isLoggedIn.collectAsState()

    if (!isLoggedIn) {
        AuthScreen(authViewModel)
    } else {
        CaravanaCrudScreen(caravanaViewModel, onLogout = { authViewModel.logout() })
    }
}

@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val errorMsg by viewModel.authError.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(DarkSurface)
                .border(1.dp, GoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "⚽ MATCH DAY",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = GoldPrimary
            )
            Text(
                text = "Caravanas de Futebol",
                fontSize = 14.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("E-mail") },
                singleLine = true,
                colors = customTextFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = customTextFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )

            errorMsg?.let {
                Spacer(modifier = Modifier.height(8.dp))
                Text(it, color = RedError, fontSize = 12.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { viewModel.login(email, password) },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DarkBackground),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Text("ENTRAR", fontWeight = FontWeight.Bold)
                }

                Spacer(modifier = Modifier.width(12.dp))

                OutlinedButton(
                    onClick = { viewModel.cadastrar(email, password) },
                    border = ButtonDefaults.outlinedButtonBorder.copy(brush = androidx.compose.ui.graphics.SolidColor(GoldPrimary)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Text("CADASTRAR", color = GoldPrimary, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaravanaCrudScreen(viewModel: CaravanaViewModel, onLogout: () -> Unit) {
    val caravanas by viewModel.caravanas.collectAsState()

    var selectedId by remember { mutableStateOf("") }
    var estadio by remember { mutableStateOf("") }
    var rota by remember { mutableStateOf("") }
    var passageiros by remember { mutableStateOf("") }
    var valor by remember { mutableStateOf("") }
    var horario by remember { mutableStateOf("") }

    // Mensagem de validação
    var formError by remember { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = DarkBackground,
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("PAINEL DE CARAVANAS", fontSize = 18.sp, fontWeight = FontWeight.Black, color = GoldPrimary)
                        Text("Gestão da Rota & Passageiros", fontSize = 11.sp, color = TextMuted)
                    }
                },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Sair", color = RedError, fontWeight = FontWeight.Bold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkSurface)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = DarkSurface),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, GoldPrimary.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        if (selectedId.isEmpty()) "➕ CADASTRAR CARAVANA" else "✏️ EDITAR CARAVANA",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = estadio,
                            onValueChange = { estadio = it },
                            label = { Text("Estádio") },
                            colors = customTextFieldColors(),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = rota,
                            onValueChange = { rota = it },
                            label = { Text("Rota") },
                            colors = customTextFieldColors(),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = passageiros,
                            onValueChange = { passageiros = it },
                            label = { Text("Passageiros") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = customTextFieldColors(),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = valor,
                            onValueChange = { valor = it; formError = null },
                            label = { Text("Valor R$ *") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = customTextFieldColors(),
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        OutlinedTextField(
                            value = horario,
                            onValueChange = { horario = it },
                            label = { Text("Horário") },
                            colors = customTextFieldColors(),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    // Exibição de erro se o valor estiver vazio
                    formError?.let {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(it, color = RedError, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row {
                        Button(
                            onClick = {
                                val valorDouble = valor.toDoubleOrNull()
                                // VALIDAÇÃO: Bloqueia se o valor for vazio ou inválido
                                if (valor.isBlank() || valorDouble == null) {
                                    formError = "O valor R$ é obrigatório e precisa ser um número."
                                    return@Button
                                }

                                viewModel.salvarCaravana(
                                    id = selectedId,
                                    estadio = estadio,
                                    rota = rota,
                                    passageiros = passageiros.toIntOrNull() ?: 0,
                                    valor = valorDouble,
                                    horario = horario
                                )
                                selectedId = ""; estadio = ""; rota = ""; passageiros = ""; valor = ""; horario = ""; formError = null
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary, contentColor = DarkBackground),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(if (selectedId.isEmpty()) "SALVAR" else "ATUALIZAR", fontWeight = FontWeight.Bold)
                        }

                        AnimatedVisibility(visible = selectedId.isNotEmpty()) {
                            Row {
                                Spacer(modifier = Modifier.width(8.dp))
                                OutlinedButton(
                                    onClick = {
                                        selectedId = ""; estadio = ""; rota = ""; passageiros = ""; valor = ""; horario = ""; formError = null
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("Cancelar", color = TextMuted)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("ROTA & PASSAGEIROS CONFIRMADOS", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextMuted)

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(caravanas) { caravana ->
                    CaravanaCard(
                        caravana = caravana,
                        onEdit = {
                            selectedId = caravana.id
                            estadio = caravana.estadio
                            rota = caravana.rota
                            passageiros = caravana.passageirosConfirmados.toString()
                            valor = caravana.valorPagamento.toString()
                            horario = caravana.horarioPartida
                            formError = null
                        },
                        onDelete = { viewModel.deletarCaravana(caravana.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CaravanaCard(caravana: Caravana, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = DarkCard),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(caravana.estadio, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                Text("R$ ${caravana.valorPagamento}", fontSize = 16.sp, fontWeight = FontWeight.Black, color = GoldPrimary)
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text("🛣️ Rota: ${caravana.rota}", fontSize = 13.sp, color = TextMuted)
            Text("👥 Passageiros Confirmados: ${caravana.passageirosConfirmados}", fontSize = 13.sp, color = TextMuted)
            Text("⏰ Horário de Saída: ${caravana.horarioPartida}", fontSize = 13.sp, color = TextMuted)

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                TextButton(onClick = onEdit) {
                    Text("Editar", color = GoldAccent, fontSize = 12.sp)
                }
                TextButton(onClick = onDelete) {
                    Text("Excluir", color = RedError, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun customTextFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor = GoldPrimary,
    unfocusedBorderColor = TextMuted.copy(alpha = 0.3f),
    focusedLabelColor = GoldPrimary,
    unfocusedLabelColor = TextMuted,
    focusedTextColor = TextWhite,
    unfocusedTextColor = TextWhite
)