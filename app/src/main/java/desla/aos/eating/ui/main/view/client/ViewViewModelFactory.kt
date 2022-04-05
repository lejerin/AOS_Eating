package desla.aos.eating.ui.main.view.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import desla.aos.eating.data.repository.ViewRepository

@Suppress("UNCHECKED_CAST")
class ViewViewModelFactory(
        val repository: ViewRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ViewViewModel(repository) as T
    }

}