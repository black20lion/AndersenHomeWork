CREATE TYPE public.ticket_type AS ENUM
    ('DAY', 'WEEK', 'MONTH', 'YEAR');

CREATE TABLE IF NOT EXISTS customer
(
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    creation_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ticket (
    id SERIAL PRIMARY KEY,
    customer_id INTEGER,
    ticket_type ticket_type NOT NULL,
    creation_date TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT ticket_user_id_fkey FOREIGN KEY (customer_id)
        REFERENCES public.customer (id)
        ON UPDATE NO ACTION
        ON DELETE CASCADE
);