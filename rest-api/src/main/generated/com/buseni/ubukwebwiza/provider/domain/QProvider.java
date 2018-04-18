package com.buseni.ubukwebwiza.provider.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProvider is a Querydsl query type for Provider
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QProvider extends EntityPathBase<Provider> {

    private static final long serialVersionUID = -1942728253L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProvider provider = new QProvider("provider");

    public final StringPath aboutme = createString("aboutme");

    public final com.buseni.ubukwebwiza.account.domain.QUserAccount account;

    public final StringPath address = createString("address");

    public final StringPath businessName = createString("businessName");

    public final StringPath country = createString("country");

    public final com.buseni.ubukwebwiza.gallery.domain.QPhoto coverPicture;

    public final StringPath createdBy = createString("createdBy");

    public final NumberPath<Long> createdDate = createNumber("createdDate", Long.class);

    public final QDistrict district;

    public final StringPath fbUsername = createString("fbUsername");

    public final NumberPath<Integer> id = createNumber("id", Integer.class);

    public final StringPath modifiedBy = createString("modifiedBy");

    public final NumberPath<Long> modifiedDate = createNumber("modifiedDate", Long.class);

    public final NumberPath<Integer> nbViews = createNumber("nbViews", Integer.class);

    public final StringPath phoneNumber = createString("phoneNumber");

    public final SetPath<com.buseni.ubukwebwiza.gallery.domain.Photo, com.buseni.ubukwebwiza.gallery.domain.QPhoto> photos = this.<com.buseni.ubukwebwiza.gallery.domain.Photo, com.buseni.ubukwebwiza.gallery.domain.QPhoto>createSet("photos", com.buseni.ubukwebwiza.gallery.domain.Photo.class, com.buseni.ubukwebwiza.gallery.domain.QPhoto.class, PathInits.DIRECT2);

    public final com.buseni.ubukwebwiza.gallery.domain.QPhoto profilPicture;

    public final SetPath<ProviderWeddingService, QProviderWeddingService> providerWeddingServices = this.<ProviderWeddingService, QProviderWeddingService>createSet("providerWeddingServices", ProviderWeddingService.class, QProviderWeddingService.class, PathInits.DIRECT2);

    public final StringPath twitterUsername = createString("twitterUsername");

    public final StringPath urlName = createString("urlName");

    public final StringPath website = createString("website");

    public QProvider(String variable) {
        this(Provider.class, forVariable(variable), INITS);
    }

    public QProvider(Path<? extends Provider> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProvider(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProvider(PathMetadata metadata, PathInits inits) {
        this(Provider.class, metadata, inits);
    }

    public QProvider(Class<? extends Provider> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.account = inits.isInitialized("account") ? new com.buseni.ubukwebwiza.account.domain.QUserAccount(forProperty("account")) : null;
        this.coverPicture = inits.isInitialized("coverPicture") ? new com.buseni.ubukwebwiza.gallery.domain.QPhoto(forProperty("coverPicture")) : null;
        this.district = inits.isInitialized("district") ? new QDistrict(forProperty("district"), inits.get("district")) : null;
        this.profilPicture = inits.isInitialized("profilPicture") ? new com.buseni.ubukwebwiza.gallery.domain.QPhoto(forProperty("profilPicture")) : null;
    }

}

