package com.bnorm.template.number

object Int1 : Int0To1, Int1To2 {

    override val data: Int get() = 1

    override operator fun plus(other: Int0): Int1 = this

    override operator fun plus(other: Int0To1): Int1To2 = Int1To2Impl(data + other.data)

    override operator fun plus(other: Int0To2): Int1To3 = Int1To3Impl(data + other.data)

    override operator fun plus(other: Int0To3): Int1To4 = Int1To4Impl(data + other.data)

    override operator fun plus(other: Int1): Int2 = Int2

    override operator fun plus(other: Int1To2): Int2To3 = Int2To3Impl(data + other.data)

    override operator fun plus(other: Int1To3): Int2To4 = Int2To4Impl(data + other.data)

    override operator fun plus(other: Int2): Int3 = Int3

    override operator fun plus(other: Int2To3): Int3To4 = Int3To4Impl(data + other.data)

    override operator fun plus(other: Int3): Int4 = Int4
}
