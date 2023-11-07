# Mobile_app_assign_2  
If entries say "Error retrieving address" please close the emulated phone, wipe the data, turn it on, and turn on then turn off airplane mode before re-running the device  
Input file must contain 2 numbers seperated by a space, with the first number being latitude and second number being longitude. File must be called latlongval and must be placed in the "raw" folder.
A sample file has been provided.  
Adding notes is done through the FAB, which is present on the first page. Editing and deleting locations require the user to click on the location entry they want to edit or delete, and then follow
the instructions that are shown.  
Querying has been implemented, click the search icon and type the address you would like to find. Inputting latitude, longitude or an address that does not exist in the database will result in no results.  
This project has been made using Android Studio, in the language java. It is utilizing SQLiteDatabase, RecyclerView and CardViews. The latitude and longitude coordinates were extracted from https://www.kaggle.com/datasets/liewyousheng/geolocation/
under the Open Data Commons Open Database License (ODbL)