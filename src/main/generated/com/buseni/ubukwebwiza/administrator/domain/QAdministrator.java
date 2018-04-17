package com.buseni.ubukwebwiza.administrator.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAdministrator is a Querydsl query type for Administrator
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QAdministrator extends EntityPathBase<Administrator> {

    private static final long serialVersionUID = 1774059785L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAdministrator administrator = new QAdministrator("administrator");

    public final com.buseni.ubukwebwiza.account.domain.QUserAccount account;

    public final StringPath firstName = createString("firstName");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath lastName = createString("lastName");

    public QAdministrator(String variable) {
        this(Administrator.class, forVariable(variable), INITS);
    }

    public QAdministrator(Path<? extends Administrator> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAdministrator(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAdministrator(PathMetadata metadata, PathInits inits) {
        this(Administrator.class, metadata, inits);
    }

    public QAdministrator(Class<? extends Administrator> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.buseni.ubukwebwiza.account.domain.QUserAccount(forProperty("account")) : null;
    }

}

