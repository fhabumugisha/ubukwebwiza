package com.buseni.ubukwebwiza.account.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;
import com.mysema.query.types.path.PathInits;


/**
 * QPasswordResetToken is a Querydsl query type for PasswordResetToken
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QPasswordResetToken extends EntityPathBase<PasswordResetToken> {

    private static final long serialVersionUID = -942891575L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPasswordResetToken passwordResetToken = new QPasswordResetToken("passwordResetToken");

    public final DateTimePath<java.util.Date> expiryDate = createDateTime("expiryDate", java.util.Date.class);

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath token = createString("token");

    public final QUserAccount userAccount;

    public QPasswordResetToken(String variable) {
        this(PasswordResetToken.class, forVariable(variable), INITS);
    }

    public QPasswordResetToken(Path<? extends PasswordResetToken> path) {
        this(path.getType(), path.getMetadata(), path.getMetadata().isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPasswordResetToken(PathMetadata<?> metadata) {
        this(metadata, metadata.isRoot() ? INITS : PathInits.DEFAULT);
    }

    public QPasswordResetToken(PathMetadata<?> metadata, PathInits inits) {
        this(PasswordResetToken.class, metadata, inits);
    }

    public QPasswordResetToken(Class<? extends PasswordResetToken> type, PathMetadata<?> metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userAccount = inits.isInitialized("userAccount") ? new QUserAccount(forProperty("userAccount")) : null;
    }

}

