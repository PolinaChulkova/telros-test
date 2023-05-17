CREATE TABLE IF NOT EXISTS public.users
(
    user_id bigserial NOT NULL,
    login character varying(100) NOT NULL,
    password character varying(255),
    email character varying(255),
    firstname character varying(255),
    lastname character varying(255),
    surname character varying(255),
    telephone character varying(255),
    date_birth timestamp(6) without time zone,
    avatar character varying(255),
    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT uk_email_users UNIQUE (email),
    CONSTRAINT uk_login_users UNIQUE (login)
);

CREATE TABLE IF NOT EXISTS public.roles
(
    role_id bigserial NOT NULL,
    role_name character varying(255),
    CONSTRAINT roles_pkey PRIMARY KEY (role_id)
);

CREATE TABLE IF NOT EXISTS public.user_roles
(
    user_id bigint NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_role_id_user_roles FOREIGN KEY (role_id)
        REFERENCES public.roles (role_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT fk_user_id_user_roles FOREIGN KEY (user_id)
        REFERENCES public.users (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

INSERT INTO public.roles (role_id, role_name)
VALUES
(1, 'ADMIN'),
(2, 'USER');