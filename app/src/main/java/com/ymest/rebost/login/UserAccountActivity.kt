package com.ymest.rebost.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import com.ymest.rebost.MainActivity
import com.ymest.rebost.R
import com.ymest.rebost.ajustes.Backup
import com.ymest.rebost.utils.Funcions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_user_account.*
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.round


class UserAccountActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var clienat: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN: Int = 124

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_account)
        mAuth = FirebaseAuth.getInstance()

        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)

        btnLogOut.setOnClickListener {
            muestraADCerrarSesion()

        }

        btnOlvidarCuenta.setOnClickListener {
            muestraADOlvidarCuenta()
        }

        if (mAuth.currentUser != null){
            tvnameUser.text = mAuth.currentUser?.displayName
            tvEmailUser.text = mAuth.currentUser?.email
            if(!mAuth.currentUser?.photoUrl.toString().isNullOrEmpty() && mAuth.currentUser?.photoUrl.toString() != "null") Picasso.get().load(mAuth.currentUser?.photoUrl).into(ivProfile)
            else ivProfile.setImageResource(R.drawable.ic_baseline_account_circle_24_primarycolor)
            //Picasso.get().load(mAuth.currentUser?.photoUrl).into(ivProfile)
            tvUid.text = mAuth.currentUser?.uid
            obtenerFechaArchivo()
        }

    }

    private fun muestraADCerrarSesion() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.cerrar_sesion))
        builder.setMessage("Se va a cerrar la sesión. Estás seguro?")
        builder.setPositiveButton(R.string.cerrar_sesion){ dialog, which ->
            mAuth.signOut()
            mGoogleSignInClient.signOut()
            this.finish()
        }
        builder.setNegativeButton("CANCELAR"){ dialog, which->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun obtenerFechaArchivo() {
        var storageRef = Firebase.storage.reference

        storageRef.child(mAuth.uid.toString() + "_Backup.csv").metadata.addOnSuccessListener {
            Log.d("FIREBASEERR", "Millis: " + it.creationTimeMillis)
            var kb = BigDecimal(it.sizeBytes.toDouble()/1024).setScale(2, RoundingMode.FLOOR)
            tvDataCopiaNuvol.text = Funcions.MillisToDate(it.creationTimeMillis) + " - " + kb + " KB"
            return@addOnSuccessListener
        }.addOnFailureListener {
            tvDataCopiaNuvol.text = "Nunca"
            return@addOnFailureListener
        }

    }

    private fun muestraADOlvidarCuenta() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.olvidar_Cuenta))
        builder.setMessage("La cuenta de usuario y las copias de seguridad almacenadas en la nube se van a eliminar (las copias de seguridad almacenadas en el teléfono se van a mantener). Estás seguro de que quieres eliminar la cuenta?")
        builder.setPositiveButton("ELIMINAR CUENTA"){ dialog, which ->
            eliminarbackups()
            reAuth()
            eliminarcuentaUsuario()
        }
        builder.setNegativeButton("CANCELAR"){ dialog, which->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun reAuth(){
        val user = mAuth.currentUser
        val acct = GoogleSignIn.getLastSignedInAccount(this)
        if (acct != null) {
            val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
            user!!.reauthenticate(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("REAUTH", "Reauthenticated.")
                }
            }
        }
    }

    private fun eliminarcuentaUsuario() {
        if(mAuth.currentUser != null){
            mAuth.currentUser!!.delete().addOnCompleteListener {
                //Log.d("ERRORELIMINAR", it.result.toString())
                if (it.isComplete) {
                    Snackbar.make(btnOlvidarCuenta,"Cuenta eliminada con éxito", Snackbar.LENGTH_SHORT).show()
                    mAuth.signOut()
                    mGoogleSignInClient.signOut()
                    intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    //Log.d("ERRORELIMINAR", it.result.toString())
                    Snackbar.make(btnOlvidarCuenta,"Error al eliminar cuenta", Snackbar.LENGTH_SHORT).show()
                }

            }
            /**/
        }
    }

    private fun eliminarbackups() {
        var backup: Backup = Backup(this)
        Log.d("FIREBASEERR", mAuth.uid.toString())
        backup.eliminarArchivosBackupFirebase()

    }
}