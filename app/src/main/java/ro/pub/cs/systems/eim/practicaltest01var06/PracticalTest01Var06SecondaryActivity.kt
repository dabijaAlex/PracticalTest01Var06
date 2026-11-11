package ro.pub.cs.systems.eim.practicaltest01var06

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var06SecondaryActivity : AppCompatActivity() {
    private lateinit var ok_btn : Button

    private lateinit var gained_text : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var06_secondary)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ok_btn = findViewById<Button>(R.id.ok_btn)
        gained_text = findViewById<TextView>(R.id.gained_text)


        val times_pressed = intent.getStringExtra("TIMES_PRESSED")!!

        val nr_1 = intent.getStringExtra("NR_1")!!
        val nr_2 = intent.getStringExtra("NR_2")!!
        val nr_3 = intent.getStringExtra("NR_3")!!

        val l = listOf(nr_1, nr_2, nr_3)
        var ok = 1
        for(elem in l) {
            if(elem == "*") {
                continue
            }
            for (elem2 in l) {
                if(elem2 == "*") {
                    continue
                }
                if(elem != elem2) {
                    ok = 0
                }
            }
        }

        var gained = 0

        if(ok == 0) {
            gained_text.visibility = View.GONE
        } else {
            if(times_pressed.toInt() == 0) {
                gained_text.visibility = View.VISIBLE
                gained_text.setText("Gained 100")
                gained = 100
            }
            if(times_pressed.toInt() == 1) {
                gained_text.visibility = View.VISIBLE
                gained_text.setText("Gained 50")
                gained = 50
            }
            if(times_pressed.toInt() == 2) {
                gained_text.visibility = View.VISIBLE
                gained_text.setText("Gained 10")
                gained = 10
            }
            if(times_pressed.toInt() == 3) {
                gained_text.visibility = View.VISIBLE
                gained_text.setText("Gained 0")
                gained = 0
            }
        }

        ok_btn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("ScoreResult", gained.toString())
            setResult(RESULT_OK, intent)
            finish()
        }


    }
}