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
			ACCESS_KEY VARCHAR(1000) NOT NULL,
			EXPIRE_DATE DATE,
			PRIMARY KEY (ID)
		);
		ALTER TABLE ORDERS AUTO_INCREMENT=1;

		INSERT INTO `CATEGORY` (`CATEGORY_ID`, `CATEGORY_NAME`, `PARENT_ID`) VALUES (1, "First",null),(2,"Second",null),(3,"Third",null),(4,"Fourth",null),(5,"Fifth",null),(6,"Sixth",null),(7,"Seventh",null);

		INSERT INTO `PRODUCT` (`PRODUCT_ID`,`CATEGORY_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`, `PRODUCT_IMAGE_SMALL`) VALUES (1,1,"amet, dapibus","601", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5vEWVsuw16aE__uLDzb3bbpI8ty4Bu0k50S7BOiJTAWBFf-aE"),(2,1,"sem mollis dui,","186", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSFwVKfwYjEZf8H4mUpr8tZW_WGsuPzTi_kkGTT6UdfN04OHPINaw"),(3,1,"amet nulla. Donec","138", "http://vignette2.wikia.nocookie.net/p__/images/0/09/Joy_%28Inside_Out%29.png/revision/latest/scale-to-width-down/335?cb=20150405005253&path-prefix=protagonist"),(4,2,"egestas rhoncus. Proin","149", "http://static.splashnology.com/articles/How-to-Optimize-PNG-and-JPEG-without-Quality-Loss/cryoPNG-f0.png"),(5,2,"dui.","468", "http://pngimg.com/upload/heart_PNG705.png"),(6,3,"amet orci.","013", "http://orig07.deviantart.net/e81f/f/2012/309/a/1/tree_png_by_camelfobia-d5k1akp.png"),(7,2,"adipiscing non,","064", "http://www.printwallart.co.za/wp-content/uploads/2015/10/154908943.png"),(8,4,"blandit","601", "http://rs89.pbsrc.com/albums/k224/shukmun/Whatsapp%20Emoji/128526_zps0d043bc4.png~c200"),(9,4,"vulputate","009", "http://rs945.pbsrc.com/albums/ad300/xxx_enia/2d4e9f9cc112.png~c200"),(10,4,"hendrerit","529", "http://findicons.com/static/images/logo/logo.png");
		INSERT INTO `PRODUCT` (`PRODUCT_ID`,`CATEGORY_ID`,`PRODUCT_NAME`,`PRODUCT_PRICE`, `PRODUCT_IMAGE_SMALL`) VALUES (11,4,"malesuada id, erat.","106", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5vEWVsuw16aE__uLDzb3bbpI8ty4Bu0k50S7BOiJTAWBFf-aE"),(12,5,"tellus","749", "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSFwVKfwYjEZf8H4mUpr8tZW_WGsuPzTi_kkGTT6UdfN04OHPINaw"),(13,5,"eu tellus eu","092", "http://vignette2.wikia.nocookie.net/p__/images/0/09/Joy_%28Inside_Out%29.png/revision/latest/scale-to-width-down/335?cb=20150405005253&path-prefix=protagonist"),(14,6,"ac, feugiat non,","899", "http://static.splashnology.com/articles/How-to-Optimize-PNG-and-JPEG-without-Quality-Loss/cryoPNG-f0.png"),(15,6,"cursus, diam","386", "http://pngimg.com/upload/heart_PNG705.png"),(16,6,"quis diam. Pellentesque","384", "http://orig07.deviantart.net/e81f/f/2012/309/a/1/tree_png_by_camelfobia-d5k1akp.png"),(17,6,"Donec","464", "http://www.printwallart.co.za/wp-content/uploads/2015/10/154908943.png"),(18,1,"odio a purus.","799", "http://rs89.pbsrc.com/albums/k224/shukmun/Whatsapp%20Emoji/128526_zps0d043bc4.png~c200"),(19,3,"lectus sit","580", "http://rs945.pbsrc.com/albums/ad300/xxx_enia/2d4e9f9cc112.png~c200"),(20,5,"arcu et","499", "http://findicons.com/static/images/logo/logo.png");

		INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_TUSSEN`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (1,"Shafira","","Bradford","uFd7wyJ+YHHm0dPf5nAC21XKqBY=","31-05-15","Dortmund","280-2865 Parturient Rd.","47","60424","porttitor.eros.nec@egetipsumSuspendisse.net","(07) 1151 6718"),(2,"Shoshana","","Baxter","FzukCFuDHM6B/W+hGtDdaBV9B+A=","07-02-17","Bersillies-l'Abbaye","433-4566 Amet Avenue","63","21146","Duis.mi@Nam.co.uk","(01) 3410 2557"),(3,"Ethan","","Merritt","axy939zMN098VDmGoIu2vTaH7GA=","02-09-16","Port Blair","5413 At Road","18","9086","pharetra.sed.hendrerit@condimentumeget.co.uk","(05) 3750 0516"),(4,"Ira","","Nunez","T85o5ZqNcC6GOoOmX2L9OBpnctY=","09-02-17","Abolens","938-6171 Non Ave","61","7216","mollis.Duis@Donec.org","(02) 4910 1987"),(5,"Lydia","","Hewitt","gS1Q+qf3X3+4AMUwIfGLo1Hw3t4=","07-03-17","Gujranwala","Ap #465-5531 At, Rd.","19","28533","eleifend.nunc@Nullaeuneque.ca","(04) 7224 2945"),(6,"Noelani","","Pitts","kuKzRcl3BbaiphcumZc4agdgoEg=","05-09-15","Avise","P.O. Box 211, 6486 Egestas. Ave","22","6447BG","inceptos.hymenaeos.Mauris@luctus.org","(09) 4534 9916"),(7,"Alana","","Kelley","ZGRax47GAYFwngo4fUi0hQYjg30=","02-07-15","Laguna Blanca","6258 Egestas Ave","49","75449","pede@sapien.ca","(06) 0667 6033"),(8,"Emmanuel","","Aguilar","3tzNeM79VJAyFRYwdXmWw2c3twg=","14-05-16","Montaldo Bormida","221-1812 Orci. Avenue","78","98-268","ornare@miDuis.net","(03) 3148 8211"),(9,"Jakeem","","Hughes","+NZXDFYY+mdU7JtFUWXf6F41Ws4=","24-07-15","Lahore","2102 Aenean Rd.","19","96972","Mauris.ut.quam@rhoncusProinnisl.ca","(05) 4460 9213"),(10,"Harlan","","Reeves","OI/0ZdJ6Nxph+kzHYasBzgiCQ5s=","19-03-15","Slough","215-6960 Quam Rd.","7","21100","ut@dolorsitamet.co.uk","(08) 9614 1200");
		INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_TUSSEN`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (101,"Bob","","Marley","Ex9LkDl73ZPYWZYHKhtYMUtBjVY=","31-05-15","Portland","Some street","47","12345","bob@abc.com","(12) 345 678 90");

		UPDATE `CUSTOMER`	SET `EMAIL_ADDRESS`=LOWER(`EMAIL_ADDRESS`);
		INSERT INTO `CUSTOMER` (`CUSTOMER_ID`,`CUSTOMER_NAME`,`CUSTOMER_TUSSEN`,`CUSTOMER_SURNAME`,`PASSWORD`,`DATE_OF_BIRTH`,`CITY`,`STREET_NAME`,`HOUSE_NUMBER`,`AREA_CODE`,`EMAIL_ADDRESS`,`CELLPHONE_NUMBER`) VALUES (102,"Ivan","","Budnikov","T+a4U5LnRHKch9h6jC4iisN9fn8=","31-05-15","Kiev","Korolova","2A","03148","ivan_budnikov@ukr.net","(097) 796 4144");

