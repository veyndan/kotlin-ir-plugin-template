package com.bnorm.template.number

sealed interface Int0To3 : Int0To4 {

    override operator fun plus(other: Int0): Int0To3

    operator fun plus(other: Int0To1): Int0To4

    operator fun plus(other: Int1): Int1To4
}

@JvmInline
value class Int0To3Impl(override val data: Int) : Int0To3 {

    init {
        require(data in 0..3) { "$data is not within 0..3" }
    }

    override operator fun plus(other: Int0): Int0To3 = this

    override operator fun plus(other: Int0To1): Int0To4 = Int0To4Impl(data + other.data)

    override operator fun plus(other: Int1): Int1To4 = Int1To4Impl(data + other.data)
}
