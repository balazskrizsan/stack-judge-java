-- requires: preset_add_10_groups.sql
INSERT INTO "review" (id, group_id, visibility, rate, review, created_at, created_by)
VALUES (103001, 101001, 0, 2, 'long review test 1', '2021-01-16 01:50:01', 123),
       (103002, 101001, 0, 2, 'long review test 2', '2021-01-17 01:50:01', 123),
       (103003, 101004, 0, 2, 'long review test 3', '2021-01-18 01:50:01', 123),
       (103004, 101004, 0, 2, 'long review test 4', '2021-01-19 01:50:01', 123),
       (103005, 101005, 0, 2, 'long review test 5', '2021-01-20 01:50:01', 123),
       (103006, 101006, 0, 2, 'long review test 6', '2021-01-21 01:50:01', 123),
       (103007, 101007, 0, 2, 'long review test 7', '2021-01-22 01:50:01', 123),
       (103008, 101008, 0, 2, 'long review test 8', '2021-01-23 01:50:01', 123),
       (103009, 101009, 0, 2, 'long review test 9', '2021-01-24 01:50:01', 123),
       (103010, 101010, 0, 2, 'long review test106', '2021-01-25 01:50:01', 123);
