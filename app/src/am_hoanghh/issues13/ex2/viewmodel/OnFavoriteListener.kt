package issues13.ex2.viewmodel

interface OnFavoriteListener {
    fun onFavorite(imageId: String, isFavorite: Boolean)
    fun onItemViewClick(url: String?)
}
