package com.example.newsapppp.presentation.screens.favorite.swipe

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapppp.common.Constants.ZERO
import com.example.newsapppp.presentation.adapters.ArticleAdapter
import com.example.newsapppp.presentation.screens.favorite.FavoriteViewModel

private const val HUNDRED = 100
private const val FADE_BUFFER = 90
private const val SWIPE_THRESHOLD = 0.4F

/**
 * [ItemTouchHelper] that fades a view out to allow deletion.
 */
class SwipeToDelete(
    private val adapter: ArticleAdapter,
    private val viewModel: FavoriteViewModel
) : ItemTouchHelper.SimpleCallback(
    ItemTouchHelper.UP or ItemTouchHelper.DOWN,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
) {

    /**
     * Called when an item in the [RecyclerView] is moved.
     * @param recyclerView The [RecyclerView] instance.
     * @param viewHolder The [RecyclerView.ViewHolder] of the item being moved.
     * @param target The [RecyclerView.ViewHolder] of the item being swapped with.
     * @return true if the item was moved successfully, false otherwise.
     */
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

    /**
     * Returns the threshold required to trigger a swipe.
     * @param viewHolder The [RecyclerView.ViewHolder] instance.
     * @return The swipe threshold value.
     */
    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder) = SWIPE_THRESHOLD

    /**
     * Called when an item in the [RecyclerView] is swiped left or right.
     * @param viewHolder The [RecyclerView.ViewHolder] instance.
     * @param direction The direction of the swipe.
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.bindingAdapterPosition
        val article = adapter.currentList[position]
        viewModel.onItemSwiped(article, position)
    }

    /**
     * Called when the item is being drawn.
     * @param c The [Canvas] instance.
     * @param recyclerView The [RecyclerView] instance.
     * @param viewHolder The [RecyclerView.ViewHolder] instance.
     * @param dX The amount of horizontal displacement caused by the swipe.
     * @param dY The amount of vertical displacement caused by the swipe.
     * @param actionState The current action state.
     * @param isCurrentlyActive true if the view is currently being interacted with, false otherwise.
     */
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView: View = viewHolder.itemView

        val white = Paint().also { it.color = Color.WHITE }
        val transparent = Paint().also { it.color = Color.TRANSPARENT }

        val icon: Bitmap = BitmapFactory.decodeResource(
            recyclerView.context.resources,
            android.R.drawable.ic_delete
        )

        // Draw background
        c.drawRect(
            itemView.right.toFloat() + dX,
            itemView.top.toFloat(),
            itemView.right.toFloat(),
            itemView.bottom.toFloat(),
            transparent
        )

        //Draw icon
        val iconMarginRight = (dX * -0.4f).coerceAtMost(110f).coerceAtLeast(15f)
        c.drawBitmap(
            icon, itemView.right.toFloat() - iconMarginRight - icon.width,
            itemView.top.toFloat() + (itemView.bottom.toFloat() - itemView.top.toFloat() - icon.height) / 2,
            white
        )

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val maxWidth = recyclerView.width
        val alphaValue = when {
            dX > ZERO -> FADE_BUFFER - dX / maxWidth * HUNDRED
            dX < ZERO -> FADE_BUFFER + dX / maxWidth * HUNDRED
            else -> HUNDRED.toFloat()
        }
        viewHolder.itemView.alpha = alphaValue / HUNDRED
    }
}
