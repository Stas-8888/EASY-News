package com.example.newsapppp.presentation.screens.favorite

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapppp.presentation.adapters.ArticleAdapter

private const val FADE_BUFFER = 90
private const val SWIPE_THRESHOLD = 0.3F

/**
 * [ItemTouchHelper] that fades a view out to allow deletion.
 */
class SwipeToDelete(
    private val adapter: ArticleAdapter,
    private val viewModel: FavoriteFragmentViewModel
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        val initial = viewHolder.bindingAdapterPosition
        val final = target.bindingAdapterPosition
        adapter.notifyItemMoved(initial, final)
        adapter.currentList.map {
            viewModel.onMoveUpdateArticle(it)
        }
        return true
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = SWIPE_THRESHOLD

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.bindingAdapterPosition
        val article = adapter.currentList[position]
        viewModel.onItemSwiped(article, position)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val maxWidth = recyclerView.width
        val alphaValue = when {
            dX > 0 -> FADE_BUFFER - dX / maxWidth * 100
            dX < 0 -> FADE_BUFFER + dX / maxWidth * 100
            else -> 100.toFloat()
        }
        viewHolder.itemView.alpha = alphaValue / 100
    }
}
