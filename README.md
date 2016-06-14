# rp16_gardening
Joint project with Rotterdam University of Applied Sciences - Gardening - 2016

Instruction on application manageability and customization:

Root directory for all settings is \IntratuinMobile\intratuin\src\main\res. If drawable-size is written, it mean it have to be changed in every directory (drawable-mdpi, drawable-hdpi, drawable-xhdpi, drawable-xxhdpi) for every image size.

Manageable options can be separated to three groups:

I. Graphical

  1)Logo(on login/registration pages, on the top): drawable/logo.png
   
  2)Background texture: drawable/pat21
   
  3)Icons of buttons on native bar: drawable-size/barcode-wide.png
                                                    drawable-size/nfc.png
                                                    
  4)Icons on fields of Login screen: drawable-size/ic_action_name.png
                                                      drawable-size/ic_action_name2.png
                                                      
  5)Twitter logo (should't be changed -- it's their brend logo): drawable-size/twitter_logo.png
                                                                                      drawable-size/twitter_logo_pressed.png
                                                                                      
  6)Logo (of .apk and installed application): mipmap-size/ic_launcher.png
    
II. Colors

   1)colorAccent -- text color of two small buttons on bottom of Login page
   
   2)textColor -- color of text on main buttons (Sign In, Sign Up,...)
   
   3)textColorHint -- color of hint text on fields
   
   4)twitter_pressed -- color of pressed Twitter button (their brend, shouldn't be changed)
   
   5)buttonGradientStartColor, buttonGradientEndColor, buttonStrokeColor -- colors of main buttons
   
   6)buttonPressedColor -- color of pressed main buttons
   
   7)buttonMinorGradientStartColor, buttonMinorGradientEndColor, buttonMinorStrokeColor -- color of minor buttons
   
   8)buttonMinorPressedColor-- color of pressed minor button
   
   9)facebookColor, facebookPressedColor -- facebook brend colors, shouldn't be changed
   
III. Text/Logical​​
   1)app_name -- name of installed application (.apk name is not important -- it can be simply renamed before distribution)
   
   2)company_id -- corresponding field in API
   
   3)company_website -- main page of website
   
   4)manageable settings: it has clear comments. But caching API screens and fingerprint login is not working anyway.
   
   5)Facebook: application id in facebook and text on "Sign in with FB" button
   
   6)Twitter: customer key and secret in twitter
   
(!!!)7)build_type: what server-side use. Options are: LOCAL(you have to launch server for it, and set host address), DEPLOYED (Bionic server), API(api.weorder.at), DEMOAPI(demo.weorder.at)

(!!!)8)mainscreen: WEB -- for webView(if build_type=DEPLOYED, it will be with dummy page, if API/DEMOAPI -- with get API screen). Other option is SEARCH -- for old native part.

   9)connection_timeout -- after what time of waiting of response we say that connection is lost.
   
   10)Bloks hosts, ports, addresses -- for URLs.
   
   11)Labels: labels of all buttons, fields, etc.
