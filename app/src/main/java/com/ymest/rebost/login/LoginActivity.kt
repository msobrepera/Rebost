package com.ymest.rebost.login

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.ymest.rebost.MainActivity
import com.ymest.rebost.R
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.properties.Delegates


class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"
    //global variables
    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var mProgressBar: ProgressDialog



    //Creamos nuestra variable de autenticación firebase
    private lateinit var mAuth: FirebaseAuth
    //Creamos nuestra variable de autenticación firebase con Google
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var googleSignInOptions: GoogleSignInOptions
    private val RC_SIGN_IN: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initViews()
        events()
        instanciasGoogle()
    }

    private fun instanciasGoogle() {
        googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions)
    }

    private fun events() {
        btnLogin.setOnClickListener {
            loginUser()
        }
        btnCrearCuenta.setOnClickListener {
            registrarUsuario()
        }
        txtForgotPassword.setOnClickListener {
            forgotPassword()
        }
        btnSignInGoogle.setOnClickListener {
            signInGoogle()
        }
    }

    private fun signInGoogle() {
        val intent = mGoogleSignInClient.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //Inicio de sesion correcto, autenticamos con firebase
                val account= task.getResult(ApiException::class.java)
                firebaseAuthGooogle(account)
            } catch (e: ApiException) {
                Toast.makeText(this, getString(R.string.error_conexion), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthGooogle(account: GoogleSignInAccount) {
        val builder = AlertDialog.Builder(this)
        val dialog: AlertDialog = builder.create()
        dialog.show()

        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if(task.isSuccessful) {
                dialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else {
                dialog.dismiss()
                Toast.makeText(this, getString(R.string.error_login), Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun forgotPassword() {
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        startActivity(intent)
    }

    private fun registrarUsuario() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun initViews() {
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        mProgressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()
    }

    private fun loginUser() {
        //Obtenemos usuario y contraseña
        email = etEmail.text.toString()
        password = etPassword.text.toString()
        //Verificamos que los campos no este vacios
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            //Mostramos el progressdialog
            mProgressBar.setMessage("Registering User...")
            mProgressBar.show()

            //Iniciamos sesión con el método signIn y enviamos usuario y contraseña
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {

                    //Verificamos que la tarea se ejecutó correctamente
                        task ->
                    if (task.isSuccessful) {
                        // Si se inició correctamente la sesión vamos a la vista Home de la aplicación
                        goHome() // Creamos nuestro método en la parte de abajo
                    } else {
                        // sino le avisamos el usuairo que orcurrio un problema
                        mProgressBar.dismiss()
                        etEmail.setError("Usuario y/o contraseña incorrectos")
                        etPassword.setError("Usuario y/o contraseña incorrectos")
                        Toast.makeText(this, "Usuario y/o contraseña incorrectos. Inténtalo de nuevo", Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goHome() {
        //Ocultamos el progress
        mProgressBar.hide()
        //Nos vamos a Home
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


}