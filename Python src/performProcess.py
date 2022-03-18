from process import initiateProcess

def menu():
    print("1. Load")
    print("2. Show")
    print("0. Exit")
    
    option = int(input("Select menu option: "))
    return option


def main():
    # data = "This is the raw data."
    # print(data)
    # updatedData = initiateProcess(data)
    # print(updatedData)

    while(True):
        optionSelected = menu()
        if(optionSelected == 0):
            break

        if(optionSelected == 1):
            print("You selected option 1 to load data...")
        elif(optionSelected == 2):
            print("You selected option 2 to show data...")
        else:
            print("invalid option. Try again...")

if __name__ == "__main__":
    main()
