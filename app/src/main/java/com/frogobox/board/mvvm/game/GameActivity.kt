package com.frogobox.board.mvvm.game

import android.app.AlertDialog
import com.frogobox.board.util.SingleFunc.deepCopy
import com.frogobox.board.util.SingleFunc.saveStatisticsToFile
import com.frogobox.board.util.SingleFunc.drawAllElements
import com.frogobox.board.util.SingleFunc.readStateFromFile
import com.frogobox.board.util.SingleFunc.readStatisticsFromFile
import com.frogobox.board.util.SingleFunc.saveStateToFile
import com.frogobox.board.util.SingleFunc.updateHighestNumber
import com.frogobox.board.util.SingleFunc.deleteStateFile
import com.frogobox.board.core.BaseActivity
import com.frogobox.board.model.GameState
import com.frogobox.board.model.GameStatistics
import android.widget.RelativeLayout
import android.os.Bundle
import com.frogobox.board.R
import com.frogobox.board.util.SingleConst
import android.view.View.OnTouchListener
import com.frogobox.board.mvvm.game.GameCallback.GameStateCallback
import android.util.DisplayMetrics
import com.frogobox.board.mvvm.game.GameCallback.GameScoreCallback
import android.content.DialogInterface
import android.content.res.Configuration
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.view.animation.LinearInterpolator
import android.view.ViewGroup.MarginLayoutParams
import com.frogobox.board.widget.Element
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : BaseActivity() {
    
    private var n = 4
    private var points = 0
    private var last_points = 0
    private var highestNumber = 0
    private var numberFieldSize = 0
    private var record: Long = 0
    
    private var startingTime = Calendar.getInstance().timeInMillis
    
    private var moved = false
    private var undo = false
    private var won2048 = false
    private var newGame = false
    private var gameOver = false
    private var firstTime = true
    private var saveState = true
    private var createNewGame = true
    private var animationActivated = true
    
    private var filename: String? = ""
    private var gameState: GameState? = null
    private var gameStatistics = GameStatistics(n)
    
    private var elements: Array<Array<Element?>?>? = null
    private var last_elements: Array<Array<Element?>?>? = null
    private var backgroundElements: Array<Array<Element?>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(this)
        animationActivated = sharedPref.getBoolean(SingleConst.Pref.PREF_ANIMATION_ACTIVATED, true)
        if (sharedPref.getBoolean(SingleConst.Pref.PREF_SETTINGS_DISPLAY, true)) window.addFlags(
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
        )
        saveState = true
        if (savedInstanceState == null) {
            val intent = intent
            if (firstTime && intent.getBooleanExtra(SingleConst.Extra.EXTRA_NEW, true)) {
                createNewGame = true
                firstTime = false
            }
        }
        initUI()
        setupShowAdsBanner(findViewById(R.id.ads_banner))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        createNewGame = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        save()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_settings) {
            return true
        } else if (id == android.R.id.home) {
            save()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        start()
    }

    public override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        save()
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        val item = menu.findItem(R.id.action_settings)
        item.isVisible = false
        super.onPrepareOptionsMenu(menu)
        return true
    }

    public override fun onPause() {
        super.onPause()
        Log.i("lifecycle", "pause")
        save()
    }

    private fun swipeListener(): OnTouchListener {
        return object : GameGesture(this@GameActivity) {
            override fun onSwipeTop(): Boolean {
                val temp = deepCopy(elements)
                val temp_points = points
                moved = false
                val s = Element(applicationContext)
                for (i in elements!!.indices) {
                    s.number = elements!![0]!![i]!!.number
                    s.posX = 0
                    s.posY = i
                    var j = 1
                    while (j < elements!![i]!!.size) {
                        if (elements!![j]!![i]!!.number != 0 && (s.number == 0 || s.number == elements!![j]!![i]!!.number)) {
                            moved = true
                            elements!![j]!![i]!!
                                .setNumber(s.number + elements!![j]!![i]!!.number)
                            elements!![s.posX]!![s.posY]!!.setNumber(0)
                            switchElementPositions(
                                elements!![j]!![i], elements!![s.posX]!![s.posY]
                            )
                            val z = elements!![j]!![i]
                            elements!![j]!![i] = elements!![s.posX]!![s.posY]
                            elements!![s.posX]!![s.posY] = z
                            if (s.number != 0) points += elements!![s.posX]!![s.posY]!!.number
                            if (s.number != 0) s.posX++
                            j = s.posX
                            s.number = elements!![j]!![i]!!.number
                        } else if (elements!![j]!![i]!!.number != 0) {
                            s.number = elements!![j]!![i]!!.number
                            s.posX = j
                            s.posY = i
                        }
                        j++
                    }
                }
                for (i in elements!!.indices) {
                    s.number = elements!![0]!![i]!!.number
                    s.posX = 0
                    s.posY = i
                    var j = 1
                    while (j < elements!![i]!!.size) {
                        if (elements!![j]!![i]!!.number != 0 && s.number == 0) {
                            moved = true
                            elements!![j]!![i]!!
                                .setNumber(s.number + elements!![j]!![i]!!.number)
                            elements!![s.posX]!![s.posY]!!.setNumber(0)
                            switchElementPositions(
                                elements!![j]!![i], elements!![s.posX]!![s.posY]
                            )
                            val z = elements!![j]!![i]
                            elements!![j]!![i] = elements!![s.posX]!![s.posY]
                            elements!![s.posX]!![s.posY] = z
                            if (s.number != 0) s.posX++
                            j = s.posX
                            s.number = elements!![j]!![i]!!.number
                        } else if (s.number != 0) {
                            s.number = elements!![j]!![i]!!.number
                            s.posX = j
                            s.posY = i
                        }
                        j++
                    }
                }
                if (moved) {
                    gameStatistics.addMoves(1)
                    last_points = temp_points
                    last_elements = temp
                    btn_undo!!.visibility = View.VISIBLE
                    undo = true
                }
                if (moved) gameStatistics.moveT()
                addNumber()
                setDPositions(animationActivated)
                updateGameState()
                return false
            }

            override fun onSwipeRight(): Boolean {
                val temp = deepCopy(elements)
                val temp_points = points
                moved = false
                val s = Element(applicationContext)
                for (i in elements!!.indices) {
                    s.number = elements!![i]!![elements!![i]!!.size - 1]!!.number
                    s.posX = i
                    s.posY = elements!![i]!!.size - 1
                    var j: Int = elements!![i]!!.size - 2
                    while (j >= 0) {
                        if (elements!![i]!![j]!!.number != 0 && (s.number == 0 || s.number == elements!![i]!![j]!!.number)) {
                            moved = true
                            elements!![i]!![j]!!
                                .setNumber(s.number + elements!![i]!![j]!!.number)
                            elements!![s.posX]!![s.posY]!!.setNumber(0)
                            switchElementPositions(
                                elements!![i]!![j], elements!![s.posX]!![s.posY]
                            )
                            val z = elements!![i]!![j]
                            elements!![i]!![j] = elements!![s.posX]!![s.posY]
                            elements!![s.posX]!![s.posY] = z
                            if (s.number != 0) points += elements!![s.posX]!![s.posY]!!.number
                            if (s.number != 0) s.posY--
                            j = s.posY
                            s.number = elements!![i]!![j]!!.number
                        } else if (elements!![i]!![j]!!.number != 0) {
                            s.number = elements!![i]!![j]!!.number
                            s.posX = i
                            s.posY = j
                        }
                        j--
                    }
                }
                for (i in elements!!.indices) {
                    s.number = elements!![i]!![elements!![i]!!.size - 1]!!.number
                    s.posX = i
                    s.posY = elements!![i]!!.size - 1
                    var j: Int = elements!![i]!!.size - 2
                    while (j >= 0) {
                        if (elements!![i]!![j]!!.number != 0 && s.number == 0) {
                            moved = true
                            elements!![i]!![j]!!
                                .setNumber(s.number + elements!![i]!![j]!!.number)
                            elements!![s.posX]!![s.posY]!!.setNumber(0)
                            switchElementPositions(
                                elements!![i]!![j], elements!![s.posX]!![s.posY]
                            )
                            val z = elements!![i]!![j]
                            elements!![i]!![j] = elements!![s.posX]!![s.posY]
                            elements!![s.posX]!![s.posY] = z
                            if (s.number != 0) s.posY--
                            j = s.posY
                            s.number = elements!![i]!![j]!!.number
                        } else if (s.number != 0) {
                            s.number = elements!![i]!![j]!!.number
                            s.posX = i
                            s.posY = j
                        }
                        j--
                    }
                }
                if (moved) {
                    gameStatistics.addMoves(1)
                    last_points = temp_points
                    last_elements = temp
                    btn_undo!!.visibility = View.VISIBLE
                    undo = true
                }
                if (moved) gameStatistics.moveR()
                addNumber()
                setDPositions(animationActivated)
                updateGameState()

                //es wurde nach rechts gewischt, hier den Code einfügen
                return false
            }

            override fun onSwipeLeft(): Boolean {
                val temp = deepCopy(elements)
                val temp_points = points
                moved = false
                val s = Element(applicationContext)
                for (i in elements!!.indices) {
                    s.number = elements!![i]!![0]!!.number
                    s.posX = i
                    s.posY = 0
                    var j = 1
                    while (j < elements!![i]!!.size) {
                        if (elements!![i]!![j]!!.number != 0 && (s.number == 0 || s.number == elements!![i]!![j]!!.number)) {
                            moved = true
                            elements!![i]!![j]!!
                                .setNumber(s.number + elements!![i]!![j]!!.number)
                            elements!![s.posX]!![s.posY]!!.setNumber(0)
                            switchElementPositions(
                                elements!![i]!![j], elements!![s.posX]!![s.posY]
                            )
                            val z = elements!![i]!![j]
                            elements!![i]!![j] = elements!![s.posX]!![s.posY]
                            elements!![s.posX]!![s.posY] = z
                            if (s.number != 0) points += elements!![s.posX]!![s.posY]!!.number
                            if (s.number != 0) s.posY++
                            j = s.posY
                            s.number = elements!![i]!![j]!!.number
                        } else if (elements!![i]!![j]!!.number != 0) {
                            s.number = elements!![i]!![j]!!.number
                            s.posX = i
                            s.posY = j
                        }
                        j++
                    }
                }
                for (i in elements!!.indices) {
                    s.number = elements!![i]!![0]!!.number
                    s.posX = i
                    s.posY = 0
                    var j = 1
                    while (j < elements!![i]!!.size) {
                        if (elements!![i]!![j]!!.number != 0 && s.number == 0) {
                            moved = true
                            elements!![i]!![j]!!
                                .setNumber(s.number + elements!![i]!![j]!!.number)
                            elements!![s.posX]!![s.posY]!!.setNumber(0)
                            switchElementPositions(
                                elements!![i]!![j], elements!![s.posX]!![s.posY]
                            )
                            val z = elements!![i]!![j]
                            elements!![i]!![j] = elements!![s.posX]!![s.posY]
                            elements!![s.posX]!![s.posY] = z
                            if (s.number != 0) s.posY++
                            j = s.posY
                            s.number = elements!![i]!![j]!!.number
                        } else if (s.number != 0) {
                            s.number = elements!![i]!![j]!!.number
                            s.posX = i
                            s.posY = j
                        }
                        j++
                    }
                }
                if (moved) {
                    gameStatistics.addMoves(1)
                    last_points = temp_points
                    last_elements = temp
                    btn_undo!!.visibility = View.VISIBLE
                    undo = true
                }
                if (moved) gameStatistics.moveL()
                addNumber()
                setDPositions(animationActivated)
                updateGameState()
                return false
            }

            override fun onSwipeBottom(): Boolean {
                val temp = deepCopy(elements)
                val temp_points = points
                moved = false
                val s = Element(applicationContext)
                for (i in elements!!.indices) {
                    s.number = elements!![elements!![i]!!.size - 1]!![i]!!.number
                    s.posX = elements!![i]!!.size - 1
                    s.posY = i
                    var j: Int = elements!![i]!!.size - 2
                    while (j >= 0) {
                        if (elements!![j]!![i]!!.number != 0 && (s.number == 0 || s.number == elements!![j]!![i]!!.number)) {
                            moved = true
                            elements!![j]!![i]!!
                                .setNumber(s.number + elements!![j]!![i]!!.number)
                            elements!![s.posX]!![s.posY]!!.setNumber(0)
                            switchElementPositions(
                                elements!![j]!![i], elements!![s.posX]!![s.posY]
                            )
                            val z = elements!![j]!![i]
                            elements!![j]!![i] = elements!![s.posX]!![s.posY]
                            elements!![s.posX]!![s.posY] = z
                            if (s.number != 0) points += elements!![s.posX]!![s.posY]!!.number
                            if (s.number != 0) s.posX--
                            j = s.posX
                            s.number = elements!![j]!![i]!!.number
                        } else if (elements!![j]!![i]!!.number != 0) {
                            s.number = elements!![j]!![i]!!.number
                            s.posX = j
                            s.posY = i
                        }
                        j--
                    }
                }
                for (i in elements!!.indices) {
                    s.number = elements!![elements!![i]!!.size - 1]!![i]!!.number
                    s.posX = elements!![i]!!.size - 1
                    s.posY = i
                    var j: Int = elements!![i]!!.size - 2
                    while (j >= 0) {
                        if (elements!![j]!![i]!!.number != 0 && s.number == 0) {
                            moved = true
                            elements!![j]!![i]!!
                                .setNumber(s.number + elements!![j]!![i]!!.number)
                            elements!![s.posX]!![s.posY]!!.setNumber(0)
                            switchElementPositions(
                                elements!![j]!![i], elements!![s.posX]!![s.posY]
                            )
                            val z = elements!![j]!![i]
                            elements!![j]!![i] = elements!![s.posX]!![s.posY]
                            elements!![s.posX]!![s.posY] = z
                            if (s.number != 0) s.posX--
                            j = s.posX
                            s.number = elements!![j]!![i]!!.number
                        } else if (s.number != 0) {
                            s.number = elements!![j]!![i]!!.number
                            s.posX = j
                            s.posY = i
                        }
                        j--
                    }
                }
                if (moved) {
                    gameStatistics.addMoves(1)
                    last_points = temp_points
                    last_elements = temp
                    btn_undo!!.visibility = View.VISIBLE
                    undo = true
                }
                if (moved) gameStatistics.moveD()
                addNumber()
                setDPositions(animationActivated)
                updateGameState()
                return false
            }

            override fun nichts(): Boolean {
                return false
            }
        }
    }

    private fun start() {
        Log.i("activity", "start")
        saveState = true
        val lp = number_field!!.layoutParams

        // setting squared Number Field
        if (number_field!!.height > number_field!!.width) lp.height =
            number_field!!.width else lp.width = number_field!!.height
        number_field!!.layoutParams = lp
        number_field_background!!.layoutParams = lp
        initGame()
        touch_field!!.setOnTouchListener(swipeListener())
        number_field!!.setOnTouchListener(swipeListener())
        for (i in elements!!.indices) {
            for (j in elements!![i]!!.indices) {
                elements!![i]!![j]!!.setOnTouchListener(swipeListener())
                backgroundElements!![i][j]!!.setOnTouchListener(swipeListener())
            }
        }
        if (newGame) {
            moved = true
            addNumber()
        }
        newGame = false
    }

    private fun initUI() {
        btn_restart!!.setOnClickListener { v: View? ->
            saveStatisticsToFile(this, gameStatistics)
            createNewGame()
            setupShowAdsInterstitial()
        }
        btn_undo!!.setOnClickListener { v: View? ->
            btn_undo!!.visibility = View.INVISIBLE
            if (undo && last_elements != null) {
                gameStatistics.undo()
                elements = last_elements
                points = last_points
                number_field!!.removeAllViews()
                points = last_points
                tv_points!!.text = points.toString()
                setDPositions(false)
                for (i in elements!!) {
                    for (j in i!!) {
                        j!!.visibility = View.INVISIBLE
                        number_field!!.addView(j)
                        j.drawItem()
                    }
                }
                for (i in elements!!.indices) {
                    for (j in elements!![i]!!.indices) {
                        elements!![i]!![j]!!.setOnTouchListener(swipeListener())
                        backgroundElements!![i][j]!!.setOnTouchListener(swipeListener())
                    }
                }
                updateGameState()
                drawAllElements(elements)
                number_field!!.refreshDrawableState()
            }
            undo = false
        }
    }

    private fun initState() {
        points = 0
        val intent = intent
        n = intent.getIntExtra(SingleConst.Extra.EXTRA_N, 4)
        newGame = intent.getBooleanExtra(SingleConst.Extra.EXTRA_NEW, true)
        filename = intent.getStringExtra(SingleConst.Extra.EXTRA_FILENAME)
        undo = intent.getBooleanExtra(SingleConst.Extra.EXTRA_UNDO, false)
        if (!newGame) {
            gameState = readStateFromFile(this, filename!!, n, object : GameStateCallback {
                override fun setupNewGame() {
                    newGame = true
                }
            })
            points = gameState!!.points
            last_points = gameState!!.last_points
        } else {
            gameState = GameState(n)
            newGame = true
        }
        elements = Array(n) { arrayOfNulls(n) }
        last_elements = Array(n) { arrayOfNulls(n) }
        backgroundElements = Array(n) { arrayOfNulls(n) }
        saveState = true
    }

    private fun initGame() {
        Log.i("activity", "initialize")
        if (intent.getIntExtra(SingleConst.Extra.EXTRA_N, 4) != n || createNewGame) {
            initState()
        }
        gameStatistics = readStatisticsFromFile(this, n)
        record = gameStatistics.record
        last_points = gameState!!.last_points
        createNewGame = false
        val metrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(metrics)
        val abstand = 10 * metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT
        numberFieldSize = number_field!!.width
        if (numberFieldSize > number_field!!.height) numberFieldSize = number_field!!.height
        val number_size = (numberFieldSize - abstand) / n - abstand
        tv_record!!.text = record.toString()
        tv_points!!.text = points.toString()
        if (undo) btn_undo!!.visibility = View.VISIBLE else btn_undo!!.visibility = View.INVISIBLE
        number_field_background!!.removeAllViews()
        number_field!!.removeAllViews()
        for (i in elements!!.indices) {
            for (j in elements!![i]!!.indices) {
                backgroundElements!![i][j] = Element(this)
                elements!![i]!![j] = Element(this)
                elements!![i]!![j]!!.setNumber(gameState!!.getNumber(i, j))
                elements!![i]!![j]!!.drawItem()
                if (elements!![i]!![j]!!.getNumber() >= SingleConst.Games.WINTHRESHOLD) won2048 =
                    true
                val lp = RelativeLayout.LayoutParams(number_size, number_size)
                lp.marginStart = abstand + j * (number_size + abstand)
                lp.topMargin = abstand + i * (number_size + abstand)
                elements!![i]!![j]!!.setDPosition(lp.marginStart, lp.topMargin)
                elements!![i]!![j]!!.layoutParams = lp
                backgroundElements!![i][j]!!.layoutParams = lp
                elements!![i]!![j]!!.updateFontSize()
                backgroundElements!![i][j]!!.layoutParams = lp
                backgroundElements!![i][j]!!.setOnTouchListener(swipeListener())
                elements!![i]!![j]!!.setOnTouchListener(swipeListener())
                number_field_background!!.addView(backgroundElements!![i][j])
                number_field!!.addView(elements!![i]!![j])
            }
        }
        last_elements = deepCopy(elements)
        if (undo) {
            for (i in elements!!.indices) {
                for (j in elements!![i]!!.indices) {
                    last_elements!![i]!![j]!!.setNumber(gameState!!.getLastNumber(i, j))
                }
            }
        }
        if (newGame) {
            moved = true
            addNumber()
            moved = true
            addNumber()
            newGame = false
        }
    }

    private fun save() {
        Log.i("saving", "save")
        if (!createNewGame) saveStateToFile(this, gameState!!, filename!!, saveState)
        gameStatistics.addTimePlayed(Calendar.getInstance().timeInMillis - startingTime)
        startingTime = Calendar.getInstance().timeInMillis
        saveStatisticsToFile(this, gameStatistics)
        firstTime = true
    }

    private fun createNewGame() {
        createNewGame = true
        intent.putExtra(SingleConst.Extra.EXTRA_NEW, true)
        number_field!!.removeAllViews()
        number_field_background!!.removeAllViews()
        initGame()
    }

    private fun updateGameState() {
        gameState = GameState(elements, last_elements)
        gameState!!.n = n
        gameState!!.points = points
        gameState!!.last_points = last_points
        gameState!!.undo = undo
        updateHighestNumber(elements, highestNumber, object : GameScoreCallback {
            override fun setupHighScore(highScore: Int) {
                highestNumber = highScore
                gameStatistics.setHighestNumber(highestNumber.toLong())
            }
        })
        check2048()
    }

    private fun switchElementPositions(e1: Element?, e2: Element?) {
        val i = e1!!.getdPosX()
        val j = e1.getdPosY()
        e1.animateMoving = true
        e1.setDPosition(e2!!.getdPosX(), e2.getdPosY())
        e2.animateMoving = false
        e2.setDPosition(i, j)
    }

    private fun check2048() {
        if (!won2048) for (element in elements!!) {
            for (value in element!!) {
                if (value!!.number == SingleConst.Games.WINTHRESHOLD) {
                    saveStatisticsToFile(this@GameActivity, gameStatistics)
                    AlertDialog.Builder(this)
                        .setTitle(this.resources.getString(R.string.Titel_V_Message))
                        .setMessage(this.resources.getString(R.string.Winning_Message))
                        .setNegativeButton(this.resources.getString(R.string.No_Message)) { dialog: DialogInterface?, which: Int ->
                            onBackPressed()
                            setupShowAdsInterstitial()
                        }
                        .setPositiveButton(this.resources.getString(R.string.Yes_Message)) { dialog: DialogInterface?, which: Int -> setupShowAdsInterstitial() }
                        .setCancelable(false)
                        .create().show()
                    won2048 = true
                }
            }
        }
    }

    private fun setDPositions(animation: Boolean) {
        var MOVINGSPEED = SingleConst.Games.INIT_MOVINGSPEED
        var scale = true
        if (!animation) {
            MOVINGSPEED = 1
            scale = false
        }
        for (i in elements!!) {
            for (j in i!!) {
                if (j!!.dPosX.toFloat() != j.x) {
                    if (j.animateMoving && animation) {
                        if (j.number != j.dNumber) j.animate().x(j.dPosX.toFloat())
                            .setDuration(MOVINGSPEED).setStartDelay(0)
                            .setInterpolator(LinearInterpolator())
                            .setListener(MovingListener(j, scale)).start() else j.animate().x(
                            j.dPosX.toFloat()
                        ).setDuration(MOVINGSPEED).setStartDelay(0)
                            .setInterpolator(LinearInterpolator())
                            .setListener(MovingListener(j, false)).start()
                    } else {
                        if (!animation) {
                            val lp1 = j.layoutParams as MarginLayoutParams
                            lp1.leftMargin = j.dPosX
                            j.layoutParams = lp1
                            j.drawItem()
                        } else j.animate().x(j.dPosX.toFloat()).setDuration(0)
                            .setStartDelay(MOVINGSPEED).setInterpolator(LinearInterpolator())
                            .setListener(MovingListener(j, false)).start()
                    }
                }
                if (j.dPosY.toFloat() != j.y) {
                    if (j.animateMoving && animation) {
                        if (j.number != j.dNumber) j.animate().y(j.dPosY.toFloat())
                            .setDuration(MOVINGSPEED).setStartDelay(0)
                            .setInterpolator(LinearInterpolator())
                            .setListener(MovingListener(j, scale)).start() else j.animate().y(
                            j.dPosY.toFloat()
                        ).setDuration(MOVINGSPEED).setStartDelay(0)
                            .setInterpolator(LinearInterpolator())
                            .setListener(MovingListener(j, false)).start()
                    } else {
                        if (!animation) {
                            val lp1 = j.layoutParams as MarginLayoutParams
                            lp1.topMargin = j.dPosY
                            j.layoutParams = lp1
                            j.drawItem()
                        } else j.animate().y(j.dPosY.toFloat()).setDuration(0)
                            .setStartDelay(MOVINGSPEED).setInterpolator(LinearInterpolator())
                            .setListener(MovingListener(j, false)).start()
                    }
                }
            }
        }
    }

    private fun addNumber() {
        if (points > record) {
            record = points.toLong()
            gameStatistics.record = record
            tv_record!!.text = record.toString()
        }
        if (moved) {
            gameOver = false
            moved = false
            tv_points!!.text = points.toString()
            val empty_fields = arrayOfNulls<Element>(n * n)
            var counter = 0
            for (element in elements!!) {
                for (value in element!!) {
                    if (value!!.number == 0) {
                        empty_fields[counter++] = value
                    }
                }
            }
            if (counter > 0) {
                val index = (Math.random() * counter).toInt()
                var number = 2
                if (Math.random() > SingleConst.Games.PROPABILITYFORTWO) number = 4
                empty_fields[index]!!.setNumber(number)
                empty_fields[index]!!.drawItem()
                if (animationActivated) {
                    empty_fields[index]!!.alpha = 0f
                    empty_fields[index]!!.animate().alpha(1f).setInterpolator(LinearInterpolator())
                        .setStartDelay(SingleConst.Games.INIT_MOVINGSPEED)
                        .setDuration(SingleConst.Games.INIT_ADDINGSPEED).start()
                }
                if (counter == 1) {
                    gameOver = true
                    for (i in elements!!.indices) {
                        for (j in elements!![i]!!.indices) {
                            if (i + 1 < elements!!.size && elements!![i]!![j]!!.number == elements!![i + 1]!![j]!!.number || j + 1 < elements!![i]!!.size && elements!![i]!![j]!!.number == elements!![i]!![j + 1]!!.number) {
                                gameOver = false
                                break
                            }
                        }
                    }
                }
            }
            updateGameState()
            if (gameOver) {
                gameOver()
            }
        }
        Log.i(
            "number of elements",
            "" + number_field!!.childCount + ", " + number_field_background!!.childCount
        )
    }

    private fun gameOver() {
        Log.i("record", "" + record + ", " + gameStatistics.record)
        saveStatisticsToFile(this, gameStatistics)
        AlertDialog.Builder(this)
            .setTitle(this.resources.getString(R.string.Titel_L_Message, points))
            .setMessage(this.resources.getString(R.string.Lost_Message, points))
            .setNegativeButton(this.resources.getString(R.string.No_Message)) { dialog: DialogInterface?, which: Int ->
                createNewGame = true
                intent.putExtra(SingleConst.Extra.EXTRA_NEW, true)
                initGame()
                deleteStateFile(this@GameActivity, filename!!)
                saveState = false
                onBackPressed()
                setupShowAdsInterstitial()
            }
            .setPositiveButton(this.resources.getString(R.string.Yes_Message)) { dialog: DialogInterface?, which: Int ->
                createNewGame()
                setupShowAdsInterstitial()
            }
            .setCancelable(false)
            .create().show()
        Log.i("record", "danach")
    }
}