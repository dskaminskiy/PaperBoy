package deniskaminskiy.paperboy.utils.glide

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.annotation.GlideExtension
import com.bumptech.glide.annotation.GlideOption
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.BaseRequestOptions
import deniskaminskiy.paperboy.utils.px

@GlideExtension
open class GlideExtensions private constructor() {

    companion object {
        /**
         * Вызывать только после применения остальных трансформаций
         * т.к. они затирают предыдущие, а этот метод сохраняет
         *
         * @param dp    - радиус закругления
         */
        @GlideOption
        @JvmStatic
        fun roundCorners(options: BaseRequestOptions<*>, dp: Int, context: Context): BaseRequestOptions<*> {
            val transformations = ArrayList<BitmapTransformation>()

            for ((_, value) in options.getTransformations()) {
                if (value is BitmapTransformation) {
                    transformations.add(value)
                }
            }

            transformations.add(RoundedCorners(dp.px(context).toInt()))

            return options.apply {
                transform(MultiTransformation<Bitmap>(transformations))
            }
        }
    }

}