package com.ahmed.a.habib.lazaapp.model.entity;

import jakarta.persistence.MappedSuperclass;
import org.hibernate.annotations.FilterDef;

import static com.ahmed.a.habib.lazaapp.utils.PosConstants.DELETED_FILTER;

@MappedSuperclass
@FilterDef(name = DELETED_FILTER)
public abstract class BaseEntity {
}
