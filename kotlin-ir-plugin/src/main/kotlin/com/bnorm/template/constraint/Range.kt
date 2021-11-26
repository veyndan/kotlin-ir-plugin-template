package com.bnorm.template.constraint

@Target(AnnotationTarget.FUNCTION)
annotation class Range(val from: Int, val to: Int)
