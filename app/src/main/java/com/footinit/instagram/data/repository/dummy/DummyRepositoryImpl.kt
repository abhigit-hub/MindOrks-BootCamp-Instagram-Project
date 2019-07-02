package com.footinit.instagram.data.repository.dummy

import com.footinit.instagram.data.local.db.DatabaseService
import com.footinit.instagram.data.model.Dummy
import com.footinit.instagram.data.remote.NetworkService
import com.footinit.instagram.data.remote.request.DummyRequest
import io.reactivex.Single
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) : DummyRepository {

    override fun fetchDummy(id: String): Single<List<Dummy>> =
        networkService.doDummyCall(DummyRequest(id)).map { it.data }

}