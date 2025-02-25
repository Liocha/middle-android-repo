package com.example.androidpracticumcustomview.ui.theme

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */

class CustomContainer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    private var firstChildIsAnimate = false

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
        val parentHeight = MeasureSpec.getSize(heightMeasureSpec)

        val firstChild = getChildAt(0)
        firstChild?.measure(
            MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(parentHeight / 3, MeasureSpec.AT_MOST)
        )

        val secondChild = getChildAt(1)
        secondChild?.measure(
            MeasureSpec.makeMeasureSpec(parentWidth, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(parentHeight / 3, MeasureSpec.AT_MOST)
        )

        setMeasuredDimension(parentWidth, parentHeight)

    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val parentHeight = height
        val parentWidth = width

        if (!firstChildIsAnimate) {
            val firstChild = getChildAt(0)
            firstChild?.apply {
                val childLeft = left + (parentWidth - measuredWidth) / 2
                val childRight = childLeft + measuredWidth
                val childTop = top + parentHeight / 2 - measuredHeight
                val childBottom = childTop + measuredHeight
                layout(childLeft, childTop, childRight, childBottom)

                val animationOffset = (top - childTop).toFloat()
                alpha = 0f
                firstChildIsAnimate = true
                animate()
                    .alpha(1f)
                    .setDuration(ALPHA_DURATION)
                animate()
                    .translationYBy(animationOffset)
                    .setDuration(TRANSITION_DURATION)
            }
        }


        val secondChild = getChildAt(1)
        secondChild?.apply {
            val childLeft = left + (parentWidth - measuredWidth) / 2
            val childRight = childLeft + measuredWidth
            val childTop = top + parentHeight / 2
            val childBottom = childTop + measuredHeight
            layout(childLeft, childTop, childRight, childBottom)

            val animationOffset = (bottom - childBottom).toFloat()
            alpha = 0f
            firstChildIsAnimate = true
            animate()
                .alpha(1f)
                .setDuration(ALPHA_DURATION)
            animate()
                .translationYBy(animationOffset)
                .setDuration(TRANSITION_DURATION)
        }
    }

    override fun addView(child: View) {
        if (childCount >= MAX_CHILD_VIEWS) {
            throw IllegalStateException()
        }
        super.addView(child)
    }

    companion object {
        const val MAX_CHILD_VIEWS = 2
        const val ALPHA_DURATION = 2000L
        const val TRANSITION_DURATION = 5000L
    }
}