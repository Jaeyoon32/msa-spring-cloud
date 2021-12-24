package com.kidd.msacatalogservice.service;

import com.kidd.msacatalogservice.repository.CatalogEntity;

public interface CatalogService {
    Iterable<CatalogEntity> getAllCatalogs();
}
