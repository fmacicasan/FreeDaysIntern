INSERT INTO `advanced_user_role` (`role_type`, `id`, `version`) VALUES ('Admin', 1, 0);
INSERT INTO `advanced_user_role` (`role_type`, `id`, `version`) VALUES ('RequestGranter', 2, 0);
INSERT INTO `advanced_user_role` (`role_type`, `id`, `version`) VALUES ('FDAdmin', 3, 0);
INSERT INTO `advanced_user_role` (`role_type`, `id`, `version`) VALUES ('HRManagement', 4, 0);
INSERT INTO `advanced_user_role` (`role_type`, `id`, `version`) VALUES ('TimesheetAdmin', 5, 0);

INSERT INTO `approval_strategy` (`strategy_type`, `id`, `version`, `succesor`) VALUES ('LevelTop', 12, 0, NULL);
INSERT INTO `approval_strategy` (`strategy_type`, `id`, `version`, `succesor`) VALUES ('Level1', 13, 0, 12);

INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (1, 'Anul Nou day 1', '2011-01-01 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (2, 'Anul Nou day 2', '2011-01-02 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (3, 'Pastele day 1', '2011-04-24 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (4, 'Pastele day 2', '2011-04-25 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (5, 'Ziua internationala a Muncii', '2011-05-01 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (6, 'Rusalii day 1', '2011-06-12 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (7, 'Rusalii day 2', '2011-06-13 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (8, 'Adormirea Maicii Domnului', '2011-08-15 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (9, 'Ziua nationala a romaniei', '2011-12-01 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (10, 'Craciun Day 1', '2011-12-25 00:00:00', 1);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (11, 'Craciun Day 2', '2011-12-26 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (12, '2nd day of new year 2011-2012', '2012-01-02 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (13, '1st day of new year 2011-2012', '2012-01-01 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (14, 'Easter day 1', '2012-04-15 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (15, 'Easter day 2', '2012-04-16 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (16, 'Labour day', '2012-05-01 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (17, 'Rusalii day 1', '2012-06-03 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (18, 'Rusalii day 2', '2012-06-04 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (19, 'Adormirea Maicii Domnului', '2012-08-15 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (20, 'National Day', '2012-12-01 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (21, 'Christmas day 1', '2012-12-25 00:00:00', 0);
INSERT INTO `free_dayrl` (`id`, `description`, `romanian_holiday`, `version`) VALUES (22, 'Christmas day 2', '2012-12-26 00:00:00', 0);


INSERT INTO `labor_billing` (`id`, `code`, `name`, `version`) VALUES (1, '01', 'DummyLaborBilling1', NULL);
INSERT INTO `labor_billing` (`id`, `code`, `name`, `version`) VALUES (2, '02', 'DummyLaborBilling2', NULL);
INSERT INTO `labor_billing` (`id`, `code`, `name`, `version`) VALUES (3, '03', 'DummyLaborBilling3', NULL);
INSERT INTO `labor_billing` (`id`, `code`, `name`, `version`) VALUES (11, '1002', 'Project Manager', NULL);
INSERT INTO `labor_billing` (`id`, `code`, `name`, `version`) VALUES (12, '1003', 'Development Engineer', NULL);
INSERT INTO `labor_billing` (`id`, `code`, `name`, `version`) VALUES (13, '1004', 'Research Scientist', NULL);
INSERT INTO `labor_billing` (`id`, `code`, `name`, `version`) VALUES (14, '1005', 'Data/Language Specialist', NULL);
INSERT INTO `labor_billing` (`id`, `code`, `name`, `version`) VALUES (15, '1013', 'Tech Support', NULL);
INSERT INTO `labor_billing` (`id`, `code`, `name`, `version`) VALUES (16, '1014', 'Quality Control', NULL);
INSERT INTO `labor_billing` (`id`, `code`, `name`, `version`) VALUES (17, '1001', 'Not applicable', NULL);


INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (1, '0001', 'DummyPhase1', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (2, '0002', 'DummyPhase2', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (3, '0003', 'DummyPhase3', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (4, '0004', 'DummyPhase4', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (5, '0005', 'DummyPhase5', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (11, '801', 'Core Products SMTS', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (12, '808', 'Customer Support', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (13, '308', 'Application Engineering', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (14, '809', 'Whiteney', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (15, '810', 'Iceberg', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (16, '811', 'WP 2', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (17, '812', 'WP 5', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (18, '813', 'WP 6', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (19, '814', 'WP 1', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (20, '815', 'WP 4', NULL);
INSERT INTO `phase` (`id`, `code`, `name`, `version`) VALUES (21, '999', 'Not applicable', NULL);


INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (1, '0001', 'DummyProj1', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (2, '0002', 'DummyProj2', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (3, '0003', 'DummyProj3', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (4, '0004', 'DummyProj4', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (5, '0005', 'DummyProj5', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (11, '08034', 'AF SBIR', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (12, '09037', 'Hummingbird IV (BBN)', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (13, '10038', 'FAUST', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (14, '10039', 'TSWG 4', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (15, '10040', 'FLAVIUS', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (16, '10041', 'GALE 5', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (17, '10042', 'Dolphin II', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (18, '10043', 'RusEng Beikao', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (19, '10044', 'UkrainEng Beikao', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (20, '03007', 'LW Engineering', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (21, '99991', 'Vacation', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (22, '99992', 'Holiday', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (23, '99993', 'Sick', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (24, '99996', 'Training & Development', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (25, '99999', 'G&A (Genral & Admin)', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (26, '10045', "DanO\'s", NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (27, '11052', 'GateWay', NULL);
INSERT INTO `project` (`id`, `code`, `name`, `version`) VALUES (28, '11053', 'EasyTranslator', NULL);


INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (1, 1);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (2, 1);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (3, 1);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (4, 1);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (1, 2);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (2, 2);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (3, 2);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (11, 11);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (12, 12);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (13, 13);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (14, 14);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (15, 15);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (16, 16);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (17, 17);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (18, 18);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (19, 19);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (20, 19);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (11, 20);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (12, 20);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (13, 20);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (14, 20);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (15, 20);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (21, 21);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (21, 22);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (21, 23);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (21, 24);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (21, 25);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (13, 27);
INSERT INTO `phase_project_lst` (`phase_lst`, `project_lst`) VALUES (14, 28);


INSERT INTO `regular_user` (`id`, `activ`, `creationdate`, `deleted`, `firstname`, `lastmodified`, `password`, `surename`, `usermodifier`, `username`, `version`) VALUES (111, '', '2011-08-31 14:40:09', '', 'Dan', '2011-09-01 19:20:18', '5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8', 'Calinescu', 'dcalinescu@sdl.com', 'dcalinescu@sdl.com', 1);
INSERT INTO `regular_user` (`id`, `activ`, `creationdate`, `deleted`, `firstname`, `lastmodified`, `password`, `surename`, `usermodifier`, `username`, `version`) VALUES (142, '', '2011-08-31 14:40:09', '', 'Florin', '2011-09-01 12:09:33', '5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8', 'Macicasan', 'fmacicasan@sdl.com', 'fmacicasan@sdl.com', 5);
INSERT INTO `regular_user` (`id`, `activ`, `creationdate`, `deleted`, `firstname`, `lastmodified`, `password`, `surename`, `usermodifier`, `username`, `version`) VALUES (181, '', '2011-09-20 14:57:35', '', 'Iulia', '2011-09-20 14:57:35', '46e6b0b4c2501dc6df7afd98e2461ae99320e48123a5fed580b3801332aaa751', 'Dolha', 'idolha@sdl.com', 'idolha@sdl.com', 0);
INSERT INTO `regular_user` (`id`, `activ`, `creationdate`, `deleted`, `firstname`, `lastmodified`, `password`, `surename`, `usermodifier`, `username`, `version`) VALUES (203, '', '2012-05-30 09:21:10', '', 'Claudia', '2012-05-30 09:21:10', '5fd924625f6ab16a19cc9807c7c506ae1813490e4ba675f843d5a10e0baacdb8', 'Sas', 'csas@sdl.com', 'csas@sdl.com', 0);


INSERT INTO `fduser` (`id`, `jobrole`, `version`, `granter`, `regular_user`, `hire_date`, `init_days`, `max_derogation`, `max_free_days`) VALUES (111, 5, 0, NULL, 111, '2012-03-12', 0, 6, 21);
INSERT INTO `fduser` (`id`, `jobrole`, `version`, `granter`, `regular_user`, `hire_date`, `init_days`, `max_derogation`, `max_free_days`) VALUES (142, 9, 3, NULL, 142, '2011-07-05', 0, 6, 21);
INSERT INTO `fduser` (`id`, `jobrole`, `version`, `granter`, `regular_user`, `hire_date`, `init_days`, `max_derogation`, `max_free_days`) VALUES (1736704, 9, 0, NULL, 181, '2011-09-05', 0, 6, 21);
INSERT INTO `fduser` (`id`, `jobrole`, `version`, `granter`, `regular_user`, `hire_date`, `init_days`, `max_derogation`, `max_free_days`) VALUES (2424836, 9, 0, NULL, 203, '2012-05-02', 0, 6, 21);

INSERT INTO `app_reg_user_adv_role` (`user_id`, `role_id`) VALUES (111, 2);
INSERT INTO `app_reg_user_adv_role` (`user_id`, `role_id`) VALUES (142, 1);
INSERT INTO `app_reg_user_adv_role` (`user_id`, `role_id`) VALUES (142, 3);
INSERT INTO `app_reg_user_adv_role` (`user_id`, `role_id`) VALUES (142, 4);
INSERT INTO `app_reg_user_adv_role` (`user_id`, `role_id`) VALUES (142, 5);
INSERT INTO `app_reg_user_adv_role` (`user_id`, `role_id`) VALUES (1736704, 4);
INSERT INTO `app_reg_user_adv_role` (`user_id`, `role_id`) VALUES (2424836, 4);






























delete from `project`
INSERT INTO `project`(`id`,`code`,`name`) VALUES(1,"0001","DummyProj1")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(2,"0002","DummyProj2")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(3,"0003","DummyProj3")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(4,"0004","DummyProj4")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(5,"0005","DummyProj5")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(6,"9999","Vacation")

INSERT INTO `project`(`id`,`code`,`name`) VALUES(11,"08034","AF SBIR")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(12,"09037","Hummingbird IV (BBN)")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(13,"10038","FAUST")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(14,"10039","TSWG 4")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(15,"10040","FLAVIUS")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(16,"10041","GALE 5")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(17,"10042","Dolphin II")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(18,"10043","RusEng Beikao")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(19,"10044","UkrainEng Beikao")

INSERT INTO `project`(`id`,`code`,`name`) VALUES(20,"03007","LW Engineering")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(21,"99991","Vacation")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(22,"99992","Holiday")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(23,"99993","Sick")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(24,"99996","Training & Development")
INSERT INTO `project`(`id`,`code`,`name`) VALUES(25,"99999","G&A (Genral & Admin)")

delete from `phase`
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(1,"0001","DummyPhase1")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(2,"0002","DummyPhase2")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(3,"0003","DummyPhase3")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(4,"0004","DummyPhase4")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(5,"0005","DummyPhase5")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(6,"9999","Not applicable")

INSERT INTO `phase`(`id`,`code`,`name`) VALUES(11,"801","Core Products SMTS")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(12,"808","Customer Support")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(13,"308","Application Engineering")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(14,"809","Whiteney")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(15,"810","Iceberg")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(16,"811","WP 2")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(17,"812","WP 5")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(18,"813","WP 6")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(19,"814","WP 1")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(20,"815","WP 4")
INSERT INTO `phase`(`id`,`code`,`name`) VALUES(21,"999","Not applicable")

delete from `phase_project_lst`
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(1,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(2,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(3,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(4,1)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(1,2)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(2,2)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(3,2)
INSERT INTO `phase_project_lst`(`phase_lst`,`project_lst`) VALUES(6,6)

INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(11,11)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(12,12)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(13,13)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(14,14)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(15,15)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(16,16)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(17,17)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(18,18)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(19,19)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(19,20)

INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(20,11)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(20,12)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(20,13)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(20,14)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(20,15)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(21,21)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(22,21)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(23,21)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(24,21)
INSERT INTO `phase_project_lst`(`project_lst`,`phase_lst`) VALUES(25,21)


delete from `labor_billing`
INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(1,"01","DummyLaborBilling1")
INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(2,"02","DummyLaborBilling2")
INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(3,"03","DummyLaborBilling3")
INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(4,"9999","Not applicable")

INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(11,"1002","Project Manager")
INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(12,"1003","Development Engineer")
INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(13,"1004","Research Scientist")
INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(14,"1005","Data/Language Specialist")
INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(15,"1013","Tech Support")
INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(16,"1014","Quality Control")
INSERT INTO `labor_billing`(`id`,`code`,`name`) VALUES(17,"1001","Not applicable")

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

delete from `free_dayrl`
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES (1,0,'Anul Nou day 1','2011-01-01 00:00:00');
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES(2,0,'Anul Nou day 2','2011-01-02 00:00:00');
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES(3,0,'Pastele day 1','2011-04-24 00:00:00');
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES(4,0,'Pastele day 2','2011-04-25 00:00:00');
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES(5,0,'Ziua internationala a Muncii','2011-05-01 00:00:00');
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES(6,0,'Rusalii day 1','2011-06-12 00:00:00');
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES(7,0,'Rusalii day 2','2011-06-13 00:00:00');
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES(8,0,'Adormirea Maicii Domnului','2011-08-15 00:00:00');
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES(9,0,'Ziua nationala a romaniei','2011-12-01 00:00:00');
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES(10,1,'Craciun Day 1','2011-12-25 00:00:00');
INSERT INTO `free_dayrl`(`id`,`version`,`description`,`romanian_holiday`) VALUES(11,0,'Craciun Day 2','2011-12-26 00:00:00');
