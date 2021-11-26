package com.bnorm.template.number

sealed interface Int3To4 : Int2To4 {

    override operator fun plus(other: Int0): Int3To4
}

@JvmInline
value class Int3To4Impl(override val data: Int) : Int3To4 {

    init {
        require(data in 3..4) { "$data is not within 3..4" }
    }

    override fun plus(other: Int0): Int3To4 = this
}
