# def divide(x,y):
#     z = x / y
#     return z

# try:
#     div = divide(10, 2)
#     div = divide(10, 3)
#     div = divide(10, 4)
#     div = divide(10, 0)
#     print(div)
# except:
#     print("Division error")

# while(True):
#     try:
#         age = int(input("What is your age? (Enter 0 to exit): "))
#         if(age == 0):
#             break

#         if(age >= 18):
#             print("your are an adult")
#         else:
#             print("You are a minor")
#     except:
#         print("Please enter a valid age")

# # raise an exception:
# num = int(input("Enter a number: "))
# if(num <= 0):
#     raise Exception("Value cannot be negative")

# # usage of "pass" keyword.
def divide(x,y):
    z = x / y
    return z

# try:
#     # div = divide(10, 2)
#     # print(div)
#     div = divide(10, 0)
#     print(div)
# except:
#     print("Division error")
#     #pass

# Handle specific exceptions.
try:
    with open('file.txt') as file:
        lines = file.read()
        print(lines)

    divide(10,2)

    #raise Exception("This is to test multiple except blocks...")
except FileNotFoundError as fnf_error:
    #print("File could not be read")
    print(fnf_error)
    # log error to file.
except ZeroDivisionError as dbz_error:
    print(dbz_error)
    # log error to file.
except:
    print("Something went wrong...")
else:
    print("This executes when there are NO EXCEPTIONS!!!")
finally:
    print("Is executed no matter what, whether there was an exception or not!")