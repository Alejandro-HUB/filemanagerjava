# File Manager Java

This is a client server program which provides word count support and offers a multi-tiered application. 
This program supports multiple client connections through a multi-threaded design and includes a separate 
tier where all the persistent data is stored. The word count system has fixed-size cache implemented in it 
meaning that the file for which the contents were counted frequently will be stored in cache while those 
files whose words were least frequently counted are removed from the cache. 
