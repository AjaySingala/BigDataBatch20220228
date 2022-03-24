from pymongo import MongoClient

client = MongoClient()
# db = client.test
db = client["test"]
#collection = db.boyDiapers
collection = db["boyDiapers"]

# # Insert doc with auto-genreated _id.
# # doc = {"size": 3, "color": "blue", "brand": "prince" }
# # print(f"Inserting the following data...")
# # print(doc)
# # doc_id = collection.insert_one(doc).inserted_id
# # print(f"Document successfully inserted with id {doc_id}")

## Insert doc with _id value specified explicitly.
# doc = {"_id": 901, "size": 3, "color": "white", "brand": "prince" }
# print(f"Inserting the following data...")
# print(doc)
# doc_id = collection.insert_one(doc).inserted_id
# print(f"Document successfully inserted with id {doc_id}")

# Bulk Insert.
docs = [ {"size": 4, "color": "white", "brand": "prince" }, {"size": 4, "color": "blue", "brand": "prince" }]

print(f"Inserting the following data...")
print(docs)
result =collection.insert_many(docs)
print(result.inserted_ids)


