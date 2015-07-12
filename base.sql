
-- Main Database: appmanager
CREATE TABLE t_application
(
  id serial NOT NULL,
  content text NOT NULL,
  modifity_date timestamp without time zone,
  name character varying(255) NOT NULL,
  reason text,
  state integer NOT NULL,
  CONSTRAINT applicationentity_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE appmanager.t_state_history 
(
  id serial NOT NULL,
  application_id integer NOT NULL,
  modifity_date timestamp without time zone,
  new_state integer NOT NULL,
  CONSTRAINT state_history_entity_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);


CREATE FUNCTION appmanager.add_history_log() RETURNS trigger AS $add_history_log$
    BEGIN
        IF NEW.state <> OLD.state THEN
            INSERT INTO t_state_history(application_id, new_state, modifity_date) VALUES(OLD.id, NEW.state, current_timestamp);
        END IF;
        RETURN NEW;
    END;
$add_history_log$
LANGUAGE plpgsql;

CREATE TRIGGER appmanager.add_history_log_trigg BEFORE UPDATE ON t_application
    FOR EACH ROW EXECUTE PROCEDURE add_history_log();
    
    
    
    
-- Test Database: appmanager_test
CREATE TABLE t_application
(
  id integer NOT NULL,
  content text NOT NULL,
  modifity_date timestamp without time zone,
  name character varying(255) NOT NULL,
  reason text,
  state integer NOT NULL,
  CONSTRAINT applicationentity_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

CREATE TABLE t_state_history 
(
  id serial NOT NULL,
  application_id integer NOT NULL,
  modifity_date timestamp without time zone,
  new_state integer NOT NULL,
  CONSTRAINT state_history_entity_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);

INSERT INTO t_application(
            id, content, name, reason, state)
    VALUES (1, 'test content 01', 'test name 01', null, 1);

CREATE SEQUENCE t_application_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 50
  CACHE 1;

ALTER TABLE t_application ALTER COLUMN id SET DEFAULT nextval('t_application_id_seq'::regclass);

CREATE FUNCTION add_history_log() RETURNS trigger AS $add_history_log$
    BEGIN
        IF NEW.state <> OLD.state THEN
            INSERT INTO t_state_history(application_id, new_state, modifity_date) VALUES(OLD.id, NEW.state, current_timestamp);
        END IF;
        RETURN NEW;
    END;
$add_history_log$
LANGUAGE plpgsql;

CREATE TRIGGER add_history_log_trigg BEFORE UPDATE ON t_application
    FOR EACH ROW EXECUTE PROCEDURE add_history_log();
