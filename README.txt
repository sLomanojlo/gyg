GYG APP


1. Overview
2. Architecture
3. Possible improvements and TODOs.
4. Documentation
5. Notes




1. OVERVIEW

   GYG uses the MVVM architecture and various JetPack libraries, including LiveData, DataBinding,
   Viewmodel, Paging, Navigation + RxJava. The purpose of the app is to fetch data from remote
   server and display it to the user & provide the option to see more details of a specific review
   in a separate Fragment.



2. ARCHITECTURE

   The  separation of concerns is done using the MVVM architecture. UI layer was further split into
   two sublayers: a parent Activity and two child fragments - each owning its ViewModel. The
   transition between fragments is implemented using Navigation library. The core of the app lays in
   the ViewModel classes, which handle the controller part of the architecture. The models are
   grouped and used mainly for fetching / populating data classes.



3. POSSIBLE IMPROVEMENTS & TODOS

   In case of adding a Room library for persistent storage of the fetched reviews, the separation
   of concerns should be more thoroughly implemented with a proper Repository class. For the
   moment, taking into account the focus of the app, it was left aside. Also, more tests should
   be added to cover various classes and cases.


4. DOCUMENTATION

   The documentation has been generated using Dokka library and can be found in the root/javadoc
   folder.


5. NOTES

   a) The app was built in seven days during the first week of May
   b) The app has (hopefully) been anonymized
   c) GYG has been tested on Samsung A10 Android Pie and Samsung S8 Android 8 Oreo
   d) Developed in Android Studio 3.6.3
   e) Can be used both in portrait or landscape mode
   f) The app doesn't cover edge cases and implements defensive programming principles against the
      remote API - considered to be working in a similar fashion to the one observed during the
      coding of this app (+ no documentation provided)
