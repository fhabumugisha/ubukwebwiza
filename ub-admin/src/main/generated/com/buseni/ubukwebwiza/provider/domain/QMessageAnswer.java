package com.buseni.ubukwebwiza.provider.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessageAnswer is a Querydsl query type for MessageAnswer
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QMessageAnswer extends EntityPathBase<MessageAnswer> {

    private static final long serialVersionUID = 149295635L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessageAnswer messageAnswer = new QMessageAnswer("messageAnswer");

    public final StringPath comment = createString("comment");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> createdDate = createNumber("createdDate", Long.class);

    public final BooleanPath fromProvider = createBoolean("fromProvider");

    public final BooleanPath fromUser = createBoolean("fromUser");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final QMessage message;

    public final StringPath modifiedBy = createString("modifiedBy");

    public final NumberPath<Long> modifiedDate = createNumber("modifiedDate", Long.class);

    public QMessageAnswer(String variable) {
        this(MessageAnswer.class, forVariable(variable), INITS);
    }

    public QMessageAnswer(Path<? extends MessageAnswer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessageAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessageAnswer(PathMetadata metadata, PathInits inits) {
        this(MessageAnswer.class, metadata, inits);
    }

    public QMessageAnswer(Class<? extends MessageAnswer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.message = inits.isInitialized("message") ? new QMessage(forProperty("message"), inits.get("message")) : null;
    }

}

