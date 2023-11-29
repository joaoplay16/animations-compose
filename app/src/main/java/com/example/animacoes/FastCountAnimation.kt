package com.example.animacoes

import android.content.res.Configuration
import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.animacoes.ui.theme.AnimacoesTheme

@Composable
fun FastCountAnimation(
    targetCount: Float,
    format: String = "%.1f",
    textStyle: TextStyle = TextStyle(
        fontSize = 46.sp,
    )
) {
    val animatedCount by animateFloatAsState(
        targetValue = targetCount,
        label = "",
        animationSpec = tween(
            3500,
            easing = Ease
        )
    )

    Text(
        text = String.format(
            format,
            animatedCount
        ),
        style = textStyle,
        maxLines = 1,
        softWrap = false
    )
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun FastCountAnimationPreview() {
    AnimacoesTheme {
        Surface {
            var count by remember {
                mutableFloatStateOf(100f)
            }

            LaunchedEffect(
                key1 = count,
                block = {
                    while (count > 0) count--
                }
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                FastCountAnimation(
                    targetCount = count
                )
            }
        }
    }
}