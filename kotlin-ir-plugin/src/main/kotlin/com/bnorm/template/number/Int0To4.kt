package com.bnorm.template.number

sealed interface Int0To4 : IntInterval {

    operator fun plus(other: Int0): Int0To4
}

@JvmInline
value class Int0To4Impl(override val data: Int) : Int0To4 {

    init {
        require(data in 0..4) { "$data is not within 0..4" }
    }

    override operator fun plus(other: Int0): Int0To1 = Int0To1Impl(this.data.plus(other.data))
}
