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


ALTER TABLE score OWNER TO trubnikov;

--
-- Name: score_id_seq; Type: SEQUENCE; Schema: public; Owner: trubnikov
--

CREATE SEQUENCE score_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE score_id_seq OWNER TO trubnikov;

--
-- Name: score_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: trubnikov
--

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


ALTER TABLE users OWNER TO trubnikov;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: trubnikov
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO trubnikov;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: trubnikov
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: trubnikov
--

ALTER TABLE ONLY score ALTER COLUMN id SET DEFAULT nextval('score_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: trubnikov
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Name: score_id_pk; Type: CONSTRAINT; Schema: public; Owner: trubnikov
--

ALTER TABLE ONLY score
    ADD CONSTRAINT score_id_pk PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: trubnikov
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);




--
-- Name: users_email_uindex; Type: INDEX; Schema: public; Owner: trubnikov
--

CREATE UNIQUE INDEX users_email_uindex ON users USING btree (email);


--
-- Name: users_username_uindex; Type: INDEX; Schema: public; Owner: trubnikov
--

CREATE UNIQUE INDEX users_username_uindex ON users USING btree (username);


--
-- PostgreSQL database dump complete
--

