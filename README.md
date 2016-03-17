# Censor-News-Reader

!!!!!!! not works (Kimono shut down on February 29th, 2016 and the cloud service has been discontinued.) [link](https://www.kimonolabs.com/)

A news feed for [censor.net.ua](http://censor.net.ua/news/)    (json api) [api](https://www.kimonolabs.com/api/8b10y57e?apikey=Iuwy0Jm59LuqQiWRMf8dox6bFRKkcwA9)

There is two screens
1) a list of news (with pull-to-refresh)
2) detailed info news (webView)

Requests done with the help of library Retrofit

Callbacks take over Eventbus

Both screens implemented through a bunch Activity->Fragment

All news data ( including html page with detail news description ) 
save to SQLite database and may loading from there in offline mode.

![alt tag](https://raw.githubusercontent.com/roma-sck/Censor-News-Reader/master/app/src/main/res/drawable/censornews_screenshots.png)

