package com.henriquesebastiao.helpos.data.remote.api

import com.henriquesebastiao.helpos.data.remote.dto.PageDto
import com.henriquesebastiao.helpos.data.remote.dto.client.ClientCreateDto
import com.henriquesebastiao.helpos.data.remote.dto.client.ClientReadDetailedDto
import com.henriquesebastiao.helpos.data.remote.dto.client.ClientReadDto
import com.henriquesebastiao.helpos.data.remote.dto.client.ClientUpdateDto
import com.henriquesebastiao.helpos.data.remote.dto.serviceorder.ServiceOrderReadDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ClientApi {

    @GET("api/v1/clients")
    suspend fun list(
        @Query("name") name: String? = null,
        @Query("login") login: String? = null,
        @Query("ixc_id") ixcId: Long? = null,
        @Query("cto") cto: String? = null,
        @Query("city") city: String? = null,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("offset") offset: Int = 0,
    ): PageDto<ClientReadDto>

    @GET("api/v1/clients/{client_id}")
    suspend fun get(@Path("client_id") clientId: Long): ClientReadDetailedDto

    @GET("api/v1/clients/by-ixc/{ixc_id}")
    suspend fun getByIxc(@Path("ixc_id") ixcId: Long): ClientReadDetailedDto

    @POST("api/v1/clients")
    suspend fun create(@Body body: ClientCreateDto): ClientReadDto

    @PATCH("api/v1/clients/{client_id}")
    suspend fun update(
        @Path("client_id") clientId: Long,
        @Body body: ClientUpdateDto,
    ): ClientReadDto

    @DELETE("api/v1/clients/{client_id}")
    suspend fun delete(@Path("client_id") clientId: Long)

    @GET("api/v1/clients/{client_id}/service-orders")
    suspend fun listServiceOrders(
        @Path("client_id") clientId: Long,
        @Query("limit") limit: Int = DEFAULT_LIMIT,
        @Query("offset") offset: Int = 0,
    ): PageDto<ServiceOrderReadDto>

    companion object {
        const val DEFAULT_LIMIT = 50
    }
}
