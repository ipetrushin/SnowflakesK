package com.example.snowflakesk

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random

// TODO: сделать снежинки различных оттенков, для чего добавить поле "цвет"
data class Snowflake(var x: Float, var y: Float, val velocity: Float, val radius: Float)
lateinit var snow: Array<Snowflake>
val paint = Paint()
var h = 1000; var w = 1000 // значения по умолчанию, будут заменены в onLayout()

class Snowflakes(ctx: Context) : View(ctx) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // перерисовывает канву
        canvas.drawColor(Color.BLUE)
        paint.setColor(Color.WHITE)
        for (s in snow) {
            canvas.drawCircle(s.x, s.y, s.radius, paint)
        }

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        h = bottom - top; w = right - left
        // TODO заполнить массив снежинками со случайными координатами, радиусами и скоростями
        val r = Random(0)
        r.nextFloat() // генерирует число от 0 до 1
        snow = Array(10) { Snowflake(r.nextFloat() * w, r.nextFloat() * h,
            15 + 10 * r.nextFloat(), 30 + 20 * r.nextFloat()) }
        Log.d("mytag", "snow: " + snow.contentToString())
    }

    fun MoveSnowflakes() {
        // TODO: сделать падение с замедлением, к нижней границе экрана
        // скорость должна снижаться до нуля
        for (s in snow) {
            s.y += s.velocity
            if (s.y > h) {
                s.y -= h // если снежинка покинула экран, вычитаем высоту
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        MoveSnowflakes()
        invalidate() // перерисовать View
        return true

    }
}