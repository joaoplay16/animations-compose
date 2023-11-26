package com.example.animacoesimport androidx.compose.animation.core.Animatableimport androidx.compose.animation.core.LinearOutSlowInEasingimport androidx.compose.animation.core.RepeatModeimport androidx.compose.animation.core.animateFloatimport androidx.compose.animation.core.infiniteRepeatableimport androidx.compose.animation.core.keyframesimport androidx.compose.animation.core.rememberInfiniteTransitionimport androidx.compose.animation.core.tweenimport androidx.compose.foundation.layout.Arrangementimport androidx.compose.foundation.layout.Boximport androidx.compose.foundation.layout.Columnimport androidx.compose.foundation.layout.fillMaxSizeimport androidx.compose.foundation.layout.sizeimport androidx.compose.runtime.Composableimport androidx.compose.runtime.LaunchedEffectimport androidx.compose.runtime.getValueimport androidx.compose.runtime.rememberimport androidx.compose.ui.Alignmentimport androidx.compose.ui.Modifierimport androidx.compose.ui.draw.alphaimport androidx.compose.ui.draw.drawBehindimport androidx.compose.ui.draw.scaleimport androidx.compose.ui.geometry.Sizeimport androidx.compose.ui.graphics.Colorimport androidx.compose.ui.graphics.drawscope.DrawScopeimport androidx.compose.ui.tooling.preview.Previewimport androidx.compose.ui.unit.Dpimport androidx.compose.ui.unit.dpimport com.example.animacoes.ui.theme.AnimacoesTheme@Composablefun CirclePulseAnimation(    modifier: Modifier = Modifier,    pulseCircleColor: Color = Color.Black,) {    val canvasSize: Dp = 300.dp    val animationDuration = 1300    val pulseCircle = remember { Animatable(initialValue = canvasSize.value * 0.8f) }    val alpha by rememberInfiniteTransition(label = "").animateFloat(        1f,        0f,        animationSpec = infiniteRepeatable(            animation = tween(durationMillis = animationDuration),            repeatMode = RepeatMode.Restart        ),        label = "float"    )    LaunchedEffect(key1 = pulseCircle) {        pulseCircle.animateTo(            targetValue = canvasSize.value,            animationSpec = infiniteRepeatable(                animation = keyframes {                    durationMillis = animationDuration                    canvasSize.value at durationMillis with LinearOutSlowInEasing                },                repeatMode = RepeatMode.Restart            )        )    }    Box(        modifier = modifier            .size(canvasSize)            .alpha(alpha)            .scale(1f)            .drawBehind {                pulseCircle(                    radius = pulseCircle.value,                    color = pulseCircleColor,                )            },    )}fun DrawScope.pulseCircle(    radius: Float,    color: Color,) {    drawCircle(        color = color,        radius = radius,    )}@Composable@Preview(showBackground = true)fun CirclePulseAnimationPreview() {    AnimacoesTheme {        Column(            Modifier.fillMaxSize(),            horizontalAlignment = Alignment.CenterHorizontally,            verticalArrangement = Arrangement.Center        ) {            CirclePulseAnimation(                pulseCircleColor = Color(0xB3995732),            )        }    }}