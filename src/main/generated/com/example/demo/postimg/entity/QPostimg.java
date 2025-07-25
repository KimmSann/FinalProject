package com.example.demo.postimg.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostimg is a Querydsl query type for Postimg
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostimg extends EntityPathBase<Postimg> {

    private static final long serialVersionUID = 1763760669L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostimg postimg = new QPostimg("postimg");

    public final NumberPath<Integer> fileid = createNumber("fileid", Integer.class);

    public final com.example.demo.post.entity.QPost postid;

    public final StringPath storedFileName = createString("storedFileName");

    public QPostimg(String variable) {
        this(Postimg.class, forVariable(variable), INITS);
    }

    public QPostimg(Path<? extends Postimg> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostimg(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostimg(PathMetadata metadata, PathInits inits) {
        this(Postimg.class, metadata, inits);
    }

    public QPostimg(Class<? extends Postimg> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.postid = inits.isInitialized("postid") ? new com.example.demo.post.entity.QPost(forProperty("postid"), inits.get("postid")) : null;
    }

}

