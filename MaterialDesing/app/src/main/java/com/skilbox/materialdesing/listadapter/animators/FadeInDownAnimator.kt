package com.skilbox.materialdesing.listadapter.animators

import android.util.Log
import android.view.animation.Interpolator
import androidx.recyclerview.widget.RecyclerView

open class FadeInDownAnimator : BaseItemAnimator {
    constructor()
    constructor(interpolator: Interpolator) {
        this.interpolator = interpolator
    }

    override fun animateRemoveImpl(holder: RecyclerView.ViewHolder) {
        Log.e("BaseItemAnimator", "animateRemoveImpl")
        holder.itemView.animate().apply {
            translationY(-holder.itemView.height * .25f)
            alpha(0f)
            duration = removeDuration
            interpolator = interpolator
            setListener(DefaultRemoveAnimatorListener(holder))
            startDelay = getRemoveDelay(holder)
        }.start()
    }

    override fun hideBottomItemMenu(holder: RecyclerView.ViewHolder) {
        Log.e("BaseItemAnimator", "hideBottomItemMenu")
        holder.itemView.animate().apply {
            translationY(-holder.itemView.height * .25f)
            alpha(0f)
            duration = removeDuration
            interpolator = interpolator
            setListener(DefaultRemoveAnimatorListener(holder))
            startDelay = getRemoveDelay(holder)
        }.start()
    }

    override fun preAnimateAddImpl(holder: RecyclerView.ViewHolder) {
        Log.e("BaseItemAnimator", "preAnimateAddImpl")
        holder.itemView.translationY = -holder.itemView.height * .25f
        holder.itemView.alpha = 0f
    }

    override fun animateAddImpl(holder: RecyclerView.ViewHolder) {
        Log.e("BaseItemAnimator", "animateAddImpl")
        holder.itemView.animate().apply {
            translationY(0f)
            alpha(1f)
            duration = addDuration
            interpolator = interpolator
            setListener(DefaultAddAnimatorListener(holder))
            startDelay = getAddDelay(holder)
        }.start()
    }
}
