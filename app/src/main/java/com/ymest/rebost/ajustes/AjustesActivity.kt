package com.ymest.rebost.ajustes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ymest.rebost.R
import com.ymest.rebost.ubicacions.UbicacionsActivity
import com.ymest.rebost.utils.Constants
import kotlinx.android.synthetic.main.activity_ajustes.*

class AjustesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        cvUbicaciones.setOnClickListener {
            val intent = Intent(this, UbicacionsActivity::class.java)
            intent.putExtra("VEDE", Constants.UBICACIONS)
            startActivity(intent)
        }
        cvListas.setOnClickListener {
            val intent = Intent(this, UbicacionsActivity::class.java)
            intent.putExtra("VEDE", Constants.LLISTES)
            startActivity(intent)
        }
        cvBackup.setOnClickListener{
            val intent = Intent(this, BackupActivity::class.java)
            intent.putExtra("VEDE", Constants.LLISTES)
            startActivity(intent)
        }

    }


}