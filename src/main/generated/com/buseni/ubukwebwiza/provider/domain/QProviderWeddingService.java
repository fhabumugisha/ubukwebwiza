package com.buseni.ubukwebwiza.provider.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QProviderWeddingService is a Querydsl query type for ProviderWeddingService
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QProviderWeddingService extends EntityPathBase<ProviderWeddingService> {

    private static final long serialVersionUID = -1822652572L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProviderWeddingService providerWeddingService = new QProviderWeddingService("providerWeddingService");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> createdDate = createNumber("createdDate", Long.class);

    public final StringPath description = createString("description");

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.util.Date> lastUpdate = createDateTime("lastUpdate", java.util.Date.class);

    public final StringPath modifiedBy = createString("modifiedBy");

    public final NumberPath<Long> modifiedDate = createNumber("modifiedDate", Long.class);

    public final NumberPath<java.math.BigDecimal> price = createNumber("price", java.math.BigDecimal.class);

    public final QProvider provider;

    public final QWeddingService weddingService;

    public QProviderWeddingService(String variable) {
        this(ProviderWeddingService.class, forVariable(variable), INITS);
    }

    public QProviderWeddingService(Path<? extends ProviderWeddingService> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProviderWeddingService(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QProviderWeddingService(PathMetadata<?> metadata, PathInits inits) {
        this(ProviderWeddingService.class, metadata, inits);
    }

    public QProviderWeddingService(Class<? extends ProviderWeddingService> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.provider = inits.isInitialized("provider") ? new QProvider(forProperty("provider"), inits.get("provider")) : null;
        this.weddingService = inits.isInitialized("weddingService") ? new QWeddingService(forProperty("weddingService")) : null;
    }

}

