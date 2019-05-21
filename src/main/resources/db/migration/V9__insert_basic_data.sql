INSERT INTO carrental."user" (id, role, status, mail, first_name, last_name, login, salt, password, created_at, modified_at)
VALUES (nextval('carrental.user_seq_id'),'A','A','test@test.pl','test','test','test','$2a$10$JgWqnymrT/62HkaEWmCEm.','$2a$10$JgWqnymrT/62HkaEWmCEm.5KBX43r6bsKZHgvcpAf39hdOssEfj.q','2019-05-04 22:45:59.053000','2019-05-04 22:45:59.105000'),
       (nextval('carrental.user_seq_id'),'U','A','test0@test.pl','test','test','test0','$2a$10$24bkNuNQ7KCI/58h2zGGke','$2a$10$24bkNuNQ7KCI/58h2zGGke2B15I4e7/trpTx87Xirtzd1MqNdM2sy','2019-05-04 22:46:08.259000','2019-05-04 22:46:08.261000') ON CONFLICT DO NOTHING;

--INSERT INTO carrental.garage (id, name, address) VALUES (nextval('carrental.garage_seq_id'),'Fast Car Rentings','Rzeszów, ul. Wyspiańskiego 17') ON CONFLICT DO NOTHING;

--INSERT INTO carrental.car(id, user_id, register_name, mark, model, engine, power, garage_id, created_at, modified_at) VALUES (nextval('carrental.car_seq_id'),1,'RZE 29034','Skoda','Superb',2000,170,'1','2019-05-04 22:45:59.053000','2019-05-04 22:45:59.053000') ON CONFLICT DO NOTHING;

--INSERT INTO carrental.car_detail(id, car_id, price, status, mileage) VALUES (nextval('carrental.car_detail_seq_id'),1,300,'READY_TO_RENT',20) ON CONFLICT DO NOTHING;