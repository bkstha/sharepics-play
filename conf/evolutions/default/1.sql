# --- !Ups


CREATE TABLE "USERS"
(
  "id"               VARCHAR(36)              NOT NULL,
  "name"             VARCHAR(50)              NOT NULL,
  "email"            VARCHAR(100)             NOT NULL,
  "user_profile_url" VARCHAR(100),
  "user_type"        VARCHAR(2)               NOT NULL,
  "last_login"       TIMESTAMP WITH TIME ZONE,
  "created"          TIMESTAMP WITH TIME ZONE NOT NULL,
  "modified"         TIMESTAMP WITH TIME ZONE,
  "active"           BOOLEAN                  NOT NULL,
  CONSTRAINT "USERS_pkey" PRIMARY KEY ("id")
);

CREATE TABLE "IMAGES"
(
  "id"                 VARCHAR(36)              NOT NULL,
  "title"              VARCHAR(50)              NOT NULL,
  --   "path"                 VARCHAR(100),
  "image_display_type" VARCHAR(2)               NOT NULL,
  "created_at"         TIMESTAMP WITH TIME ZONE NOT NULL,
  "created_by"         VARCHAR(36)              NOT NULL,
  "modified_at"        TIMESTAMP WITH TIME ZONE,
  CONSTRAINT "IMAGES_pkey" PRIMARY KEY (id)
);

CREATE TABLE "TAGS"
(
  "id"    SERIAL,
  "title" VARCHAR(25) UNIQUE NOT NULL,
  CONSTRAINT TAGS_pkey PRIMARY KEY (id)
);

CREATE TABLE "IMAGES_TAGS"
(
  "image_id" VARCHAR(36) NOT NULL,
  "tag_id"   INT         NOT NULL,
  CONSTRAINT fk_image_id FOREIGN KEY ("image_id") REFERENCES "IMAGES" (id),
  CONSTRAINT fk_tag_id FOREIGN KEY ("tag_id") REFERENCES "TAGS" (id),
  CONSTRAINT IMAGES_TAGS_pkey PRIMARY KEY (image_id, tag_id)
);

# --- !Downs

DROP TABLE "USERS" CASCADE;
DROP TABLE "IMAGES" CASCADE;
DROP TABLE "TAGS" CASCADE;
DROP TABLE "IMAGES_TAGS" CASCADE;