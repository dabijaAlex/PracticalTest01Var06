package ro.pub.cs.systems.eim.practicaltest01var06

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.random.Random

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

    private var total_score = 0





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

        random_btn.setOnClickListener {
            val text_options_list = listOf("1", "2", "3", "*")

            if(!first_hold.isChecked) {
                val randomIndex1 = Random.nextInt(text_options_list.size);
                val randomElement1 = text_options_list[randomIndex1]
                first_edit.setText(randomElement1.toString())
                first_edit_val = randomElement1.toString()
            }
            if(!second_hold.isChecked) {
                val randomIndex2 = Random.nextInt(text_options_list.size);
                val randomElement2 = text_options_list[randomIndex2]
                second_edit.setText(randomElement2.toString())
                second_edit_val = randomElement2.toString()
            }

            if(!third_hold.isChecked) {
                val randomIndex3 = Random.nextInt(text_options_list.size);
                val randomElement3 = text_options_list[randomIndex3]
                third_edit.setText(randomElement3.toString())
                third_edit_val = randomElement3.toString()
            }

            Log.i("task_b_log", "$first_edit_val $second_edit_val $third_edit_val")
        }

        val contactsLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    total_score += result.data!!.getStringExtra("ScoreResult")!!.toInt()
                    Log.i("task_b_log", "total score is $total_score")
                    Toast.makeText(
                        this, "total score is $total_score",
                        Toast.LENGTH_SHORT
                    ).show()
                    checkAndStartService()

                }
                if (result.resultCode == RESULT_CANCELED) {
                    Toast.makeText(
                        this, "CANCELED",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        play_btn.setOnClickListener {
            var nr_hold = 0
            if(first_hold.isChecked) {
                nr_hold++
            }
            if(second_hold.isChecked) {
                nr_hold++
            }
            if(third_hold.isChecked) {
                nr_hold++
            }


            val intent = Intent(this, PracticalTest01Var06SecondaryActivity::class.java).apply {
                putExtra("TIMES_PRESSED", (nr_hold).toString())

                putExtra("NR_1", (first_edit_val).toString())
                putExtra("NR_2", (second_edit_val).toString())
                putExtra("NR_3", (third_edit_val).toString())

            }
            contactsLauncher.launch(intent)
        }



    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("SCORE", total_score.toString())
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.getString("SCORE")?.let { times ->
            total_score = times.toInt()
        }

//        savedInstanceState.getString("SECOND_EDIT")?.let { times ->
//            second_edit.setText(times)
//            times_clicked_second = times.toInt()
//        }
    }


    // service with receiver piece

    var serviceStarted = false
    private val messageBroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("PracticalTest01Broadcast", intent?.getStringExtra("message") ?: "Empty")



            // this is used to see which action ran exactly
//            when (intent?.action) {
//                "ro.pub.cs.systems.eim.action1" -> Log.d("PracticalTest01Broadcast", "Action 1: $message")
//                "ro.pub.cs.systems.eim.action2" -> Log.d("PracticalTest01Broadcast", "Action 2: $message")
//                "ro.pub.cs.systems.eim.action3" -> Log.d("PracticalTest01Broadcast", "Action 3: $message")
//                else -> Log.d("PracticalTest01Broadcast", "Unknown action: ${intent?.action}")
//            }
        }
    }

    private val intentFilter = IntentFilter().apply {
        addAction("ro.pub.cs.systems.eim.action1")
    }



    private fun checkAndStartService() {
        if (total_score > 110 && !serviceStarted) {
            val intent = Intent(this, PracticalTest01Var06Service::class.java)
            intent.putExtra("first_score", total_score)
            startService(intent)
            serviceStarted = true
        }
    }



    // right befor activity becomes interactive
    override fun onResume() {
        super.onResume()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(messageBroadcastReceiver, intentFilter, RECEIVER_EXPORTED)
        } else {
            registerReceiver(messageBroadcastReceiver, intentFilter)
        }
    }

    //It runs as soon as the activity stops being in the foreground:
    override fun onPause() {
        unregisterReceiver(messageBroadcastReceiver)
        super.onPause()
    }


    override fun onDestroy() {
        stopService(Intent(this, PracticalTest01Var06Service::class.java))
        super.onDestroy()
    }
}


