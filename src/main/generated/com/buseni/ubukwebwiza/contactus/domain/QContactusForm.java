package com.buseni.ubukwebwiza.contactus.domain;

import static com.mysema.query.types.PathMetadataFactory.*;

import com.mysema.query.types.path.*;

import com.mysema.query.types.PathMetadata;
import javax.annotation.Generated;
import com.mysema.query.types.Path;


/**
 * QContactusForm is a Querydsl query type for ContactusForm
 */
@Generated("com.mysema.query.codegen.EntitySerializer")
public class QContactusForm extends EntityPathBase<ContactusForm> {

    private static final long serialVersionUID = -1671000563L;

    public static final QContactusForm contactusForm = new QContactusForm("contactusForm");

    public final DateTimePath<java.util.Date> createdAt = createDateTime("createdAt", java.util.Date.class);

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> createdDate = createNumber("createdDate", Long.class);

    public final StringPath email = createString("email");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final DateTimePath<java.util.Date> lastUpdate = createDateTime("lastUpdate", java.util.Date.class);

    public final StringPath message = createString("message");

    public final StringPath modifiedBy = createString("modifiedBy");

    public final NumberPath<Long> modifiedDate = createNumber("modifiedDate", Long.class);

    public final StringPath name = createString("name");

    public final BooleanPath readed = createBoolean("readed");

    public QContactusForm(String variable) {
        super(ContactusForm.class, forVariable(variable));
    }

    public QContactusForm(Path<? extends ContactusForm> path) {
        super(path.getType(), path.getMetadata());
    }

    public QContactusForm(PathMetadata<?> metadata) {
        super(ContactusForm.class, metadata);
    }

}

