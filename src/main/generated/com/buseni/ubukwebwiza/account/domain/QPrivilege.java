package com.buseni.ubukwebwiza.account.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPrivilege is a Querydsl query type for Privilege
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPrivilege extends EntityPathBase<Privilege> {

    private static final long serialVersionUID = 59391821L;

    public static final QPrivilege privilege = new QPrivilege("privilege");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final CollectionPath<Role, QRole> roles = this.<Role, QRole>createCollection("roles", Role.class, QRole.class, PathInits.DIRECT2);

    public QPrivilege(String variable) {
        super(Privilege.class, forVariable(variable));
    }

    public QPrivilege(Path<? extends Privilege> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPrivilege(PathMetadata<?> metadata) {
        super(Privilege.class, metadata);
    }

}

