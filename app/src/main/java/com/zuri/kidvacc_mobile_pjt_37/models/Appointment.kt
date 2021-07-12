package com.zuri.kidvacc_mobile_pjt_37.models

class Appointment {
    var name: String? = null
    var vaccine: String? = null
    var hospital: String? = null
    var date_due: String? = null
    var time: String? = null
    var id: Long = 0

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Appointment

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}