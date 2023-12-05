--
-- PostgreSQL database dump
--

-- Dumped from database version 16.0
-- Dumped by pg_dump version 16.0

-- Started on 2023-12-04 23:16:56

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 217 (class 1259 OID 16422)
-- Name: book; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.book (
    isbn character varying(20) NOT NULL,
    copy_id integer NOT NULL,
    name character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    published_year integer,
    copies_available integer
);


ALTER TABLE public.book OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16417)
-- Name: bookcopies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bookcopies (
    copy_id integer NOT NULL,
    availability_status character varying(20) NOT NULL
);


ALTER TABLE public.bookcopies OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16410)
-- Name: bookstorebranches; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bookstorebranches (
    branch_id integer NOT NULL,
    name character varying(255) NOT NULL,
    address character varying(255)
);


ALTER TABLE public.bookstorebranches OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 16475)
-- Name: has; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.has (
    branch_id integer NOT NULL,
    isbn character varying(20),
    copy_id integer NOT NULL
);


ALTER TABLE public.has OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16443)
-- Name: reserves; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reserves (
    copy_id integer NOT NULL,
    user_id integer,
    reservation_id integer
);


ALTER TABLE public.reserves OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16458)
-- Name: searches; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.searches (
    search_id integer NOT NULL,
    user_id integer,
    isbn character varying(20),
    copy_id integer,
    book_name character varying(255),
    author character varying(255)
);


ALTER TABLE public.searches OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16434)
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
    user_id integer NOT NULL,
    name character varying(255) NOT NULL,
    phn_no character varying(20),
    email_id character varying(255)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- TOC entry 4879 (class 0 OID 16422)
-- Dependencies: 217
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.book (isbn, copy_id, name, author, published_year, copies_available) FROM stdin;
978-1-4000-7890-2	1	The Lord of the Rings: The Fellowship of the Ring	J.R.R. Tolkien	1954	3
978-0-141-04594-3	2	To Kill a Mockingbird	Harper Lee	1960	4
978-0-15-694501-7	3	The Harry Potter and the Sorcerers Stone	J.K. Rowling	1997	3
978-0-316-76715-2	4	The Catcher in the Rye	J.D. Salinger	1951	5
123456789	5	Mahesh Autobio	Mahesh Gali	2029	6
904-567-1245-0	6	Harry Potter: Half Blooded Prince	JK Rowling	1998	8
78949834506	7	Vikash Autobio	Vikash Suthapalli	2038	6
876-567-9876	8	Akatsuki	Naruto	2003	8
\.


--
-- TOC entry 4878 (class 0 OID 16417)
-- Dependencies: 216
-- Data for Name: bookcopies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bookcopies (copy_id, availability_status) FROM stdin;
3	Reserved
4	Reserved
2	Reserved
6	Available
1	Available
7	Available
5	available
8	Reserved
\.


--
-- TOC entry 4877 (class 0 OID 16410)
-- Dependencies: 215
-- Data for Name: bookstorebranches; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bookstorebranches (branch_id, name, address) FROM stdin;
1	Tempe Public Library	3500 S. Rural Rd. Tempe
2	Mesa Public Library	64 E 1st, Mesa
3	Noble Library	601 E Tyler Mall, Tempe
4	Hayden Library	300 E Orange St, Tempe
5		500 E University Dr, Tempe, AZ
6	Northern Arizona Universitty Library	780 W 3rd Street, Flagstaff, AZ
7	Texas Public Library	675 E 4th street, dallas, TX
8	Lowe's Public Library	876 E 6th Street, Tempe, AZ
9	Irving Public Library	567 E 9th Street, Iving, TX
\.


--
-- TOC entry 4883 (class 0 OID 16475)
-- Dependencies: 221
-- Data for Name: has; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.has (branch_id, isbn, copy_id) FROM stdin;
1	978-1-4000-7890-2	1
1	978-0-141-04594-3	2
2	978-0-15-694501-7	3
5	123456789	5
6	123456789	5
\.


--
-- TOC entry 4881 (class 0 OID 16443)
-- Dependencies: 219
-- Data for Name: reserves; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.reserves (copy_id, user_id, reservation_id) FROM stdin;
3	3	1001
1	1	1001
2	2	2002
6	9	631549
4	5	481517
\.


--
-- TOC entry 4882 (class 0 OID 16458)
-- Dependencies: 220
-- Data for Name: searches; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.searches (search_id, user_id, isbn, copy_id, book_name, author) FROM stdin;
1	1	978-1-4000-7890-2	\N	\N	\N
2	2	\N	\N	To Kill a Mockingbird	\N
\.


--
-- TOC entry 4880 (class 0 OID 16434)
-- Dependencies: 218
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (user_id, name, phn_no, email_id) FROM stdin;
1	John Doe	923-456-7890	johndoe@email.com
2	Jane Smith	415-567-8901	janesmith@email.com
3	Alice Johnson	602-678-9012	alicej@email.com
5	Satwik	7654322345	sponnam@asu.edu
6	deepika	6543456789	duppalap@asu.edu
7	vikash	5678901234	vsuthapa@asu.edu
8	raga	7890986543	rgali3@asu.edu
9	tanishq	8907654321	tmanikya@asu.edu
10	Robert	9876542345	ratkins1@asu.edu
11	Jake	8895673456	jakate@asu.edu
12	Kristi	8765432345	kradih1@asu.edu
13	James	6789054321	jmiolh@asu.edu
14	Madison	6895432345	madhh7@asu.edu
15	Jamie Jones	8906541256	jjnoes@asu.edu
16	Matt	6789873456	mattc@asu.edu
17	Thomas	6543456789	thamis@asu.edu
18	Hannah	8765432345	hann1@asu.edu
19	Samira	7654567890	sghyud@asu.edu
21	juan 	8774673456	pablorga@asu.edu
22	Sanjeev	7895468907	sjeev@asu.edu
23	Tim Cook	89765412356	tcook@asu.edu
\.


--
-- TOC entry 4716 (class 2606 OID 16428)
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (isbn, copy_id);


--
-- TOC entry 4714 (class 2606 OID 16421)
-- Name: bookcopies bookcopies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bookcopies
    ADD CONSTRAINT bookcopies_pkey PRIMARY KEY (copy_id);


--
-- TOC entry 4712 (class 2606 OID 16416)
-- Name: bookstorebranches bookstorebranches_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bookstorebranches
    ADD CONSTRAINT bookstorebranches_pkey PRIMARY KEY (branch_id);


--
-- TOC entry 4726 (class 2606 OID 16479)
-- Name: has has_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.has
    ADD CONSTRAINT has_pkey PRIMARY KEY (branch_id, copy_id);


--
-- TOC entry 4722 (class 2606 OID 16447)
-- Name: reserves reserves_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reserves
    ADD CONSTRAINT reserves_pkey PRIMARY KEY (copy_id);


--
-- TOC entry 4724 (class 2606 OID 16464)
-- Name: searches searches_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.searches
    ADD CONSTRAINT searches_pkey PRIMARY KEY (search_id);


--
-- TOC entry 4718 (class 2606 OID 16442)
-- Name: users users_email_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_email_id_key UNIQUE (email_id);


--
-- TOC entry 4720 (class 2606 OID 16440)
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (user_id);


--
-- TOC entry 4727 (class 2606 OID 16429)
-- Name: book book_copy_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_copy_id_fkey FOREIGN KEY (copy_id) REFERENCES public.bookcopies(copy_id);


--
-- TOC entry 4732 (class 2606 OID 16480)
-- Name: has has_branch_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.has
    ADD CONSTRAINT has_branch_id_fkey FOREIGN KEY (branch_id) REFERENCES public.bookstorebranches(branch_id);


--
-- TOC entry 4733 (class 2606 OID 16485)
-- Name: has has_copy_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.has
    ADD CONSTRAINT has_copy_id_fkey FOREIGN KEY (copy_id) REFERENCES public.bookcopies(copy_id);


--
-- TOC entry 4728 (class 2606 OID 16448)
-- Name: reserves reserves_copy_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reserves
    ADD CONSTRAINT reserves_copy_id_fkey FOREIGN KEY (copy_id) REFERENCES public.bookcopies(copy_id);


--
-- TOC entry 4729 (class 2606 OID 16453)
-- Name: reserves reserves_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reserves
    ADD CONSTRAINT reserves_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);


--
-- TOC entry 4730 (class 2606 OID 16470)
-- Name: searches searches_copy_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.searches
    ADD CONSTRAINT searches_copy_id_fkey FOREIGN KEY (copy_id) REFERENCES public.bookcopies(copy_id);


--
-- TOC entry 4731 (class 2606 OID 16465)
-- Name: searches searches_user_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.searches
    ADD CONSTRAINT searches_user_id_fkey FOREIGN KEY (user_id) REFERENCES public.users(user_id);


-- Completed on 2023-12-04 23:16:56

--
-- PostgreSQL database dump complete
--

