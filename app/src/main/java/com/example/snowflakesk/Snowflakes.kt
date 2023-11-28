package com.example.snowflakesk

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.view.View

data class Snowflake(var x: Float, var y: Float, val velocity: Float, val radius: Float)
lateinit var snow: Array<Snowflake>

class Snowflakes(ctx: Context) : View(ctx) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // перерисовывает канву
        canvas.drawColor(Color.BLUE)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        // TODO заполнить массив снежинками со случайными координатами, радиусами и скоростями
        snow = Array(10, {  Snowflake(0F,0F,0F,0F)})
    }
}