package com.yasinmoridi.miniverse.utils

import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.isPrimaryPressed
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.launch
import kotlin.math.abs

@Composable
fun Modifier.mouseDragScroll(state: ScrollableState): Modifier {
    val scope = rememberCoroutineScope()
    return this.pointerInput(state) {
        awaitPointerEventScope {
            while (true) {
                val event = awaitPointerEvent()
                if (event.type == PointerEventType.Move && event.buttons.isPrimaryPressed) {
                    val change = event.changes.first()
                    val dragAmount = change.position.y - change.previousPosition.y
                    if (abs(dragAmount) > 0.5f) {
                        scope.launch {
                            state.scrollBy(-dragAmount)
                        }
                    }
                }
            }
        }
    }
}
