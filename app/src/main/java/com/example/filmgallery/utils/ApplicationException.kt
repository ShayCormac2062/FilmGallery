package com.example.filmgallery.utils

sealed class ApplicationException(override val message: String) : Exception(message) {

    data class ApiException(override val message: String = API_EXCEPTION)
        : ApplicationException(message)

    data class InternetException(override val message: String = INTERNET_CONNECTION_EXCEPTION)
        : ApplicationException(message)

    data class VPNException(override val message: String = VPN_EXCEPTION)
        : ApplicationException(message)

    companion object {
        const val INTERNET_CONNECTION_EXCEPTION = "You have some trouble with the internet connection. Please turn it on and try again."
        const val VPN_EXCEPTION = "Sorry, we are banned in Russia. Please, enable VPN"
        const val API_EXCEPTION = "Sorry, problems on the server. Please try again later."
    }
}
