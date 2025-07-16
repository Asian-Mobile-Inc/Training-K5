package issues13.ex2.viewmodel

fun interface OnFavoriteListener {
    fun onFavorite(imageId: String, isFavorite: Boolean)
}
