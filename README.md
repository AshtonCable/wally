Wallak - Build your own wallpaper app!
=====

Wallak is an open source android app wallpaper app. What makes this app special is the fact that you
can easily change the source of the images and provide your own wallpaper source.

Wallak is a fork of the open source app [Wally](https://github.com/Musenkishi/wally).
Its name is also a variation of the original app name. In the Arabic language adding
"ak" is like adding a possessive noun which makes Wallak mean "Your wall"!

In it's current state Wally gets its source of images from a Firebase backend which I have built. I
have gathered a nice collection of HQ photos from around my small country Lebanon.

Features
--------
* Collection of high quality photos from Lebanon
* Easily save, share or set photo as wallpaper
* Automatic Daily wallpaper change feature
* Fast and efficient loading of images

![](https://github.com/mahermalaeb/wally/blob/github_ready/assets/wallak_demo.gif)

Development
-----------
Wally is a gradle project built with Android Studio 0.8.x. To get started, import the project in
Android Studio by choosing the <code>build.gradle</code> located in the root folder.

However, you might notice that it won't build right away. This is because you have to provide it
with/generate your own release- and debug keystore. The debug keystore can be generated the same
way as a release keystore. Put the release- and debug keystore files in a directory of your choice
and reference them in a <code>local.properties</code> file in the root folder of this project.

Changing Image source
----------------------
To change the image source and make Wallak read your own source you need to edit 1 Class only which
is the "CustomDataProvider" following the below path
`wally\dataprovider\src\main\java\com\musenkishi\wally\dataprovider\CustomDataProvider.java`

Each function in this class includes comments on how it should be changed.

Architecture
------------
Wally is divided into multiple modules; Models, Data Provider, and the main module Wally (UI). This
architecture allows a project to more easily expand to other platforms (e.g. Android Wear
or Android Auto).

License
-------
Apache 2.0 where applicable. See LICENSE file for details.

Contributing
------------
Pull requests are welcome!

Thanks
------
Musenkishi for making the app open source. He did all the work. Wallak is just a small variation

Author
------
Freddie Lust-Hed - @musenkishi

Maher Malaeb
