from pymongo import MongoClient 
    
# creation of MongoClient 
client=MongoClient() 
    
# Connect with the portnumber and host 
client = MongoClient("mongodb://localhost:27017/") 
    
# Access database 
mydatabase = client['test'] 

# Access collection of the database 
mycollection=mydatabase['writers'] 
# writer_profiles = [
#     {"_id":1, "user":"Ajay", "title":"Python", "comments":5},
#     {"_id":2, "user":"John",  "title":"JavaScript", "comments":15},
#     {"_id":3, "user":"Ajay",  "title":"C#", "comments":6},
#     {"_id":4, "user":"John",  "title":"MongoDB", "comments":2},
#     {"_id":5, "user":"Mary",  "title":"MongoDB", "comments":9}]
  
# mycollection.insert_many(writer_profiles)

# Group By Users.
# Just count the no. of docs for each user.
print("No. of Articles Grouped by Writer...")
agg_result= mycollection.aggregate(
    [{
    "$group" : 
        {"_id" : "$user", 
         "num_tutorial" : {"$sum" : 1}
         }}
    ])
for i in agg_result:
    print(i)

# Group By Title.
print("No. of Articles Grouped by Title...")
agg_result= mycollection.aggregate(
    [{
    "$group" : 
        {"_id" : "$title",  
         "total" : {"$sum" : 1}
         }}
    ])
for i in agg_result:
    print(i)
