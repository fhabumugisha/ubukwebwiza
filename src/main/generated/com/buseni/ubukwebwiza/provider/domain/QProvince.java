package com.buseni.ubukwebwiza.provider.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QProvince is a Querydsl query type for Province
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QProvince extends EntityPathBase<Province> {

    private static final long serialVersionUID = -1942718718L;

    public static final QProvince province = new QProvince("province");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> createdDate = createNumber("createdDate", Long.class);

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.util.Date> lastUpdate = createDateTime("lastUpdate", java.util.Date.class);

    public final StringPath libelle = createString("libelle");

    public final StringPath modifiedBy = createString("modifiedBy");

    public final NumberPath<Long> modifiedDate = createNumber("modifiedDate", Long.class);

    public QProvince(String variable) {
        super(Province.class, forVariable(variable));
    }

    public QProvince(Path<? extends Province> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProvince(PathMetadata<?> metadata) {
        super(Province.class, metadata);
    }

}

