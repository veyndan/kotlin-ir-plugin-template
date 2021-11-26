package com.bnorm.template.number

sealed interface Int1To4 : Int0To4 {

    override operator fun plus(other: Int0): Int1To4
}

@JvmInline
value class Int1To4Impl(override val data: Int) : Int1To4 {

    init {
        require(data in 1..4) { "$data is not within 1..4" }
    }

    override operator fun plus(other: Int0): Int1To4 = Int1To4Impl(this.data.plus(other.data))
}
