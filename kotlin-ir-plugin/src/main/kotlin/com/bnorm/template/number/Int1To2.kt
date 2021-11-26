package com.bnorm.template.number

sealed interface Int1To2 : Int0To2, Int1To3 {

    override operator fun plus(other: Int0): Int1To2

    override operator fun plus(other: Int0To1): Int1To3

    override operator fun plus(other: Int0To2): Int1To4

    override operator fun plus(other: Int1): Int2To3

    override operator fun plus(other: Int1To2): Int2To4

    override operator fun plus(other: Int2): Int3To4
}

@JvmInline
value class Int1To2Impl(override val data: Int) : Int1To2 {

    init {
        require(data in 1..2) { "$data is not within 1..2" }
    }

    override fun plus(other: Int0): Int1To2 = this

    override fun plus(other: Int0To1): Int1To3 = Int1To3Impl(data + other.data)

    override fun plus(other: Int0To2): Int1To4 = Int1To4Impl(data + other.data)

    override fun plus(other: Int1): Int2To3 = Int2To3Impl(data + other.data)

    override fun plus(other: Int1To2): Int2To4 = Int2To4Impl(data + other.data)

    override fun plus(other: Int2): Int3To4 = Int3To4Impl(data + other.data)
}
