package westgerani.knit

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform