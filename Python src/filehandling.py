# Read a file.
# file = open("file.txt")
# #file = open("file.txt", "r")

# # Read full file.
# #print(file.read())

# # read 1st 10 chars of the file.
# #print(file.read(10))

# # if(file.mode == "r"):
# #     contents = file.read()
# #     print(contents)

# Read lines into a list.
# lines = file.readlines()
# for line in lines:
#     print(line)

# print(lines)
# file.close()

# Write to file.
# fw = open("newfile.txt", "w")
# fw.write("This is a new file.\n")
# fw.write("This is line #1.\n")

# for i in range(10):
#     fw.write("This is line %d\n" % (i+1))

# fw.close()

# # Append mode.
# file = open("newfile.txt", "a")
# file.write("This line was 'appended'.\n")
# file.close()

# # Handle files using "with".
# # Closes the file immediately after the "with" block is executed.
# with open("file.txt") as file:
#     data = file.read()
#     print(data)

# Split lines.
# with open("file.txt") as file:
#     lines = file.readlines()
#     print("Extracting words...")
#     for line in lines:
#         word = line.split()
#         print(word)
#         #print(type(word))

with open("file.csv") as file:
    lines = file.readlines()
    print("Extracting words...")
    for line in lines:
        word = line.split(",")
        print(word)

