package ro.pub.cs.systems.eim.practicaltest01var06

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PracticalTest01Var06MainActivity : AppCompatActivity() {
    private lateinit var play_btn : Button
    private lateinit var random_btn : Button

    private lateinit var first_hold : CheckBox
    private lateinit var second_hold : CheckBox
    private lateinit var third_hold : CheckBox

    private lateinit var first_edit : EditText
    private lateinit var second_edit : EditText
    private lateinit var third_edit : EditText

    private var first_edit_val : String = "*"
    private var second_edit_val : String = "*"
    private var third_edit_val : String = "*"






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_practical_test01_var06_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        play_btn = findViewById<Button>(R.id.play_btn)
        random_btn = findViewById<Button>(R.id.random_btn)

        first_hold = findViewById<CheckBox>(R.id.first_hold)
        second_hold = findViewById<CheckBox>(R.id.second_hold)
        third_hold = findViewById<CheckBox>(R.id.third_hold)

        first_edit = findViewById<EditText>(R.id.first_edit)
        second_edit = findViewById<EditText>(R.id.second_edit)
        third_edit = findViewById<EditText>(R.id.third_edit)





    }
}