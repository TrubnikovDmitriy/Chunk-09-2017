--
-- PostgreSQL database dump
--
--
-- Name: score; Type: TABLE; Schema: public; Owner: trubnikov
--

CREATE TABLE score (
    id bigint NOT NULL,
    result double precision NOT NULL,
    "time" timestamp without time zone DEFAULT now() NOT NULL,
    user_id bigint NOT NULL
);


--
-- Name: score_id_seq; Type: SEQUENCE; Schema: public; Owner: trubnikov
--

CREATE SEQUENCE score_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

ALTER SEQUENCE score_id_seq OWNED BY score.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: trubnikov
--

CREATE TABLE users (
    id bigint NOT NULL,
    username character varying(40) NOT NULL,
    password text NOT NULL,
    email character varying(50) NOT NULL
);


--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: trubnikov
--

CREATE SEQUENCE users_id_seq
START WITH 1
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;


ALTER SEQUENCE users_id_seq OWNED BY users.id;
ALTER TABLE ONLY score ALTER COLUMN id SET DEFAULT nextval('score_id_seq'::regclass);
ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


ALTER TABLE ONLY score
    ADD CONSTRAINT score_id_pk PRIMARY KEY (id);
ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);
CREATE UNIQUE INDEX users_email_uindex ON users USING btree (email);
CREATE UNIQUE INDEX users_username_uindex ON users USING btree (username);


--
-- PostgreSQL database dump complete
--

