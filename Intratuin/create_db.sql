		DROP DATABASE IF EXISTS BIONIC;
		CREATE DATABASE BIONIC;
		USE BIONIC;

		CREATE TABLE CATEGORY(
			CATEGORY_ID INT NOT NULL AUTO_INCREMENT,
			CATEGORY_NAME VARCHAR(100) NOT NULL,
			PARENT_ID INT,
			PRIMARY KEY (CATEGORY_ID),
			FOREIGN KEY (PARENT_ID) REFERENCES CATEGORY(CATEGORY_ID)
		);

		CREATE TABLE RETAIL (
			RETAIL_ID INT NOT NULL AUTO_INCREMENT,
			EMPLOYEE_NAМE VARCHAR(100) NOT NULL,
			EMPLOYEE_SURNAМE VARCHAR(100) NOT NULL,
			DATE_OF_BIRTH DATE,
			DATE_OF_EMPLOYMENT DATE,
			PRIMARY KEY (RETAIL_ID)
		);
		ALTER TABLE RETAIL AUTO_INCREMENT=1;

		CREATE TABLE CUSTOMER (
			CUSTOMER_ID INT NOT NULL AUTO_INCREMENT,
			CUSTOMER_NAME VARCHAR(100),
			CUSTOMER_TUSSEN VARCHAR(10),
			CUSTOMER_SURNAME VARCHAR(100),
			PASSWORD VARCHAR(32),
			DATE_OF_BIRTH DATE,
			CITY VARCHAR(60),
			STREET_NAME VARCHAR(60),
			HOUSE_NUMBER VARCHAR(5),
			AREA_CODE VARCHAR(10),
			GENDER INT,
			EMAIL_ADDRESS VARCHAR(60) NOT NULL UNIQUE,
			CELLPHONE_NUMBER VARCHAR(20),
			FINGERPRINT MEDIUMBLOB,
			PRIMARY KEY (CUSTOMER_ID)
		);
		ALTER TABLE CUSTOMER AUTO_INCREMENT=1;

		CREATE TABLE RETAIL_CUSTOMER (
			RC_ID INT NOT NULL AUTO_INCREMENT,
			RETAIL_ID INT NOT NULL,
			CUSTOMER_ID INT NOT NULL,
			PRIMARY KEY (RC_ID),
			FOREIGN KEY (RETAIL_ID) REFERENCES RETAIL(RETAIL_ID),
			FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID)
		);

		CREATE TABLE PRODUCT (
			PRODUCT_ID INT NOT NULL AUTO_INCREMENT,
			CATEGORY_ID INT,
			BARCODE BIGINT,
			PRODUCT_NAME VARCHAR(100) NOT NULL,
			PRODUCT_PRICE VARCHAR(10) NOT NULL,
			PRODUCT_IMAGE_SMALL VARCHAR(1000),
			PRIMARY KEY (PRODUCT_ID),
			FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORY(CATEGORY_ID)
		);
		ALTER TABLE PRODUCT AUTO_INCREMENT=1;

		CREATE TABLE ORDER_RULE (
			ORDER_RULE_ID INT NOT NULL AUTO_INCREMENT,
			PRODUCT_ID INT NOT NULL,
			AMOUNT INT NOT NULL,
			TOTAL INT NOT NULL,
			PRIMARY KEY (ORDER_RULE_ID),
			FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCT(PRODUCT_ID)
		);
		ALTER TABLE ORDER_RULE AUTO_INCREMENT=1;

		CREATE TABLE ORDERS (
			ORDER_ID INT NOT NULL AUTO_INCREMENT,
			CUSTOMER_ID INT NOT NULL,
			ORDER_RULE_ID INT NOT NULL,
			ORDER_DATE DATE,
			PRIMARY KEY (ORDER_ID),
			FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMER(CUSTOMER_ID),
			FOREIGN KEY (ORDER_RULE_ID) REFERENCES ORDER_RULE(ORDER_RULE_ID)
		);
		ALTER TABLE ORDERS AUTO_INCREMENT=1;

		CREATE TABLE ACCESSKEY (
			ID INT NOT NULL AUTO_INCREMENT,
			CUSTOMER_ID INT NOT NULL,
			ACCESS_KEY VARCHAR(40) NOT NULL,
			REFRESH_ACCESS_KEY VARCHAR(40),
			EXPIRE_DATE TIMESTAMP,
			PRIMARY KEY (ID)
		);
		ALTER TABLE ACCESSKEY AUTO_INCREMENT=1;

		INSERT INTO `CATEGORY` (`CATEGORY_ID`, `CATEGORY_NAME`, `PARENT_ID`) VALUES (1, "First",null),(2,"Second",null),(3,"Third",null),(4,"Fourth",null),(5,"Fifth",null),(6,"Sixth",null),(7,"Seventh",null);

		INSERT INTO `PRODUCT` (`PRODUCT_ID`,`CATEGORY_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`, `PRODUCT_IMAGE_SMALL`) VALUES (1,1,"amet, dapibus","601", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5vEWVsuw16aE__uLDzb3bbpI8ty4Bu0k50S7BOiJTAWBFf-aE"),(2,1,"sem mollis dui,","186", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSFwVKfwYjEZf8H4mUpr8tZW_WGsuPzTi_kkGTT6UdfN04OHPINaw"),(3,1,"amet nulla. Donec","138", "http://vignette2.wikia.nocookie.net/p__/images/0/09/Joy_%28Inside_Out%29.png/revision/latest/scale-to-width-down/335?cb=20150405005253&path-prefix=protagonist"),(4,2,"egestas rhoncus. Proin","149", "http://static.splashnology.com/articles/How-to-Optimize-PNG-and-JPEG-without-Quality-Loss/cryoPNG-f0.png"),(5,2,"dui.","468", "http://pngimg.com/upload/heart_PNG705.png"),(6,3,"amet orci.","013", "http://orig07.deviantart.net/e81f/f/2012/309/a/1/tree_png_by_camelfobia-d5k1akp.png"),(7,2,"adipiscing non,","064", "http://www.printwallart.co.za/wp-content/uploads/2015/10/154908943.png"),(8,4,"blandit","601", "http://rs89.pbsrc.com/albums/k224/shukmun/Whatsapp%20Emoji/128526_zps0d043bc4.png~c200"),(9,4,"vulputate","009", "http://rs945.pbsrc.com/albums/ad300/xxx_enia/2d4e9f9cc112.png~c200"),(10,4,"hendrerit","529", "http://findicons.com/static/images/logo/logo.png");
		INSERT INTO `PRODUCT` (`PRODUCT_ID`,`CATEGORY_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`, `PRODUCT_IMAGE_SMALL`) VALUES (11,4,"malesuada id, erat.","106", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5vEWVsuw16aE__uLDzb3bbpI8ty4Bu0k50S7BOiJTAWBFf-aE"),(12,5,"tellus","749", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSFwVKfwYjEZf8H4mUpr8tZW_WGsuPzTi_kkGTT6UdfN04OHPINaw"),(13,5,"eu tellus eu","092", "http://vignette2.wikia.nocookie.net/p__/images/0/09/Joy_%28Inside_Out%29.png/revision/latest/scale-to-width-down/335?cb=20150405005253&path-prefix=protagonist"),(14,6,"ac, feugiat non,","899", "http://static.splashnology.com/articles/How-to-Optimize-PNG-and-JPEG-without-Quality-Loss/cryoPNG-f0.png"),(15,6,"cursus, diam","386", "http://pngimg.com/upload/heart_PNG705.png"),(16,6,"quis diam. Pellentesque","384", "http://orig07.deviantart.net/e81f/f/2012/309/a/1/tree_png_by_camelfobia-d5k1akp.png"),(17,6,"Donec","464", "http://www.printwallart.co.za/wp-content/uploads/2015/10/154908943.png"),(18,1,"odio a purus.","799", "http://rs89.pbsrc.com/albums/k224/shukmun/Whatsapp%20Emoji/128526_zps0d043bc4.png~c200"),(19,3,"lectus sit","580", "http://rs945.pbsrc.com/albums/ad300/xxx_enia/2d4e9f9cc112.png~c200"),(20,5,"arcu et","499", "http://findicons.com/static/images/logo/logo.png");

		INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_TUSSEN`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (1,"Shafira","","Bradford","Qq1671012431299","31-05-15","Dortmund","280-2865 Parturient Rd.","47","60424","porttitor.eros.nec@egetipsumSuspendisse.net","(07) 1151 6718"),(2,"Shoshana","","Baxter","Qq1611050473299","07-02-17","Bersillies-l'Abbaye","433-4566 Amet Avenue","63","21146","Duis.mi@Nam.co.uk","(01) 3410 2557"),(3,"Ethan","","Merritt","Qq1611101993599","02-09-16","Port Blair","5413 At Road","18","9086","pharetra.sed.hendrerit@condimentumeget.co.uk","(05) 3750 0516"),(4,"Ira","","Nunez","Qq1645012036499","09-02-17","Abolens","938-6171 Non Ave","61","7216","mollis.Duis@Donec.org","(02) 4910 1987"),(5,"Lydia","","Hewitt","Qq1632042702099","07-03-17","Gujranwala","Ap #465-5531 At, Rd.","19","28533","eleifend.nunc@Nullaeuneque.ca","(04) 7224 2945"),(6,"Noelani","","Pitts","Qq1618100168599","05-09-15","Avise","P.O. Box 211, 6486 Egestas. Ave","22","6447BG","inceptos.hymenaeos.Mauris@luctus.org","(09) 4534 9916"),(7,"Alana","","Kelley","Qq1661072893999","02-07-15","Laguna Blanca","6258 Egestas Ave","49","75449","pede@sapien.ca","(06) 0667 6033"),(8,"Emmanuel","","Aguilar","Qq1695050671599","14-05-16","Montaldo Bormida","221-1812 Orci. Avenue","78","98-268","ornare@miDuis.net","(03) 3148 8211"),(9,"Jakeem","","Hughes","Qq1661031108199","24-07-15","Lahore","2102 Aenean Rd.","19","96972","Mauris.ut.quam@rhoncusProinnisl.ca","(05) 4460 9213"),(10,"Harlan","","Reeves","Qq1682112947399","19-03-15","Slough","215-6960 Quam Rd.","7","21100","ut@dolorsitamet.co.uk","(08) 9614 1200");
		INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_TUSSEN`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (101,"Bob","","Marley","QWer12","31-05-15","Portland","Some street","47","12345","bob@abc.com","(12) 345 678 90");

		UPDATE `CUSTOMER`	SET `EMAIL_ADDRESS`=LOWER(`EMAIL_ADDRESS`);
		UPDATE `PRODUCT` SET `BARCODE`=`PRODUCT_ID`+123456789;

INSERT INTO `CATEGORY` (`CATEGORY_ID`, `CATEGORY_NAME`, `PARENT_ID`) VALUES (1, "kamerplanten",null),(2,"tuinplanten",null),(3,"binnen- en buitenpotten",null),(4,"tuin & terras",null),(5,"tuinmeubelen",null),(6,"barbecues",null),(7,"dieren",null);
INSERT INTO `CATEGORY` (`CATEGORY_ID`, `CATEGORY_NAME`, `PARENT_ID`) VALUES (8, "bloeiende kamerplanten",1),(9,"groene kamerplanten",1),(10,"benodigdheden",1),(11,"voeding en verzorging",1),(12,"kunstbloemen en -planten",1),(6,"barbecues",null),(7,"dieren",null);
INSERT INTO `CATEGORY` (`CATEGORY_ID`, `CATEGORY_NAME`, `PARENT_ID`) VALUES (1, "kamerplanten",null),(2,"tuinplanten",null),(3,"binnen- en buitenpotten",null),(4,"tuin & terras",null),(5,"tuinmeubelen",null),(6,"barbecues",null),(7,"dieren",null);

INSERT INTO `PRODUCT` (`PRODUCT_ID`,`CATEGORY_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`, `PRODUCT_IMAGE_SMALL`, `DESCRIPTION`) VALUES (1,1,"Monstera (Monstera 'Pertusum')","32.99", "https://www.intratuin.nl/website/var/tmp/image-thumbnails/0/6896/thumb__auto_68f04510cd25188b9b627969f519a49e/8717263115632.jpeg", "De gatenplant is een plant uit de aronskelkfamilie. Het is een kruipende klimplant, die van nature voorkomt in de tropische regenwouden van zuidelijk Mexico tot in Panama. De naam dankt de plant aan de speciale vorm van het blad. Het is een zeer sterke kamerplant, die ook prima op een wat donkerdere plek kan staan en niet al teveel water nodig heeft. Een 'makkie' qua verzorging voor een optimaal resultaat in je huiskamer!"),(2,1,"Vlinderorchidee (Phalaenopsis)","7.99", "https://www.intratuin.nl/website/var/tmp/image-thumbnails/0/6926/thumb__auto_68f04510cd25188b9b627969f519a49e/8717263130734-2-.jpeg", "De naam Phalaenopsis komt van het Griekse 'phalaina' wat 'mot' betekent, en 'opsis' wat 'gelijkend' betekent. De benaming beschrijft de bloeiwijze van sommige soorten, die op een vliegende mot of vlinder lijkt. De planten komen oorspronkelijk in Zuidoost-Azië voor. Typerende plaatsten waar ze in het wild gevonden worden zijn onder het bladerdak van de hoogste bomen in het regenwoud en vochtige laaglandbossen, beschermd tegen direct zonlicht. De bloemstengels bloeien over het algemeen 2 tot 3 maanden."),(3,1,"Curcuma (Curcuma ali. 'Siam Striking')","9.99", "https://www.intratuin.nl/website/var/tmp/image-thumbnails/0/9644/thumb__auto_623c169df4e22aaf09a2066153e97142/8717263121466.jpeg", "De Curcuma komt van origine uit Thailand en wordt ook wel de Siamese tulp genoemd. De Thaise vertaling voor Thailand is Siam. Het is dus niet zo verwonderlijk dat de Curcuma een exotische uitstraling heeft. Curcuma is een seizoensproduct, van april tot en met september zijn alle soorten beschikbaar, daarna wordt het te koud voor de plant. De Curcuma kan in de zomer ook op terras of balkon geplaatst worden."),(4,2,"Minipetunia (Calibrachoa)","2.49", "https://www.intratuin.nl/website/var/tmp/image-thumbnails/10000/11098/thumb__auto_68f04510cd25188b9b627969f519a49e/8717191278140.jpeg", "De Calibrachoa is het broertje van petunia. De bloemetjes zijn alleen kleiner en de Calibrachoa heeft minder verzorging nodig dan de gewone petunia. De uitgebloeide bloemen hoeven niet weggeplukt te worden want deze drogen vanzelf uit en vallen er dan vanaf. Wel even weghalen dus, maar dan van de grond in plaats van direct van de plant. Ook heeft de Calibrachoa minder water nodig dan de petunia. De hele zomer lang zorgt hij voor kleurenpracht in je tuin of op je balkon. Kortom: een makkelijke én prachtige plant!"),(5,2,"Hortensia (Hydrangea Double Flow","14.99", "https://www.intratuin.nl/website/var/tmp/image-thumbnails/10000/11075/thumb__auto_68f04510cd25188b9b627969f519a49e/8717263683674.jpeg", "De boerenhortensia is de meest bekende én populaire hortensia. Er zijn twee soorten: de schermhortensia’s en de bolhortensia’s. Bij schermhortensia’s bestaan de bloemschermen uit kleine bloemetjes in het midden en grotere aan de buitenkant. De grote bloemen zijn bestemd om bijen naar de kleine bloemetjes te lokken. Bolhortensia’s hebben alleen maar grote bloemen die samen een mooie bol vormen. Van nature zijn boerenhortensia’s er in wit, roze en blauw maar tegenwoordig zijn ze er ook in het rood, paars en zelfs dubbelbloemige soorten zoals deze beauty. "),(6,2,"Handappel (Malus domestica ‘Rode...","14.95", "https://www.intratuin.nl/website/var/tmp/image-thumbnails/10000/10078/thumb__auto_68f04510cd25188b9b627969f519a49e/8717263019244.jpeg", "Een lekkere appel, die nog lekkerder wordt als je hem aan je eigen boom rijp laat worden. Bij alle rijpe appels zijn de pitten trouwens geheel bruin geworden, daaraan kun je dus herkennen of ze al eetbaar zijn. Geef hem wel een bestuiver."),(7,2,"adipiscing non,","064", "http://www.printwallart.co.za/wp-content/uploads/2015/10/154908943.png"),(8,2,"Drijfplanten in 6-pak","6.99", "https://www.intratuin.nl/website/var/tmp/image-thumbnails/10000/10719/thumb__auto_68f04510cd25188b9b627969f519a49e/8718226850553.jpeg", "Vijverplanten brengen leven in je vijver en zijn daarnaast ook nog eens goed voor helder schoon water. Het is belangrijk om te weten dat verschillende soorten vijverplanten op verschillende diepten geplant dienen te worden. Zorg desnoods voor de juiste diepte door verhogingen te plaatsen met bijvoorbeeld bakstenen of verzwaarde kratten. De drijfplanten uit deze sixpack zijn oever/moerasplanten van ongeveer 10 cm hoog. Hun blad moet dus boven het wateroppervlak uitkomen. "),(9,8,"Lidcactus (Schlumbergera)","5.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8717263114161.jpeg", "Schlumbergera truncatus is een cactus uit het geslacht Schlumbergera behorende tot de Cactusfamilie. De planten groeien als epifyt op bomen in regenwouden. Het is een bekende kamerplant, die vooral in de winter aangeboden wordt. De plant is makkelijk te vermeerderen, omdat de stengel uit afgeplatte, losse leden bestaat, die makkelijk afbreken."),(10,8,"Lidcactus (Schlumbergera)","5.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8717263114192.jpg", "Schlumbergera truncatus is een cactus uit het geslacht Schlumbergera behorende tot de Cactusfamilie. De planten groeien als epifyt op bomen in regenwouden. Het is een bekende kamerplant, die vooral in de winter aangeboden wordt. De plant is makkelijk te vermeerderen, omdat de stengel uit afgeplatte, losse leden bestaat, die makkelijk afbreken.");
		INSERT INTO `PRODUCT` (`PRODUCT_ID`,`CATEGORY_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`, `PRODUCT_IMAGE_SMALL`, `DESCRIPTION`) VALUES (11,8,"Curcuma (Curcuma ali. 'Siam Striking')","9.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8717263121466.jpeg", "Schlumbergera truncatus is een cactus uit het geslacht Schlumbergera behorende tot de Cactusfamilie. De planten groeien als epifyt op bomen in regenwouden. Het is een bekende kamerplant, die vooral in de winter aangeboden wordt. De plant is makkelijk te vermeerderen, omdat de stengel uit afgeplatte, losse leden bestaat, die makkelijk afbreken."),(12,8,"Lidcactus (Schlumbergera)","2.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8717263114185.jpg", "De Curcuma komt van origine uit Thailand en wordt ook wel de Siamese tulp genoemd. De Thaise vertaling voor Thailand is Siam. Het is dus niet zo verwonderlijk dat de Curcuma een exotische uitstraling heeft. Curcuma is een seizoensproduct, van april tot en met september zijn alle soorten beschikbaar, daarna wordt het te koud voor de plant. De Curcuma kan in de zomer ook op terras of balkon geplaatst worden."),(13,8,"Mikado (Syngonanthus chry. 'Mikado')","3.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8717263115434.jpeg", "Je zult blij zijn met je Mikado. Het is een nieuw, modieus en grappig plantje dat zich zal bewijzen als een zon in je huiskamer. Na een tijdje gaan de gouden knopjes open en zullen ze bloeien in kleine bloemetjes die op hun steeltjes lijken te zweven. Droge steeltjes trek je heel makkelijk uit het plantje, waarmee weer ruimte ontstaat voor nieuwe steeltjes en bijbehorende bloemen."),(14,9,"Ficus (Ficus el. 'Robusta')","22.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8717263109860.jpg", "De Latijnse naam Ficus betekent vijg. De Ficus komt van oorsprong uit Zuidoost-Azië en Australië. Als kamerplant kent de Ficus familie vele telgen, die veelal van elkaar verschillen in bladvorm en kleur. Daar bereiken deze planten soms wel hoogtes van 15 meter! Al sinds 1860 wordt de Ficus al als sierplant gebruikt. Met meer dan 2.000 soorten, lijken sommige Ficussen nauwelijks meer op elkaar. De plant is niet bestand tegen kou en moet tegen tocht worden beschermd. Indien u hier rekening mee houdt zult u nog jarenlang plezier beleven aan uw aankoop."),(15,9,"Venushaar (Adiantum rad. 'Fragrans')","3.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8717191247856.jpeg", "De Adiantum maakt deel uit van de Varen familie en komt wereldwijd voor in gematigde en subtropische klimaten. De plant houdt van lichte schaduw en hoge luchtvochtigheid. Al in de oudheid was Adiantum bekend als medicijn. Vanwege de gelijkenis van de bladsteel met donker, glanzend haar werd het o.a.gebruikt ter bevordering van haargroei."),(16,9,"Kangoeroepoot (Microsorum diversifolium)","5.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8717263123057.jpeg", "De Microsorum komt van oorsprong uit Australië. Het is een pakkende en buitengewoon gebruikersvriendelijke varen. De plant kan thuis of op kantoor gebruikt worden als staande of als hangplant en heeft weinig water en licht nodig. De donkergroene bladeren zijn diep ingesneden en sterk glanzend."),(17,10,"Intratuin orchideeënvoeding 500ml","5.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8717263337379.jpg", "Intratuin orchideeënvoeding is geschikt voor alle soorten orchideeën. Zorgt voor langdurige, uitbundige bloei. Intratuin orchideeënvoeding is een 100% organische, vloeibare meststof. Met Intratuin orchideeënvoeding heeft u beslist meer plezier van uw orchideeën. Inhoud 500ml."),(18,11,"ECOstyle Vital 500ml","9.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8711731520201.jpg", "Door de plant regelmatig te behandelen met ECOstyle Vital wordt de weerstand van de plant verhoogd. Vital moet preventief worden ingezet bij kwetsbare planten als rozen voor een zorgenloze, prachtige en langdurig bloei. Het kan toegepast worden op kamerplanten, groenten, fruit- en siergewassen, zowel binnen als buiten. Inhoud 500ml."),(19,11,"Pokon Terras en Balkon ","2.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8711969004498.png", "Pokon terras- en balkon voedingskegels, langwerkende hoogwaardige voedingskegels voor terras- en balkonplanten. 1x voeden, 6 maanden voeding! Inhoud 10 stuks."),(20,11,"Pokon terras en balkon ","8.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8711969004511.png", "Pokon terras- en balkon voedingskegels, langwerkende hoogwaardige voedingskegels voor terras- en balkonplanten. 1x voeden, 6 maanden voeding! Inhoud 40 stuks.");
INSERT INTO `PRODUCT` (`PRODUCT_ID`,`CATEGORY_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`, `PRODUCT_IMAGE_SMALL`, `DESCRIPTION`) VALUES (21,11,"Pokon Bio groenten & kruiden voedingskegels 12st","2.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8711969013827.jpg", "Pokon Bio groenten & kruiden voedingskegels, handige Bio voedingskegels die voeding afgeeft gedurende 3 maanden, erg handig voor groenten en kruiden in pot. Inhoud 12 stuks."),(22,11,"Pokon langwerkende voedingskorrels 750gr","9.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8711969007215.png", "Pokon langwerkende voedingskorrels, handige voedingskorrels voor terras- en balkonplanten. 1x voeden, 6 maanden voeding! Langwerkende hoogwaardige voedingskorrels voor terras- en balkonplanten. Inhoud 750 gram."),(23,11,"Pokon langwerkende voedingskorrels 1650gr","12.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8712171016705.png", "Bayer rozen twist spray, tegen meeldauw, sterroetdauw, roest en bladvlekkenziekte op rozen. Inhoud 1 liter."),(24,11,"Intratuin kamerplantenvoeding 1000ml","6.49", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8717263337362.jpg", "Intratuin plantenvoeding is geschikt voor binnen- en buitenplanten. Voor groene en bloeiende planten. Intratuin plantenvoeding is een 100% organische, vloeibare meststof. Met Intratuin plantenvoeding heeft u beslist meer plezier van uw planten. Inhoud 1000ml."),(25,11,"Pokon kamerplanten langwerkende","2.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8711969004412.png", "Pokon kamerplanten langwerkende voedingskegels zijn handige voedingskegels voor uw kamerplanten. Met 1x voeden, heeft uw maar liefst 6 maanden voeding! Langwerkende hoogwaardige voedingskegels voor kamerplanten. Inhoud 10 stuks."),(26,12,"Jasaco gras in pot 55cm","17.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070289326.jpg", "Een natuurgetrouwe gras in pot 55cm hoog van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(27,12,"Jasaco rozentak Crèmegroen","3.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070140993.jpg", "Een natuurgetrouwe rozentak in de kleur crème groen van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(28,12,"Jasaco rozenblaadjestak groen","2.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070133230.jpg", "Een natuurgetrouwe rozenblaadjestak in de kleur groen van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(29,12,"Jasaco hederastruik x7 groen","7.29", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070133025.jpg", "Een natuurgetrouwe hederastruik in de kleur groen van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(30,12,"Jasaco plant x2","5.29", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070119838.jpg", "Een natuurgetrouwe plant van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!");
		INSERT INTO `PRODUCT` (`PRODUCT_ID`,`CATEGORY_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`, `PRODUCT_IMAGE_SMALL`, `DESCRIPTION`) VALUES (31,12,"Jasaco schermbloem bruin","5.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070084167.jpg", "Een natuurgetrouwe schermbloem in de kleur bruin van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(32,12,"Jasaco grote tak met blad","9.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070082033.jpg", "Een natuurgetrouwe grote tak met blad van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(33,12,"Jasaco asparagus tak","13.49", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070068273.jpg", "Een natuurgetrouwe asparagus tak van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(34,12,"Jasaco gras met pluim","2.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070045786.jpg", "Een natuurgetrouwe gras met pluim van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(35,12,"Jasaco aspargus tak","3.79", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070039594.jpg", "Een natuurgetrouwe aspargus tak van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(37,12,"Jasaco lathyrus tak lavendel","5.29", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070032786.jpg", "Een natuurgetrouwe lathyrus tak in de kleur paars van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(38,12,"Jasaco lathyrus tak Crème","5.29", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/5/4/5415070032755.jpg", "Een natuurgetrouwe lathyrus tak in de kleur crème van Jasaco. Langer genieten van groen en kleur in je interieur? Kies dan voor een kunstbloem. Combineer stoere takken met vrolijke bloemen, voor ieder wat wils!"),(39,13,"Pokon Terras en Balkon ","2.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8711969004498.png", "Pokon terras- en balkon voedingskegels, langwerkende hoogwaardige voedingskegels voor terras- en balkonplanten. 1x voeden, 6 maanden voeding! Inhoud 10 stuks."),(20,11,"Pokon terras en balkon ","8.99", "https://www.intratuin.nl/media/catalog/product/cache/1/small_image/262x270/9df78eab33525d08d6e5fb8d27136e95/8/7/8711969004511.png", "Pokon terras- en balkon voedingskegels, langwerkende hoogwaardige voedingskegels voor terras- en balkonplanten. 1x voeden, 6 maanden voeding! Inhoud 40 stuks.");