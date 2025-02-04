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

class TvShowServiceRepositoryImpl(
    private val tvShowService: TvShowService
) : TvShowServiceRepository {
    override suspend fun getMostPopularTvShows(): Flow<PagingData<TvShowHomeResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, //1 sayfada olcak item sayısını temsil eder
                prefetchDistance = 2, // Kullanıcı 2 son iteme yaklaştığında yeni veri yükle
                enablePlaceholders = false, //Sayfalanmamış veriler için null placeholder'ların kullanılıp kullanılmayacağını belirtir.
                initialLoadSize = 10, //İlk yüklemede 20 veri getirecek.
                maxSize = 100, // Bellekte en fazla 100 veri tut.Burdada şöyle oluyor 101. veriye geldiği anda ilk 1-10 veriyi silecektir(pageSize değerine 10 dedim ondan 1-10).Eğer geri ilk sayafaya kadar dönersek geri yükleyecek.
                //jumpThreshold = 50,//Kullanıcı liste üzerinde çok büyük bir mesafe kaydırdığında, sayfalama sistemi eski yöntemle sayfa sayfa veri çekmek yerine, doğrudan belirtilen konumdan veri çekmek için REFRESH tetikler.
                //Bu yaklaşım, büyük sıçramalarda daha verimli bir veri yükleme sağlar ve gereksiz ara yüklemeleri önler.
                //Şuanda 50 öge birden zıplarsa gittiği hedefteki sayfayı yani hangi sayfaya gittiyse onu yükleyecek.
                //jumpTresholdu çalıştırmak istiyorsan TvShowPagingSource içerisinde jumpingSupported override etmem gerekli ! bunuda true setlemeliyim
            ),
            pagingSourceFactory = { TvShowPagingSource(tvShowService) },
            //remoteMediator =
            //RemoteMediator'ın Temel Amacı
            //API ve Veritabanı Arasında Köprü Kurar: API'den verileri alır ve veritabanına yazar.
            //Yerel Veri Kaynağını Önceliklendirir: UI verileri doğrudan veritabanından çeker, API'den gelen veriler arka planda güncellenir.
            //Çevrimdışı Destek Sağlar: Veritabanında önbelleğe alınan verilerle uygulama çevrimdışı çalışabilir.
            //Sayfalı Veri Yükleme: API'den gelen veriler veritabanına sayfa sayfa kaydedilir.

            //RemoteMediator Çalışma Mantığı
            //UI veri talep eder.
            //Veri öncelikle veritabanından okunur.
            //Veritabanında veri yoksa veya güncelleme gerekiyorsa, RemoteMediator API'den veri çeker.
            //API’den gelen veri veritabanına yazılır.
            //UI, güncellenmiş veriyi veritabanından çeker ve gösterir.
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