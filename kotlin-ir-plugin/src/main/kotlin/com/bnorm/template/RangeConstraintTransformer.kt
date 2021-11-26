package com.bnorm.template

import org.jetbrains.kotlin.backend.common.IrElementTransformerVoidWithContext
import org.jetbrains.kotlin.backend.common.extensions.IrPluginContext
import org.jetbrains.kotlin.backend.common.lower.DeclarationIrBuilder
import org.jetbrains.kotlin.backend.common.lower.irCatch
import org.jetbrains.kotlin.backend.common.lower.irThrow
import org.jetbrains.kotlin.fir.expressions.builder.buildReturnExpression
import org.jetbrains.kotlin.ir.IrStatement
import org.jetbrains.kotlin.ir.builders.IrBuilderWithScope
import org.jetbrains.kotlin.ir.builders.IrElementBuilder
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder
import org.jetbrains.kotlin.ir.builders.declarations.buildFun
import org.jetbrains.kotlin.ir.builders.declarations.buildVariable
import org.jetbrains.kotlin.ir.builders.irBlock
import org.jetbrains.kotlin.ir.builders.irBlockBody
import org.jetbrains.kotlin.ir.builders.irCall
import org.jetbrains.kotlin.ir.builders.irCallOp
import org.jetbrains.kotlin.ir.builders.irConcat
import org.jetbrains.kotlin.ir.builders.irGet
import org.jetbrains.kotlin.ir.builders.irGetObject
import org.jetbrains.kotlin.ir.builders.irString
import org.jetbrains.kotlin.ir.builders.irTemporary
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin
import org.jetbrains.kotlin.ir.declarations.IrFunction
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction
import org.jetbrains.kotlin.ir.declarations.IrValueDeclaration
import org.jetbrains.kotlin.ir.declarations.IrVariable
import org.jetbrains.kotlin.ir.expressions.IrBlockBody
import org.jetbrains.kotlin.ir.expressions.IrBody
import org.jetbrains.kotlin.ir.expressions.IrCall
import org.jetbrains.kotlin.ir.expressions.IrConst
import org.jetbrains.kotlin.ir.expressions.IrExpression
import org.jetbrains.kotlin.ir.expressions.IrFunctionAccessExpression
import org.jetbrains.kotlin.ir.expressions.IrFunctionExpression
import org.jetbrains.kotlin.ir.expressions.IrFunctionReference
import org.jetbrains.kotlin.ir.expressions.IrRawFunctionReference
import org.jetbrains.kotlin.ir.expressions.IrReturn
import org.jetbrains.kotlin.ir.expressions.getConstructorTypeArguments
import org.jetbrains.kotlin.ir.expressions.impl.IrCallImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrConstImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrReturnImpl
import org.jetbrains.kotlin.ir.expressions.impl.IrTryImpl
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol
import org.jetbrains.kotlin.ir.types.createType
import org.jetbrains.kotlin.ir.types.defaultType
import org.jetbrains.kotlin.ir.types.impl.IrSimpleTypeImpl
import org.jetbrains.kotlin.ir.util.IdSignature
import org.jetbrains.kotlin.ir.util.dump
import org.jetbrains.kotlin.ir.util.getAnnotation
import org.jetbrains.kotlin.ir.util.getArgumentsWithSymbols
import org.jetbrains.kotlin.ir.util.hasAnnotation
import org.jetbrains.kotlin.ir.util.statements
import org.jetbrains.kotlin.ir.visitors.IrElementTransformerVoid
import org.jetbrains.kotlin.name.FqName
import org.jetbrains.kotlin.name.Name

class RangeConstraintTransformer(
    private val pluginContext: IrPluginContext,
) : IrElementTransformerVoidWithContext() {
//    private val typeUnit = pluginContext.irBuiltIns.unitType
//    private val typeThrowable = pluginContext.irBuiltIns.throwableType

    private val rangeConstraintAnnotationFqName = FqName("com.bnorm.template.constraint.Range")
    private val rangeConstraintAnnotation = pluginContext.referenceClass(rangeConstraintAnnotationFqName)!!

//    private val classMonotonic = pluginContext.referenceClass(FqName("kotlin.time.TimeSource.Monotonic"))!!
//
//    private val funMarkNow = pluginContext.referenceFunctions(FqName("kotlin.time.TimeSource.markNow")).single()
//
//    private val funElapsedNow = pluginContext.referenceFunctions(FqName("kotlin.time.TimeMark.elapsedNow")).single()

    override fun visitFunctionNew(declaration: IrFunction): IrStatement {
        val body = declaration.body
        if (body != null && declaration.hasAnnotation(rangeConstraintAnnotation)) {
            val annotation = declaration.getAnnotation(rangeConstraintAnnotationFqName)!!
            val from = (annotation.getValueArgument(0) as IrConst<Int>).value
            val to = (annotation.getValueArgument(1) as IrConst<Int>).value

            declaration.returnType = pluginContext.referenceClass(FqName("com.bnorm.template.number.Int${from}To${to}"))!!.defaultType

//            val result = ((body.statements.single() as IrReturnImpl).value as IrConstImpl<Int>).value

            declaration.body = body.transform(
                object : IrElementTransformerVoidWithContext() {
                    override fun <T> visitConst(expression: IrConst<T>): IrExpression {
                        return DeclarationIrBuilder(pluginContext, declaration.symbol).irGetObject(pluginContext.referenceClass(FqName("com.bnorm.template.number.Int${(expression.value as Int)}"))!!)
                    }

                    override fun visitFunctionAccess(expression: IrFunctionAccessExpression): IrExpression {
                        println("visitFunctionAccess $expression")
//                        if ((expression as IrCallImpl).symbol.signature!!.asPublic()!! == IdSignature.PublicSignature("kotlin", "Int.plus", null))
                        return (expression as IrCallImpl).symbol.signature!!.asPublic()!!
                            .takeIf { it.packageFqName == "kotlin" && it.declarationFqName == "Int.plus" }
                            ?.let {
                                val func = (pluginContext.referenceFunctions(FqName("com.bnorm.template.number.Int${((expression.dispatchReceiver as IrConst<Int>).value)}.plus")) as List)
                                    .single {
                                        val parameters = it.owner.valueParameters
                                        (parameters[0].type as IrSimpleTypeImpl).kotlinType.toString() == "Int${((expression.getValueArgument(0) as IrConst<Int>).value)}"
                                    }
                                DeclarationIrBuilder(pluginContext, declaration.symbol).irCallOp(func, pluginContext.referenceClass(FqName("com.bnorm.template.number.Int${(expression.dispatchReceiver as IrConst<Int>).value}"))!!.createType(hasQuestionMark = false, arguments = emptyList()), DeclarationIrBuilder(pluginContext, declaration.symbol).irGetObject(pluginContext.referenceClass(FqName("com.bnorm.template.number.Int${(expression.dispatchReceiver as IrConst<Int>).value}"))!!)!!, DeclarationIrBuilder(pluginContext, declaration.symbol).irGetObject(pluginContext.referenceClass(FqName("com.bnorm.template.number.Int${(expression.getValueArgument(0) as IrConst<Int>).value}"))!!)).also { call ->
//                                    call.putValueArgument(0, DeclarationIrBuilder(pluginContext, declaration.symbol).irGetObject(pluginContext.referenceClass(FqName("com.bnorm.template.number.Int${(expression.dispatchReceiver as IrConst<Int>).value}"))!!))
//                                    call.putValueArgument(1, DeclarationIrBuilder(pluginContext, declaration.symbol).irGetObject(pluginContext.referenceClass(FqName("com.bnorm.template.number.Int${(expression.getValueArgument(0) as IrConst<Int>).value}"))!!))
                                }
                            }
                            ?: super.visitFunctionAccess(expression)
                    }
                },
                null,
            )

//            declaration.body = DeclarationIrBuilder(pluginContext, declaration.symbol).irBlockBody {
//                +irGetObject(pluginContext.referenceClass(FqName("com.bnorm.template.number.Int$result"))!!)
//                    .transform(object : IrElementTransformerVoidWithContext() {
//                        override fun visitReturn(expression: IrReturn): IrExpression {
//                            return DeclarationIrBuilder(pluginContext, declaration.symbol).irBlock {
//                                +expression.apply {
//                                    value = expression.value
//                                }
//                            }
//                        }
//                    }, null)
//            }
        }
        return super.visitFunctionNew(declaration)
    }
}
