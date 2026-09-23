package com.example.caravanasfutebol.repository

import com.example.caravanasfutebol.model.Caravana
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class CaravanaRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val caravanasCollection = db.collection("caravanas")

    private val currentUserId: String
        get() = auth.currentUser?.uid ?: ""

    // Filtra no Firestore apenas as caravanas onde userId é igual ao usuário logado
    fun observarCaravanas(): Flow<List<Caravana>> = callbackFlow {
        if (currentUserId.isEmpty()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener: ListenerRegistration = caravanasCollection
            .whereEqualTo("userId", currentUserId)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null) {
                    val lista = snapshot.toObjects(Caravana::class.java)
                    trySend(lista)
                }
            }
        awaitClose { listener.remove() }
    }

    suspend fun adicionarCaravana(caravana: Caravana) {
        val documentRef = caravanasCollection.document()
        val novaCaravana = caravana.copy(
            id = documentRef.id,
            userId = currentUserId // Associa ao UID do usuário atual
        )
        documentRef.set(novaCaravana).await()
    }

    suspend fun atualizarCaravana(caravana: Caravana) {
        val caravanaAtualizada = caravana.copy(userId = currentUserId)
        caravanasCollection.document(caravana.id).set(caravanaAtualizada).await()
    }

    suspend fun deletarCaravana(id: String) {
        caravanasCollection.document(id).delete().await()
    }
}