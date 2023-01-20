package com.example.newsapppp.core

interface BaseMapper <Entity, DomainModel>{

    fun mapFromEntity(data: Entity): DomainModel

    fun mapToEntity(data: DomainModel): Entity
}