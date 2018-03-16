package com.buseni.ubukwebwiza.provider.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QDistrict is a Querydsl query type for District
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QDistrict extends EntityPathBase<District> {

    private static final long serialVersionUID = -666271904L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDistrict district = new QDistrict("district");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> createdDate = createNumber("createdDate", Long.class);

    public final BooleanPath enabled = createBoolean("enabled");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.util.Date> lastUpdate = createDateTime("lastUpdate", java.util.Date.class);

    public final StringPath libelle = createString("libelle");

    public final StringPath libelleEn = createString("libelleEn");

    public final StringPath libelleFr = createString("libelleFr");

    public final StringPath libelleKn = createString("libelleKn");

    public final StringPath modifiedBy = createString("modifiedBy");

    public final NumberPath<Long> modifiedDate = createNumber("modifiedDate", Long.class);

    public final QProvince province;

    public final StringPath urlName = createString("urlName");

    public QDistrict(String variable) {
        this(District.class, forVariable(variable), INITS);
    }

    public QDistrict(Path<? extends District> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDistrict(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QDistrict(PathMetadata<?> metadata, PathInits inits) {
        this(District.class, metadata, inits);
    }

    public QDistrict(Class<? extends District> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.province = inits.isInitialized("province") ? new QProvince(forProperty("province")) : null;
    }

}

