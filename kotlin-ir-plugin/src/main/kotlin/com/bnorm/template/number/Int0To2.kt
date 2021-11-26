package com.bnorm.template.number

sealed interface Int0To2 : Int0To3 {

    override operator fun plus(other: Int0): Int0To2

    override operator fun plus(other: Int0To1): Int0To3

    operator fun plus(other: Int0To2): Int0To4

    override operator fun plus(other: Int1): Int1To3

    operator fun plus(other: Int1To2): Int1To4

    operator fun plus(other: Int2): Int2To4
}

@JvmInline
value class Int0To2Impl(override val data: Int) : Int0To2 {

    init {
        require(data in 0..2) { "$data is not within 0..2" }
    }

    override fun plus(other: Int0): Int0To2 = this

    override fun plus(other: Int0To1): Int0To3 = Int0To3Impl(data + other.data)

    override fun plus(other: Int0To2): Int0To4 = Int0To4Impl(data + other.data)

    override fun plus(other: Int1): Int1To3 = Int1To3Impl(data + other.data)

    override fun plus(other: Int1To2): Int1To4 = Int1To4Impl(data + other.data)

    override fun plus(other: Int2): Int2To4 = Int2To4Impl(data + other.data)
}
