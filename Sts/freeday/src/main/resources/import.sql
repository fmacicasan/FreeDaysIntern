delete from `advanced_user_role`
INSERT INTO `advanced_user_role`(`role_type`,`version`) VALUES("Admin",0);
INSERT INTO `advanced_user_role`(`role_type`,`version`) VALUES("RequestGranter",0);
INSERT INTO `advanced_user_role`(`role_type`,`version`) VALUES("FDAdmin",0);
INSERT INTO `advanced_user_role`(`role_type`,`version`) VALUES("HRManagement",0)
INSERT INTO `advanced_user_role`(`role_type`,`version`) VALUES("TimesheetAdmin",0)

delete from `approval_strategy`
INSERT INTO `approval_strategy`(`strategy_type`,`id`,`version`,`succesor`) VALUES ("LevelTop",12,0,null)
INSERT INTO `approval_strategy`(`strategy_type`,`id`,`version`,`succesor`) VALUES ("Level1",13,0,12)


delete from `regular_user`
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (111,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Ionel","Condor","icondor@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (112,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Gabriela","Visinari","gvisinari@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (113,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Paula","Silaghi","psilaghi@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (114,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Bogdan","Faraga","bfaraga@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (115,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Raul","Chiorean","rchiorean@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (116,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Andrei","Chis","achis@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (117,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Marius","Zubac","mzubac@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (118,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Vlad","Nicula","vnicula@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (119,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Florina","Paduraru","fpaduraru@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (120,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Bogdan","Giurgiu","bgiurgiu@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (121,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Alexandru","Gabor","agabor@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (122,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Cristian","Calugaru","ccalugaru@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (123,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Radu","Bordea","rbordea@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (124,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Dan","Tarba","dtarba@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (125,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Sorin","Suciu","ssuciu@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (126,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Ovidiu","Petridean","opetridean@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (127,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Peter","Bodocz","pbodocz@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (128,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Adela","Pop","apop@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (129,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Nicolae","Matei","nmatei@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (130,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Ana-Maria","Totea","atotea@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (131,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Dan","Oprita","doprita@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (132,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Cristina","Nistor","cnistor@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (133,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Daniel","Sarbe","dsarbe@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (134,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Emil","Tamas","etamas@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (135,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Mihai","Plesa","mplesa@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (136,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","George","Bara","gbara@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (137,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Dan","Chiorean","dchiorean@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (138,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Radu","Moldovan","rmoldovan@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (139,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Emil","Stetco","estetco@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (140,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Angela","Mironescu","amironescu@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (141,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Flavius","Matis","fmatis@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (142,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Florin","Macicasan","fmacicasan@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (143,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Andreea","Hazi","ahazi@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (144,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Claudia-Alteia","Pavel","apavel@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (145,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Gabriel-Adrian","Ferician","gferician@sdl.com")
INSERT INTO `regular_user`(`id`,`version`,`activ`,`deleted`,`usermodifier`,`creationdate`,`lastmodified`,`password`,`firstname`,`surename`,`username`) VALUES (146,0,1,0,NULL,"2011-08-31 14:40:09","2011-07-20 01:21:09","5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8","Ciprian-Anton","Catana","ccatana@sdl.com")

delete from `fduser`
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(111,0,5,null,111,"2009-03-02",0,6,30)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(112,0,1,null,112,"2011-08-01",0,6,11)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(113,0,1,null,113,"2011-07-25",0,6,9)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(114,0,1,null,114,"2009-07-01",0,6,27)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(115,0,2,null,115,"2010-07-07",0,6,19)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(116,0,1,null,116,"2010-09-08",0,6,22)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(117,0,2,null,117,"2008-01-08",0,6,29)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(118,0,0,null,118,"2011-04-01",0,6,16)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(119,0,1,null,119,"2011-01-04",0,6,21)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(120,0,3,null,120,"2009-10-19",0,6,34)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(121,0,4,null,121,"2010-01-20",0,6,25)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(122,0,0,null,122,"2009-09-01",0,6,28)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(123,0,0,null,123,"2009-09-03",0,6,25)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(124,0,0,null,124,"2011-05-18",0,6,13)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(125,0,0,null,125,"2009-09-01",0,6,34)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(126,0,0,null,126,"2010-08-18",0,6,21)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(127,0,0,null,127,"2008-04-08",0,6,24)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(128,0,1,null,128,"2009-11-09",0,6,21)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(129,0,0,null,129,"2010-03-29",0,6,27)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(130,0,0,null,130,"2009-12-01",0,6,24)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(131,0,0,null,131,"2009-09-01",0,6,27)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(132,0,0,null,132,"2009-11-11",0,6,25)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(133,0,0,null,133,"2010-03-01",0,6,28)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(134,0,6,null,134,"2011-01-04",0,6,21)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(135,0,2,null,135,"2011-03-01",0,6,18)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(136,0,7,null,136,"2011-02-21",0,6,18)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(137,0,8,null,137,"2011-05-04",0,6,16)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(138,0,0,null,138,"2011-06-06",0,6,12)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(139,0,6,null,139,"2011-06-13",0,6,11)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(140,0,1,null,140,"2011-07-01",0,6,11)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(141,0,0,null,141,"2011-07-25",0,6,9)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(142,0,0,null,142,"2011-07-05",0,6,10)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(143,0,0,null,143,"2011-05-01",0,6,14)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(144,0,0,null,144,"2011-09-01",0,6,7)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(145,0,1,null,145,"2011-09-01",0,6,7)
INSERT INTO `fduser`(`id`,`version`,`jobrole`,`granter`,`regular_user`,`hire_date`,`init_days`,`max_derogation`,`max_free_days`) VALUES(146,0,1,null,116,"2011-09-01",0,6,7)
delete from `app_reg_user_adv_role`;
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(111,1)
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(111,2)
INSERT INTO `app_reg_user_adv_role`(`user_id`,`role_id`)VALUES(111,3)


























delete from `project`
INSERT INTO `project`(`code`,`name`) VALUES("0001","DummyProj1")
INSERT INTO `project`(`code`,`name`) VALUES("0002","DummyProj2")
INSERT INTO `project`(`code`,`name`) VALUES("0003","DummyProj3")
INSERT INTO `project`(`code`,`name`) VALUES("0004","DummyProj4")
INSERT INTO `project`(`code`,`name`) VALUES("0005","DummyProj5")
INSERT INTO `project`(`code`,`name`) VALUES("9999","Vacation")

delete from `phase`
INSERT INTO `phase`(`code`,`name`) VALUES("0001","DummyPhase1")
INSERT INTO `phase`(`code`,`name`) VALUES("0002","DummyPhase2")
INSERT INTO `phase`(`code`,`name`) VALUES("0003","DummyPhase3")
INSERT INTO `phase`(`code`,`name`) VALUES("0004","DummyPhase4")
INSERT INTO `phase`(`code`,`name`) VALUES("0005","DummyPhase5")
INSERT INTO `phase`(`code`,`name`) VALUES("9999","Not applicable")

delete from `phase_project_lst`
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(1,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(2,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(3,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(4,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(1,2)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(2,2)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(3,2)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(6,6)

delete from `labor_billing`
INSERT INTO `labor_billing`(`code`,`name`) VALUES("01","DummyLaborBilling1")
INSERT INTO `labor_billing`(`code`,`name`) VALUES("02","DummyLaborBilling2")
INSERT INTO `labor_billing`(`code`,`name`) VALUES("03","DummyLaborBilling3")
INSERT INTO `labor_billing`(`code`,`name`) VALUES("9999","Not applicable")

delete from `pattern`
INSERT INTO `pattern`(`no_of_hours`) VALUES(8)

delete from `phase_labor`
INSERT INTO `phase_labor`(`percentage`,`laborbilling`,`pattern`,`phase`,`project`) VALUES(20,1,1,1,1)
INSERT INTO `phase_labor`(`percentage`,`laborbilling`,`pattern`,`phase`,`project`) VALUES(20,1,1,2,1)
INSERT INTO `phase_labor`(`percentage`,`laborbilling`,`pattern`,`phase`,`project`) VALUES(20,2,1,3,1)
INSERT INTO `phase_labor`(`percentage`,`laborbilling`,`pattern`,`phase`,`project`) VALUES(40,2,1,4,1)

delete from `timesheet_user`
INSERT INTO `timesheet_user`(`id`,`fduser`) VALUES(100,111);
INSERT INTO `timesheet_user`(`id`,`fduser`) VALUES(113,113);

delete from `schedule`
INSERT INTO `schedule`(`start_date`,`end_date`,`employee`,`pattern`) VALUES("2011-07-20", "2011-12-20", 100, 1)
delete from `schedule`
