package com.bnorm.template.number

sealed interface Int2To3 : Int1To3, Int2To4 {

    override operator fun plus(other: Int0): Int2To3

    override operator fun plus(other: Int0To1): Int2To4

    override operator fun plus(other: Int1): Int3To4
}

@JvmInline
value class Int2To3Impl(override val data: Int) : Int2To3 {

    init {
        require(data in 2..3) { "$data is not within 2..3" }
    }

    override fun plus(other: Int0): Int2To3 = this

    override fun plus(other: Int0To1): Int2To4 = Int2To4Impl(data + other.data)

    override fun plus(other: Int1): Int3To4 = Int3To4Impl(data + other.data)
}
