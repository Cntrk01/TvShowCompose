package com.tvshow.tvshowapp.data.mapper

import com.tvshow.tvshowapp.domain.model.response.TvShowHomeResponse
import com.tvshow.tvshowapp.domain.model.response.TvShowDetail
import com.tvshow.tvshowapp.domain.model.attr.TvShowDetailAttr
import com.tvshow.tvshowapp.domain.model.attr.TvShowHomeAttr
import com.tvshow.tvshowapp.domain.model.db.TvShowFavoriteAttr

/**
 * `TvShowDescription` sınıfının kullanım avantajları ve dezavantajları.
 *
 * ### Avantajları:
 * - **Gereksiz Verilere Erişimi Engeller**: `TvShowDescription`, yalnızca gereken alanları içerdiğinden, UI katmanı gibi bileşenler gereksiz verilere erişmeye çalışmaz. Bu, sınıfın sorumluluğunu daraltır.
 * - **Bakımı Kolaylaştırır**: `TvShow` sınıfına yeni alanlar eklenirse, bu değişiklik `TvShowDescription` ve onu kullanan bileşenleri etkilemez.
 * - **Performansı Artırır**: Örneğin, `RecyclerView` gibi liste bileşenleri, yalnızca ihtiyaç duyulan verileri işleyerek performans açısından avantaj sağlar.
 * - **Hafif ve Optimize Edilmiş Modeller**: Büyük veri yapılarıyla çalışırken, UI odaklı ve optimize edilmiş modeller kullanmak bellek yönetimi açısından daha verimlidir.
 * - **Daha Verimli API Çağrıları**: API çağrılarında veri miktarını azaltarak bellek kullanımını düşürür ve performansı artırır.
 *
 * ### Dezavantajları:
 * - **Ekstra Dönüşüm Maliyeti**: `TvShow` nesnesini `TvShowDescription` nesnesine dönüştürmek küçük bir maliyet yaratabilir, ancak genellikle ihmal edilebilir düzeydedir.
 * - **Fazladan Kod Yazımı**: Yeni bir sınıf ve dönüşüm fonksiyonu oluşturmak kod miktarını artırır, ancak bu genellikle daha iyi yapılandırılmış ve sürdürülebilir bir kod tabanı sağlar.
 */

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
        id = id.toString(),
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
        country = country,
    )
}

fun TvShowDetailAttr.toTvShowFavorite() : TvShowFavoriteAttr{
    return TvShowFavoriteAttr(
        showId = id.toString(),
        showPictures = imageList ?: emptyList(),
        showName = name ?: "",
        showStatus = status ?: "",
        showStartDate = startDate ?: "",
        showEndDate = endDate.toString(),
        showRating = rating ?: "",
        showNetwork = network ?: "",
        showCountry = country ?: "",
        showUrl = url,
        showDescriptionSource = descriptionSource,
        showYoutubeLink = youtubeLink,
        showEpisodes = episodes,
        showImageThumbnail = imageThumbnailPath,
        showDescription = description,
    )
}

fun TvShowFavoriteAttr.toTvShowDetailAttr(): TvShowDetailAttr {
    return TvShowDetailAttr(
        id = showId,
        name = showName,
        status = showStatus,
        startDate = showStartDate,
        endDate = showEndDate,
        rating = showRating,
        network = showNetwork,
        imageList = showPictures,
        url = showUrl,
        descriptionSource = showDescriptionSource,
        youtubeLink = showYoutubeLink,
        episodes = showEpisodes,
        country = showCountry,
        description = showDescription,
        imageThumbnailPath = showImageThumbnail,
    )
}
