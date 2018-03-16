package com.buseni.ubukwebwiza.gallery.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QPhoto is a Querydsl query type for Photo
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPhoto extends EntityPathBase<Photo> {

    private static final long serialVersionUID = -8549431L;

    public static final QPhoto photo = new QPhoto("photo");

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final StringPath contentType = createString("contentType");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> createdDate = createNumber("createdDate", Long.class);

    public final StringPath description = createString("description");

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath filename = createString("filename");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final BooleanPath isGalleryPhoto = createBoolean("isGalleryPhoto");

    public final DateTimePath<java.util.Date> lastUpdate = createDateTime("lastUpdate", java.util.Date.class);

    public final StringPath modifiedBy = createString("modifiedBy");

    public final NumberPath<Long> modifiedDate = createNumber("modifiedDate", Long.class);

    public QPhoto(String variable) {
        super(Photo.class, forVariable(variable));
    }

    public QPhoto(Path<? extends Photo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPhoto(PathMetadata<?> metadata) {
        super(Photo.class, metadata);
    }

}

