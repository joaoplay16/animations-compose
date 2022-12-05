package com.example.animacoes

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
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
fun BouncingWavesAnimation(
    modifier: Modifier = Modifier,
){

    val canvasSize: Dp = 300.dp

    val firstStickHeight = remember { Animatable(initialValue = 160f) }
    val secondStickHeight = remember { Animatable(initialValue = 160f) }
    val thirdStickHeight = remember { Animatable(initialValue = 160f) }
    val fourthStickHeight = remember { Animatable(initialValue = 160f) }

    Box(modifier = modifier
        .size(canvasSize)
        .scale(1f)
        .drawBehind {

            val firstStickSize = Size(width = 1f, height = firstStickHeight.value)
            val secondStickSize = Size(width = 1f, height = secondStickHeight.value)
            val thirdStickSize = Size(width = 1f, height = thirdStickHeight.value)
            val fourthStickSize = Size(width = 1f, height = fourthStickHeight.value)

            //FIRST
            stick(
                componentSize = firstStickSize,
                color = Color.Magenta,
                stroke = 30f,
                offset = Offset(
                    x = (size.width / 2.46f),
                    y = (size.height - firstStickSize.height) / 2f
                )
            )

            //SECOND
            stick(
                componentSize = secondStickSize,
                color = Color.Green,
                stroke = 30f,
                offset = Offset(
                    x = (size.width / 2.08f),
                    y = (size.height - secondStickSize.height) / 2f
                )
            )

            // THIRD
            stick(
                componentSize = thirdStickSize,
                color = Color.Cyan,
                stroke = 30f,
                offset = Offset(
                    x = (size.width / 1.8f),
                    y = (size.height - thirdStickSize.height) / 2f
                )
            )

            // FOURTH
            stick(
                componentSize = fourthStickSize,
                color = Color.Blue,
                stroke = 30f,
                offset = Offset(
                    x = (size.width / 1.60f),
                    y = (size.height - fourthStickSize.height) / 2f
                )
            )
        },
    )
}

fun DrawScope.stick(
    componentSize: Size,
    color: Color,
    stroke: Float,
    offset: Offset
){
    drawRoundRect(
        size = componentSize,
        color = color,
        cornerRadius = CornerRadius(1f, 1f),
        style = Stroke(
            width = stroke,
            cap = StrokeCap.Round
        ),
        topLeft = offset
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewBouncingWavesAnimation() {
    AnimacoesTheme {
        Surface {
            BouncingWavesAnimation()
        }
    }
}