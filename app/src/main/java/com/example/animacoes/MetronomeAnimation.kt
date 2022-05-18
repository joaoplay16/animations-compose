package com.example.animacoes

import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animacoes.ui.theme.AnimacoesTheme

@Composable
fun MetronomeAnimation() {

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = -30f,
        targetValue = 30f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier
        .size(300.dp)
        .drawBehind {
            ponteiro(
                indicatorStrokeWidth = 25f,
                indicatorColor = Color.Black,
                translateValue = translateAnim.value,
            )
        }
    )
}

fun DrawScope.ponteiro(
    translateValue: Float,
    indicatorStrokeWidth: Float,
    indicatorColor: Color
){
    withTransform({
        translate(top = (size.height / 2f ) )
        rotate(translateValue)
    }){
        val angleOffset = size.maxDimension / 2.0f
        drawLine(
            color = indicatorColor,
            start = Offset(x = angleOffset, y = 0f),
            end = Offset(x = angleOffset, y = angleOffset),
            strokeWidth = indicatorStrokeWidth,
            cap = StrokeCap.Round
        )
    }

}

@Preview(showBackground = true)
@Composable
fun MetronomeAnimationPreview() {
    AnimacoesTheme{
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

        MetronomeAnimation()
        }
    }
}