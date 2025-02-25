package com.example.androidpracticumcustomview.ui.theme


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */
@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?
) {
    // Блок создания и инициализации переменных
    val alpha = remember { Animatable(0f) }

    val firstChildOffsetY = remember { Animatable(0f) }
    var firstChildTargetValue = 0f

    val secondChildOffsetY = remember { Animatable(0f) }
    var secondChildTargetValue = 0f

    val density = LocalDensity.current.density

    val animationDuration = 5000
    val fadeDuration = 2000

    // Блок активации анимации при первом запуске
    LaunchedEffect(Unit) {

        launch {
            firstChildOffsetY.animateTo(
                targetValue = -(firstChildTargetValue / density),
                animationSpec = tween(animationDuration)
            )
        }

        launch {
            secondChildOffsetY.animateTo(
                targetValue = (secondChildTargetValue / density),
                animationSpec = tween(animationDuration)
            )
        }
        launch {
            alpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(fadeDuration)
            )
        }

    }

    // Основной контейнер
    Box {
        Layout(
            content = {

                firstChild?.let {
                    Box(
                        modifier = Modifier
                            .offset(0.dp, firstChildOffsetY.value.dp)
                            .alpha(alpha.value)
                    ) {
                        it()
                    }
                }

                secondChild?.let {
                    Box(
                        modifier = Modifier
                            .offset(0.dp, secondChildOffsetY.value.dp)
                            .alpha(alpha.value)
                    ) {
                        it()
                    }
                }


            },
            modifier = Modifier
                .background(Color.Yellow)
        ) { measurables, constraints ->

            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }

            val width = constraints.maxWidth
            val height = constraints.maxHeight

            firstChildTargetValue = (height / 2 - (placeables.getOrNull(0)?.height ?: 0)).toFloat()
            secondChildTargetValue = (height / 2 - (placeables.getOrNull(1)?.height ?: 0)).toFloat()

            layout(width, height) {
                placeables.forEachIndexed { index, placeable ->

                    if (index == 0) {
                        placeable.place(
                            x = (width - placeable.width) / 2,
                            y = height / 2 - placeable.height
                        )
                    }


                    if (index == 1) {
                        placeable.place(
                            x = (width - placeable.width) / 2,
                            y = height / 2
                        )
                    }
                }
            }
        }
    }
}