package tech.danielwaiguru.droidhub.common

import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun View.visible() {
    visibility = View.VISIBLE
}
fun View.gone() {
    visibility = View.GONE
}
fun View.snackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}
fun View.toast(message: String, duration: Int = Toast.LENGTH_SHORT){
    Toast.makeText(context, message, duration).show()
}