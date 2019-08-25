package deniskaminskiy.paperboy.data.post

data class Post(
    val id: String,
    val content: List<PostContent>
)

sealed class PostContent

data class PostText(
    val order: Int,
    val text: String
) : PostContent()

data class PostImage(
    val order: Int,
    val url: String
) : PostContent()