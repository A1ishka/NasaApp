package by.bsuir.makogon.alina.data.remote.dto

data class MarsPicture(
    val camera: Camera,
    val earth_date: String,
    val id: Int,
    val img_src: String,
    val rover: Rover,
    val sol: Int
)