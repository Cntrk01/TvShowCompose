package com.tvshow.tvshowapp.data.mapper

import com.tvshow.tvshowapp.domain.model.response.TvShowHomeResponse
import com.tvshow.tvshowapp.domain.model.response.TvShowDetail
import com.tvshow.tvshowapp.domain.model.attr.TvShowDetailAttr
import com.tvshow.tvshowapp.domain.model.attr.TvShowHomeAttr

//Avantajları
//TvShowDescription yalnızca gereken alanları içerdiği için, bu yapıyı kullanan diğer bileşenler (ör. UI katmanı) gereksiz verilere erişmeye çalışmaz. Bu, sınıfın sorumluluğunu daraltır.
//TvShow sınıfına yeni alanlar eklenirse, bu değişiklik TvShowDescription ve onu kullanan bileşenleri etkilemez. Bu, kodun bakımını kolaylaştırır.
//Örneğin, RecyclerView gibi bir liste görünümü kullanıyorsanız, her satırda yalnızca ihtiyaç duyulan verilerin işlenmesi, performansı artırır.
//Çok büyük veri yapılarıyla çalışıyorsanız,
//UI odaklı, hafif ve optimize edilmiş modeller istiyorsanız,
//API çağrılarında veri miktarını azaltmak istiyorsanız önemlidir. (bellek ve performans açısından avantaj saglar )

//Dezavantajları
//Ekstra Dönüşüm Maliyeti:
//TvShow'dan TvShowDescription gibi bir sınıfa dönüşüm işlemi küçük bir maliyet yaratabilir. Ancak bu genelde ihmal edilebilir düzeydedir.
//Fazladan Kod:
//Yeni bir sınıf ve dönüşüm fonksiyonu yazmak kodu artırır. Ancak, bu genelde daha iyi yapılandırılmış ve sürdürülebilir bir kod tabanı sağlar.

fun TvShowHomeResponse.toShowMapper(): TvShowHomeAttr {
    return TvShowHomeAttr(
        permaLink = permalink ?: "",
        id = id ?: 0,
        name = name ?: "",
        imageThumbnailPath = imageThumbnailPath ?: "",
        country = country ?: "",
        network = network ?: "",
        status = status ?: ""
    )
}

fun TvShowDetail.toTvShowDescription(): TvShowDetailAttr {
    return TvShowDetailAttr(
        name = name,
        status = status,
        startDate = startDate,
        endDate = endDate,
        rating = rating,
        network = network,
        description = description,
        imageThumbnailPath = imageThumbnailPath,
        imageList = pictures,
        url= url,
        descriptionSource = descriptionSource,
        youtubeLink = youtubeLink,
        episodes = episodes,
    )
}