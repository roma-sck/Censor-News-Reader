# Censor-News-Reader
	A news feed for http://censor.net.ua/    ( json api)

	There is two screens
	1) a list of news (with pull-to-refresh)
	2) detailed info news (webView)

	Requests done with the help of library Retrofit
	Callbacks take over Eventbus

	Both screens implemented through a bunch Activity->Fragment

	When internet is available, all news data ( including html page with detail news description ) 
	save to database and may loading from there in offline mode.

