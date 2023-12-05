package com.example.snowflakesk

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.AsyncTask
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

// TODO: сделать снежинки различных оттенков, для чего добавить поле "цвет"
data class Snowflake(var x: Float, var y: Float, val velocity: Float, val radius: Float, val color: Int)
lateinit var snow: Array<Snowflake>
val paint = Paint()
var h = 1000; var w = 1000 // значения по умолчанию, будут заменены в onLayout()

open class Snowflakes(ctx: Context) : View(ctx) {
    lateinit var moveTask: MoveTask
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // перерисовывает канву
        canvas.drawColor(Color.BLUE)

        for (s in snow) {
            paint.color = s.color
            canvas.drawCircle(s.x, s.y, s.radius, paint)
        }

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        h = bottom - top; w = right - left
        // TODO заполнить массив снежинками со случайными координатами, радиусами и скоростями
        val r = Random(0)
        r.nextFloat() // генерирует число от 0 до 1
        snow = Array(10) { Snowflake(x = r.nextFloat() * w, y =  r.nextFloat() * h,
            velocity = 15 + 10 * r.nextFloat(), radius = 30 + 20 * r.nextFloat(),
            Color.rgb(255F,255F,255F)) // TODO: сделать снежинки разного оттенка
        }
        Log.d("mytag", "snow: " + snow.contentToString())
    }

    fun moveSnowflakes() {
        // TODO: сделать падение с замедлением, к нижней границе экрана
        // скорость должна снижаться до нуля
        for (s in snow) {
            s.y += s.velocity
            if (s.y > h) {
                s.y -= h // если снежинка покинула экран, вычитаем высоту
            }
        }
        invalidate() // перерисовать
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        //moveSnowflakes()
        //invalidate() // перерисовать View
        moveTask = MoveTask(this)
        moveTask.execute(100)
        return false // защита от множественных срабатываний

    }
    class MoveTask(val s: Snowflakes) : AsyncTask<Int,Int,Int>() {
    // https://developer.alexanderklimov.ru/android/theory/asynctask.php
        override fun doInBackground(vararg params: Int?): Int {
            val delay = params[0] ?: 200 // если задерка не задана
            while (true) {
                Thread.sleep(delay.toLong())
                s.moveSnowflakes()
            }
            return 0
        }
    }
}