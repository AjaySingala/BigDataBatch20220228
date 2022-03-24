from pymongo import MongoClient

import pprint

client = MongoClient()								# connect to the server.
db = client.test									# returns an object pointing to the DB named "test".
collections = db.list_collection_names()            # Get a list of collections from the db.
for i in range(len(collections)):                   # iterate thru the list of collections.
    aCollection = collections[i]                    # extract the collection name.
    print(f"Colletion name: {aCollection}")
    collObject = db.get_collection(aCollection)     # return an object pointing to the collection.
    for doc in collObject.find({"size": 2}):        # find docs where "sie = 2", iterate thru the search results.
        # print(doc)                                  # print the doc.
        pprint.pprint(doc)                          # pretty print. You have to import pprint.

    # for doc in collObject.find({}):                 # iterate thru all docs in the collection.
    #     print(doc)                                  # print the doc.
