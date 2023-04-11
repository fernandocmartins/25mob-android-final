package br.com.fiap25mob.mbamobile.utils

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import br.com.fiap25mob.mbamobile.data.db.CarsEntity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database

class FirebaseUtils {

    fun saveValue(id: Long, brand: String, model: String) {
        val database = Firebase.database
        val pref = App.context.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE)
        val myRef = pref.getString(USER_ID_KEY, "")?.let { database.getReference(it) }

        myRef!!.child(CAR_PATH).child(id.toString()).setValue(CarsEntity(id, brand, model))
    }

    fun deleteValue(id: Long) {
        val database = Firebase.database
        val pref = App.context.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE)
        val myRef = pref.getString(USER_ID_KEY, "")?.let { database.getReference(it) }

        myRef!!.child(CAR_PATH).child(id.toString()).removeValue()
    }

    fun readValues() : List<CarsEntity> {
        val list = mutableListOf<CarsEntity>()
        val database = Firebase.database
        val pref = App.context.getSharedPreferences(USER_ID_KEY, Context.MODE_PRIVATE)
        val myRef = pref.getString(USER_ID_KEY, "")?.let { database.getReference(it) }

        myRef!!.child(CAR_PATH).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    child.getValue(CarsEntity::class.java)?.let { list.add(it) }
                }
                Log.d(TAG, "Value has ${list.size} amount of elements on cloud")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        return list
    }
}