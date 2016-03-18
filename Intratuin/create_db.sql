		CREATE DATABASE IF NOT EXISTS BIONIC;
		USE BIONIC;

CREATE TABLE IF NOT EXISTS RETAIL (
	RETAIL_ID INT NOT NULL AUTO_INCREMENT, 
	EMPLOYEE_NAМE VARCHAR(40) NOT NULL,
	EMPLOYEE_SURNAМE VARCHAR(40) NOT NULL,
	DATE_OF_BIRTH DATE, 
	DATE_OF_EMPLOYMENT DATE,
	PRIMARY KEY (RETAIL_ID)
);
ALTER TABLE RETAIL AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS CUSTOMER (
	CUSTOMER_ID INT NOT NULL AUTO_INCREMENT,
	CUSTOMER_NAME VARCHAR(40) NOT NULL,
	CUSTOMER_SURNAME VARCHAR(40) NOT NULL,
	PASSWORD VARCHAR(40) NOT NULL,
	DATE_OF_BIRTH DATE, 
	CITY VARCHAR(60),
	STREET_NAME VARCHAR(60),
	HOUSE_NUMBER INT,
	AREA_CODE VARCHAR(10),
	GENDER INT,
	EMAIL_ADDRESS VARCHAR(60) NOT NULL UNIQUE,
	CELLPHONE_NUMBER VARCHAR(20) NOT NULL UNIQUE,
	PRIMARY KEY (CUSTOMER_ID)
);
ALTER TABLE CUSTOMER AUTO_INCREMENT=1;
	
CREATE TABLE IF NOT EXISTS RETAIL_CUSTOMER (
	RC_ID INT NOT NULL AUTO_INCREMENT,
	RETAIL_ID INT NOT NULL,
	CUSTOMER_ID INT NOT NULL,
	PRIMARY KEY (RC_ID),
	FOREIGN KEY (RETAIL_ID) REFERENCES RETAIL(RETAIL_ID),
	FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID)
);

CREATE TABLE IF NOT EXISTS PRODUCT (
	PRODUCT_ID INT NOT NULL AUTO_INCREMENT,
	PRODUCT_NAME VARCHAR(100) NOT NULL UNIQUE,
	PRODUCT_PRICE DOUBLE NOT NULL,
	PRIMARY KEY (PRODUCT_ID)
);
ALTER TABLE PRODUCT AUTO_INCREMENT=1;

CREATE TABLE IF NOT EXISTS ORDER_RULE (
	ORDER_RULE_ID INT NOT NULL AUTO_INCREMENT,
	PRODUCT_ID INT NOT NULL,
	AMOUNT INT NOT NULL,
	TOTAL INT NOT NULL,
	PRIMARY KEY (ORDER_RULE_ID),
	FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)
);
ALTER TABLE ORDER_RULE AUTO_INCREMENT=1;
	
CREATE TABLE IF NOT EXISTS ORDERS (
	ORDER_ID INT NOT NULL AUTO_INCREMENT,
	CUSTOMER_ID INT NOT NULL,
	ORDER_RULE_ID INT NOT NULL,
	ORDER_DATE DATE,
	PRIMARY KEY (ORDER_ID),
	FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID),
	FOREIGN KEY (ORDER_RULE_ID) REFERENCES ORDER_RULE(ORDER_RULE_ID)
);
ALTER TABLE ORDERS AUTO_INCREMENT=1;

INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (1,"Shafira","Bradford","1671012431299","31-05-15","Dortmund","280-2865 Parturient Rd.",47,"60424","porttitor.eros.nec@egetipsumSuspendisse.net","(07) 1151 6718"),(2,"Shoshana","Baxter","1611050473299","07-02-17","Bersillies-l'Abbaye","433-4566 Amet Avenue",63,"21146","Duis.mi@Nam.co.uk","(01) 3410 2557"),(3,"Ethan","Merritt","1611101993599","02-09-16","Port Blair","5413 At Road",18,"9086","pharetra.sed.hendrerit@condimentumeget.co.uk","(05) 3750 0516"),(4,"Ira","Nunez","1645012036499","09-02-17","Abolens","938-6171 Non Ave",61,"7216","mollis.Duis@Donec.org","(02) 4910 1987"),(5,"Lydia","Hewitt","1632042702099","07-03-17","Gujranwala","Ap #465-5531 At, Rd.",19,"28533","eleifend.nunc@Nullaeuneque.ca","(04) 7224 2945"),(6,"Noelani","Pitts","1618100168599","05-09-15","Avise","P.O. Box 211, 6486 Egestas. Ave",22,"6447BG","inceptos.hymenaeos.Mauris@luctus.org","(09) 4534 9916"),(7,"Alana","Kelley","1661072893999","02-07-15","Laguna Blanca","6258 Egestas Ave",49,"75449","pede@sapien.ca","(06) 0667 6033"),(8,"Emmanuel","Aguilar","1695050671599","14-05-16","Montaldo Bormida","221-1812 Orci. Avenue",78,"98-268","ornare@miDuis.net","(03) 3148 8211"),(9,"Jakeem","Hughes","1661031108199","24-07-15","Lahore","2102 Aenean Rd.",19,"96972","Mauris.ut.quam@rhoncusProinnisl.ca","(05) 4460 9213"),(10,"Harlan","Reeves","1682112947399","19-03-15","Slough","215-6960 Quam Rd.",7,"21100","ut@dolorsitamet.co.uk","(08) 9614 1200");
INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (11,"Joan","Hickman","1649101639999","13-04-16","Acireale","681-4073 Mi Rd.",87,"17-848","purus.gravida.sagittis@eudoloregestas.com","(03) 0400 3438"),(12,"Marny","Baldwin","1639082698099","30-03-16","Chelm","P.O. Box 731, 6059 Libero. Rd.",48,"1801OL","Fusce@metusAenean.co.uk","(08) 2474 1626"),(13,"Simon","Estes","1663012485999","05-10-15","Montreal","P.O. Box 249, 2136 Risus. Ave",71,"27432","eu.euismod@nunc.com","(03) 5132 2911"),(14,"Kylie","Vazquez","1629072270599","13-11-15","Aieta","314-1303 Orci Av.",71,"18-788","nisi@eratvolutpatNulla.ca","(09) 4539 5162"),(15,"Linda","Torres","1600010819499","04-05-15","Nuragus","P.O. Box 559, 6185 Pede. St.",44,"006141","bibendum.Donec@quis.co.uk","(03) 1389 7494"),(16,"Dominique","Patrick","1699112276999","28-11-15","Haren","2324 Ac Rd.",59,"E0T 7X3","dictum.Phasellus.in@nisiMaurisnulla.org","(05) 0648 5682"),(17,"Rama","Steele","1659101246499","15-12-15","Vicuna","P.O. Box 284, 9681 Eu Ave",1,"21001","nisl.arcu.iaculis@arcuvelquam.com","(04) 0908 0590"),(18,"Shelly","Walls","1613122077499","10-08-16","Villers-aux-Tours","Ap #295-9435 Sit Rd.",40,"53324","dolor@vestibulumMaurismagna.org","(01) 8908 4633"),(19,"Maris","Goff","1646041146999","13-02-17","Sonnino","P.O. Box 468, 1546 Felis. Rd.",21,"08986","varius.orci.in@convallisantelectus.co.uk","(09) 4618 1668"),(20,"Heidi","Roberson","1698101668799","09-02-17","Bientina","665-6456 Lacus, Road",58,"42497","elit@erateget.ca","(08) 8376 2400");
INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (21,"Blaze","Hayden","1650081880399","06-07-16","Tay","817-4825 Vulputate Street",7,"5203","Nunc.pulvinar@auguemalesuadamalesuada.co.uk","(03) 8325 3872"),(22,"Darrel","Briggs","1610052644099","16-01-17","Sonnino","5634 Ac Rd.",63,"10005","Vestibulum@fermentumconvallisligula.co.uk","(09) 6551 2764"),(23,"Herman","Villarreal","1676052469199","14-01-16","Collines-de-l'Outaouais","910-4141 Et St.",2,"25855","sapien@tellusimperdiet.com","(05) 4901 0567"),(24,"Adrian","Estrada","1616041318299","05-08-15","Gro?petersdorf","Ap #943-1366 Sed Road",92,"70130","Donec.tempor.est@loremsit.org","(09) 4253 7796"),(25,"Yasir","Garcia","1616081069399","15-02-16","Bearberry","8922 Rutrum Street",17,"73058","laoreet.libero@risusInmi.co.uk","(06) 0855 1312"),(26,"Ruby","Lyons","1699060757999","26-10-15","Burlington","Ap #143-5025 Libero Street",100,"455379","imperdiet.nec@aceleifend.ca","(05) 1715 0774"),(27,"Alexa","Nash","1602061718699","22-09-16","Sanl?urfa","Ap #164-1588 Dolor Avenue",61,"18-749","ultrices.iaculis@dignissimtemporarcu.edu","(05) 7098 1376"),(28,"Ursula","Moore","1664110464599","09-05-16","Curridabat","7800 Urna. Av.",100,"80-185","velit.eget@risusDuis.com","(03) 4617 4330"),(29,"Kirby","Dejesus","1622092572899","10-09-16","Borghetto di Borbera","100-2644 Mollis. Avenue",62,"5677GF","Suspendisse.non.leo@aliquamiaculislacus.edu","(07) 4938 3777"),(30,"Ross","Acevedo","1629032396599","15-02-16","Davenport","874-1659 Tellus St.",30,"14804","Sed.malesuada@nonummyultriciesornare.edu","(06) 0427 4673");
INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (31,"Isaac","Medina","1694112664699","14-07-15","Ronciglione","214-5102 Curabitur Av.",61,"61446","amet.diam@etultrices.net","(04) 9819 4127"),(32,"Bethany","Patrick","1637011883399","19-03-16","Etroubles","503-6596 Proin Street",11,"2928","est.Nunc@luctusetultrices.edu","(04) 5269 1745"),(33,"Ezra","Aguirre","1644122917499","23-08-15","Barasat","Ap #980-6018 Id St.",66,"25191","tempus.eu.ligula@nunc.edu","(07) 8411 6260"),(34,"Alden","Byrd","1647032743899","06-09-16","Copiapo","740 Et Ave",67,"10113","dui.Cum@bibendum.edu","(05) 5701 5140"),(35,"Jane","Hoover","1691022988399","12-12-15","Houtvenne","1417 Euismod Avenue",65,"6425JE","sapien.molestie@netusetmalesuada.net","(02) 8007 9494"),(36,"Georgia","Myers","1663062006999","28-06-16","Ashbourne","Ap #816-6659 Mi Avenue",57,"50708","ut.nulla@duisemperet.net","(07) 0309 3622"),(37,"Declan","Stout","1663101953299","01-04-16","Lidingo","754-5422 Maecenas Street",19,"566300","dictum.placerat@neque.net","(08) 4273 4210"),(38,"Ian","Powell","1666031424299","05-02-17","Brussegem","Ap #111-6552 Ultrices St.",70,"63318","lorem.luctus.ut@Donecsollicitudinadipiscing.com","(01) 6008 5864"),(39,"Colin","Snyder","1699031725499","26-06-15","Matamata","Ap #838-1802 Sollicitudin Ave",61,"89617","non@veliteu.com","(09) 0341 8117"),(40,"Kyla","Dillard","1695032257899","11-10-15","Oss","3701 Ac St.",77,"2996","eleifend@dolor.co.uk","(07) 0203 4025");
INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (41,"Kevyn","Mooney","1619032344499","06-11-15","Attert","537-8048 Sit Rd.",83,"71835","lacus.Quisque.imperdiet@adlitora.org","(07) 1822 8576"),(42,"Chastity","Kaufman","1608041832499","30-06-16","Primavera","P.O. Box 368, 4295 Dictum St.",18,"77714","lacinia.Sed@NulladignissimMaecenas.co.uk","(05) 8257 0822"),(43,"Holmes","Britt","1682082913799","05-10-16","Tula","5402 Aliquam Avenue",93,"31385","id@Maecenas.com","(07) 2152 9878"),(44,"Holly","Love","1684052591999","11-05-16","Okigwe","9930 Parturient Rd.",75,"65183","dictum@leoin.com","(08) 0363 1078"),(45,"Beverly","Hardin","1629122742799","31-07-16","Vosselaar","P.O. Box 510, 3535 Elementum Street",53,"F5P 2BQ","non.luctus.sit@maurissitamet.com","(09) 8272 6805"),(46,"Kelsey","Tyler","1613082914299","08-02-17","Oyace","8231 In Rd.",99,"26888","Nunc.mauris.sapien@adipiscingelit.ca","(05) 4842 8919"),(47,"Harriet","Holland","1628020657599","30-06-16","Burlington","P.O. Box 485, 6135 Ipsum St.",93,"E5 4VZ","Nunc@Quisque.org","(05) 5089 1510"),(48,"Lionel","Lancaster","1610061032599","22-09-15","Wigtown","P.O. Box 350, 3969 Augue. Ave",16,"00976","sagittis.felis@auctornunc.edu","(07) 8947 2131"),(49,"Burton","Page","1617072353499","17-01-16","Matagami","Ap #963-9557 Ligula. St.",64,"14292-301","Nulla.eget@arcu.com","(08) 5782 1208"),(50,"Michael","Goodman","1695022340099","06-08-15","San Felipe","P.O. Box 311, 5195 Nunc Av.",75,"39981-592","ligula.consectetuer.rhoncus@quis.edu","(01) 5736 6807");
INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (51,"Aubrey","Mathews","1602121863499","12-02-17","Campagna","Ap #850-5338 Consectetuer Rd.",63,"3411AM","mi.lorem.vehicula@sodalesMaurisblandit.edu","(06) 5412 2016"),(52,"Alfreda","Mcneil","1624041080099","06-07-15","Kermt","Ap #872-201 Convallis Avenue",49,"245377","scelerisque@leoVivamus.co.uk","(06) 1810 9109"),(53,"Zephr","Stevenson","1632011349099","09-12-15","Berwick-upon-Tweed","Ap #983-2545 Odio St.",2,"07880","euismod@non.org","(01) 3637 9712"),(54,"Heidi","Lowe","1667011130999","13-03-16","Amiens","922-2161 Ornare Avenue",87,"14854","vitae.aliquam@Suspendissealiquetmolestie.ca","(07) 9160 3286"),(55,"Hanna","Lawson","1651103064299","06-02-16","Kasur","482-8073 Eu St.",13,"3182BP","posuere.enim@sedhendrerit.ca","(07) 5582 4055"),(56,"Ryder","Perry","1674111830999","18-02-16","Wilmington","Ap #502-1535 Lectus Street",65,"2895FG","est@ametmassaQuisque.co.uk","(01) 7892 0624"),(57,"Arsenio","Melendez","1699121528699","02-06-16","Serrata","139-1637 Aliquam Rd.",69,"89095","pellentesque.massa.lobortis@ametanteVivamus.org","(01) 1084 5038"),(58,"Bree","Navarro","1658080607099","23-04-16","Konigs Wusterhausen","P.O. Box 584, 1256 Gravida St.",60,"70849","Nulla.interdum@nibh.org","(08) 0075 2135"),(59,"Beatrice","Clayton","1649113092699","14-07-16","Ingelheim","280-2384 Blandit. St.",42,"53-957","iaculis@amet.org","(06) 2403 3382"),(60,"Amos","Welch","1628041884799","03-12-16","Susegana","Ap #873-8735 Orci St.",61,"718242","lobortis@suscipitnonummy.edu","(07) 9638 0616");
INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (61,"Cooper","Frazier","1683073099399","09-11-15","Muiden","986-8725 Convallis Av.",96,"41107","quam.vel.sapien@blanditcongueIn.edu","(09) 4351 3208"),(62,"Zachery","Chapman","1630010707899","12-09-16","Xhoris","162-4898 At, Av.",50,"51492-127","Lorem.ipsum@molestiedapibus.com","(08) 0528 0874"),(63,"Jena","Reed","1601063055599","25-07-16","Ville-en-Hesbaye","Ap #715-6257 Consectetuer Avenue",65,"547885","lorem.ut.aliquam@imperdiet.org","(08) 6089 3849"),(64,"Ray","Collier","1648081711299","02-07-16","Mobile","685-1476 Ac Av.",25,"9900","tellus@actellus.net","(04) 4592 7744"),(65,"Aurelia","Bradley","1660070161199","13-09-16","Merbes-le-Ch‰teau","931-1849 Urna. Av.",83,"5546","vestibulum.nec.euismod@sitametorci.com","(05) 0593 7411"),(66,"Dennis","Walker","1643122274299","08-07-16","Boignee","553-3868 Morbi Rd.",50,"W54 3GL","sit@posuere.com","(04) 6799 3469"),(67,"Clare","Brooks","1643110102899","24-08-15","Auxerre","878-6491 Dui, St.",68,"0641VM","nascetur.ridiculus.mus@tempor.edu","(04) 8974 9225"),(68,"Gail","Drake","1607112895099","06-11-16","Ciudad Real","Ap #994-5141 Ultrices Avenue",68,"68579-795","a.enim@natoquepenatibuset.ca","(04) 6009 5171"),(69,"Valentine","Kelley","1647050456099","31-12-16","Anzegem","5757 Sit St.",62,"30315-470","Curabitur@Etiamligula.com","(02) 9105 9517"),(70,"Kasper","Nolan","1636121017299","08-09-15","Kontich","252-5450 Torquent Av.",46,"716868","odio@sedfacilisisvitae.net","(09) 5405 8227");
INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (71,"Leila","Salas","1667073073099","18-08-16","Trois-Rivi?res","707-5117 Hymenaeos. Avenue",71,"01253","lorem.ac.risus@parturient.net","(05) 4058 0578"),(72,"Timothy","Glenn","1681042326299","02-07-16","Faisalabad","Ap #294-5178 Urna, Street",15,"3882","et.rutrum@Inscelerisque.com","(03) 5229 2139"),(73,"Jermaine","Sherman","1677030745799","15-05-16","Shawinigan","9864 Cum Street",2,"99328-295","Suspendisse@erat.net","(08) 2086 2077"),(74,"Penelope","Parker","1619060723899","18-09-16","Civo","Ap #815-7737 Nulla. Avenue",2,"6001","neque@lacus.co.uk","(05) 9990 7438"),(75,"Logan","Lindsay","1661060833399","19-07-16","Jaboatao dos Guararapes","Ap #968-876 Aenean St.",17,"7853","Nunc.ullamcorper.velit@dictumeleifend.co.uk","(05) 8707 1928"),(76,"Kuame","Glenn","1683030598399","14-06-15","Mumbai","Ap #411-539 Est. Avenue",29,"484363","ac.urna.Ut@euaccumsansed.co.uk","(07) 3434 9713"),(77,"Athena","Porter","1622042259099","01-04-15","Bard","P.O. Box 975, 9960 Sem Avenue",95,"00505","non.lorem.vitae@viverraMaecenas.co.uk","(06) 9377 9516"),(78,"Kato","Spence","1670080565399","14-01-16","Motta Camastra","7133 Integer St.",20,"60282","ipsum.dolor.sit@Cumsociis.net","(01) 5158 7326"),(79,"Demetria","Schroeder","1627120963999","14-12-16","Saarbrucken","Ap #472-6278 Sed Road",80,"70995","risus@mifringilla.ca","(06) 8355 2922"),(80,"Dominic","Steele","1626112468599","17-08-15","Pelarco","Ap #806-6781 Primis Av.",87,"9648","blandit.congue@lobortis.net","(09) 7828 4265");
INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (81,"Anjolie","Foley","1635070728899","04-06-15","La Baie","P.O. Box 680, 2130 Elit, Street",77,"X8N 2ER","neque@Duisvolutpatnunc.edu","(06) 7777 8889"),(82,"Kiara","Schwartz","1690080179299","28-06-15","Lac Ste. Anne","Ap #350-6911 Pellentesque Street",58,"72542","mollis.lectus.pede@semutcursus.com","(06) 2558 7646"),(83,"Dylan","Manning","1688051299399","02-10-15","Marlborough","Ap #261-890 Interdum. Street",35,"921529","arcu.imperdiet.ullamcorper@eleifendnon.com","(05) 4140 2823"),(84,"Ryder","Patel","1699121204399","31-03-15","South Dum Dum","3502 At, Street",61,"91934","ornare.facilisis@sem.com","(04) 0283 0767"),(85,"Quinlan","Glover","1614011836999","12-07-15","Gillette","P.O. Box 596, 4102 Tellus Rd.",51,"47872","est.mauris.rhoncus@sitamet.ca","(08) 3244 6247"),(86,"Velma","Lamb","1660021802199","26-07-15","Quillon","P.O. Box 541, 4304 Nulla Av.",91,"AX9 2JX","justo.nec.ante@etnetuset.edu","(08) 7528 1603"),(87,"Cathleen","Rosales","1625040681199","23-01-17","Forges","Ap #729-6495 Ac Avenue",74,"M9A 2Y0","arcu.eu.odio@sapien.org","(09) 4806 9487"),(88,"Jamal","Mcpherson","1635011061999","02-12-16","Hachy","Ap #429-774 Semper St.",49,"Y0H 5A0","molestie@lacusEtiambibendum.org","(06) 3692 9576"),(89,"Shad","Cameron","1667030150499","28-10-16","Palermo","Ap #238-2731 Ac, Rd.",72,"20-376","volutpat@Morbinon.ca","(02) 1205 3120"),(90,"Quin","Moreno","1697030245599","03-02-17","Isola di Capo Rizzuto","592-4187 Pharetra. St.",51,"10212","dictum.Phasellus.in@purus.edu","(04) 8844 1177");
INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (91,"Wade","Espinoza","1648030440099","09-05-15","Chandler","Ap #962-4715 Ut Rd.",48,"MB35 4TY","lacus@nulla.net","(01) 7278 4796"),(92,"Jacqueline","Adkins","1689040609299","13-08-16","Wittenberg","Ap #545-4346 Vel, Ave",36,"6254","aliquet.vel@diamluctus.net","(04) 0290 6034"),(93,"Olympia","Kidd","1610110557099","28-12-15","Bassiano","637-3091 Feugiat Av.",7,"9614PB","lectus.pede@ametmassa.com","(07) 1356 7937"),(94,"Cassady","Holcomb","1668053033799","07-09-15","Stekene","745-4575 Pretium Rd.",4,"0257","diam@mauriseu.edu","(08) 4236 2635"),(95,"Otto","Reynolds","1609120879699","20-02-17","Sint-Joost-ten-Node","122-5119 Suspendisse Rd.",40,"6185","ipsum.dolor.sit@nuncrisusvarius.edu","(09) 4013 5942"),(96,"Jena","Hatfield","1693111794699","24-04-16","Artena","Ap #833-6712 Consequat Road",60,"54802","suscipit.nonummy.Fusce@rhoncusProinnisl.co.uk","(01) 1720 2078"),(97,"Mollie","Frazier","1639052697699","18-02-16","Kinross","P.O. Box 622, 2518 Fusce Av.",46,"0993YC","morbi.tristique.senectus@tempor.net","(07) 7984 0902"),(98,"Forrest","Harding","1641120457599","27-12-16","Rothes","P.O. Box 257, 1944 Ultrices St.",78,"40992","ipsum.dolor@pharetrafelis.com","(03) 8736 0260"),(99,"Lev","Irwin","1665101141799","20-08-15","Sant'Elia a Pianisi","9716 Ullamcorper, Av.",36,"13-694","luctus@atlacus.org","(08) 3927 1539"),(100,"Dana","Charles","1627061885699","25-02-17","Grembergen","Ap #988-3580 Ipsum Road",21,"89291","dolor.elit@nequesed.co.uk","(08) 4151 3295");

INSERT INTO `PRODUCT` (`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`) VALUES (1,"amet, dapibus","6.01"),(2,"sem mollis dui,","1.86"),(3,"amet nulla. Donec","1.38"),(4,"egestas rhoncus. Proin","1.49"),(5,"dui.","4.68"),(6,"amet orci.","0.13"),(7,"adipiscing non,","0.64"),(8,"blandit","6.01"),(9,"vulputate","0.09"),(10,"hendrerit","5.29");
INSERT INTO `PRODUCT` (`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`) VALUES (11,"malesuada id, erat.","1.06"),(12,"tellus","7.49"),(13,"eu tellus eu","0.92"),(14,"ac, feugiat non,","8.99"),(15,"cursus, diam","3.86"),(16,"quis diam. Pellentesque","3.84"),(17,"Donec","4.64"),(18,"odio a purus.","7.99"),(19,"lectus sit","5.80"),(20,"arcu et","4.99");
INSERT INTO `PRODUCT` (`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`) VALUES (21,"at sem molestie","9.67"),(22,"Nunc","6.77"),(23,"Nullam lobortis quam","7.52"),(24,"Nullam scelerisque neque","5.38"),(25,"felis, adipiscing","9.63"),(26,"lectus","6.14"),(27,"tellus id","5.63"),(28,"consequat","1.73"),(29,"tellus. Aenean egestas","1.91"),(30,"lobortis","1.85");
INSERT INTO `PRODUCT` (`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`) VALUES (31,"risus odio,","2.25"),(32,"lorem,","4.14"),(33,"mus. Donec","8.27"),(34,"Class aptent","2.40"),(35,"venenatis a,","2.76"),(36,"et, euismod et,","1.38"),(37,"Donecf non","7.96"),(38,"Vivamus rhoncus. Donec","0.53"),(39,"adipiscing elit. Aliquam","1.44"),(40,"felis ullamcorper viverra.","0.84");
INSERT INTO `PRODUCT` (`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`) VALUES (41,"id,","3.62"),(42,"scelerisque scelerisque dui.","8.37"),(43,"at pretium","6.77"),(44,"quis turpis","7.46"),(45,"Aliquam nisl.","4.63"),(46,"fringilla ornare placerat,","1.52"),(47,"purus ac tellus.","1.64"),(48,"In tincidunt congue","6.13"),(49,"nec urna suscipit","3.93"),(50,"inceptos hymenaeos.","2.24");
INSERT INTO `PRODUCT` (`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`) VALUES (51,"egestas hendrerit","7.60"),(52,"senectus","3.91"),(53,"augue","3.28"),(54,"diam at pretium","5.42"),(55,"Doneca sollicitudin","8.73"),(56,"a,","9.71"),(57,"vitae","7.53"),(58,"tincidunt orci","5.88"),(59,"eget","6.59"),(60,"egets","5.79");
INSERT INTO `PRODUCT` (`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`) VALUES (61,"mus. Proin vel","1.29"),(62,"quam. Curabitur vel","9.87"),(63,"lobortis quis, pede.","8.98"),(64,"augue id ante","1.99"),(65,"Cras eget","5.10"),(66,"metus. Aenean sed","2.91"),(67,"interdum libero","5.76"),(68,"molestie. Sed","2.98"),(69,"metus urna convallis","6.38"),(70,"at augue","6.50");
INSERT INTO `PRODUCT` (`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`) VALUES (71,"Donec elementum,","3.09"),(72,"libero nec ligula","7.25"),(73,"libero lacus, varius","6.63"),(74,"auctor velit.","7.92"),(75,"Nulla aliquet.","0.83"),(76,"porttitor eros","5.67"),(77,"vitae velit","8.64"),(78,"Suspendisse eleifend.","0.96"),(79,"pharetra.","1.79"),(80,"mollis nec,","3.22");
INSERT INTO `PRODUCT` (`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`) VALUES (81,"faucibus orci luctus","2.26"),(82,"lacus. Mauris non","5.19"),(83,"erat volutpat. Nulla","8.22"),(84,"magnis","5.37"),(85,"Mauris magna.","5.87"),(86,"sit amet nulla.","9.33"),(87,"est","5.41"),(88,"eu, accumsan sed,","4.04"),(89,"orci. Ut","8.50"),(90,"vulputate, risus a","6.34");
INSERT INTO `PRODUCT` (`PRODUCT_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`) VALUES (91,"sem eget massa.","4.74"),(92,"Donecz","1.58"),(93,"vel","3.25"),(94,"at,","0.77"),(95,"at, nisi. Cum","7.15"),(96,"lobortisas","7.36"),(97,"neque non quam.","9.59"),(98,"Donec tempor,","5.20"),(99,"porttitor scelerisque","7.80"),(100,"risus. Quisque","2.24");
