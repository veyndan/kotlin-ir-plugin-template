package com.bnorm.template.number

sealed interface Int1To3 : Int0To3, Int1To4 {

    override operator fun plus(other: Int0): Int1To3

    override operator fun plus(other: Int0To1): Int1To4

    override operator fun plus(other: Int1): Int2To4
}

@JvmInline
value class Int1To3Impl(override val data: Int) : Int1To3 {

    init {
        require(data in 1..3) { "$data is not within 1..3" }
    }

    override fun plus(other: Int0): Int1To3 = this

    override fun plus(other: Int0To1): Int1To4 = Int1To4Impl(data + other.data)

    override fun plus(other: Int1): Int2To4 = Int2To4Impl(data + other.data)
}
