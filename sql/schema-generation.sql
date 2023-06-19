CREATE TABLE IF NOT EXISTS public.hotel_search_availability
(
    search_id      varchar(255) not null primary key,
    hotel_id       varchar(255) not null,
    check_in_date  timestamp(6) not null,
    check_out_date timestamp(6) not null,
    ages           jsonb
);

CREATE INDEX IF NOT EXISTS idx_hotel_id_check_in_date
    on hotel_search_availability (hotel_id, check_in_date);

comment on index idx_hotel_id_check_in_date is 'Index for similar searches';
