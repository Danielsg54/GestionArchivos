package jose.daniel.gestionarchivos


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.documentfile.provider.DocumentFile

class MainActivity : AppCompatActivity() {

    private lateinit var btnPlay: Button
    private lateinit var btnStop: Button
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var nombre: TextView
    private lateinit var rootTree : DocumentFile
    private lateinit var mediaPlayer: MediaPlayer
    private var contador: Int = 1

    private var vect: ArrayList<DocumentFile> = arrayListOf()

    companion object {
        var OPEN_DIRECTORY_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nombre = findViewById(R.id.txtNombre)
        btnPlay = findViewById(R.id.btnPlay)
        btnStop = findViewById(R.id.btnStop)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)

        mediaPlayer = MediaPlayer()

        var intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        startActivityForResult(intent, OPEN_DIRECTORY_REQUEST_CODE)
    }

    private fun setOnClickListeners(context: Context) {

        btnPlay.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer()
            nombre.setText(vect[contador].name)
            mediaPlayer.setDataSource(this, vect[contador].uri)
            mediaPlayer.prepare()
            mediaPlayer.start()
            Toast.makeText(context, "Reproduciendo...", Toast.LENGTH_SHORT).show()
        }


        btnStop.setOnClickListener {
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer()
            Toast.makeText(context, "Parando...", Toast.LENGTH_SHORT).show()
        }


        btnPrev.setOnClickListener {
            nombre.setText(vect[contador--].name)
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(this, vect[contador--].uri)
            mediaPlayer.prepare()
            mediaPlayer.start()
            Toast.makeText(context, "Anterior...", Toast.LENGTH_SHORT).show()
        }

        btnNext.setOnClickListener{
            nombre.setText(vect[contador++].name)
            mediaPlayer.stop()
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(this, vect[contador++].uri)
            mediaPlayer.prepare()
            mediaPlayer.start()
            Toast.makeText(context, "Siguiente...", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == OPEN_DIRECTORY_REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                var directoryUri = data?.data ?: return

                rootTree = DocumentFile.fromTreeUri(this, directoryUri)!!

                for (file in rootTree!!.listFiles()) {

                    vect.add(file)

                }
            }
        }

        setOnClickListeners(this)
    }


}