package com.tvshow.tvshowapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tvshow.tvshowapp.data.network.TvShowService
import com.tvshow.tvshowapp.domain.model.response.TvShowHomeResponse
import com.tvshow.tvshowapp.domain.model.response.TvShowDetailResponse
import com.tvshow.tvshowapp.domain.repository.TvShowServiceRepository
import com.tvshow.tvshowapp.core.CustomExceptions
import com.tvshow.tvshowapp.common.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 *  07.02.2025
 * PagingConfig() =
 * @param pageSize Bir sayfada bulunacak öğe sayısını temsil eder.

 * @param prefetchDistance Kullanıcı, listedeki son öğelere yaklaştığında kaç öğe kala yeni verinin yüklenmesi gerektiğini belirtir.
 * Örneğin, `prefetchDistance = 2` olduğunda, kullanıcı son 2 öğeye yaklaştığında yeni veri yüklenir.

 * @param enablePlaceholders Sayfalanmamış veriler için `null` placeholder'ların kullanılıp kullanılmayacağını belirtir.
 * `false` olarak ayarlandığında eksik veriler için placeholder kullanılmaz.

 * @param initialLoadSize İlk yükleme sırasında getirilecek veri sayısını belirler.
 * Örneğin, `initialLoadSize = 10` olduğunda başlangıçta 10 veri yüklenir.

 * @param maxSize Bellekte tutulabilecek maksimum öğe sayısını belirtir.
 * Örneğin, `maxSize = 100` olduğunda, 101. öğe geldiğinde ilk 10 öğe silinir (`pageSize` değeri 10 olduğu için 1-10 arasındaki öğeler kaldırılır).
 * Eğer kullanıcı ilk sayfaya geri dönerse, bu veriler tekrar yüklenecektir.

 * @param jumpThreshold Kullanıcı liste üzerinde büyük bir mesafe kaydırdığında, sayfalama sistemi eski yöntemle sayfa sayfa veri çekmek yerine,
 * doğrudan belirtilen konumdan veri çekmek için `REFRESH` işlemi tetikler.
 * Örneğin, `jumpThreshold = 50` olarak ayarlandığında, kullanıcı 50 öğe birden atlarsa, hedef sayfa doğrudan yüklenir.
 * !!!! `jumpThreshold` özelliğini kullanmak için `TvShowPagingSource` içinde `jumpingSupported` metodunu `true` olarak override etmek gerekir. !!!!!!

 * remoteMediator = `RemoteMediator`, API ve veritabanı arasında köprü kurarak sayfalı veri yüklemeyi yönetir.
 * ### Temel Amaçları:
 * - **API ve Veritabanı Arasında Köprü Kurar**: API'den verileri alır ve veritabanına kaydeder.
 * - **Yerel Veri Kaynağını Önceliklendirir**: UI, verileri doğrudan veritabanından çeker; API'den gelen veriler arka planda güncellenir.
 * - **Çevrimdışı Destek Sağlar**: Önceden önbelleğe alınmış verilerle uygulama çevrimdışı çalışabilir.
 * - **Sayfalı Veri Yükleme**: API’den gelen veriler, veritabanına sayfa sayfa kaydedilir.
 *
 * ### Çalışma Mantığı:
 * 1. UI veri talep eder.
 * 2. Veri öncelikle veritabanından okunur.
 * 3. Veritabanında veri yoksa veya güncelleme gerekiyorsa, `RemoteMediator` API'den veri çeker.
 * 4. API’den gelen veri veritabanına yazılır.
 * 5. UI, güncellenmiş veriyi veritabanından çeker ve görüntüler.
 */

class TvShowServiceRepositoryImpl(
    private val tvShowService: TvShowService
) : TvShowServiceRepository {
    override suspend fun getMostPopularTvShows(): Flow<PagingData<TvShowHomeResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 2,
                enablePlaceholders = false,
                initialLoadSize = 10,
                maxSize = 100,
            ),
            pagingSourceFactory = { TvShowPagingSource(tvShowService) },
        ).flow
    }

    override suspend fun getTvShowDetails(permaLink: String): Flow<Response<TvShowDetailResponse>> {
        return flow {
            emit(Response.Loading())
            try {
                val response = tvShowService.getTvShowDetails(permaLink)
                emit(Response.Success(response))
            } catch (e: Exception) {
                emit(Response.Error(CustomExceptions(e)))
            }
        }
    }

    override suspend fun getTvShowDetailsById(id: Int): Flow<Response<TvShowDetailResponse>> {
        return flow {
            emit(Response.Loading())
            try {
                val response = tvShowService.getTvShowDetailsById(id)
                emit(Response.Success(response))
            } catch (e: Exception) {
                emit(Response.Error(CustomExceptions(e)))
            }
        }
    }
}