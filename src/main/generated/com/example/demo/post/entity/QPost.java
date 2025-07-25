package com.example.demo.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPost is a Querydsl query type for Post
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPost extends EntityPathBase<Post> {

    private static final long serialVersionUID = 2047840023L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPost post = new QPost("post");

    public final com.example.demo.board.entity.QBoard boardid;

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> creatdate = createDateTime("creatdate", java.time.LocalDateTime.class);

    public final NumberPath<Integer> likecount = createNumber("likecount", Integer.class);

    public final NumberPath<Integer> postid = createNumber("postid", Integer.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> unlikecount = createNumber("unlikecount", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> updatdate = createDateTime("updatdate", java.time.LocalDateTime.class);

    public final com.example.demo.user.entity.QUser userid;

    public final NumberPath<Integer> viewcount = createNumber("viewcount", Integer.class);

    public QPost(String variable) {
        this(Post.class, forVariable(variable), INITS);
    }

    public QPost(Path<? extends Post> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPost(PathMetadata metadata, PathInits inits) {
        this(Post.class, metadata, inits);
    }

    public QPost(Class<? extends Post> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.boardid = inits.isInitialized("boardid") ? new com.example.demo.board.entity.QBoard(forProperty("boardid"), inits.get("boardid")) : null;
        this.userid = inits.isInitialized("userid") ? new com.example.demo.user.entity.QUser(forProperty("userid")) : null;
    }

}

