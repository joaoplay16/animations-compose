package com.example.animacoes

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.animacoes.ui.theme.AnimacoesTheme

@Composable
fun LoadingCircleAnimation(
) {
    val canvasSize = 30.dp

    val animatable = remember { Animatable(initialValue = 0f)}
    LaunchedEffect(key1 = animatable ){

        animatable.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = 1000
                    0.0f at 0 with  LinearEasing
                    360f at durationMillis with LinearEasing
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    val sweepAngle = animatable.value

    Box(modifier = Modifier
        .size(canvasSize)
        .drawBehind {
            val componentSize = size / 1.25f
            val componentSize2 = componentSize / 2.3f

            externalCircle(
                componentSize = componentSize
            )

            internalCircle(
                componentSize = componentSize2, sweepAngle = sweepAngle
            )

        },
    )
}

fun DrawScope.internalCircle(
    componentSize: Size,
    sweepAngle: Float,

    ){
    drawArc(
        size = componentSize,
        color = Color.Black,
        startAngle = sweepAngle,
        sweepAngle = 1f, //abertura do angulo
        useCenter = false,
        style = Stroke(
            width = 17f,
            cap = StrokeCap.Round
        ),

        topLeft = Offset(
            //size é o canvasSize
            //centralizando
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.externalCircle(
    componentSize: Size,
    ){
    drawArc(
        size = componentSize,
        color = Color.Black,
        startAngle = 0f,
        sweepAngle = 360f, //abertura do angulo
        useCenter = false,
        style = Stroke(
            width = 10f,
            cap = StrokeCap.Round
        ),

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
fun LoadingCircleAnimationPreview() {
    AnimacoesTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {

        LoadingCircleAnimation()
        }
    }
}
