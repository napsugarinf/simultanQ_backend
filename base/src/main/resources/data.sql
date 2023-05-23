INSERT into quizes (pin, title) VALUES (9876,'First quiz');
INSERT into questions (text, quiz_id) VALUES ('In which year did the first colour television come out?', 1);
INSERT into answers (correct, description, question_id) VALUES (false,'1954', 1);
INSERT into answers (correct, description, question_id) VALUES (false,'1936', 1);
INSERT into answers (correct, description, question_id) VALUES (false, '1945', 1);
INSERT into answers (correct, description, question_id) VALUES (true,'1928', 1);

INSERT into questions (text, quiz_id) VALUES ('Who invented the light bulb?', 1);
INSERT into answers (correct, description, question_id) VALUES (false,'Benjamin Franklin', 2);
INSERT into answers (correct, description, question_id) VALUES (false,'Nikola Tesla', 2);
INSERT into answers (correct, description, question_id) VALUES (false, 'Alexander Graham Bell', 2);
INSERT into answers (correct, description, question_id) VALUES (true,'Thomas Edison', 2);

INSERT into questions (text, quiz_id) VALUES ('Who invented the first printing press?', 1);
INSERT into answers (correct, description, question_id) VALUES (false,'Hans Dünne', 3);
INSERT into answers (correct, description, question_id) VALUES (false,'Andreas Dritzehn', 3);
INSERT into answers (correct, description, question_id) VALUES (false, 'John Calvin', 3);
INSERT into answers (correct, description, question_id) VALUES (true,'Johannes Gutenberg', 3);

INSERT into questions (text, quiz_id) VALUES ('Who is known for formulating the law of gravitation?', 1);
INSERT into answers (correct, description, question_id) VALUES (false,'Einstein', 4);
INSERT into answers (correct, description, question_id) VALUES (false,'Bolyai Farkas', 4);
INSERT into answers (correct, description, question_id) VALUES (false, 'Gottfried Leibniz', 4);
INSERT into answers (correct, description, question_id) VALUES (true,'Isaac Newton', 4);