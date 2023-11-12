package com.example.moviemood.utils

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat

fun RecyclerView.initRecycler(layoutManager: RecyclerView.LayoutManager, adapter: RecyclerView.Adapter<*>) {
    this.layoutManager = layoutManager
    this.adapter = adapter
}
fun View.showInvisible(isShown: Boolean) {
    if (isShown) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

private val formatter2= DecimalFormat("##.##")
private val formatter3= DecimalFormat("##.###")

fun Double.roundToTwoDecimals() = formatter2.format(this).toString()
fun Double.roundToThreeDecimals() = formatter3.format(this).toString()