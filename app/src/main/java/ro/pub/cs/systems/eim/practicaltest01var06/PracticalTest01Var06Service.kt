package ro.pub.cs.systems.eim.practicaltest01var06
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import java.util.Date

class PracticalTest01Var06Service : Service() {
    private var processingThread: ProcessingThread? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // get data for intent
        val score = intent?.getIntExtra("first_score", 0) ?: 0
//        val second = intent?.getIntExtra("secondNumber", 0) ?: 0

        // start thread
        processingThread = ProcessingThread(this, score)
        processingThread?.start()

//        set_score(score)

        return START_REDELIVER_INTENT
    }

//    static fun set_score(score: Int) {
//        processingThread!!.set_score(score)
//    }



    // make this null
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        processingThread?.stopThread()
        super.onDestroy()
    }
}

class ProcessingThread(private val context: Context, private val score:Int) : Thread() {
    private var isRunning = true
    private val actions = listOf(
        "ro.pub.cs.systems.eim.action1",
    )

//    private     var score = 0

    override fun run() {
        while (isRunning) {
            // set data to display
            val date = Date().toString()


                // create and send message
//                try {
//                    sleep(2000)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
            try {
                sleep(2000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            val intent = Intent()
            intent.action = actions.random() // gets a random action from one of those three
            intent.putExtra("message", "Time: $date, Victory $score")
            context.sendBroadcast(intent)

            // sleep
        }
    }

//    fun set_score(score1: Int) {
//        score = score1
//    }

    fun stopThread() {
        isRunning = false
    }
}