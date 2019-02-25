# --- !Ups


CREATE TABLE "USERS" (
  "id"                VARCHAR(36)               NOT NULL,
  "name"              VARCHAR(50)               NOT NULL,
  "email"             VARCHAR(100)              NOT NULL,
  "user_profile_url"  VARCHAR(100),
  "user_type"         VARCHAR(2)                NOT NULL,
  "last_login"        TIMESTAMP WITH TIME ZONE,
  "created"           TIMESTAMP WITH TIME ZONE  NOT NULL,
  "modified"          TIMESTAMP WITH TIME ZONE,
  "active"            BOOLEAN                   NOT NULL,
   CONSTRAINT "USERS_pkey" PRIMARY KEY ("id")
);

# --- !Downs

DROP TABLE "USERS" CASCADE;