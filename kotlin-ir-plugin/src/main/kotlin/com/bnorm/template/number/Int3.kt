package com.bnorm.template.number

object Int3 : Int2To3, Int3To4 {

    override val data: Int get() = 3

    override fun plus(other: Int0): Int3 = this

    override fun plus(other: Int0To1): Int3To4 = Int3To4Impl(data + other.data)

    override fun plus(other: Int1): Int4 = Int4
}
