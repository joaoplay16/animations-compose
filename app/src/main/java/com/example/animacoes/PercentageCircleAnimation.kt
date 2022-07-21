package com.example.animacoes

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animacoes.ui.theme.LittleGreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.onEach

@Composable
fun CompassAnimation(
    canvasSize: Dp = 300.dp,
    value: Int = 23,
    maxValue: Int = 60
) {
    val percentage = (value.toFloat() / maxValue.toFloat())
    Log.d("PERCENT", "percentage $percentage sweep ${percentage * 360}")
    val sweepAngle by animateFloatAsState(
        targetValue = ( percentage * 360  ),
        animationSpec = tween(1000)
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
        color = LittleGreen,
        startAngle =270f,
        sweepAngle = sweepAngle, //abertura do angulo
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
    val oneMinute = 60

    var second by remember {
        mutableStateOf(0)
    }
    LaunchedEffect(key1 = null,  ){
        (oneMinute downTo 0)
            .asSequence()
            .asFlow()

            .onEach {
            }.collect{
                delay(1000)
                Log.d("FLOW", "$it")
                second = it as Int

            }
    }
    CompassAnimation(
        canvasSize= 200.dp,
        value = second,
        maxValue = oneMinute
    )
}
