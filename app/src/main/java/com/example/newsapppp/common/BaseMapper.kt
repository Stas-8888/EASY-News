package com.example.newsapppp.common

/**
 * Base interface for mappers.
 * Where [E] incoming class, [T] - outgoing class
 */
interface BaseMapper <E, T>{
    fun mapToModel(data: E): T
    fun mapFromModel(data: T): E
}
