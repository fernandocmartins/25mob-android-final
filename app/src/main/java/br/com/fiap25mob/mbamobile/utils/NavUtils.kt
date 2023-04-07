package br.com.fiap25mob.mbamobile.utils

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import br.com.fiap25mob.mbamobile.R

private val animType = NavOptions.Builder()
    .setEnterAnim(R.anim.anim_in_right)
    .setExitAnim(R.anim.anim_out_left)
    .setPopEnterAnim(R.anim.anim_left_in)
    .setPopExitAnim(R.anim.anim_out_right)
    .build()

fun NavController.navigateWithAnimations(
    destinationId: Int,
    animation: NavOptions = animType
) {
    this.navigate(destinationId, null, animation)
}

fun NavController.navigateWithAnimations(
    destinationId: NavDirections,
    animation: NavOptions = animType
) {
    this.navigate(destinationId, animation)
}