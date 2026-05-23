package com.henriquesebastiao.helpos.core.util

object UrlValidator {
    private val regex = Regex("^https?://[^\\s/$.?#].[^\\s]*$", RegexOption.IGNORE_CASE)

    fun isValid(url: String): Boolean = url.isNotBlank() && regex.matches(url.trim())
}
