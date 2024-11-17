package com.tvshow.tvshowapp.domain.model.detail

import com.google.gson.annotations.SerializedName

data class TvShow(
    val countdown: Any?,
    val country: String?,
    val description: String?,
    @SerializedName("description_source")
    val descriptionSource: String?,
    @SerializedName("end_date")
    val endDate: Any?,
    val episodes: List<Episode>?,
    val genres: List<String>?,
    val id: Int?,
    @SerializedName("image_path")
    val imagePath: String?,
    @SerializedName("image_thumbnail_path")
    val imageThumbnailPath: String?,
    val name: String?,
    val network: String?,
    val permalink: String?,
    val pictures: List<String>?,
    val rating: String?,
    @SerializedName("rating_count")
    val ratingCount: String?,
    val runtime: Int?,
    @SerializedName("start_date")
    val startDate: String?,
    val status: String?,
    val url: String?,
    @SerializedName("youtube_link")
    val youtubeLink: String?
)
data class TvShowDescription(
    val name : String?,
    val status : String?,
    val startDate : String?,
    val endDate : Any?,
    val rating : String?,
    val network : String?,
    val description : String?,
    val imageThumbnailPath: String?,
)
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
fun TvShow.toTvShowDescription(): TvShowDescription {
    return TvShowDescription(
        name = name,
        status = status,
        startDate = startDate,
        endDate = endDate,
        rating = rating,
        network = network,
        description = description,
        imageThumbnailPath = imageThumbnailPath
    )
}