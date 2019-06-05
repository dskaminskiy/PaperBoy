package deniskaminskiy.paperboy.utils.api

import retrofit2.HttpException

fun retrofit2.Response<*>.toErrorResponse(): ErrorResponse =
    errorBody()?.string().toErrorResponse()

fun String?.toErrorResponse(): ErrorResponse = try {
    GsonFactory.create()
        .fromJson(this, ErrorResponse::class.java)
} catch (e: Exception) {
    ErrorResponse()
}

fun Throwable.responseOrError(): Either<ErrorResponse, Throwable> {
    return if (this is HttpException) {
        Left(response().toErrorResponse())
    } else {
        Right(this)
    }
}