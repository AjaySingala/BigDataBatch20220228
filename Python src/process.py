#print("This is the program to process data.")

def initiateProcess(data):
    print("Begin processing...")
    newData = data + " MODIFIED!!!"
    print("End  processing...")
    return newData

if __name__ == "__main__":
    print(initiateProcess("Some value."))
