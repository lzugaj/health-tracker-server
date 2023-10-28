package com.luv2code.health.tracker.rest.mapper;

/**
 * @param <D> - DTO object
 * @param <E> - Entity object
 * */

public interface HealthTrackerMapper<E, D> {

    E toEntity(D dto);

    D toDto(E entity);

}
