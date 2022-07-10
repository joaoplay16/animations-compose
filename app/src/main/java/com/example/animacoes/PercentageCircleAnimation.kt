package com.example.animacoes

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animacoes.ui.theme.AnimacoesTheme

@Composable
fun PercentageCircleAnimation(
    canvasSize: Dp = 300.dp,
    value: Int = 30
) {
    val percentage = (value.toFloat() / 60) * 100
    Log.d("PERCENT", "$percentage")
    val sweepAngle by animateFloatAsState(
        targetValue = ( 360/percentage).toFloat(),
        animationSpec = tween(5000)
    )

    Column(modifier = Modifier
        .size(canvasSize)
        .drawBehind {
            val componentSize = size / 1.25f

            foregroundIndicator(componentSize = componentSize
            , sweepAngle = sweepAngle)

        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {

    }
}

fun DrawScope.foregroundIndicator(
    componentSize: Size,
    sweepAngle: Float,

    ){
    drawArc(
        size = componentSize,
        color = Color.Blue,
        startAngle =270f,
        sweepAngle = sweepAngle, //abertura do circulo
        useCenter = true,

        topLeft = Offset(
            //size é o canvasSize
            //centralizando
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}


@Composable
@Preview(showBackground = true)
fun PercentageCircleAnimationPreview() {
    AnimacoesTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PercentageCircleAnimation(value = 10)
        }
    }
}
