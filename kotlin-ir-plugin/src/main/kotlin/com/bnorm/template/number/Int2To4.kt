package com.bnorm.template.number

sealed interface Int2To4 : Int1To4 {

    override operator fun plus(other: Int0): Int2To4
}

@JvmInline
value class Int2To4Impl(override val data: Int) : Int2To4 {

    init {
        require(data in 2..4) { "$data is not within 2..4" }
    }

    override fun plus(other: Int0): Int2To4 = this
}
