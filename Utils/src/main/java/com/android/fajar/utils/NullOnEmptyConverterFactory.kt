package com.android.fajar.utils

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class NullOnEmptyConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(
        var1: Type,
        var2: Array<Annotation>,
        var3: Retrofit
    ): Converter<ResponseBody, *> {
        val var4: Converter<*, *> = var3.nextResponseBodyConverter<Any>(this, var1, var2)
        return Converter { var1 -> if (var1.contentLength() == 0L) null else var4.convert(var1 as Nothing) }
    }
}