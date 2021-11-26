package com.bnorm.template.number

object Int0 : Int0To1 {

    override val data: Int get() = 0

    override operator fun plus(other: Int0): Int0 = other

    override operator fun plus(other: Int0To1): Int0To1 = other

    override operator fun plus(other: Int0To2): Int0To2 = other

    override operator fun plus(other: Int0To3): Int0To3 = other

    operator fun plus(other: Int0To4): Int0To4 = other

    override operator fun plus(other: Int1): Int1 = other

    override operator fun plus(other: Int1To2): Int1To2 = other

    override operator fun plus(other: Int1To3): Int1To3 = other

    operator fun plus(other: Int1To4): Int1To4 = other

    override operator fun plus(other: Int2): Int2 = other

    override operator fun plus(other: Int2To3): Int2To3 = other

    operator fun plus(other: Int2To4): Int2To4 = other

    override operator fun plus(other: Int3): Int3 = other

    operator fun plus(other: Int3To4): Int3To4 = other

    operator fun plus(other: Int4): Int4 = other
}
