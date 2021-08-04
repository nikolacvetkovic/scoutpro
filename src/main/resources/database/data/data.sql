insert into app_user (username, pass, enabled) values ('cvele', '1111', 1);

insert into player values
(1, 90, 34, 50, 96, 96, 50, 'Without Club', 'Early Cross,Trickster,Speeding Bullet,Mazing Run,Incisive Run', '-', 90, 'Jun 24, 1987 ', 45, 97, 95, 'LEFT', 8, 40, 40, 40, 40, 40, 70, 2, NULL, 63, 82, 91, 90, TIMESTAMP '2021-07-29 18:06:27.396607', 'Argentina', 'Argentina', 95, 'SS,CF', 'LWF,AMF,RMF', 94, TIMESTAMP '2021-07-29 18:06:27.395608', 'L. MESSI', 'FC BARCELONA', 'https://pesdb.net/pes2021/?id=7511', 70, 94, 'Lionel Messi', 'Long Range Drive,Captaincy,Through Passing,One-touch Pass,Penalty Specialist,Dipping Shot,Chip Shot Control,Long Range Shooting,Double Touch,First-time Shot', 'Creative Playmaker', 'RWF', TIMESTAMP '2021-07-29 18:06:55.026275', 'AC Milan', 'https://psml.rs/index.php?action=shwply&playerID=7511', 80000000.00, 81, 78, NULL, NULL, NULL, 96, TIMESTAMP '2021-07-29 18:06:27.420604', 'attack - Right Winger', 'https://www.transfermarkt.com/lionel-messi/profil/spieler/28003', 3, 1, NULL, 'C', 'https://www.whoscored.com/Players/11119/Show/Lionel-Messi'),
(2, 83, 24, 78, 80, 86, 75, 'FC Barcelona', 'Long Ball Expert,Mazing Run', 'Jun 30, 2026', 71, 'May 12, 1997 ', 71, 85, 67, 'RIGHT', 5, 40, 40, 40, 40, 40, 74, 2, NULL, 78, 74, 87, 89, TIMESTAMP '2021-07-29 18:10:32.771326', 'Netherlands', 'Netherlands', 77, 'DMF', 'AMF,CB', 87, TIMESTAMP '2021-07-29 18:10:32.688323', 'F. DE JONG', 'FC BARCELONA', 'https://pesdb.net/pes2021/?id=108662', 72, 70, 'Frenkie de Jong', 'Step On Skill Control,Weighted Pass,Through Passing,Outside Curler,One-touch Pass,Heel Trick,Gamesmanship,Cut Behind & Turn,Scotch Move,Double Touch', 'Orchestrator', 'CMF', TIMESTAMP '2021-07-29 18:11:06.930629', 'Atomic Ants FC', 'https://psml.rs/index.php?action=shwply&playerID=108662', 40000000.00, 81, 84, NULL, NULL, NULL, 88, TIMESTAMP '2021-07-29 18:10:32.773324', 'midfield - Central Midfield', 'https://www.transfermarkt.com/frenkie-de-jong/profil/spieler/326330', 3, 3, NULL, 'C', 'https://www.whoscored.com/Players/279423/Show/Frenkie-de-Jong'),
(3, 81, 28, 82, 72, 76, 50, 'Inter Milan', 'Mazing Run', 'Jun 30, 2024', 80, 'May 13, 1993 ', 50, 80, 87, 'LEFT', 6, 40, 40, 40, 40, 40, 86, 2, NULL, 84, 87, 77, 72, TIMESTAMP '2021-08-03 10:58:47.809075', 'Belgium', 'Belgium', 86, 'SS', NULL, 86, TIMESTAMP '2021-08-03 10:58:48.220076', 'R. LUKAKU', 'LOMBARDIA NA', 'https://pesdb.net/pes2021/?id=40122', 95, 70, 'Romelu Lukaku', 'Long Range Drive,Heading,Captaincy,Outside Curler,Penalty Specialist,Gamesmanship,First-time Shot,Fighting Spirit', 'Fox in the Box', 'CF', TIMESTAMP '2021-08-03 10:59:08.209608', 'Hull City', 'https://psml.rs/index.php?action=shwply&playerID=40122', 30000000.00, 88, 82, NULL, NULL, NULL, 73, TIMESTAMP '2021-08-03 10:58:47.89531', 'attack - Centre-Forward', 'https://www.transfermarkt.com/romelu-lukaku/profil/spieler/96341', 3, 2, NULL, 'C', 'https://www.whoscored.com/Players/78498/Show/Romelu-Lukaku');

insert into app_user_player values
(1, 0, 1),
(1, 0, 2);

insert into transfer values
(1, DATE '2021-07-01', 'Barcelona', STRINGDECODE('\u20ac80.00m'), 'Without Club', '-', 1),
(2, DATE '2005-07-01', 'Barcelona B', STRINGDECODE('\u20ac3.00m'), 'Barcelona', '-', 1),
(3, DATE '2000-07-01', 'Newell''s U19', '-', STRINGDECODE('Bar\u00e7a Youth'), 'free transfer', 1),
(4, DATE '2002-07-01', STRINGDECODE('Bar\u00e7a Youth'), '-', 'Barcelona U16', '-', 1),
(5, DATE '2003-11-28', 'Barcelona U19', '-', 'Barcelona C', '-', 1),
(6, DATE '2003-09-13', 'Barcelona U16', '-', 'Barcelona U19', '-', 1),
(7, DATE '2004-03-05', 'Barcelona C', '-', 'Barcelona B', '-', 1),
(8, DATE '2019-07-01', 'Ajax', STRINGDECODE('\u20ac85.00m'), 'Barcelona', STRINGDECODE('\u20ac86.00m'), 2),
(9, DATE '2015-08-23', 'Ajax U21', STRINGDECODE('\u20ac250Th.'), 'Willem II', 'loan transfer', 2),
(10, DATE '2016-07-01', 'Ajax U21', STRINGDECODE('\u20ac600Th.'), 'Ajax', '-', 2),
(11, DATE '2015-02-01', 'Willem II U19', '-', 'Willem II U21', '-', 2),
(12, DATE '2015-12-31', 'Willem II', STRINGDECODE('\u20ac400Th.'), 'Ajax U21', 'End of loan', 2),
(13, DATE '2015-08-22', 'Willem II', STRINGDECODE('\u20ac250Th.'), 'Ajax U21', STRINGDECODE('\u20ac10.13m'), 2),
(14, DATE '2014-09-01', 'Willem II U18', '-', 'Willem II U19', '-', 2),
(15, DATE '2015-05-01', 'Willem II U21', '-', 'Willem II', '-', 2),
(16, DATE '2014-05-10', 'Willem II/RKC-17', '-', 'Willem II U18', '-', 2),
(17, DATE '2013-07-01', 'Willem II/RKC Y.', '-', 'Willem II/RKC-17', '-', 2),
(18, DATE '2019-08-08', 'Man Utd', STRINGDECODE('\u20ac75.00m'), 'Inter', STRINGDECODE('\u20ac74.00m'), 3),
(19, DATE '2011-08-08', 'RSC Anderlecht', STRINGDECODE('\u20ac15.00m'), 'Chelsea', STRINGDECODE('\u20ac15.00m'), 3),
(20, DATE '2014-05-31', 'Everton', STRINGDECODE('\u20ac25.00m'), 'Chelsea', 'End of loan', 3),
(21, DATE '2012-08-10', 'Chelsea', STRINGDECODE('\u20ac14.00m'), 'West Brom', 'loan transfer', 3),
(22, DATE '2009-07-01', 'Anderlecht U17', STRINGDECODE('\u20ac400Th.'), 'RSC Anderlecht', '-', 3),
(23, DATE '2008-07-01', 'Anderlecht Yth.', '-', 'Anderlecht U17', '-', 3),
(24, DATE '2017-07-10', 'Everton', STRINGDECODE('\u20ac50.00m'), 'Man Utd', STRINGDECODE('\u20ac84.70m'), 3),
(25, DATE '2006-07-01', 'Lierse SK Yth.', '-', 'Anderlecht Yth.', '?', 3),
(26, DATE '2013-09-02', 'Chelsea', STRINGDECODE('\u20ac24.00m'), 'Everton', STRINGDECODE('Loan fee: \u20ac3.50m'), 3),
(27, DATE '2013-05-31', 'West Brom', STRINGDECODE('\u20ac19.00m'), 'Chelsea', 'End of loan', 3),
(28, DATE '2014-07-30', 'Chelsea', STRINGDECODE('\u20ac25.00m'), 'Everton', STRINGDECODE('\u20ac35.36m'), 3);

insert into market_value values
(1, 'FC Barcelona B', DATE '2004-12-20', 3000000.00, 1),
(2, 'FC Barcelona', DATE '2011-07-29', 100000000.00, 1),
(3, 'FC Barcelona', DATE '2012-08-07', 120000000.00, 1),
(4, 'FC Barcelona', DATE '2017-01-24', 120000000.00, 1),
(5, 'FC Barcelona', DATE '2020-04-08', 112000000.00, 1),
(6, 'FC Barcelona', DATE '2011-02-04', 100000000.00, 1),
(7, 'FC Barcelona', DATE '2016-07-15', 120000000.00, 1),
(8, 'FC Barcelona', DATE '2006-01-20', 15000000.00, 1),
(9, 'FC Barcelona', DATE '2019-06-11', 150000000.00, 1),
(10, 'FC Barcelona', DATE '2014-01-23', 120000000.00, 1),
(11, 'FC Barcelona', DATE '2009-01-26', 55000000.00, 1),
(12, 'FC Barcelona', DATE '2014-07-20', 120000000.00, 1),
(13, 'FC Barcelona', DATE '2005-12-28', 5000000.00, 1),
(14, 'FC Barcelona', DATE '2019-12-20', 140000000.00, 1),
(15, 'FC Barcelona', DATE '2010-08-27', 100000000.00, 1),
(16, 'FC Barcelona', DATE '2013-06-12', 120000000.00, 1),
(17, 'FC Barcelona', DATE '2015-07-01', 120000000.00, 1),
(18, 'FC Barcelona', DATE '2010-04-12', 80000000.00, 1),
(19, 'FC Barcelona', DATE '2020-10-08', 100000000.00, 1),
(20, 'FC Barcelona', DATE '2008-02-04', 55000000.00, 1),
(21, 'FC Barcelona', DATE '2017-06-05', 120000000.00, 1),
(22, 'FC Barcelona', DATE '2016-02-22', 120000000.00, 1),
(23, 'FC Barcelona', DATE '2018-05-30', 180000000.00, 1),
(24, 'FC Barcelona', DATE '2021-01-05', 80000000.00, 1),
(25, 'FC Barcelona', DATE '2009-04-28', 60000000.00, 1),
(26, 'FC Barcelona', DATE '2008-07-10', 55000000.00, 1),
(27, 'FC Barcelona', DATE '2009-12-03', 70000000.00, 1),
(28, 'FC Barcelona', DATE '2010-01-07', 80000000.00, 1),
(29, 'FC Barcelona', DATE '2013-01-10', 120000000.00, 1),
(30, 'FC Barcelona', DATE '2007-07-26', 40000000.00, 1),
(31, 'FC Barcelona', DATE '2012-02-03', 100000000.00, 1),
(32, 'FC Barcelona', DATE '2018-01-01', 180000000.00, 1),
(33, 'FC Barcelona', DATE '2007-09-12', 60000000.00, 1),
(34, 'FC Barcelona', DATE '2009-07-22', 70000000.00, 1),
(35, 'FC Barcelona', DATE '2015-01-23', 120000000.00, 1),
(36, 'FC Barcelona', DATE '2018-12-21', 160000000.00, 1),
(37, 'FC Barcelona', DATE '2021-06-10', 80000000.00, 1),
(38, 'Ajax Amsterdam', DATE '2016-07-01', 600000.00, 2),
(39, 'FC Barcelona', DATE '2021-03-19', 80000000.00, 2),
(40, 'Ajax Amsterdam U21', DATE '2016-02-05', 400000.00, 2),
(41, 'Willem II Tilburg', DATE '2015-11-11', 400000.00, 2),
(42, 'FC Barcelona', DATE '2019-12-20', 90000000.00, 2),
(43, 'FC Barcelona', DATE '2020-10-08', 70000000.00, 2),
(44, 'Willem II Tilburg', DATE '2015-05-11', 50000.00, 2),
(45, 'Ajax Amsterdam', DATE '2018-01-17', 7000000.00, 2),
(46, 'Ajax Amsterdam', DATE '2018-06-12', 7000000.00, 2),
(47, 'Ajax Amsterdam', DATE '2019-06-12', 85000000.00, 2),
(48, 'FC Barcelona', DATE '2021-05-27', 90000000.00, 2),
(49, 'Ajax Amsterdam', DATE '2019-03-13', 75000000.00, 2),
(50, 'FC Barcelona', DATE '2021-01-05', 65000000.00, 2),
(51, 'Ajax Amsterdam', DATE '2017-06-07', 2000000.00, 2),
(52, 'Ajax Amsterdam', DATE '2018-10-04', 40000000.00, 2),
(53, 'FC Barcelona', DATE '2020-04-08', 72000000.00, 2),
(54, 'Ajax Amsterdam U21', DATE '2016-04-08', 600000.00, 2),
(55, 'Ajax Amsterdam', DATE '2017-10-26', 3500000.00, 2),
(56, 'Ajax Amsterdam', DATE '2017-01-18', 750000.00, 2),
(57, 'Ajax Amsterdam', DATE '2018-12-28', 60000000.00, 2),
(58, 'Willem II Tilburg', DATE '2015-07-01', 250000.00, 2),
(59, 'Chelsea FC', DATE '2012-06-24', 14000000.00, 3),
(60, 'Everton FC', DATE '2015-07-01', 28000000.00, 3),
(61, 'Everton FC', DATE '2017-02-20', 50000000.00, 3),
(62, 'Inter Milan', DATE '2020-08-25', 85000000.00, 3),
(63, 'Manchester United', DATE '2018-05-28', 90000000.00, 3),
(64, 'Inter Milan', DATE '2019-12-12', 75000000.00, 3),
(65, 'RSC Anderlecht U17', DATE '2009-05-27', 400000.00, 3),
(66, 'Manchester United', DATE '2018-07-16', 100000000.00, 3),
(67, 'Manchester United', DATE '2017-08-16', 60000000.00, 3),
(68, 'Inter Milan', DATE '2021-05-26', 100000000.00, 3),
(69, 'Everton FC', DATE '2017-06-28', 50000000.00, 3),
(70, 'Manchester United', DATE '2018-12-19', 85000000.00, 3),
(71, 'Inter Milan', DATE '2020-03-03', 85000000.00, 3);