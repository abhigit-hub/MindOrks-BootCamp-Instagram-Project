package com.footinit.instagram.data.repository.dummy

import com.footinit.instagram.data.model.Dummy
import io.reactivex.Single

interface DummyRepository {

    fun fetchDummy(id: String): Single<List<Dummy>>
}