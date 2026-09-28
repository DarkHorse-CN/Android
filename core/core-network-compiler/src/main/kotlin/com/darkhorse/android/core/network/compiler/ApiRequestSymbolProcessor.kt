package com.darkhorse.android.core.network.compiler

import com.darkhorse.android.core.network.ApiRequest
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSName

internal class ApiRequestSymbolProcessor(
    private val codeGenerator: CodeGenerator,
    private val logger: KSPLogger,
) : SymbolProcessor {

    private val generator = ApiServiceGenerator(codeGenerator, logger)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val annotatedSymbols = resolver.getSymbolsWithAnnotation(
            ApiRequest::class.qualifiedName!!
        )

        val deferred = mutableListOf<KSAnnotated>()

        annotatedSymbols.forEach { symbol ->
            if (symbol is KSClassDeclaration) {
                processRequestClass(symbol, resolver)
            } else {
                deferred.add(symbol)
            }
        }

        return deferred
    }

    private fun processRequestClass(
        declaration: KSClassDeclaration,
        resolver: Resolver,
    ) {
        val annotation = declaration.annotations
            .firstOrNull { it.shortName.asString() == "ApiRequest" }
            ?: return

        val url = annotation.arguments
            .firstOrNull { it.name?.asString() == "url" }
            ?.value as? String
            ?: run {
                logger.error("@ApiRequest requires a non-empty url", declaration)
                return
            }

        val method = annotation.arguments
            .firstOrNull { it.name?.asString() == "method" }
            ?.value as? String
            ?: "POST"

        val dtoPackageName = declaration.packageName.asString()
        val simpleName = declaration.simpleName.asString()
        val responseType = resolveResponseType(declaration, resolver)
            ?: run {
                logger.error(
                    "Cannot resolve response type from DhRequest<R> for $simpleName",
                    declaration,
                )
                return
            }

        generator.generate(
            requestClassName = simpleName,
            requestPackageName = dtoPackageName,
            requestTypeName = declaration.qualifiedName!!.asString(),
            responseTypeName = responseType,
            url = url,
            method = method,
        )
    }

    private fun resolveResponseType(
        declaration: KSClassDeclaration,
        resolver: Resolver,
    ): String? {
        val supertypes = declaration.superTypes
        for (superType in supertypes) {
            val type = superType.resolve()
            val qualifiedName = type.declaration.qualifiedName?.asString()

            if (qualifiedName == "com.darkhorse.android.core.network.DhRequest") {
                val typeArgs = type.arguments
                if (typeArgs.isNotEmpty()) {
                    val firstArg = typeArgs.first()
                    val resolvedType = firstArg.type?.resolve()
                    if (resolvedType != null) {
                        return resolvedType.declaration.qualifiedName?.asString()
                    }
                }
            }
        }

        for (superType in supertypes) {
            val resolved = superType.resolve()
            val declarationFromResolved = resolved.declaration
            if (declarationFromResolved is KSClassDeclaration) {
                val result = resolveResponseTypeFromHierarchy(
                    declarationFromResolved,
                )
                if (result != null) return result
            }
        }

        return null
    }

    private fun resolveResponseTypeFromHierarchy(
        classDecl: KSClassDeclaration,
    ): String? {
        for (superType in classDecl.superTypes) {
            val type = superType.resolve()
            val qualifiedName = type.declaration.qualifiedName?.asString()

            if (qualifiedName == "com.darkhorse.android.core.network.DhRequest") {
                val typeArgs = type.arguments
                if (typeArgs.isNotEmpty()) {
                    val firstArg = typeArgs.first()
                    val resolvedType = firstArg.type?.resolve()
                    if (resolvedType != null) {
                        return resolvedType.declaration.qualifiedName?.asString()
                    }
                }
            }
        }
        return null
    }
}
