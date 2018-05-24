package com.buseni.ubukwebwiza.account.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRole is a Querydsl query type for Role
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRole extends EntityPathBase<Role> {

    private static final long serialVersionUID = 1592411738L;

    public static final QRole role = new QRole("role");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath name = createString("name");

    public final ListPath<Privilege, QPrivilege> privileges = this.<Privilege, QPrivilege>createList("privileges", Privilege.class, QPrivilege.class, PathInits.DIRECT2);

    public final ListPath<UserAccount, QUserAccount> UserAccounts = this.<UserAccount, QUserAccount>createList("UserAccounts", UserAccount.class, QUserAccount.class, PathInits.DIRECT2);

    public QRole(String variable) {
        super(Role.class, forVariable(variable));
    }

    public QRole(Path<? extends Role> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRole(PathMetadata metadata) {
        super(Role.class, metadata);
    }

}

