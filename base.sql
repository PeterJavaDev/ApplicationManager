CREATE TABLE t_application
(
  id serial NOT NULL,
  content character text,
  modifity_date timestamp without time zone,
  name character varying(255),
  reason character text,
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