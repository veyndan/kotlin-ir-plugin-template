package com.bnorm.template.number

sealed interface Int0To1 : Int0To2 {

    override operator fun plus(other: Int0): Int0To1

    override operator fun plus(other: Int0To1): Int0To2

    override operator fun plus(other: Int0To2): Int0To3

    operator fun plus(other: Int0To3): Int0To4

    override operator fun plus(other: Int1): Int1To2

    override operator fun plus(other: Int1To2): Int1To3

    operator fun plus(other: Int1To3): Int1To4

    override operator fun plus(other: Int2): Int2To3

    operator fun plus(other: Int2To3): Int2To4

    operator fun plus(other: Int3): Int3To4
}

@JvmInline
value class Int0To1Impl(override val data: Int) : Int0To1 {

    init {
        require(data in 0..1) { "$data is not within 0..1" }
    }

    override fun plus(other: Int0): Int0To1 = this

    override fun plus(other: Int0To1): Int0To2 = Int0To2Impl(data + other.data)

    override fun plus(other: Int0To2): Int0To3 = Int0To3Impl(data + other.data)

    override fun plus(other: Int0To3): Int0To4 = Int0To4Impl(data + other.data)

    override fun plus(other: Int1): Int1To2 = Int1To2Impl(data + other.data)

    override fun plus(other: Int1To2): Int1To3 = Int1To3Impl(data + other.data)

    override fun plus(other: Int1To3): Int1To4 = Int1To4Impl(data + other.data)

    override fun plus(other: Int2): Int2To3 = Int2To3Impl(data + other.data)

    override fun plus(other: Int2To3): Int2To4 = Int2To4Impl(data + other.data)

    override fun plus(other: Int3): Int3To4 = Int3To4Impl(data + other.data)
}
