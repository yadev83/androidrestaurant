# Android Restaurant

[![forthebadge](https://forthebadge.com/images/badges/0-percent-optimized.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/built-for-android.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/powered-by-coffee.svg)](https://forthebadge.com)

A project made to discover the android native application developement in the scope of a class in school.  

### Pre-requisite

To work with this project / source you will need to have Android Studio installed to run the project files.  

- Install [Android Studio](https://developer.android.com/studio)

### Installation/Compilation

To compile the app you can download the source code by cloning this repository and opening the project in a Android Studio setup. (Link is available below to install android studio)  
If you want to install the app on an android device, the compiled apk is available in the release folder or on the release page of the last stable version.  
Once downloaded, simply drag the file to your own device and install the apk as you would would normally with any other app an simply start it to browse the food and dishes...  

## Made with

* [Android Studio](https://developer.android.com/studio) - An IDE made for Android app development based on IntelliJ.  

## Features  

This app contains many features to see and try. Most of them were added as an experiment to discover Android development.  
* Main Activity containing a banner and 3 main menu items to choose from (Starters/Main/Desserts)  
* For each main menu item, there is a list with all the dishes that correspond to the category.  
* For each dish, a detailed activity is shown containing a list of ingredients and the ability to add them to the basket.  
* You can then access your basket and click the "Commander" button to order your food. This redirects you to the login page.  
* From the login page it is possible to login using email/password or to register an account.  
* Registered and logged users are stored in cache so you don't have to log in every time. You can obviously choose to logout.  

## Versions
**Last Stable Version :** [1.5.0-d](https://github.com/yadev83/androidrestaurant/releases/tag/v1.5.0-d)  
**Last Version :** [1.5.0-d](https://github.com/yadev83/androidrestaurant/releases/tag/v1.5.0-d)  

Full Versions list : [Click here to see](https://github.com/yadev83/androidrestaurant/tags)  

### Version Log  
**[1.5.0-d](https://github.com/yadev83/androidrestaurant/releases/tag/v1.5.0-d)**: Login/Logout/Register has been re-enabled and is now fully functionnal !  
**[1.4.0-d](https://github.com/yadev83/androidrestaurant/releases/tag/v1.4.0-d)**: Adds order checkout. This version initially contained login/logout/register mechanisms but it has been disabled for stability purposes.  
**[1.3.0-d](https://github.com/yadev83/androidrestaurant/releases/tag/v1.3.0-d)**: Adds carousel (one using a lib and custom one using view pager) and quantity texts and arithmetic   
**[1.2.2-d](https://github.com/yadev83/androidrestaurant/releases/tag/v1.2.2-d)**: Fixes all banner constraints errors that remained in 1.2.1d  
**[1.2.1-d](https://github.com/yadev83/androidrestaurant/releases/tag/v1.2.1-d)**: Fixes graphical issue on category activity  
**[1.2.0-d](https://github.com/yadev83/androidrestaurant/releases/tag/v1.2.0-d)**: Adds even more graphical updates to the app and a full set of activities to see all the dish list from categories to specific details (ingredients and prices). Now downloadable as an APK from release page as well.  
**[1.1.0-d](https://github.com/yadev83/androidrestaurant/releases/tag/v1.1.0-d)**: Adds some design stuff to the app. Dishes details access will be refactored soon though. This is why it isn't a major stable version.  
**[1.0.0-d](https://github.com/yadev83/androidrestaurant/releases/tag/v1.0.0-d)** : First stable version containing a main page and 3 categories with a list of dishes for each category. Dishes are grabbed on a distant API asynchronously.  

## Authors

* **Yanis ATTIA** _alias_ [@yadev83](https://github.com/yadev83)

## License

This project is under the ``GPL v3.0`` license - see the file [LICENSE.md](LICENSE.md) for more information  

