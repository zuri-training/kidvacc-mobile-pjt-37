package com.zuri.kidvacc_mobile_pjt_37.models

class Hospital {
    var name: String? = null
    var address: String? = null
    var type: String? = null
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Hospital

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}