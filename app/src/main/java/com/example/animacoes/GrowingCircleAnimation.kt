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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animacoes.ui.theme.AnimacoesTheme

@Composable
fun GrowingCircleAnimation(
    color: Color = Color.Black
) {
    val canvasSize = 400.dp

    val circleSize = remember { Animatable(initialValue = 80f)}

    val animationDuration = 2000

    val alpha by rememberInfiniteTransition().animateFloat(
        1f,
        0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = animationDuration),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(key1 = circleSize ){
        circleSize.animateTo(
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


    Box(modifier = Modifier
        .size(canvasSize)
        .alpha(alpha)
        .drawBehind {

            val componentSize = Size(width = circleSize.value, height = circleSize.value)

            firstCircle(
                componentSize = componentSize,
                color = color
            )

        },
    )
}

fun DrawScope.firstCircle(
    componentSize: Size,
    color: Color
){
    drawArc(
        size = componentSize,
        color = color,
        startAngle = 0f,
        sweepAngle =360f,
        useCenter = false,
        style = Stroke(
            width = 30f,
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

           GrowingCircleAnimation()
        }
    }
}
