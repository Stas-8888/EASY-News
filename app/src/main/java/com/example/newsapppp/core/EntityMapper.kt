package com.example.newsapppp.core

interface EntityMapper <Entity, DomainModel>{

    fun mapFromEntity(data: Entity): DomainModel

    fun mapToEntity(data: DomainModel): Entity
}
