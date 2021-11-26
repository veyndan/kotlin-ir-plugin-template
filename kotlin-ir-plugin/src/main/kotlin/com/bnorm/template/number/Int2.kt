package com.bnorm.template.number

object Int2 : Int1To2, Int2To3 {

    override val data: Int get() = 2

    override operator fun plus(other: Int0): Int2 = this

    override operator fun plus(other: Int0To1): Int2To3 = Int2To3Impl(data + other.data)

    override operator fun plus(other: Int0To2): Int2To4 = Int2To4Impl(data + other.data)

    override operator fun plus(other: Int1): Int3 = Int3

    override operator fun plus(other: Int1To2): Int3To4 = Int3To4Impl(data + other.data)

    override operator fun plus(other: Int2): Int4 = Int4
}
