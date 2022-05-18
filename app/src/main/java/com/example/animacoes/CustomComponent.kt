package com.example.animacoes

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.example.animacoes.ui.theme.AnimacoesTheme

@Composable
fun CustomComponent(
    canvasSize: Dp = 300.dp,
    indicatorValue: Int = 0,
    maxIndicatorValue: Int = 100,
    backgroundIndicatorColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.1f),
    backgroundIndicatorWidth: Float = 100f,
    foregroundIndicatorColor: Color = MaterialTheme.colors.primary,
    foregroundIndicatorStrokeWidth: Float = 100f,
    bigTextFontSize: TextUnit = MaterialTheme.typography.h3.fontSize,
    bigTextColor: Color = MaterialTheme.colors.onSurface,
    bigTextSufix: String = "GB",
    smallText: String = "Remaining",
    smallTextFontSize: TextUnit = MaterialTheme.typography.h6.fontSize,
    smallTextColor: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
) {

    var allowedIndicatorValue by remember {
        mutableStateOf(maxIndicatorValue)
    }
    allowedIndicatorValue = if (indicatorValue <= maxIndicatorValue){
        indicatorValue
    }else{
        maxIndicatorValue
    }

    var animatedIndicatorValue by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = allowedIndicatorValue ){
        animatedIndicatorValue = allowedIndicatorValue.toFloat()
    }

    val percentage = (animatedIndicatorValue / 0) * 100

    val sweepAngle by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(),
        animationSpec = tween(1000)
    )

    val receivedValue by animateIntAsState(
        targetValue = allowedIndicatorValue,
        animationSpec = tween(1000)
    )

    val animatedBigTextColor by animateColorAsState(
        targetValue = if (allowedIndicatorValue == 0)
            MaterialTheme.colors.onSurface.copy(alpha = 0.3f)
        else
            bigTextColor,
        animationSpec = tween(1000)

    )

    Column(modifier = Modifier
        .size(canvasSize)
        .drawBehind {
            val componentSize = size / 1.25f

            backgroundIndicator(
                componentSize = componentSize,
                indicatorColor = backgroundIndicatorColor,
                indicatorStrokeWidth = backgroundIndicatorWidth
            )
            foregroundIndicator(
                sweepAngle = sweepAngle,
                componentSize = componentSize,
                indicatorColor = foregroundIndicatorColor,
                indicatorStrokeWidth = foregroundIndicatorStrokeWidth
            )
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EmbeddedElements(
                bigText = receivedValue,
                bigTextFontSize = bigTextFontSize,
                bigTextColor = animatedBigTextColor,
                bigTextSufix = bigTextSufix,
                smallText = smallText,
                smallTextColor = smallTextColor,
                smallTextFontSize = smallTextFontSize
            )
    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
){
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = 240f,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            //size é o canvasSize
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        ).also {
            Log.d("CANVAS", "size ${size.width} - componentSize ${componentSize.width} result ${(size.width - componentSize.width) / 2f}")
        }
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float
){
    drawArc(
        size = componentSize,
        color = indicatorColor,
        startAngle = 150f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(
            width = indicatorStrokeWidth,
            cap = StrokeCap.Round
        ),
        topLeft = Offset(
            //size é o canvasSize
            x = (size.width - componentSize.width) / 2f,
            y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
fun EmbeddedElements(
    bigText: Int,
    bigTextFontSize: TextUnit,
    bigTextColor: Color,
    bigTextSufix: String,
    smallText: String,
    smallTextColor: Color,
    smallTextFontSize: TextUnit
){
    Text(
        text = smallText,
        color = smallTextColor,
        fontSize = smallTextFontSize,
        textAlign = TextAlign.Center
    )
    Text(
        text = "$bigText ${bigTextSufix.take(2)}",
        color = bigTextColor,
        fontSize = bigTextFontSize,
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold
    )
}

@Composable
@Preview(showBackground = true)
fun CustomComponentPreview() {
    AnimacoesTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var value by remember { mutableStateOf(0)}

            CustomComponent(
                indicatorValue = value
            )

            TextField(
                value = value.toString(),
                onValueChange = {
                    value = if(it.isNotEmpty()){
                        it.toInt()
                    }else {
                        0
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType =  KeyboardType.Number
                )
            )

            Slider(
                value = value.toFloat(),
                onValueChange = { value = it.toInt()},
                steps = 10,
                valueRange = 0f..100f
            )
        }
    }
}
