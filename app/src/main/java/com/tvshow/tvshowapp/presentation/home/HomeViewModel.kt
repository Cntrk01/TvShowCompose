package com.tvshow.tvshowapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.tvshow.tvshowapp.data.mapper.toShowMapper
import com.tvshow.tvshowapp.domain.model.attr.TvShowHomeAttr
import com.tvshow.tvshowapp.domain.repository.TvShowServiceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 02.02.2025
 * 39.satırdaki map için açıklama :
 * Bu mapping işlemini collectLatestta yapıyordum.Bu da her detaya gidip döndüğümde sayfanın en başına atmasına sebebiyet veriyordu.Aslında bir side effect yaratıyordu
 * Bunun sebebi de bizim cachedIn den sonra map işlemi yapıp akış zincirini bozmamız.Bundan dolayı da cachedIn yeni bir akışta çalıştırıyor.Eski cacheledimiz durumdan farklı bir durum çıktığı için uida böyle bir side effect çıkıyor
 * Şu durumda ise önce map işlemi yapılıyor.Daha sonrasında da cachedIn yeniden akış oluşturmayı engelleyerek aynı veri seti üzerinden çalışmasını devam etmesini sağlar.Varolan akış üzerine uygular.
 * Bu kısımı usecase de de yapabiliriz.Böyle yapınca da akışı bozmaz zaten cachedIn üzerinde kalıyor.Fakat mapping işlemini burada yapıyorum.
 *
 *
 * 51.satırdaki cachedIn için açıklama :
 * cachedIn operatörü, Flow<PagingData> akışını belirli bir
 * CoroutineScope içerisinde önbelleğe alır ve aynı veri akışının birden fazla kez toplanmasına izin verir.
 * cachedIn kullanılmazsa performans kaybı yaşanır ve scroll state sıfırlanır.
 * viewModelScope kullanarak bu veri akışının UI Lifecycle'ı boyunca canlı kalmasını sağlanır.
 * **/

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tvShowServiceRepository: TvShowServiceRepository,
) : ViewModel() {

    private val _tvShowPagingData = MutableStateFlow<PagingData<TvShowHomeAttr>>(PagingData.empty())
    val tvShowPagingData: StateFlow<PagingData<TvShowHomeAttr>> = _tvShowPagingData

    init {
        getTvShows()
    }

    private fun getTvShows() = viewModelScope.launch(Dispatchers.IO) {
        tvShowServiceRepository.getMostPopularTvShows()
            .map {
                it.map { pagingData ->
                    pagingData.toShowMapper()
                }
            }
            .cachedIn(viewModelScope)
            .collectLatest { pagingData ->
                _tvShowPagingData.value = pagingData
            }
    }
}