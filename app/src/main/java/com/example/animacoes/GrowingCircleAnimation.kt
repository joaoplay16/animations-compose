package com.example.animacoes

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
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
fun GrowingCircleAnimation(
    modifier: Modifier = Modifier,
    externalCircleColor: Color = Color.Black,
    innerCircleColor: Color = Color.Black,
    circleStroke: Float = 20f,
) {

    val canvasSize: Dp = 300.dp

    val animationDuration = 2000

    val externalCircle = remember { Animatable(initialValue = 160f)}
    val innerCircle = remember { Animatable(initialValue = 60f)}

    val alpha by rememberInfiniteTransition().animateFloat(
        1f,
        0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(key1 = externalCircle ){
        externalCircle.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration

                    360f at durationMillis with LinearOutSlowInEasing
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }

    LaunchedEffect(key1 = innerCircle ){
        innerCircle.animateTo(
            targetValue = 180f,
            animationSpec = infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration

                    180f at durationMillis with LinearOutSlowInEasing
                },
                repeatMode = RepeatMode.Restart
            )
        )
    }


    Box(modifier = modifier
        .size(canvasSize)
        .alpha(alpha)
        .scale(1f)
        .drawBehind {

            val externalCircleSize = Size(width = externalCircle.value, height = externalCircle.value)
            val innerCircleSize = Size(width = innerCircle.value, height = innerCircle.value)

            externalCircle(
                componentSize = externalCircleSize,
                color = externalCircleColor,
                stroke = circleStroke
            )

            innerCircle(
                componentSize = innerCircleSize,
                color = innerCircleColor,
                stroke = circleStroke
            )
        },
    )
}

fun DrawScope.externalCircle(
    componentSize: Size,
    color: Color,
    stroke: Float
){
    drawArc(
        size = componentSize,
        color = color,
        startAngle = 0f,
        sweepAngle =360f,
        useCenter = false,
        style = Stroke(
            width = stroke,
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

fun DrawScope.innerCircle(
    componentSize: Size,
    color: Color,
    stroke: Float
){
    drawArc(
        size = componentSize,
        color = color,
        startAngle = 0f,
        sweepAngle =360f,
        useCenter = false,
        style = Stroke(
            width = stroke,
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
fun GrowingCircleAnimationPreview() {
    AnimacoesTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
           GrowingCircleAnimation(
               externalCircleColor = Color(0xB37C0BA2),
               innerCircleColor = Color(0xB3B05BFF),
           )
        }
    }
}
