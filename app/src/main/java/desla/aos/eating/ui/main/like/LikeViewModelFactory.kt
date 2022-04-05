package desla.aos.eating.ui.main.like

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.data.repository.LikeRepository

@Suppress("UNCHECKED_CAST")
class LikeViewModelFactory(
        private val repository: LikeRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LikeViewModel(repository) as T
    }

}