# Caravanas de Futebol

Aplicação Android desenvolvida em **Kotlin** e **Jetpack Compose** para gestão e organização de caravanas esportivas para jogos de futebol. O projeto utiliza Firebase (Auth e Firestore) para autenticação segura e sincronização de dados em tempo real.

---

## Design & Interface

- **Tema Dark Premium:** Fundo escuro elegante (`#0A0E17`) com destaques em **Amarelo Dourado (`#FFD700`)**.
- **Interface Reativa:** Construída totalmente com Jetpack Compose e componentes Material 3.

---

## Funcionalidades

- **Autenticação de Usuários:** Cadastro e Login via e-mail e senha com Firebase Auth.
- **Dados Privados por Usuário:** Cada usuário gerencia e visualiza **exclusivamente suas próprias caravanas** (garantido por regras no Firestore).
- **Atualização em Tempo Real:** Sincronização automática com o banco via `addSnapshotListener` (mudanças refletem instantaneamente no app).
- **CRUD Completo de Caravanas:**
  - Cadastro de estádio, rota, passageiros confirmados, valor e horário.
  - Edição de caravanas existentes.
  - Exclusão com atualização em tempo real.
- **Validações de Formulário:**
  - Validação de senha mínima (6 caracteres).
  - Impede cadastro de caravanas sem informar o valor ($R\$).

---

## Tecnologias e Arquitetura

- **Linguagem:** Kotlin
- **UI:** Jetpack Compose + Material 3
- **Arquitetura:** MVVM (Model-View-ViewModel) + Repository Pattern
- **Banco de Dados:** Firebase Firestore (Cloud Database)
- **Autenticação:** Firebase Authentication
- **Concorrência:** Kotlin Coroutines & StateFlow

---

## Estrutura do Projeto

```text
com.example.caravanasfutebol
├── model
│   └── Caravana.kt
├── repository
│   └── CaravanaRepository.kt
├── viewmodel
│   ├── AuthViewModel.kt
│   └── CaravanaViewModel.kt
├── ui
│   ├── screens
│   │   └── MainScreen.kt
│   └── theme
│       ├── Color.kt
│       └── Theme.kt
└── MainActivity.kt
