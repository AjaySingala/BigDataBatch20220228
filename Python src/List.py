# # list1 = [1,2,3,4,5]
# # print(list1)

# # list1 = ["John", 32, 145.78, ["Boston", "MA"], ("555-1234", "555-0987")]
# # print(list1)

# # print(list1[0])
# # print(list1[3])
# # print(list1[3][0])
# # print(list1[2:4])

# # list1[0] = "John Smith"
# # print(list1)

# # print(32 in list1)
# # print("John" in list1)
# # print(["Boston","MA"] in list1)
# # print("Boston" in list1[3])

# # list2 = ["Mary", 41, 167.98, ["Dallas", "TX"], ("555-5555", "555-6666")]
# # list3 =list1 + list2
# # print(list3)

# # t1 = ("555-5555", "555-6666")
# # t2 = ("555-1234", "555-0987")
# # t3 = t1 + t2
# # print(t3)

# # t1 = (1,2,3)
# # print(t1 * 3)

# # list1 = [1,2,3,]
# # print(list1  *3)

# # Operations that be performed on a list but not on a tuple:
# list = [1, 11, 5, 9, 3, 20, 8]
# print(list)
# list.append(7)
# print(list)

# list.insert(3, 22)
# print(list)

# list.sort()  # sorting in-place.
# print(list)

# list2 = ["a", "z", "c", "b", "t", "b"]
# print(list2.count("b"))
# list2.sort()
# print(list2)

# print(list2.index("t"))

# list2.remove("b")
# print(list2)

# line = "I " + "am " + "Mr. " + "Ajay " \
#     + "Singala " + "and " + \
#         "I " + "am " + "teaching " + "Python"
# print(line)

# # The pop operator.
# list = ["a", "b", "c", "d"]
# print(list)
# x = list.pop(2)
# print(x)
# print(list)

# # The del operator.
# list = ["a", "b", "c", "d", "e", "f", "g", "h"]
# print(list)
# #del list[2]
# del list[2:6]
# print(list)

# # Some mor elist functions:
# nums = [1,2,3,4,5,6]
# print(len(nums))        # length.
# print(max(nums))
# print(min(nums))
# print(sum(nums))
# print(sum(nums) / len(nums))        # average.

# # append values to a list.
# numbers = list()
# while(True):
#     num = int(input("Ener a number (0 to exit): "))
#     if(num == 0):
#         break;
#     numbers.append(num)

# for number in numbers:
#     print(number)

# Modifying multiple values in a list.
names = ["John", "Ajay", "Mary", "Joe", "Neo", "Trinity"]
print(names)
names[1:3] = ["Singala", "Jane"]
print(names)

names = ["John", "Ajay", "Mary", "Joe", "Neo", "Trinity"]
print(names)
names[1:4] = ["Singala"]
print(names)
