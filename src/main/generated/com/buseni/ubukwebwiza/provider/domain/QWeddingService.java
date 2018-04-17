package com.buseni.ubukwebwiza.provider.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QWeddingService is a Querydsl query type for WeddingService
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QWeddingService extends EntityPathBase<WeddingService> {

    private static final long serialVersionUID = -496377581L;

    public static final QWeddingService weddingService = new QWeddingService("weddingService");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> createdDate = createNumber("createdDate", Long.class);

    public final BooleanPath deleted = createBoolean("deleted");

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.util.Date> lastUpdate = createDateTime("lastUpdate", java.util.Date.class);

    public final StringPath libelle = createString("libelle");

    public final StringPath libelleEn = createString("libelleEn");

    public final StringPath libelleFr = createString("libelleFr");

    public final StringPath libelleKn = createString("libelleKn");

    public final StringPath modifiedBy = createString("modifiedBy");

    public final NumberPath<Long> modifiedDate = createNumber("modifiedDate", Long.class);

    public final StringPath urlName = createString("urlName");

    public QWeddingService(String variable) {
        super(WeddingService.class, forVariable(variable));
    }

    public QWeddingService(Path<? extends WeddingService> path) {
        super(path.getType(), path.getMetadata());
    }

    public QWeddingService(PathMetadata metadata) {
        super(WeddingService.class, metadata);
    }

}

